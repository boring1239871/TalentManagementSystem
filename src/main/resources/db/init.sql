-- =============================================
-- 人力资源管理系统数据库初始化脚本 - 优化版
-- 版本: 5.0
-- 描述: 完整优化的数据库结构和初始化数据
-- =============================================

-- 创建数据库
DROP DATABASE IF EXISTS hr_management_db;
CREATE DATABASE hr_management_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE hr_management_db;

-- =============================================
-- 1. 系统基础表
-- =============================================

-- 1.1 用户表
CREATE TABLE system_user (
    user_id INT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    real_name VARCHAR(50) NOT NULL,
    associated_emp_id VARCHAR(20) UNIQUE,
    email VARCHAR(100),
    phone VARCHAR(20),
    role ENUM('hr_specialist', 'hr_manager', 'salary_specialist', 'salary_manager', 'admin', 'finance') NOT NULL,
    status ENUM('active', 'inactive', 'locked') DEFAULT 'active',
    last_login_time DATETIME,
    created_by INT,
    created_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (created_by) REFERENCES system_user(user_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 1.2 机构表 - 优化：严格三级机构约束
CREATE TABLE organization (
    org_id INT PRIMARY KEY AUTO_INCREMENT,
    org_code VARCHAR(50) NOT NULL UNIQUE,
    org_name VARCHAR(100) NOT NULL,
    parent_org_id INT NULL,
    org_path VARCHAR(500) NOT NULL,
    level_code CHAR(2) NOT NULL,
    org_level TINYINT NOT NULL CHECK (org_level BETWEEN 1 AND 3),
    full_code VARCHAR(50) NOT NULL,
    status ENUM('active', 'inactive') DEFAULT 'active',
    created_by INT NOT NULL,
    created_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (created_by) REFERENCES system_user(user_id),
    FOREIGN KEY (parent_org_id) REFERENCES organization(org_id),
    CONSTRAINT chk_level_code_format CHECK (level_code REGEXP '^[0-9]{2}$'),
    CONSTRAINT chk_org_hierarchy CHECK (
        (org_level = 1 AND parent_org_id IS NULL) OR 
        (org_level > 1 AND parent_org_id IS NOT NULL)
    )
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 1.3 职称表
CREATE TABLE professional_title (
    title_id INT PRIMARY KEY AUTO_INCREMENT,
    title_code VARCHAR(20) NOT NULL UNIQUE,
    title_name VARCHAR(50) NOT NULL,
    title_level ENUM('junior', 'intermediate', 'senior', 'expert') NOT NULL,
    description TEXT,
    display_order INT DEFAULT 0,
    status ENUM('active', 'inactive') DEFAULT 'active',
    created_by INT NOT NULL,
    created_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (created_by) REFERENCES system_user(user_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 1.4 职位表
CREATE TABLE `position` (
    position_id INT PRIMARY KEY AUTO_INCREMENT,
    position_code VARCHAR(20) NOT NULL UNIQUE,
    position_name VARCHAR(100) NOT NULL,
    position_desc TEXT,
    org_id INT NOT NULL,
    position_category ENUM('technical', 'market', 'functional', 'management') NOT NULL,
    position_grade VARCHAR(20),
    status ENUM('active', 'inactive') DEFAULT 'active',
    created_by INT NOT NULL,
    created_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (org_id) REFERENCES organization(org_id),
    FOREIGN KEY (created_by) REFERENCES system_user(user_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- =============================================
-- 2. 权限安全控制表
-- =============================================

-- 2.1 操作权限规则表
CREATE TABLE security_rule (
    rule_id INT PRIMARY KEY AUTO_INCREMENT,
    rule_name VARCHAR(100) NOT NULL,
    rule_type ENUM('self_operation', 'department_scope', 'hierarchy_control') NOT NULL,
    rule_condition VARCHAR(500) NOT NULL,
    error_message VARCHAR(200) NOT NULL,
    status ENUM('active', 'inactive') DEFAULT 'active',
    created_by INT NOT NULL,
    created_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (created_by) REFERENCES system_user(user_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 2.2 安全审计日志表
CREATE TABLE security_audit_log (
    audit_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id INT NOT NULL,
    target_emp_id VARCHAR(20),
    operation_type VARCHAR(50) NOT NULL,
    table_name VARCHAR(50) NOT NULL,
    record_id VARCHAR(50),
    old_values JSON,
    new_values JSON,
    ip_address VARCHAR(45),
    user_agent VARCHAR(500),
    is_self_operation BOOLEAN DEFAULT FALSE,
    security_check_passed BOOLEAN DEFAULT TRUE,
    check_failed_reason VARCHAR(500),
    operation_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES system_user(user_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- =============================================
-- 3. 薪酬管理表 - 优化：增强公式计算能力
-- =============================================

-- 3.1 薪酬项目表
CREATE TABLE salary_item (
    item_id INT PRIMARY KEY AUTO_INCREMENT,
    item_code VARCHAR(20) NOT NULL UNIQUE,
    item_name VARCHAR(100) NOT NULL,
    item_type ENUM('income', 'deduction') NOT NULL,
    calculation_type ENUM('fixed', 'formula', 'auto_calculation') DEFAULT 'fixed',
    formula_expression VARCHAR(500),
    formula_base_field VARCHAR(50),
    formula_multiplier DECIMAL(10,4),
    formula_constant DECIMAL(10,2),
    is_system_item BOOLEAN DEFAULT FALSE,
    display_order INT DEFAULT 0,
    status ENUM('active', 'inactive') DEFAULT 'active',
    created_by INT NOT NULL,
    created_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (created_by) REFERENCES system_user(user_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 3.2 薪酬标准表
CREATE TABLE salary_standard (
    std_id INT PRIMARY KEY AUTO_INCREMENT,
    std_code VARCHAR(20) NOT NULL UNIQUE,
    std_name VARCHAR(100) NOT NULL,
    creator VARCHAR(50) NOT NULL,
    created_by INT NOT NULL,
    created_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    applicable_position_id INT NOT NULL,
    status ENUM('draft', 'pending_review', 'approved', 'rejected', 'inactive') DEFAULT 'draft',
    reviewed_by INT,
    reviewed_time DATETIME,
    review_comment TEXT,
    salary_items JSON NOT NULL,
    base_salary DECIMAL(12,2) DEFAULT 0.00,
    total_amount DECIMAL(12,2) DEFAULT 0.00,
    updated_by INT,
    updated_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (created_by) REFERENCES system_user(user_id),
    FOREIGN KEY (reviewed_by) REFERENCES system_user(user_id),
    FOREIGN KEY (updated_by) REFERENCES system_user(user_id),
    FOREIGN KEY (applicable_position_id) REFERENCES `position`(position_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- =============================================
-- 4. 关系映射表
-- =============================================

-- 4.1 职位职称映射表
CREATE TABLE position_title_mapping (
    mapping_id INT PRIMARY KEY AUTO_INCREMENT,
    position_id INT NOT NULL,
    title_id INT NOT NULL,
    is_required BOOLEAN DEFAULT FALSE,
    display_order INT DEFAULT 0,
    status ENUM('active', 'inactive') DEFAULT 'active',
    created_by INT NOT NULL,
    created_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    UNIQUE KEY uk_position_title (position_id, title_id),
    FOREIGN KEY (position_id) REFERENCES `position`(position_id),
    FOREIGN KEY (title_id) REFERENCES professional_title(title_id),
    FOREIGN KEY (created_by) REFERENCES system_user(user_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 4.2 职位职称薪酬标准映射表
CREATE TABLE position_title_salary_std (
    map_id INT PRIMARY KEY AUTO_INCREMENT,
    position_id INT NOT NULL,
    title_id INT NOT NULL,
    salary_standard_id INT NOT NULL,
    effective_date DATE NOT NULL,
    expiration_date DATE,
    is_current BOOLEAN DEFAULT TRUE,
    status ENUM('active', 'inactive', 'historical') DEFAULT 'active',
    created_by INT NOT NULL,
    created_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    UNIQUE KEY uk_position_title_effective (position_id, title_id, effective_date),
    FOREIGN KEY (position_id) REFERENCES `position`(position_id),
    FOREIGN KEY (title_id) REFERENCES professional_title(title_id),
    FOREIGN KEY (salary_standard_id) REFERENCES salary_standard(std_id),
    FOREIGN KEY (created_by) REFERENCES system_user(user_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- =============================================
-- 5. 人力资源档案表 - 优化：完整档案信息和删除管理
-- =============================================

-- 5.1 员工档案表
CREATE TABLE employee (
    emp_id VARCHAR(20) PRIMARY KEY,
    employee_no VARCHAR(20) UNIQUE,
    name VARCHAR(50) NOT NULL,
    gender ENUM('male', 'female') NOT NULL,
    id_card VARCHAR(18) NOT NULL UNIQUE,
    email VARCHAR(100),
    phone VARCHAR(20),
    mobile VARCHAR(20),
    qq VARCHAR(20),
    address VARCHAR(200),
    postal_code VARCHAR(10),
    nationality VARCHAR(50),
    birthplace VARCHAR(100),
    birth_date DATE,
    ethnicity VARCHAR(50),
    religion VARCHAR(50),
    political_status VARCHAR(50),
    education ENUM('high_school', 'associate', 'bachelor', 'master', 'doctor') NOT NULL,
    hobbies TEXT,
    resume_history TEXT,
    family_relations TEXT,
    remarks TEXT,
    photo_path VARCHAR(500),
    
    org_id INT NOT NULL,
    position_id INT NOT NULL,
    title_id INT NOT NULL,
    salary_standard_id INT,
    
    is_sensitive_record BOOLEAN DEFAULT FALSE,
    require_double_approval BOOLEAN DEFAULT FALSE,
    status ENUM('pending_review', 'active', 'deleted') DEFAULT 'pending_review',
    
    created_by INT NOT NULL,
    created_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    reviewed_by INT,
    reviewed_time DATETIME,
    deleted_by INT,
    deleted_time DATETIME,
    
    FOREIGN KEY (org_id) REFERENCES organization(org_id),
    FOREIGN KEY (position_id) REFERENCES `position`(position_id),
    FOREIGN KEY (title_id) REFERENCES professional_title(title_id),
    FOREIGN KEY (salary_standard_id) REFERENCES salary_standard(std_id),
    FOREIGN KEY (created_by) REFERENCES system_user(user_id),
    FOREIGN KEY (reviewed_by) REFERENCES system_user(user_id),
    FOREIGN KEY (deleted_by) REFERENCES system_user(user_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 5.2 员工变更记录表
CREATE TABLE employee_change_log (
    log_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    emp_id VARCHAR(20) NOT NULL,
    change_type ENUM('create', 'update', 'delete', 'restore', 'review') NOT NULL,
    changed_field VARCHAR(50),
    old_value TEXT,
    new_value TEXT,
    change_reason VARCHAR(500),
    changed_by INT NOT NULL,
    changed_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (emp_id) REFERENCES employee(emp_id),
    FOREIGN KEY (changed_by) REFERENCES system_user(user_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 5.3 员工薪酬历史表
CREATE TABLE employee_salary_history (
    history_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    emp_id VARCHAR(20) NOT NULL,
    salary_standard_id INT NOT NULL,
    salary_items JSON NOT NULL,
    total_amount DECIMAL(12,2) NOT NULL,
    effective_date DATE NOT NULL,
    expiration_date DATE,
    change_reason VARCHAR(500),
    changed_by INT NOT NULL,
    changed_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (emp_id) REFERENCES employee(emp_id),
    FOREIGN KEY (salary_standard_id) REFERENCES salary_standard(std_id),
    FOREIGN KEY (changed_by) REFERENCES system_user(user_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- =============================================
-- 6. 薪酬发放表 - 优化：完整发放流程状态
-- =============================================

-- 6.1 薪酬发放主表
CREATE TABLE salary_issue (
    issue_id INT PRIMARY KEY AUTO_INCREMENT,
    issue_code VARCHAR(30) NOT NULL UNIQUE,
    issue_year SMALLINT NOT NULL,
    issue_month TINYINT NOT NULL,
    issue_date DATE NOT NULL,
    org_id INT NOT NULL,
    org_level TINYINT NOT NULL,
    total_base_amount DECIMAL(12,2) DEFAULT 0.00,
    total_bonus_amount DECIMAL(12,2) DEFAULT 0.00,
    total_deduction_amount DECIMAL(12,2) DEFAULT 0.00,
    total_amount DECIMAL(12,2) DEFAULT 0.00,
    total_people INT DEFAULT 0,
    contains_operator_data BOOLEAN DEFAULT FALSE,
    requires_special_approval BOOLEAN DEFAULT FALSE,
    status ENUM('draft', 'pending_review', 'reviewed', 'approved', 'paid', 'rejected', 'cancelled') DEFAULT 'draft',
    created_by INT NOT NULL,
    created_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    reviewed_by INT,
    reviewed_time DATETIME,
    review_comment TEXT,
    approved_by INT,
    approved_time DATETIME,
    paid_time DATETIME,
    payment_ref VARCHAR(100),
    updated_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (org_id) REFERENCES organization(org_id),
    FOREIGN KEY (created_by) REFERENCES system_user(user_id),
    FOREIGN KEY (reviewed_by) REFERENCES system_user(user_id),
    FOREIGN KEY (approved_by) REFERENCES system_user(user_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 6.2 薪酬发放明细表
CREATE TABLE salary_issue_detail (
    detail_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    issue_id INT NOT NULL,
    emp_id VARCHAR(20) NOT NULL,
    salary_standard_id INT NOT NULL,
    standard_items JSON NOT NULL,
    base_salary_amount DECIMAL(10,2) DEFAULT 0.00,
    bonus_amount DECIMAL(10,2) DEFAULT 0.00,
    deduction_amount DECIMAL(10,2) DEFAULT 0.00,
    adjustment_reason VARCHAR(500),
    subtotal_amount DECIMAL(10,2) DEFAULT 0.00,
    total_amount DECIMAL(10,2) DEFAULT 0.00,
    status ENUM('active', 'adjusted', 'cancelled') DEFAULT 'active',
    created_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    UNIQUE KEY uk_issue_emp (issue_id, emp_id),
    FOREIGN KEY (issue_id) REFERENCES salary_issue(issue_id),
    FOREIGN KEY (emp_id) REFERENCES employee(emp_id),
    FOREIGN KEY (salary_standard_id) REFERENCES salary_standard(std_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 6.3 薪酬发放调整记录表
CREATE TABLE salary_issue_adjustment (
    adjustment_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    detail_id BIGINT NOT NULL,
    adjust_type ENUM('bonus', 'deduction') NOT NULL,
    old_amount DECIMAL(10,2) NOT NULL,
    new_amount DECIMAL(10,2) NOT NULL,
    adjust_reason VARCHAR(500),
    adjusted_by INT NOT NULL,
    adjusted_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (detail_id) REFERENCES salary_issue_detail(detail_id),
    FOREIGN KEY (adjusted_by) REFERENCES system_user(user_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- =============================================
-- 7. 档案编号生成支持表
-- =============================================

CREATE TABLE employee_sequence (
    seq_id INT PRIMARY KEY AUTO_INCREMENT,
    org_id INT NOT NULL,
    sequence_year INT NOT NULL,
    current_value INT NOT NULL DEFAULT 0,
    created_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    UNIQUE KEY uk_org_year (org_id, sequence_year),
    FOREIGN KEY (org_id) REFERENCES organization(org_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- =============================================
-- 8. 存储过程和函数 - 新增：自动生成档案编号
-- =============================================

DELIMITER //

-- 生成员工档案编号的函数
CREATE FUNCTION generate_employee_id(p_org_id INT) RETURNS VARCHAR(20)
READS SQL DATA
DETERMINISTIC
BEGIN
    DECLARE v_current_year CHAR(4);
    DECLARE v_org_full_code VARCHAR(6);
    DECLARE v_sequence_val INT;
    DECLARE v_employee_id VARCHAR(20);
    
    -- 获取当前年份
    SET v_current_year = YEAR(CURDATE());
    
    -- 获取机构的完整编码（取前6位：一级2位 + 二级2位 + 三级2位）
    SELECT SUBSTRING(full_code, 1, 6) INTO v_org_full_code 
    FROM organization WHERE org_id = p_org_id;
    
    IF v_org_full_code IS NULL THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = '机构不存在';
    END IF;
    
    -- 获取并更新序列号
    INSERT INTO employee_sequence (org_id, sequence_year, current_value) 
    VALUES (p_org_id, v_current_year, 1)
    ON DUPLICATE KEY UPDATE current_value = current_value + 1;
    
    SELECT current_value INTO v_sequence_val 
    FROM employee_sequence 
    WHERE org_id = p_org_id AND sequence_year = v_current_year;
    
    -- 生成档案编号：年份(4) + 机构编码(6) + 序列号(2)
    SET v_employee_id = CONCAT(v_current_year, v_org_full_code, LPAD(v_sequence_val, 2, '0'));
    
    RETURN v_employee_id;
END//

-- 自动计算社保公积金的函数
CREATE FUNCTION calculate_insurance_amount(
    p_base_salary DECIMAL(10,2), 
    p_insurance_type VARCHAR(20)
) RETURNS DECIMAL(10,2)
DETERMINISTIC
BEGIN
    DECLARE v_result DECIMAL(10,2);
    
    CASE p_insurance_type
        WHEN 'PENSION' THEN -- 养老保险 8%
            SET v_result = ROUND(p_base_salary * 0.08, 2);
        WHEN 'MEDICAL' THEN -- 医疗保险 2% + 3
            SET v_result = ROUND(p_base_salary * 0.02 + 3, 2);
        WHEN 'UNEMPLOYMENT' THEN -- 失业保险 0.5%
            SET v_result = ROUND(p_base_salary * 0.005, 2);
        WHEN 'HOUSING_FUND' THEN -- 住房公积金 8%
            SET v_result = ROUND(p_base_salary * 0.08, 2);
        ELSE
            SET v_result = 0.00;
    END CASE;
    
    RETURN v_result;
END//

DELIMITER ;

-- =============================================
-- 9. 触发器 - 新增：自动化处理
-- =============================================

DELIMITER //

-- 员工档案创建前自动生成ID
CREATE TRIGGER before_employee_insert
BEFORE INSERT ON employee
FOR EACH ROW
BEGIN
    IF NEW.emp_id IS NULL THEN
        SET NEW.emp_id = generate_employee_id(NEW.org_id);
    END IF;
END//

-- 员工状态变更记录
CREATE TRIGGER after_employee_status_update
AFTER UPDATE ON employee
FOR EACH ROW
BEGIN
    IF OLD.status != NEW.status THEN
        INSERT INTO employee_change_log (emp_id, change_type, changed_field, old_value, new_value, change_reason, changed_by)
        VALUES (NEW.emp_id, 'update', 'status', OLD.status, NEW.status, '状态变更', NEW.reviewed_by);
    END IF;
END//

DELIMITER ;


-- =============================================
-- 11. 创建索引
-- =============================================

-- 机构表索引
CREATE INDEX idx_organization_path ON organization(org_path(255));
CREATE INDEX idx_organization_level ON organization(org_level);
CREATE INDEX idx_organization_status ON organization(status);
CREATE INDEX idx_organization_parent ON organization(parent_org_id);

-- 职称表索引
CREATE INDEX idx_title_level ON professional_title(title_level);
CREATE INDEX idx_title_status ON professional_title(status);

-- 职位表索引
CREATE INDEX idx_position_org_id ON `position`(org_id);
CREATE INDEX idx_position_status ON `position`(status);
CREATE INDEX idx_position_category ON `position`(position_category);

-- 员工表索引
CREATE INDEX idx_employee_org_id ON employee(org_id);
CREATE INDEX idx_employee_position_id ON employee(position_id);
CREATE INDEX idx_employee_title_id ON employee(title_id);
CREATE INDEX idx_employee_status ON employee(status);
CREATE INDEX idx_employee_sensitive ON employee(is_sensitive_record, require_double_approval);
CREATE INDEX idx_employee_name ON employee(name);
CREATE INDEX idx_employee_id_card ON employee(id_card);

-- 薪酬相关索引
CREATE INDEX idx_salary_standard_status ON salary_standard(status);
CREATE INDEX idx_salary_issue_org_date ON salary_issue(org_id, issue_year, issue_month);
CREATE INDEX idx_salary_issue_contains_self ON salary_issue(contains_operator_data, status);
CREATE INDEX idx_salary_issue_status ON salary_issue(status);

-- 安全审计日志索引
CREATE INDEX idx_audit_user_time ON security_audit_log(user_id, operation_time);
CREATE INDEX idx_audit_self_operations ON security_audit_log(is_self_operation, security_check_passed);
CREATE INDEX idx_audit_target_emp ON security_audit_log(target_emp_id, operation_time);
CREATE INDEX idx_audit_operation_type ON security_audit_log(operation_type, operation_time);

-- =============================================
-- 12. 创建视图
-- =============================================

-- 员工详细信息视图
CREATE VIEW v_employee_detail AS
SELECT
    e.emp_id, e.employee_no, e.name, e.gender, e.id_card,
    e.email, e.phone, e.mobile, e.education,
    o.org_name, o.full_code as org_code,
    p.position_name, p.position_category,
    t.title_name, t.title_level,
    s.std_name as salary_standard_name,
    e.status, e.created_time, e.reviewed_time,
    creator.real_name as created_by_name,
    reviewer.real_name as reviewed_by_name
FROM employee e
    LEFT JOIN organization o ON e.org_id = o.org_id
    LEFT JOIN `position` p ON e.position_id = p.position_id
    LEFT JOIN professional_title t ON e.title_id = t.title_id
    LEFT JOIN salary_standard s ON e.salary_standard_id = s.std_id
    LEFT JOIN system_user creator ON e.created_by = creator.user_id
    LEFT JOIN system_user reviewer ON e.reviewed_by = reviewer.user_id;

-- 薪酬发放单视图
CREATE VIEW v_salary_issue AS
SELECT
    si.issue_id, si.issue_code, si.issue_year, si.issue_month,
    o.org_name, o.full_code as org_code,
    si.total_amount, si.total_people,
    si.status, si.created_time, si.reviewed_time, si.approved_time,
    creator.real_name as created_by_name,
    reviewer.real_name as reviewed_by_name,
    approver.real_name as approved_by_name
FROM salary_issue si
    LEFT JOIN organization o ON si.org_id = o.org_id
    LEFT JOIN system_user creator ON si.created_by = creator.user_id
    LEFT JOIN system_user reviewer ON si.reviewed_by = reviewer.user_id
    LEFT JOIN system_user approver ON si.approved_by = approver.user_id;

-- 机构层级视图
CREATE VIEW v_organization_hierarchy AS
SELECT
    l1.org_id as level1_id, l1.org_name as level1_name, l1.org_code as level1_code,
    l2.org_id as level2_id, l2.org_name as level2_name, l2.org_code as level2_code,
    l3.org_id as level3_id, l3.org_name as level3_name, l3.org_code as level3_code
FROM organization l1
    LEFT JOIN organization l2 ON l2.parent_org_id = l1.org_id AND l2.org_level = 2
    LEFT JOIN organization l3 ON l3.parent_org_id = l2.org_id AND l3.org_level = 3
WHERE l1.org_level = 1;

-- =============================================
-- 13. 初始化完成
-- =============================================

SELECT '✅ 人力资源管理系统初始化完成！' AS '初始化状态';

-- 显示初始化统计信息
SELECT
    (SELECT COUNT(*) FROM system_user) as '用户数量',
    (SELECT COUNT(*) FROM organization) as '机构数量',
    (SELECT COUNT(*) FROM employee) as '员工数量',
    (SELECT COUNT(*) FROM salary_standard) as '薪酬标准数量',
    (SELECT COUNT(*) FROM security_rule) as '安全规则数量';

-- 显示测试账号信息
SELECT
    username as '用户名',
    real_name as '姓名',
    role as '角色',
    '123456' as '默认密码'
FROM system_user;

-- 显示自动生成的员工档案编号示例
SELECT 
    emp_id as '自动生成的档案编号',
    name as '姓名',
    org_name as '所属机构'
FROM v_employee_detail
LIMIT 5;
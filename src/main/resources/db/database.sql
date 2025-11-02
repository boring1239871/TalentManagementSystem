
-- =============================================
-- 人力资源管理系统 - 演示数据
-- 注意：请先执行数据库结构创建，再执行本数据文件
-- =============================================

-- 1. 清空现有数据（可选，用于重置环境）
SET FOREIGN_KEY_CHECKS = 0;
TRUNCATE TABLE security_audit_log;
TRUNCATE TABLE salary_issue_adjustment;
TRUNCATE TABLE salary_issue_detail;
TRUNCATE TABLE salary_issue;
TRUNCATE TABLE employee_salary_history;
TRUNCATE TABLE employee_change_log;
TRUNCATE TABLE position_title_salary_std;
TRUNCATE TABLE position_title_mapping;
TRUNCATE TABLE employee;
TRUNCATE TABLE salary_standard;
TRUNCATE TABLE salary_item;
TRUNCATE TABLE `position`;
TRUNCATE TABLE professional_title;
TRUNCATE TABLE organization;
TRUNCATE TABLE system_user;
SET FOREIGN_KEY_CHECKS = 1;

-- 2. 插入系统用户数据
INSERT INTO system_user (username, password, real_name, email, phone, role, status, created_by) VALUES
    ('admin', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTV5UiC', '系统管理员', 'admin@company.com', '13800138001', 'admin', 'active', 1),
    ('hr_manager', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTV5UiC', '人事经理', 'hr_manager@company.com', '13800138002', 'hr_manager', 'active', 1),
    ('hr_specialist', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTV5UiC', '人事专员', 'hr_specialist@company.com', '13800138003', 'hr_specialist', 'active', 1),
    ('finance_manager', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTV5UiC', '财务经理', 'finance@company.com', '13800138004', 'finance', 'active', 1),
    ('salary_manager', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTV5UiC', '薪酬经理', 'salary_manager@company.com', '13800138005', 'salary_manager', 'active', 1),
    ('salary_specialist', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTV5UiC', '薪酬专员', 'salary_specialist@company.com', '13800138006', 'salary_specialist', 'active', 1);  -- 新增

-- 3. 插入机构数据（从根节点开始构建）
INSERT INTO organization (org_code, org_name, parent_org_id, org_path, level_code, org_level, full_code, status, created_by) VALUES
    ('001', '总部', NULL, '/1/', '00', 1, '001', 'active', 1),
    ('002', '技术研发中心', 1, '/1/2/', '01', 2, '001002', 'active', 1),
    ('003', '市场营销中心', 1, '/1/3/', '02', 2, '001003', 'active', 1),
    ('004', '财务管理中心', 1, '/1/4/', '03', 2, '001004', 'active', 1),
    ('005', '人力资源中心', 1, '/1/5/', '04', 2, '001005', 'active', 1),
    ('006', '开发一部', 2, '/1/2/6/', '01', 3, '001002006', 'active', 1),
    ('007', '开发二部', 2, '/1/2/7/', '02', 3, '001002007', 'active', 1),
    ('008', '市场推广部', 3, '/1/3/8/', '01', 3, '001003008', 'active', 1),
    ('009', '品牌策划部', 3, '/1/3/9/', '02', 3, '001003009', 'active', 1),
    ('010', '财务会计部', 4, '/1/4/10/', '01', 3, '001004010', 'active', 1),
    ('011', '人力资源部', 5, '/1/5/11/', '01', 3, '001005011', 'active', 1);

-- 4. 插入职称数据
INSERT INTO professional_title (title_code, title_name, title_level, description, display_order, status, created_by) VALUES
    ('T01', '初级工程师', 'junior', '初级技术职称', 1, 'active', 1),
    ('T02', '中级工程师', 'intermediate', '中级技术职称', 2, 'active', 1),
    ('T03', '高级工程师', 'senior', '高级技术职称', 3, 'active', 1),
    ('T04', '技术专家', 'expert', '技术专家职称', 4, 'active', 1),
    ('T05', '初级专员', 'junior', '初级业务职称', 5, 'active', 1),
    ('T06', '中级专员', 'intermediate', '中级业务职称', 6, 'active', 1),
    ('T07', '高级专员', 'senior', '高级业务职称', 7, 'active', 1),
    ('T08', '部门经理', 'senior', '部门管理职称', 8, 'active', 1),  -- manager改为senior
    ('T09', '总监', 'expert', '总监级职称', 9, 'active', 1);      -- director改为expert


-- 5. 插入职位数据
INSERT INTO `position` (position_code, position_name, position_desc, org_id, position_category, position_grade, status, created_by) VALUES
    ('P001', 'Java开发工程师', '负责Java后端开发工作', 6, 'technical', 'P5', 'active', 1),
    ('P002', '前端开发工程师', '负责前端页面开发', 6, 'technical', 'P5', 'active', 1),
    ('P003', '测试工程师', '负责软件测试工作', 7, 'technical', 'P4', 'active', 1),
    ('P004', '运维工程师', '负责系统运维保障', 7, 'technical', 'P5', 'active', 1),
    ('P005', '市场专员', '负责市场推广活动', 8, 'market', 'P4', 'active', 1),
    ('P006', '品牌专员', '负责品牌建设工作', 9, 'market', 'P4', 'active', 1),
    ('P007', '会计', '负责财务核算工作', 10, 'functional', 'P4', 'active', 1),
    ('P008', '税务专员', '负责税务申报工作', 10, 'functional', 'P4', 'active', 1),
    ('P009', '招聘专员', '负责员工招聘工作', 11, 'functional', 'P4', 'active', 1),  -- hr改为functional
    ('P010', '薪酬专员', '负责薪酬核算发放', 11, 'functional', 'P4', 'active', 1),  -- hr改为functional
    ('P011', '技术总监', '负责技术团队管理', 2, 'management', 'P8', 'active', 1),
    ('P012', '市场总监', '负责市场团队管理', 3, 'management', 'P8', 'active', 1);

-- 6. 插入薪酬项目数据
INSERT INTO salary_item (item_code, item_name, item_type, calculation_type, formula_expression, formula_base_field, formula_multiplier, formula_constant, is_system_item, display_order, status, created_by) VALUES
    ('BASE_SALARY', '基本工资', 'income', 'fixed', NULL, NULL, NULL, NULL, TRUE, 1, 'active', 1),
    ('PERFORMANCE', '绩效工资', 'income', 'fixed', NULL, NULL, NULL, NULL, FALSE, 2, 'active', 1),
    ('TRAFFIC', '交通补贴', 'income', 'fixed', NULL, NULL, NULL, NULL, FALSE, 3, 'active', 1),
    ('LUNCH', '午餐补贴', 'income', 'fixed', NULL, NULL, NULL, NULL, FALSE, 4, 'active', 1),
    ('COMMUNICATION', '通讯补贴', 'income', 'fixed', NULL, NULL, NULL, NULL, FALSE, 5, 'active', 1),
    ('OVERTIME', '加班费', 'income', 'formula', 'base_salary / 21.75 * overtime_hours * multiplier', 'BASE_SALARY', 0.06897, 0, FALSE, 6, 'active', 1),
    ('ALLOWANCE', '岗位津贴', 'income', 'fixed', NULL, NULL, NULL, NULL, FALSE, 7, 'active', 1),
    ('PENSION', '养老保险', 'deduction', 'formula', NULL, 'BASE_SALARY', 0.08, 0, TRUE, 8, 'active', 1),  -- percentage改为formula
    ('MEDICAL', '医疗保险', 'deduction', 'formula', NULL, 'BASE_SALARY', 0.02, 0, TRUE, 9, 'active', 1),  -- percentage改为formula
    ('UNEMPLOYMENT', '失业保险', 'deduction', 'formula', NULL, 'BASE_SALARY', 0.005, 0, TRUE, 10, 'active', 1),  -- percentage改为formula
    ('HOUSING_FUND', '住房公积金', 'deduction', 'formula', NULL, 'BASE_SALARY', 0.08, 0, TRUE, 11, 'active', 1),  -- percentage改为formula
    ('TAX', '个人所得税', 'deduction', 'auto_calculation', 'calculate_tax(taxable_income)', 'BASE_SALARY', 0, 0, TRUE, 12, 'active', 1);
    
-- 7. 插入薪酬标准数据
INSERT INTO salary_standard (std_code, std_name, creator, created_by, applicable_position_id, status, salary_items, base_salary, total_amount, reviewed_by, reviewed_time) VALUES
    ('STD2024001', '初级开发标准', '系统管理员', 1, 1, 'approved', '{"BASE_SALARY": 12000, "PERFORMANCE": 2000, "TRAFFIC": 500, "LUNCH": 300, "COMMUNICATION": 200, "ALLOWANCE": 800, "PENSION": 960, "MEDICAL": 240, "UNEMPLOYMENT": 60, "HOUSING_FUND": 960}', 12000, 13680, 1, NOW()),
    ('STD2024002', '中级开发标准', '系统管理员', 1, 1, 'approved', '{"BASE_SALARY": 18000, "PERFORMANCE": 4000, "TRAFFIC": 600, "LUNCH": 300, "COMMUNICATION": 300, "ALLOWANCE": 1200, "PENSION": 1440, "MEDICAL": 360, "UNEMPLOYMENT": 90, "HOUSING_FUND": 1440}', 18000, 20470, 1, NOW()),
    ('STD2024003','前端开发标准', '系统管理员', 1, 2, 'approved', '{"BASE_SALARY": 13000, "PERFORMANCE": 2500, "TRAFFIC": 500, "LUNCH": 300, "COMMUNICATION": 200, "ALLOWANCE": 700, "PENSION": 1040, "MEDICAL": 260, "UNEMPLOYMENT": 65, "HOUSING_FUND": 1040}', 13000, 14795, 1, NOW()),
    ('STD2024004', '测试工程师标准', '系统管理员', 1, 3, 'approved', '{"BASE_SALARY": 10000, "PERFORMANCE": 1500, "TRAFFIC": 400, "LUNCH": 300, "COMMUNICATION": 150, "ALLOWANCE": 500, "PENSION": 800, "MEDICAL": 200, "UNEMPLOYMENT": 50, "HOUSING_FUND": 800}', 10000, 11200, 1, NOW()),
    ('STD2024005', '市场专员标准', '系统管理员', 1, 5, 'approved', '{"BASE_SALARY": 8000, "PERFORMANCE": 2000, "TRAFFIC": 600, "LUNCH": 300, "COMMUNICATION": 300, "ALLOWANCE": 600, "PENSION": 640, "MEDICAL": 160, "UNEMPLOYMENT": 40, "HOUSING_FUND": 640}', 8000, 9520, 1, NOW());

-- 8. 插入员工档案数据
INSERT INTO employee (emp_id, employee_no, name, gender, id_card, email, phone, mobile, qq, address, postal_code, nationality, birthplace, birth_date, ethnicity, education, org_id, position_id, title_id, salary_standard_id, status, created_by, reviewed_by, reviewed_time) VALUES
    ('EMP001', 'EMP001', '张三', 'male', '110101199001011234', 'zhangsan@company.com', '010-12345678', '13800138006', '100001', '北京市朝阳区建国路1号', '100020', '中国', '北京', '1990-01-01', '汉族', 'bachelor', 6, 1, 1, 1, 'active', 1, 1, NOW()),
    ('EMP002', 'EMP002', '李四', 'male', '110101199002021235', 'lisi@company.com', '010-12345679', '13800138007', '100002', '北京市海淀区中关村大街2号', '100080', '中国', '上海', '1990-02-02', '汉族', 'master', 6, 1, 2, 2, 'active', 1, 1, NOW()),
    ('EMP003', 'EMP003', '王五', 'female', '110101199003031236', 'wangwu@company.com', '010-12345680', '13800138008', '100003', '北京市西城区金融街3号', '100033', '中国', '广州', '1990-03-03', '汉族', 'bachelor', 7, 2, 1, 3, 'active', 1, 1, NOW()),
    ('EMP004', 'EMP004', '赵六', 'male', '110101199004041237', 'zhaoliu@company.com', '010-12345681', '13800138009', '100004', '北京市东城区王府井4号', '100006', '中国', '深圳', '1990-04-04', '汉族', 'bachelor', 7, 3, 1, 4, 'active', 1, 1, NOW()),
    ('EMP005', 'EMP005', '钱七', 'female', '110101199005051238', 'qianqi@company.com', '010-12345682', '13800138010', '100005', '北京市丰台区科技园5号', '100070', '中国', '杭州', '1990-05-05', '汉族', 'bachelor', 8, 5, 5, 5, 'active', 1, 1, NOW()),
    ('EMP006', 'EMP006', '孙八', 'male', '110101199006061239', 'sunba@company.com', '010-12345683', '13800138011', '100006', '北京市石景山区古城路6号', '100043', '中国', '南京', '1990-06-06', '汉族', 'associate', 9, 6, 5, 5, 'active', 1, 1, NOW()),
    ('EMP007', 'EMP007', '周九', 'female', '110101199007071240', 'zhoujiu@company.com', '010-12345684', '13800138012', '100007', '北京市通州区运河大街7号', '101100', '中国', '武汉', '1990-07-07', '汉族', 'bachelor', 10, 7, 6, 4, 'active', 1, 1, NOW()),
    ('EMP008', 'EMP008', '吴十', 'male', '110101199008081241', 'wushi@company.com', '010-12345685', '13800138013', '100008', '北京市大兴区亦庄开发区8号', '100176', '中国', '成都', '1990-08-08', '汉族', 'bachelor', 11, 9, 5, 4, 'active', 1, 1, NOW());

-- 9. 更新用户表的员工关联
UPDATE system_user SET associated_emp_id = 'EMP001' WHERE username = 'admin';
UPDATE system_user SET associated_emp_id = 'EMP002' WHERE username = 'hr_manager';
UPDATE system_user SET associated_emp_id = 'EMP003' WHERE username = 'hr_specialist';
UPDATE system_user SET associated_emp_id = 'EMP004' WHERE username = 'finance_manager';
UPDATE system_user SET associated_emp_id = 'EMP008' WHERE username = 'salary_specialist';  -- 改为salary_specialist

-- 10. 插入职位职称映射
INSERT INTO position_title_mapping (position_id, title_id, is_required, display_order, status, created_by) VALUES
    (1, 1, TRUE, 1, 'active', 1),
    (1, 2, FALSE, 2, 'active', 1),
    (1, 3, FALSE, 3, 'active', 1),
    (2, 1, TRUE, 1, 'active', 1),
    (2, 2, FALSE, 2, 'active', 1),
    (3, 1, TRUE, 1, 'active', 1),
    (4, 1, TRUE, 1, 'active', 1),
    (5, 5, TRUE, 1, 'active', 1),
    (5, 6, FALSE, 2, 'active', 1),
    (6, 5, TRUE, 1, 'active', 1),
    (7, 6, TRUE, 1, 'active', 1),
    (8, 6, TRUE, 1, 'active', 1),
    (9, 5, TRUE, 1, 'active', 1),
    (10, 6, TRUE, 1, 'active', 1),
    (11, 9, TRUE, 1, 'active', 1),
    (12, 9, TRUE, 1, 'active', 1);

-- 11. 插入薪酬标准映射
INSERT INTO position_title_salary_std (position_id, title_id, salary_standard_id, effective_date, is_current, status, created_by) VALUES
    (1, 1, 1, '2024-01-01', TRUE, 'active', 1),
    (1, 2, 2, '2024-01-01', TRUE, 'active', 1),
    (2, 1, 3, '2024-01-01', TRUE, 'active', 1),
    (3, 1, 4, '2024-01-01', TRUE, 'active', 1),
    (5, 5, 5, '2024-01-01', TRUE, 'active', 1),
    (6, 5, 5, '2024-01-01', TRUE, 'active', 1);

-- 12. 插入安全审计日志数据
INSERT INTO security_audit_log (user_id, target_emp_id, operation_type, table_name, record_id, old_values, new_values, ip_address, user_agent, is_self_operation, security_check_passed, operation_time) VALUES
    (1, 'EMP001', 'CREATE', 'employee', 'EMP001', NULL, '{"name": "张三", "status": "pending_review"}', '192.168.1.100', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36', FALSE, TRUE, '2024-01-15 09:00:00'),
    (1, 'EMP001', 'REVIEW', 'employee', 'EMP001', '{"status": "pending_review"}', '{"status": "active"}', '192.168.1.100', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36', FALSE, TRUE, '2024-01-15 10:30:00'),
    (1, 'EMP002', 'CREATE', 'employee', 'EMP002', NULL, '{"name": "李四", "status": "pending_review"}', '192.168.1.100', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36', FALSE, TRUE, '2024-01-16 09:15:00'),
    (2, 'EMP003', 'UPDATE', 'employee', 'EMP003', '{"position_id": 1}', '{"position_id": 2}', '192.168.1.101', 'Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36', FALSE, TRUE, '2024-01-17 14:20:00'),
    (3, 'EMP004', 'CREATE', 'salary_issue', '1', NULL, '{"issue_code": "SAL202401001", "status": "draft"}', '192.168.1.102', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36', FALSE, TRUE, '2024-01-18 11:00:00');

-- 13. 插入薪酬发放数据
INSERT INTO salary_issue (issue_code, issue_year, issue_month, issue_date, org_id, org_level, total_base_amount, total_bonus_amount, total_deduction_amount, total_amount, total_people, contains_operator_data, requires_special_approval, status, created_by, created_time) VALUES
    ('SAL202401001', 2024, 1, '2024-01-25', 6, 3, 120000, 20000, 15000, 125000, 10, FALSE, FALSE, 'approved', 3, '2024-01-20 09:00:00'),
    ('SAL202401002', 2024, 1, '2024-01-26', 7, 3, 180000, 30000, 22000, 188000, 15, FALSE, FALSE, 'reviewed', 3, '2024-01-21 10:00:00'),
    ('SAL202402001', 2024, 2, '2024-02-25', 6, 3, 125000, 22000, 16000, 131000, 11, TRUE, TRUE, 'draft', 3, '2024-02-20 09:00:00');

-- 更新已审核和批准的薪酬发放单
UPDATE salary_issue SET 
    reviewed_by = 2, reviewed_time = '2024-01-22 14:00:00',
    approved_by = 1, approved_time = '2024-01-23 16:00:00'
WHERE issue_code = 'SAL202401001';

UPDATE salary_issue SET 
    reviewed_by = 2, reviewed_time = '2024-01-23 15:00:00'
WHERE issue_code = 'SAL202401002';

-- 14. 插入薪酬发放明细数据
INSERT INTO salary_issue_detail (issue_id, emp_id, salary_standard_id, standard_items, base_salary_amount, bonus_amount, deduction_amount, adjustment_reason, subtotal_amount, total_amount, status) VALUES
    (1, 'EMP001', 1, '{"BASE_SALARY": 12000, "PERFORMANCE": 2000, "TRAFFIC": 500, "LUNCH": 300, "COMMUNICATION": 200, "PENSION": 960, "MEDICAL": 240, "UNEMPLOYMENT": 60, "HOUSING_FUND": 960}', 12000, 3000, 2220, NULL, 15000, 12780, 'active'),
    (1, 'EMP002', 2, '{"BASE_SALARY": 18000, "PERFORMANCE": 4000, "TRAFFIC": 600, "LUNCH": 300, "COMMUNICATION": 300, "PENSION": 1440, "MEDICAL": 360, "UNEMPLOYMENT": 90, "HOUSING_FUND": 1440}', 18000, 5200, 3330, NULL, 23200, 19870, 'active'),
    (2, 'EMP003', 3, '{"BASE_SALARY": 13000, "PERFORMANCE": 2500, "TRAFFIC": 500, "LUNCH": 300, "COMMUNICATION": 200, "PENSION": 1040, "MEDICAL": 260, "UNEMPLOYMENT": 65, "HOUSING_FUND": 1040}', 13000, 3500, 2405, NULL, 16500, 14095, 'active'),
    (3, 'EMP001', 1, '{"BASE_SALARY": 12000, "PERFORMANCE": 2000, "TRAFFIC": 500, "LUNCH": 300, "COMMUNICATION": 200, "PENSION": 960, "MEDICAL": 240, "UNEMPLOYMENT": 60, "HOUSING_FUND": 960}', 12000, 3000, 2220, NULL, 15000, 12780, 'active');

-- 15. 插入薪酬调整记录
INSERT INTO salary_issue_adjustment (detail_id, adjust_type, old_amount, new_amount, adjust_reason, adjusted_by, adjusted_time) VALUES
    (1, 'bonus', 2000, 3000, '优秀绩效奖励', 3, '2024-01-21 11:30:00'),
    (2, 'bonus', 4000, 5200, '项目完成奖励', 3, '2024-01-21 11:45:00');

-- 16. 插入员工变更记录
INSERT INTO employee_change_log (emp_id, change_type, changed_field, old_value, new_value, change_reason, changed_by, changed_time) VALUES
    ('EMP001', 'create', NULL, NULL, NULL, '新员工入职', 1, '2024-01-15 09:00:00'),
    ('EMP001', 'review', 'status', 'pending_review', 'active', '档案审核通过', 1, '2024-01-15 10:30:00'),
    ('EMP002', 'create', NULL, NULL, NULL, '新员工入职', 1, '2024-01-16 09:15:00'),
    ('EMP003', 'update', 'position_id', '1', '2', '岗位调动', 2, '2024-01-17 14:20:00'),
    ('EMP002', 'update', 'salary_standard_id', '1', '2', '薪酬标准升级', 1, '2024-01-25 16:00:00');

-- 17. 插入员工薪酬历史
INSERT INTO employee_salary_history (emp_id, salary_standard_id, salary_items, total_amount, effective_date, expiration_date, change_reason, changed_by, changed_time) VALUES
    ('EMP001', 1, '{"BASE_SALARY": 12000, "PERFORMANCE": 2000, "TRAFFIC": 500, "LUNCH": 300, "COMMUNICATION": 200, "PENSION": 960, "MEDICAL": 240, "UNEMPLOYMENT": 60, "HOUSING_FUND": 960}', 12780, '2024-01-01', NULL, '初始薪酬标准', 1, '2024-01-15 09:00:00'),
    ('EMP002', 1, '{"BASE_SALARY": 12000, "PERFORMANCE": 2000, "TRAFFIC": 500, "LUNCH": 300, "COMMUNICATION": 200, "PENSION": 960, "MEDICAL": 240, "UNEMPLOYMENT": 60, "HOUSING_FUND": 960}', 12780, '2024-01-01', '2024-01-31', '初始薪酬标准', 1, '2024-01-16 09:15:00'),
    ('EMP002', 2, '{"BASE_SALARY": 18000, "PERFORMANCE": 4000, "TRAFFIC": 600, "LUNCH": 300, "COMMUNICATION": 300, "PENSION": 1440, "MEDICAL": 360, "UNEMPLOYMENT": 90, "HOUSING_FUND": 1440}', 19870, '2024-02-01', NULL, '薪酬标准升级', 1, '2024-01-25 16:00:00');

-- 在用户数据后插入安全规则数据
INSERT INTO security_rule (rule_name, rule_type, rule_condition, error_message, status, created_by) VALUES
    ('禁止操作自己档案', 'self_operation', 'target_emp_id = current_user_emp_id', '不允许操作自己的员工档案', 'active', 1),
    ('部门数据隔离', 'department_scope', 'target_org_id IN current_user_org_scope', '只能操作本部门及下级部门数据', 'active', 1),
    ('敏感数据双重审批', 'hierarchy_control', 'is_sensitive_record = true', '敏感数据需要上级审批', 'active', 1);
-- =============================================
-- 数据统计验证
-- =============================================

SELECT '✅ 演示数据插入完成！' AS '执行状态';

-- 显示数据统计信息
SELECT
    (SELECT COUNT(*) FROM system_user) as '用户总数',
    (SELECT COUNT(*) FROM organization) as '机构总数',
    (SELECT COUNT(*) FROM employee) as '员工总数',
    (SELECT COUNT(*) FROM salary_standard) as '薪酬标准总数',
    (SELECT COUNT(*) FROM salary_issue) as '薪酬发放单总数',
    (SELECT COUNT(*) FROM security_audit_log) as '审计日志总数';

-- 显示各状态薪酬发放单统计
SELECT 
    status as '状态',
    COUNT(*) as '数量',
    SUM(total_amount) as '总金额',
    SUM(total_people) as '总人数'
FROM salary_issue 
GROUP BY status;

-- 显示各部门员工数量统计
SELECT 
    o.org_name as '部门',
    COUNT(e.emp_id) as '员工数量'
FROM organization o
LEFT JOIN employee e ON o.org_id = e.org_id AND e.status = 'active'
WHERE o.org_level = 3
GROUP BY o.org_id, o.org_name
ORDER BY COUNT(e.emp_id) DESC;

-- 显示用户及其关联员工信息
SELECT 
    u.username as '用户名',
    u.real_name as '姓名',
    u.role as '角色',
    e.name as '关联员工',
    p.position_name as '职位'
FROM system_user u
LEFT JOIN employee e ON u.associated_emp_id = e.emp_id
LEFT JOIN `position` p ON e.position_id = p.position_id;
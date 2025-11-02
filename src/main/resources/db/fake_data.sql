-- =============================================
-- 人力资源管理系统测试数据生成脚本
-- 版本: 1.0
-- 描述: 为HR管理系统所有表生成合理的测试数据
-- =============================================

-- 使用已创建的数据库
USE hr_management_db;

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

-- =============================================
-- 1. 插入基础用户数据
-- =============================================

-- 插入管理员用户
INSERT INTO system_user (username, password, real_name, role, status)
VALUES 
('admin', '$2a$10$LqPZJf5rH9Xq57J1z8W7aO0Z0g9B0y7aPZJf5rH9Xq57J1z8W7aO', '系统管理员', 'admin', 'active'),
('hr_supervisor', '$2a$10$LqPZJf5rH9Xq57J1z8W7aO0Z0g9B0y7aPZJf5rH9Xq57J1z8W7aO', '人事总监', 'hr_manager', 'active'),
('salary_manager', '$2a$10$LqPZJf5rH9Xq57J1z8W7aO0Z0g9B0y7aPZJf5rH9Xq57J1z8W7aO', '薪酬经理', 'salary_manager', 'active'),
('hr_specialist', '$2a$10$LqPZJf5rH9Xq57J1z8W7aO0Z0g9B0y7aPZJf5rH9Xq57J1z8W7aO', '人事专员', 'hr_specialist', 'active'),
('salary_specialist', '$2a$10$LqPZJf5rH9Xq57J1z8W7aO0Z0g9B0y7aPZJf5rH9Xq57J1z8W7aO', '薪酬专员', 'salary_specialist', 'active'),
('finance_staff', '$2a$10$LqPZJf5rH9Xq57J1z8W7aO0Z0g9B0y7aPZJf5rH9Xq57J1z8W7aO', '财务人员', 'finance', 'active');

-- =============================================
-- 2. 插入机构数据（三级结构）
-- =============================================

-- 一级机构
INSERT INTO organization (org_code, org_name, parent_org_id, org_path, level_code, org_level, full_code, status, created_by)
VALUES
('HQ', '总部', NULL, '/1', '01', 1, '01', 'active', 1);

-- 二级机构
INSERT INTO organization (org_code, org_name, parent_org_id, org_path, level_code, org_level, full_code, status, created_by)
VALUES
('HRD', '人力资源部', 1, '/1/2', '01', 2, '0101', 'active', 1),
('FIN', '财务部', 1, '/1/3', '02', 2, '0102', 'active', 1),
('R&D', '研发部', 1, '/1/4', '03', 2, '0103', 'active', 1),
('MKT', '市场部', 1, '/1/5', '04', 2, '0104', 'active', 1),
('OPS', '运营部', 1, '/1/6', '05', 2, '0105', 'active', 1);

-- 三级机构
INSERT INTO organization (org_code, org_name, parent_org_id, org_path, level_code, org_level, full_code, status, created_by)
VALUES
('HRR', '人力资源招聘组', 2, '/1/2/7', '01', 3, '010101', 'active', 1),
('HRT', '人力资源培训组', 2, '/1/2/8', '02', 3, '010102', 'active', 1),
('HRS', '人力资源薪酬组', 2, '/1/2/9', '03', 3, '010103', 'active', 1),
('FINA', '财务会计组', 3, '/1/3/10', '01', 3, '010201', 'active', 1),
('FINB', '财务预算组', 3, '/1/3/11', '02', 3, '010202', 'active', 1),
('RDA', '研发前端组', 4, '/1/4/12', '01', 3, '010301', 'active', 1),
('RDB', '研发后端组', 4, '/1/4/13', '02', 3, '010302', 'active', 1),
('RDC', '研发测试组', 4, '/1/4/14', '03', 3, '010303', 'active', 1),
('MKTA', '市场策划组', 5, '/1/5/15', '01', 3, '010401', 'active', 1),
('MKTB', '市场销售组', 5, '/1/5/16', '02', 3, '010402', 'active', 1);

-- =============================================
-- 3. 插入职称数据
-- =============================================

INSERT INTO professional_title (title_code, title_name, title_level, description, display_order, status, created_by)
VALUES
('T001', '助理工程师', 'junior', '初级技术职称', 1, 'active', 1),
('T002', '工程师', 'intermediate', '中级技术职称', 2, 'active', 1),
('T003', '高级工程师', 'senior', '高级技术职称', 3, 'active', 1),
('T004', '技术专家', 'expert', '专家级技术职称', 4, 'active', 1),
('T005', '助理专员', 'junior', '初级管理职称', 1, 'active', 1),
('T006', '专员', 'intermediate', '中级管理职称', 2, 'active', 1),
('T007', '高级专员', 'senior', '高级管理职称', 3, 'active', 1),
('T008', '经理', 'expert', '经理级管理职称', 4, 'active', 1),
('T009', '助理', 'junior', '初级行政职称', 1, 'active', 1),
('T010', '主管', 'intermediate', '主管级行政职称', 2, 'active', 1);

-- =============================================
-- 4. 插入职位数据
-- =============================================

INSERT INTO `position` (position_code, position_name, position_desc, org_id, position_category, position_grade, status, created_by)
VALUES
('P001', '人力资源总监', '人力资源部负责人', 2, 'management', 'G7', 'active', 1),
('P002', '招聘经理', '负责公司招聘工作', 7, 'management', 'G6', 'active', 1),
('P003', '培训专员', '负责员工培训工作', 8, 'functional', 'G4', 'active', 1),
('P004', '薪酬专员', '负责薪酬核算工作', 9, 'functional', 'G4', 'active', 1),
('P005', '财务经理', '财务部负责人', 3, 'management', 'G6', 'active', 1),
('P006', '会计', '负责会计核算工作', 10, 'functional', 'G4', 'active', 1),
('P007', '研发总监', '研发部负责人', 4, 'management', 'G7', 'active', 1),
('P008', '前端开发工程师', '负责前端开发工作', 12, 'technical', 'G5', 'active', 1),
('P009', '后端开发工程师', '负责后端开发工作', 13, 'technical', 'G5', 'active', 1),
('P010', '测试工程师', '负责软件测试工作', 14, 'technical', 'G4', 'active', 1),
('P011', '市场总监', '市场部负责人', 5, 'management', 'G6', 'active', 1),
('P012', '市场策划专员', '负责市场策划工作', 15, 'functional', 'G4', 'active', 1),
('P013', '销售经理', '负责销售团队管理', 16, 'management', 'G5', 'active', 1),
('P014', '运营总监', '运营部负责人', 6, 'management', 'G6', 'active', 1),
('P015', '行政助理', '负责行政支持工作', 1, 'functional', 'G3', 'active', 1);

-- =============================================
-- 5. 插入薪酬项目数据
-- =============================================

INSERT INTO salary_item (item_code, item_name, item_type, calculation_type, formula_expression, formula_base_field, formula_multiplier, formula_constant, is_system_item, display_order, status, created_by)
VALUES
('S001', '基本工资', 'income', 'fixed', NULL, NULL, NULL, NULL, TRUE, 1, 'active', 1),
('S002', '绩效工资', 'income', 'formula', 'base_salary * multiplier', 'base_salary', 0.3, NULL, TRUE, 2, 'active', 1),
('S003', '年终奖', 'income', 'formula', 'base_salary * months', 'base_salary', 1.0, NULL, FALSE, 3, 'active', 1),
('S004', '交通补贴', 'income', 'fixed', NULL, NULL, NULL, 500, TRUE, 4, 'active', 1),
('S005', '餐补', 'income', 'fixed', NULL, NULL, NULL, 300, TRUE, 5, 'active', 1),
('S006', '养老保险', 'deduction', 'formula', 'base_salary * 0.08', 'base_salary', 0.08, NULL, TRUE, 6, 'active', 1),
('S007', '医疗保险', 'deduction', 'formula', 'base_salary * 0.02 + 3', 'base_salary', 0.02, 3, TRUE, 7, 'active', 1),
('S008', '失业保险', 'deduction', 'formula', 'base_salary * 0.005', 'base_salary', 0.005, NULL, TRUE, 8, 'active', 1),
('S009', '住房公积金', 'deduction', 'formula', 'base_salary * 0.08', 'base_salary', 0.08, NULL, TRUE, 9, 'active', 1),
('S010', '个人所得税', 'deduction', 'auto_calculation', NULL, NULL, NULL, NULL, TRUE, 10, 'active', 1),
('S011', '迟到扣款', 'deduction', 'fixed', NULL, NULL, NULL, NULL, FALSE, 11, 'active', 1),
('S012', '加班补贴', 'income', 'formula', 'base_salary / 21.75 / 8 * hours * 1.5', 'base_salary', 1.5, NULL, FALSE, 12, 'active', 1);

-- =============================================
-- 6. 插入安全规则数据
-- =============================================

INSERT INTO security_rule (rule_name, rule_type, rule_condition, error_message, status, created_by)
VALUES
('员工只能查看自己的信息', 'self_operation', 'target_emp_id != current_user.associated_emp_id AND operation_type = ''VIEW''', '无权限查看其他员工信息', 'active', 1),
('只能管理本部门员工', 'department_scope', 'target_emp_org_id NOT IN (user_department_ids) AND operation_type IN (''UPDATE'', ''DELETE'')', '无权限管理其他部门员工', 'active', 1),
('敏感信息需要双重审批', 'hierarchy_control', 'is_sensitive_record = TRUE AND require_double_approval = TRUE', '敏感信息需要双重审批', 'active', 1),
('薪酬专员不能审核自己创建的薪酬单', 'self_operation', 'created_by = current_user.user_id AND operation_type = ''APPROVE'' AND table_name = ''salary_issue''', '不能审核自己创建的薪酬单', 'active', 1),
('管理员可以访问所有信息', 'hierarchy_control', 'user_role = ''admin''', '管理员权限覆盖所有规则', 'active', 1);

-- =============================================
-- 7. 插入薪酬标准数据
-- =============================================

INSERT INTO salary_standard (std_code, std_name, creator, created_by, applicable_position_id, status, salary_items, base_salary, total_amount)
VALUES
('STD001', 'G7级别薪酬标准', '系统管理员', 1, 1, 'approved', '{"S001":25000,"S002":7500,"S004":500,"S005":300}', 25000, 33300),
('STD002', 'G6级别薪酬标准', '系统管理员', 1, 2, 'approved', '{"S001":18000,"S002":5400,"S004":500,"S005":300}', 18000, 24200),
('STD003', 'G5级别薪酬标准', '系统管理员', 1, 8, 'approved', '{"S001":12000,"S002":3600,"S004":500,"S005":300}', 12000, 16400),
('STD004', 'G4级别薪酬标准', '系统管理员', 1, 3, 'approved', '{"S001":8000,"S002":2400,"S004":500,"S005":300}', 8000, 11200),
('STD005', 'G3级别薪酬标准', '系统管理员', 1, 15, 'approved', '{"S001":5000,"S002":1500,"S004":500,"S005":300}', 5000, 7300);

-- 8. 插入职位职称映射数据
-- =============================================

INSERT INTO position_title_mapping (position_id, title_id, is_required, display_order, status, created_by)
VALUES
(1, 8, TRUE, 1, 'active', 1), -- 人力资源总监 - 经理
(2, 7, TRUE, 1, 'active', 1), -- 招聘经理 - 高级专员
(3, 6, FALSE, 1, 'active', 1), -- 培训专员 - 专员
(4, 6, FALSE, 1, 'active', 1), -- 薪酬专员 - 专员
(5, 8, TRUE, 1, 'active', 1), -- 财务经理 - 经理
(6, 6, FALSE, 1, 'active', 1), -- 会计 - 专员
(7, 4, TRUE, 1, 'active', 1), -- 研发总监 - 技术专家
(8, 3, FALSE, 1, 'active', 1), -- 前端开发工程师 - 高级工程师
(8, 2, TRUE, 2, 'active', 1), -- 前端开发工程师 - 工程师
(9, 3, FALSE, 1, 'active', 1), -- 后端开发工程师 - 高级工程师
(9, 2, TRUE, 2, 'active', 1), -- 后端开发工程师 - 工程师
(10, 2, FALSE, 1, 'active', 1), -- 测试工程师 - 工程师
(10, 1, TRUE, 2, 'active', 1), -- 测试工程师 - 助理工程师
(11, 8, TRUE, 1, 'active', 1), -- 市场总监 - 经理
(12, 6, FALSE, 1, 'active', 1), -- 市场策划专员 - 专员
(13, 7, TRUE, 1, 'active', 1), -- 销售经理 - 高级专员
(14, 8, TRUE, 1, 'active', 1), -- 运营总监 - 经理
(15, 5, TRUE, 1, 'active', 1); -- 行政助理 - 助理专员

-- =============================================
-- 9. 插入职位职称薪酬标准映射数据
-- =============================================

INSERT INTO position_title_salary_std (position_id, title_id, salary_standard_id, effective_date, expiration_date, is_current, status, created_by)
VALUES
(1, 8, 1, '2024-01-01', NULL, TRUE, 'active', 1),
(2, 7, 2, '2024-01-01', NULL, TRUE, 'active', 1),
(3, 6, 4, '2024-01-01', NULL, TRUE, 'active', 1),
(4, 6, 4, '2024-01-01', NULL, TRUE, 'active', 1),
(5, 8, 2, '2024-01-01', NULL, TRUE, 'active', 1),
(6, 6, 4, '2024-01-01', NULL, TRUE, 'active', 1),
(7, 4, 1, '2024-01-01', NULL, TRUE, 'active', 1),
(8, 3, 3, '2024-01-01', NULL, TRUE, 'active', 1),
(8, 2, 4, '2024-01-01', NULL, TRUE, 'active', 1),
(9, 3, 3, '2024-01-01', NULL, TRUE, 'active', 1),
(9, 2, 4, '2024-01-01', NULL, TRUE, 'active', 1),
(10, 2, 4, '2024-01-01', NULL, TRUE, 'active', 1),
(10, 1, 4, '2024-01-01', NULL, TRUE, 'active', 1),
(11, 8, 2, '2024-01-01', NULL, TRUE, 'active', 1),
(12, 6, 4, '2024-01-01', NULL, TRUE, 'active', 1),
(13, 7, 3, '2024-01-01', NULL, TRUE, 'active', 1),
(14, 8, 2, '2024-01-01', NULL, TRUE, 'active', 1),
(15, 5, 5, '2024-01-01', NULL, TRUE, 'active', 1);

-- =============================================
-- 10. 插入员工数据
-- =============================================

INSERT INTO employee (emp_id, name, gender, id_card, email, phone, mobile, org_id, position_id, title_id, salary_standard_id, status, created_by, reviewed_by, reviewed_time)
VALUES
('2024010101', '张三', 'male', '110101199001011234', 'zhangsan@example.com', '010-12345678', '13800138001', 2, 1, 8, 1, 'active', 1, 1, NOW()),
('2024010102', '李四', 'female', '110101199102022345', 'lisi@example.com', '010-23456789', '13800138002', 7, 2, 7, 2, 'active', 1, 1, NOW()),
('2024010103', '王五', 'male', '110101199203033456', 'wangwu@example.com', '010-34567890', '13800138003', 8, 3, 6, 4, 'active', 1, 1, NOW()),
('2024010104', '赵六', 'female', '110101199304044567', 'zhaoliu@example.com', '010-45678901', '13800138004', 9, 4, 6, 4, 'active', 1, 1, NOW()),
('2024010201', '钱七', 'male', '110101199405055678', 'qianqi@example.com', '010-56789012', '13800138005', 3, 5, 8, 2, 'active', 1, 1, NOW()),
('2024010202', '孙八', 'female', '110101199506066789', 'sunba@example.com', '010-67890123', '13800138006', 10, 6, 6, 4, 'active', 1, 1, NOW()),
('2024010301', '周九', 'male', '110101199607077890', 'zhoujiu@example.com', '010-78901234', '13800138007', 4, 7, 4, 1, 'active', 1, 1, NOW()),
('2024010302', '吴十', 'male', '110101199708088901', 'wushi@example.com', '010-89012345', '13800138008', 12, 8, 3, 3, 'active', 1, 1, NOW()),
('2024010303', '郑十一', 'female', '110101199809099012', 'zhengsy@example.com', '010-90123456', '13800138009', 12, 8, 2, 4, 'active', 1, 1, NOW()),
('2024010304', '王十二', 'male', '110101199910100123', 'wangse@example.com', '010-01234567', '13800138010', 13, 9, 3, 3, 'active', 1, 1, NOW()),
('2024010305', '李十三', 'female', '110101200011111234', 'lishisan@example.com', '010-12345678', '13800138011', 13, 9, 2, 4, 'active', 1, 1, NOW()),
('2024010306', '张十四', 'male', '110101200112122345', 'zhangsi@example.com', '010-23456789', '13800138012', 14, 10, 2, 4, 'active', 1, 1, NOW()),
('2024010307', '刘十五', 'female', '110101200201013456', 'liushi@example.com', '010-34567890', '13800138013', 14, 10, 1, 4, 'active', 1, 1, NOW()),
('2024010401', '陈十六', 'male', '110101200302024567', 'chenshiliu@example.com', '010-45678901', '13800138014', 5, 11, 8, 2, 'active', 1, 1, NOW()),
('2024010402', '杨十七', 'female', '110101200403035678', 'yangshiqi@example.com', '010-56789012', '13800138015', 15, 12, 6, 4, 'active', 1, 1, NOW()),
('2024010403', '黄十八', 'male', '110101200504046789', 'huangshi@example.com', '010-67890123', '13800138016', 16, 13, 7, 3, 'active', 1, 1, NOW()),
('2024010501', '周十九', 'female', '110101200605057890', 'zhoushi@example.com', '010-78901234', '13800138017', 6, 14, 8, 2, 'active', 1, 1, NOW()),
('2024010105', '吴二十', 'male', '110101200706068901', 'wuer@example.com', '010-89012345', '13800138018', 1, 15, 5, 5, 'active', 1, 1, NOW());

-- 更新用户与员工关联关系
UPDATE system_user SET associated_emp_id = '2024010101' WHERE username = 'admin';
UPDATE system_user SET associated_emp_id = '2024010102' WHERE username = 'hr_supervisor';
UPDATE system_user SET associated_emp_id = '2024010104' WHERE username = 'salary_manager';
UPDATE system_user SET associated_emp_id = '2024010103' WHERE username = 'hr_specialist';
UPDATE system_user SET associated_emp_id = '2024010105' WHERE username = 'salary_specialist';  -- 改为其他员工ID
UPDATE system_user SET associated_emp_id = '2024010202' WHERE username = 'finance_staff';

-- =============================================
-- 11. 插入员工变更记录数据
-- =============================================

INSERT INTO employee_change_log (emp_id, change_type, changed_field, old_value, new_value, change_reason, changed_by)
VALUES
('2024010101', 'create', NULL, NULL, NULL, '新员工入职', 1),
('2024010102', 'create', NULL, NULL, NULL, '新员工入职', 1),
('2024010103', 'create', NULL, NULL, NULL, '新员工入职', 1),
('2024010104', 'update', 'position_id', '10', '4', '岗位调整', 2),
('2024010201', 'create', NULL, NULL, NULL, '新员工入职', 1),
('2024010302', 'update', 'salary_standard_id', '4', '3', '晋升调整', 2);

-- =============================================
-- 12. 插入员工薪酬历史数据
-- =============================================

INSERT INTO employee_salary_history (emp_id, salary_standard_id, salary_items, total_amount, effective_date, expiration_date, change_reason, changed_by)
VALUES
('2024010101', 1, '{"S001":25000,"S002":7500}', 32500, '2024-01-01', NULL, '入职定薪', 1),
('2024010102', 2, '{"S001":18000,"S002":5400}', 23400, '2024-01-01', NULL, '入职定薪', 1),
('2024010103', 4, '{"S001":8000,"S002":2400}', 10400, '2024-01-01', NULL, '入职定薪', 1),
('2024010302', 4, '{"S001":8000,"S002":2400}', 10400, '2024-01-01', '2024-02-28', '入职定薪', 1),
('2024010302', 3, '{"S001":12000,"S002":3600}', 15600, '2024-03-01', NULL, '晋升加薪', 2);

-- =============================================
-- 13. 插入薪酬发放数据
-- =============================================

-- 插入薪酬发放主表数据
INSERT INTO salary_issue (issue_code, issue_year, issue_month, issue_date, org_id, org_level, total_base_amount, total_bonus_amount, total_deduction_amount, total_amount, total_people, status, created_by, reviewed_by, reviewed_time, approved_by, approved_time)
VALUES
('SALARY202401', 2024, 1, '2024-01-10', 1, 1, 200000, 50000, 30000, 220000, 18, 'approved', 4, 2, '2024-01-05', 1, '2024-01-06'),
('SALARY202402', 2024, 2, '2024-02-10', 1, 1, 200000, 50000, 30000, 220000, 18, 'approved', 4, 2, '2024-02-05', 1, '2024-02-06'),
('SALARY202403', 2024, 3, '2024-03-10', 1, 1, 205000, 51000, 31000, 225000, 18, 'paid', 4, 2, '2024-03-05', 1, '2024-03-06');

-- 插入薪酬发放明细表数据
INSERT INTO salary_issue_detail (issue_id, emp_id, salary_standard_id, standard_items, base_salary_amount, bonus_amount, deduction_amount, subtotal_amount, total_amount, status)
VALUES
(1, '2024010101', 1, '{"S001":25000,"S002":7500}', 25000, 0, 3500, 32500, 29000, 'active'),
(1, '2024010102', 2, '{"S001":18000,"S002":5400}', 18000, 0, 2500, 23400, 20900, 'active'),
(1, '2024010103', 4, '{"S001":8000,"S002":2400}', 8000, 0, 1200, 10400, 9200, 'active'),
(1, '2024010104', 4, '{"S001":8000,"S002":2400}', 8000, 0, 1200, 10400, 9200, 'active'),
(2, '2024010101', 1, '{"S001":25000,"S002":7500}', 25000, 0, 3500, 32500, 29000, 'active'),
(2, '2024010102', 2, '{"S001":18000,"S002":5400}', 18000, 0, 2500, 23400, 20900, 'active'),
(2, '2024010103', 4, '{"S001":8000,"S002":2400}', 8000, 0, 1200, 10400, 9200, 'active'),
(2, '2024010104', 4, '{"S001":8000,"S002":2400}', 8000, 0, 1200, 10400, 9200, 'active'),
(3, '2024010101', 1, '{"S001":25000,"S002":7500}', 25000, 0, 3500, 32500, 29000, 'active'),
(3, '2024010102', 2, '{"S001":18000,"S002":5400}', 18000, 0, 2500, 23400, 20900, 'active'),
(3, '2024010103', 4, '{"S001":8000,"S002":2400}', 8000, 0, 1200, 10400, 9200, 'active'),
(3, '2024010104', 4, '{"S001":8000,"S002":2400}', 8000, 0, 1200, 10400, 9200, 'active'),
(3, '2024010302', 3, '{"S001":12000,"S002":3600}', 12000, 0, 1800, 15600, 13800, 'active');

-- =============================================
-- 14. 插入薪酬发放调整记录数据
-- =============================================

INSERT INTO salary_issue_adjustment (detail_id, adjust_type, old_amount, new_amount, adjust_reason, adjusted_by)
VALUES
(1, 'bonus', 29000, 31000, '春节 bonus', 4),
(2, 'bonus', 20900, 22900, '春节 bonus', 4),
(13, 'bonus', 13800, 15800, '优秀员工奖励', 4);

-- =============================================
-- 15. 插入安全审计日志数据
-- =============================================

INSERT INTO security_audit_log (user_id, target_emp_id, operation_type, table_name, record_id, old_values, new_values, ip_address, user_agent, is_self_operation, security_check_passed)
VALUES
(1, '2024010101', 'VIEW', 'employee', '2024010101', NULL, '{"emp_id":"2024010101","name":"张三"}', '127.0.0.1', 'Mozilla/5.0', TRUE, TRUE),
(2, '2024010103', 'UPDATE', 'employee', '2024010103', '{"position_id":"8"}', '{"position_id":"3"}', '127.0.0.1', 'Mozilla/5.0', FALSE, TRUE),
(4, '2024010302', 'UPDATE', 'salary_standard', '3', '{"status":"draft"}', '{"status":"approved"}', '127.0.0.1', 'Mozilla/5.0', FALSE, TRUE),
(3, '2024010101', 'VIEW', 'employee', '2024010101', NULL, NULL, '127.0.0.1', 'Mozilla/5.0', FALSE, TRUE),
(5, '2024010104', 'VIEW', 'salary_issue', '1', NULL, NULL, '127.0.0.1', 'Mozilla/5.0', FALSE, TRUE);

-- =============================================
-- 16. 插入员工序列数据
-- =============================================

INSERT INTO employee_sequence (org_id, sequence_year, current_value)
VALUES
(1, 2024, 1),
(2, 2024, 5),
(3, 2024, 2),
(4, 2024, 7),
(5, 2024, 3),
(6, 2024, 1);

-- =============================================
-- 17. 创建视图
-- =============================================

-- 员工详细信息视图
CREATE OR REPLACE VIEW v_employee_detail AS
SELECT 
    e.emp_id, e.name, e.gender, e.id_card, e.email, e.phone, e.mobile,
    o.org_name, o.org_code, o.org_level,
    p.position_name, p.position_code,
    t.title_name, t.title_level,
    s.std_code, s.std_name, s.base_salary,
    e.status, e.created_time, e.reviewed_time,
    creator.real_name as created_by_name,  -- 修改别名
    reviewer.real_name as reviewed_by_name  -- 修改别名
FROM employee e
JOIN organization o ON e.org_id = o.org_id
JOIN `position` p ON e.position_id = p.position_id
JOIN professional_title t ON e.title_id = t.title_id
LEFT JOIN salary_standard s ON e.salary_standard_id = s.std_id
LEFT JOIN system_user creator ON e.created_by = creator.user_id  -- 改为LEFT JOIN
LEFT JOIN system_user reviewer ON e.reviewed_by = reviewer.user_id;  -- 改为LEFT JOIN

-- 薪酬发放单视图
CREATE OR REPLACE VIEW v_salary_issue AS
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
CREATE OR REPLACE VIEW v_organization_hierarchy AS
SELECT
    l1.org_id as level1_id, l1.org_name as level1_name, l1.org_code as level1_code,
    l2.org_id as level2_id, l2.org_name as level2_name, l2.org_code as level2_code,
    l3.org_id as level3_id, l3.org_name as level3_name, l3.org_code as level3_code
FROM organization l1
    LEFT JOIN organization l2 ON l2.parent_org_id = l1.org_id AND l2.org_level = 2
    LEFT JOIN organization l3 ON l3.parent_org_id = l2.org_id AND l3.org_level = 3
WHERE l1.org_level = 1;

-- =============================================
-- 18. 初始化完成
-- =============================================

SELECT '✅ 人力资源管理系统测试数据初始化完成！' AS '初始化状态';

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
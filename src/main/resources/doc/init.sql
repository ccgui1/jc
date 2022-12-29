CREATE DATABASE IF NOT EXISTS jc DEFAULT CHARACTER SET utf8mb4;
use jc;

--账户信息表
CREATE TABLE IF NOT EXISTS jc.jc_user (
  user_id int NOT NULL PRIMARY KEY AUTO_INCREMENT COMMENT '账号编码',
  user_nm varchar(20) NOT NULL COMMENT '账号名',
    passwd varchar(20) NOT NULL COMMENT '密码',
    role varchar(10) NOT NULL COMMENT '权限 1:管理员 2:普通用户 ',
    create_user varchar(10) DEFAULT '',
    create_time datetime DEFAULT CURRENT_TIMESTAMP,
    UNIQUE (user_nm)
    ) ENGINE=InnoDB ;

INSERT INTO jc.jc_user(user_nm,passwd,role,create_user)
VALUES ('jiance', 'jiance', 1, 'system');

--学生信息表
CREATE TABLE IF NOT EXISTS jc.jc_student (
 student_id int NOT NULL PRIMARY KEY AUTO_INCREMENT COMMENT '编号',
 student_nm varchar(20) NOT NULL COMMENT '姓名',
    college varchar(40) NOT NULL COMMENT '院校单位',
    major_nm varchar(10) NOT NULL COMMENT '专业',
    uid varchar(20) NOT NULL COMMENT '身份证号',
    phone varchar(20) NOT NULL,
    email varchar(100)  DEFAULT '' COMMENT '邮箱',
    qq varchar(20) DEFAULT '' ,
    adviser varchar(20) DEFAULT '' COMMENT '课程顾问',
    sign_course varchar(20) DEFAULT '' COMMENT '报名课程',
    sign_time varchar(40) DEFAULT '' COMMENT '报名时间,多个时间',
    exam_passed varchar(40) DEFAULT '' COMMENT '已通过考试',
    employment_unit varchar(40) DEFAULT '' COMMENT '就业单位',
    memo varchar(256) DEFAULT '' COMMENT '备注',
    create_user varchar(10) DEFAULT '',
    create_time datetime DEFAULT CURRENT_TIMESTAMP
    ) ENGINE=InnoDB ;

--课程
CREATE TABLE IF NOT EXISTS jc.jc_course (
    course_id int NOT NULL PRIMARY KEY AUTO_INCREMENT COMMENT '课程编号',
    course_nm varchar(20) NOT NULL COMMENT '课程名称'
    ) ENGINE=InnoDB ;
INSERT INTO jc.jc_course(course_nm)
VALUES ('RHCSA'),('RHCE'),('H3CNE');


--班级
CREATE TABLE IF NOT EXISTS jc.jc_class (
   class_id int NOT NULL PRIMARY KEY AUTO_INCREMENT COMMENT '班级编号',
   course_nm varchar(20) NOT NULL COMMENT '课程名称',
    class_type varchar(10) NOT NULL COMMENT '班型',
    date_range varchar(20) NOT NULL COMMENT '日期范围',
    date_detail varchar(100) DEFAULT '' COMMENT '上课时间明细',
    teacher_nm varchar(20) NOT NULL COMMENT '任课老师',
    headmaster varchar(20) DEFAULT '' COMMENT '班主任',
    create_user varchar(10) DEFAULT '',
    create_time datetime DEFAULT CURRENT_TIMESTAMP
    ) ENGINE=InnoDB ;


--班级学生
CREATE TABLE IF NOT EXISTS jc.jc_class_student (
   class_id int NOT NULL COMMENT '班级id',
   student_id int NOT NULL COMMENT '学生id',
   student_nm varchar(20) NOT NULL COMMENT '学生姓名',
    college varchar(40) NOT NULL COMMENT '院校',
    phone varchar(20) NOT NULL COMMENT '手机',
    course_nm varchar(20) NOT NULL COMMENT '课程名称',
    create_user varchar(10) DEFAULT NULL,
    create_time datetime DEFAULT CURRENT_TIMESTAMP,
    primary key(class_id,student_id)
    ) ENGINE=InnoDB ;


DROP TABLE IF EXISTS STAFF CASCADE;
create table STAFF(
	STAFFID varchar(20) not null primary key,
	PASSWORD varchar(20),
	NAME varchar(20),
	KFLAG char(1)
);

DROP TABLE IF EXISTS CLASSLIST CASCADE;
create table CLASSLIST(
	CLASSCD char(3) primary key,
	NAME varchar(20) not null
);

DROP TABLE IF EXISTS STAFFTOCLASS CASCADE;
create table STAFFTOCLASS(
	STAFFID varchar(20) not null,
	CLASSCD char(3) not null,
	primary key (STAFFID, CLASSCD),
	foreign key (STAFFID) references STAFF(STAFFID),
	foreign key (CLASSCD) references CLASSLIST(CLASSCD)
);

DROP TABLE IF EXISTS STUDENT CASCADE;
create table STUDENT(
	STUDENTID varchar(10) not null primary key ,
	NAME varchar(10) ,
	SEX char(2) ,
	STUDENTTEL varchar(15) ,
	PARENTTEL varchar(15) ,
	DROPFLAG boolean default FALSE
);


DROP TABLE IF EXISTS CLASS;
DROP TABLE IF EXISTS CLASSROSTER CASCADE;
create table CLASSROSTER(
	ID serial primary key ,
	CLASSCD char(3) not null ,
	NAME varchar(20),
	STUDENTID varchar(10),
	foreign key (CLASSCD) references CLASSLIST(CLASSCD),
	foreign key (STUDENTID) references STUDENT(STUDENTID)
);

DROP TABLE IF EXISTS SUBJECT CASCADE;
create table SUBJECT(
	SUBJECTCD char(3) not null primary key,
	SUBJECTNAME varchar(20),
	CREDIT int,
	CLOSINGDATE date
);

DROP TABLE IF EXISTS SCORE CASCADE;
create table SCORE(
	STUDENTID varchar(10) not null,
	SUBJECTCD varchar(30) not null,
	YEAR char(4),
	MONTH char(2),
	SCORE int,
	COUNT int,
	primary key(STUDENTID, SUBJECTCD, COUNT),
	foreign key (STUDENTID) references STUDENT(STUDENTID),
	foreign key (SUBJECTCD) references SUBJECT(SUBJECTCD)
);

DROP TABLE IF EXISTS ATTENDANCENAME CASCADE;
create table ATTENDANCENAME(
	ATTENDANCE char(2) not null primary key ,
	ATTENDANCENAME varchar(10)
);

DROP TABLE IF EXISTS ATTENDANCE CASCADE;
create table ATTENDANCE(
	STUDENTID varchar(10) not null,
	ATTENDANCE char(2)  default 0,
	ATREASON varchar(20),
	ATDATE date not null,
	POINT boolean default FALSE,
	primary key (STUDENTID, ATDATE),
	foreign key (STUDENTID) references STUDENT(STUDENTID),
	foreign key (ATTENDANCE) references ATTENDANCENAME(ATTENDANCE)
);

DROP TABLE IF EXISTS NOTIFICATIONNAME CASCADE;
create table NOTIFICATIONNAME(
	NOTIFICATION int primary key,
	NOTIFICATIONNAME varchar(10)
);

DROP TABLE IF EXISTS NOTIFICATION CASCADE;
create table NOTIFICATION(
	STUDENTID varchar(10),
	NOTIFICATION int,
	NDATE date,
	primary key(STUDENTID, NOTIFICATION),
	foreign key (STUDENTID) references STUDENT(STUDENTID),
	foreign key (NOTIFICATION) references NOTIFICATIONNAME(NOTIFICATION)
);

DROP TABLE IF EXISTS ATTENDANCERATE CASCADE;
CREATE TABLE ATTENDANCERATE (
    STUDENTID VARCHAR(10) NOT NULL,           -- 学生番号 (10文字、NULL不可)
    ATTENDANCERATE DECIMAL(6, 5),             -- 出席率 (最大6桁、5桁小数)
    ABSENCERATE DECIMAL(6, 5),               -- 欠席率 (最大6桁、5桁小数)
    TARDINESS INT,                           -- 遅刻 (整数)
    EARLYLEAVE INT,                          -- 早退 (整数)
    CUMULATIVEABSENCE INT,                   -- 累計欠席 (整数)
    FRACTION INT,                            -- 1未満累積欠席 (整数)
    OTHERABSENCE INT,                        -- その他欠席 (整数)
    LEAVEOFABSENCE INT,                      -- 休学 (整数)

    PRIMARY KEY (STUDENTID),                 -- 主キー: 学生番号
    FOREIGN KEY (STUDENTID) REFERENCES STUDENT(STUDENTID) -- 外部キー制約: STUDENT テーブルの STUDENTID を参照
);

DROP TABLE IF EXISTS ATTENDANCERATEBYCLASS CASCADE;
CREATE TABLE ATTENDANCERATEBYCLASS (
    ADMISSIONYEAR INT NOT NULL,              -- 入学年度 (整数型、NULL不可)
    CLASSCD VARCHAR(10) NOT NULL,             -- クラスコード (文字列、10文字、NULL不可)
    ATTENDANCERATE DECIMAL(6, 5),            -- 出席率 (最大6桁、5桁小数)
    ABSENCERATE DECIMAL(6, 5),              -- 欠席率 (最大6桁、5桁小数)

    PRIMARY KEY (ADMISSIONYEAR, CLASSCD),    -- 主キー: 入学年度 + クラスコードの組み合わせ
    FOREIGN KEY (CLASSCD) REFERENCES CLASSLIST(CLASSCD) -- 外部キー制約: CLASSLIST テーブルの CLASSCD を参照
);

DROP TABLE IF EXISTS ATTENDANCERATEFINALIZED CASCADE;
CREATE TABLE ATTENDANCERATEFINALIZED (
    STUDENTID VARCHAR(10) NOT NULL,              -- 学生番号 (文字列、10文字、NULL不可)
    FINALIZEDMONTH VARCHAR(7) NOT NULL,          -- 確定月 (文字列、YYYY-MM形式、NULL不可)
    ATTENDANCERATE DECIMAL(6, 5),                -- 出席率 (最大6桁、5桁小数)
    ABSENCERATE DECIMAL(6, 5),                  -- 欠席率 (最大6桁、5桁小数)
    REQUIREDATTENDANCEDAYS INT,                  -- 必要出席日数 (整数型)
    TARDINESS INT,                              -- 遅刻 (整数型)
    EARLYLEAVE INT,                             -- 早退 (整数型)
    CUMULATIVEABSENCE INT,                      -- 累計欠席 (整数型)
    FRACTION INT,                               -- 1未満累積欠席 (0, 1, 2 が入る)
    OTHERABSENCE INT,                           -- その他欠席 (整数型)
    LEAVEOFABSENCE INT,                         -- 休学 (整数型)

    PRIMARY KEY (STUDENTID, FINALIZEDMONTH),    -- 主キー: 学生番号 + 確定月の組み合わせ
    FOREIGN KEY (STUDENTID) REFERENCES STUDENT(STUDENTID) -- 外部キー制約: STUDENT テーブルの STUDENTID を参照
);

DROP TABLE IF EXISTS ATTENDANCERATEBYCLASSFINALIZED CASCADE;
CREATE TABLE ATTENDANCERATEBYCLASSFINALIZED (
    ADMISSIONYEAR INT NOT NULL,                -- 入学年度 (整数型、NULL不可)
    CLASSCD VARCHAR(10) NOT NULL,               -- クラスコード (文字列、10文字、NULL不可)
    FINALIZEDMONTH VARCHAR(7) NOT NULL,        -- 確定月 (YYYY-MM形式、NULL不可)
    ATTENDANCERATE DECIMAL(6, 5),              -- 出席率 (最大6桁、整数部1桁、小数部5桁)
    ABSENCERATE DECIMAL(6, 5),                 -- 欠席率 (最大6桁、整数部1桁、小数部5桁)

    PRIMARY KEY (ADMISSIONYEAR, CLASSCD, FINALIZEDMONTH),  -- 主キー: 入学年度 + クラスコード + 確定月の組み合わせ
    FOREIGN KEY (CLASSCD) REFERENCES CLASSLIST(CLASSCD)   -- 外部キー制約: CLASSLIST テーブルの CLASSCD を参照
);
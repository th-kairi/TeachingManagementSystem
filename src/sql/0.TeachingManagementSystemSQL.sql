
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

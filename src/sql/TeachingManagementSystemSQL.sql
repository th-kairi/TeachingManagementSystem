
create table STAFF(
	STAFFID varchar(20) not null primary key,
	PASSWORD varchar(20),
	NAME varchar(20),
	CLASS char(3),
	KFLAG char(1)
);

create table CLASSLIST(
	CLASSCD char(3) primary key,
	NAME varchar(20) not null
);

create table STAFFTOCLASS(
	STAFFID varchar(20) not null,
	CLASSCD char(3) not null,
	primary key (STAFFID, CLASSCD),
	foreign key (STAFFID) references STAFF(STAFFID),
	foreign key (CLASSCD) references CLASSLIST(CLASSCD)
);

create table STUDENT(
	STUDENTID varchar(10) not null primary key ,
	NAME varchar(10) ,
	SEX char(2) ,
	STUDENTTEL varchar(15) ,
	PAIRENTTEL varchar(15) ,
	DROPFLAG boolean default FALSE
);

create table CLASS(
	ID serial primary key ,
	CLASSCD char(3) not null ,
	NAME varchar(20),
	STUDENTID varchar(10),
	foreign key (CLASSCD) references CLASSLIST(CLASSCD),
	foreign key (STUDENTID) references STUDENT(STUDENTID)
);

create table SUBJECT(
	SUBJECTCD char(3) not null primary key,
	SUBJECTNAME varchar(20),
	CREDIT int,
	CLOSINGDATE date
);

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

create table ATTENDANCENAME(
	ATTENDANCE char(2) not null primary key ,
	ATTENDANCENAME varchar(10)
);

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

create table NOTIFICATIONNAME(
	NOTIFICATION int primary key,
	NOTIFICATIONNAME varchar(10)
);

create table NOTIFICATION(
	STUDENTID varchar(10),
	NOTIFICATION int,
	NDATE date,
	primary key(STUDENTID, NOTIFICATION),
	foreign key (STUDENTID) references STUDENT(STUDENTID),
	foreign key (NOTIFICATION) references NOTIFICATIONNAME(NOTIFICATION)
);

CREATE SEQUENCE TOTALSEQ;

CREATE TABLE TOTAL(
	JOINEMAIL VARCHAR2(1000) PRIMARY KEY,
	JOINNAME VARCHAR2(1000) ,
	JOINPW VARCHAR2(1000),
	JOINBIRTH VARCHAR2(1000) ,
	JOINSEX VARCHAR2(1000) ,
	PHOTO VARCHAR2(1000),
	MILILTARY VARCHAR2(100) ,
	PHONE VARCHAR2(500) ,
	ADDRESS VARCHAR2(1000) ,
	KAKAO VARCHAR2(100),
	SIGNUP VARCHAR2(10),
	CATEGORY VARCHAR2(100),
	ITSKILL1 VARCHAR2(100) ,
	ITSKILL2 VARCHAR2(100) ,
	ITSKILL3 VARCHAR2(100) ,
	ITSKILL4 VARCHAR2(100) ,
	ITSKILL5 VARCHAR2(100) ,
	ITSCORE VARCHAR2(100),
	CERTIFICATE VARCHAR2(1000),
	LANGUAGENAME VARCHAR2(300),
	LANGUAGESCORE NUMBER,
	LANGUAGEREGDATE VARCHAR2(100),
	CONTEST VARCHAR2(100),
	PRIZE VARCHAR2(50),
	ORGANIZATION VARCHAR2(100),
	STARTORGANIZATION VARCHAR2(100),
	REGDATE VARCHAR2(100),
	CAREER VARCHAR2(50),
	SCHOOLNAME VARCHAR2(100),
	ADMISSION VARCHAR2(100),
	GRADUATE VARCHAR2(100),
	MAJOR VARCHAR2(100),
	GRADE VARCHAR2(30)
);
INSERT INTO TOTAL(JOINEMAIL,JOINNAME,JOINBIRTH,JOINSEX,MILILTARY,PHONE,ADDRESS,ITSKILL1,ITSKILL2,ITSKILL3,ITSKILL4,ITSKILL5,MAJOR) VALUES('igs99275@naver.com','조수민','940802','남성','군필','010-8842-1869','인천광역시 부평구','자바','파이썬','씨언어','씨큐리티','오라클','항소');
SELECT * FROM TOTAL;
DROP TABLE TOTAL;
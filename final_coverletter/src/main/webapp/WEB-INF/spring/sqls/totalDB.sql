-- 유저 기본 정보
DROP SEQUENCE JOIN_SEQ;
DROP TABLE JOINUSER;

CREATE SEQUENCE JOIN_SEQ;

CREATE TABLE JOINUSER
(
    JOINSEQ      NUMBER           NOT NULL, 
    JOINEMAIL    VARCHAR2(200)    NOT NULL, 
    JOINNAME     VARCHAR2(200)    NOT NULL, 
    JOINPW       VARCHAR2(200)    NOT NULL, 
    JOINBIRTH    CHAR(8)          NOT NULL, 
    JOINSEX      CHAR(1)          NOT NULL, 
    PHOTO        VARCHAR2(500), 
    MILILTARY    VARCHAR2(100), 
    PHONE        CHAR(13), 
    ADDRESS      VARCHAR2(200), 
    KAKAO        VARCHAR2(200),             -- 카카오API에 사용
    SINGUP       CHAR(1)          NOT NULL,    -- 가입유무
    CONSTRAINT USER_PK PRIMARY KEY (JOINEMAIL)
);

SELECT * FROM JOINUSER;

INSERT INTO JOINUSER(JOINSEQ,JOINEMAIL,JOINNAME,JOINPW,JOINBIRTH,JOINSEX, SINGUP )
VALUES(JOIN_SEQ.NEXTVAL, 'yeo@gmail.com', '여정현', 'abc', '19960829', 'F', 'Y');
INSERT INTO JOINUSER(JOINSEQ,JOINEMAIL,JOINNAME,JOINPW,JOINBIRTH,JOINSEX, SINGUP )
VALUES(JOIN_SEQ.NEXTVAL, 'abc@naver.com', '이형권', 'abc', '19960829', 'F', 'Y');

DELETE FROM JOINUSER WHERE JOINEMAIL = 'abc@naver.com';


--======================================================================================================
--기존 학력 테이블 + 기존 스킬 테이블 하나의 테이블로 변경, + 유저정보는 세션에서 사용  
DROP SEQUENCE TOTAL_SEQ;
DROP TABLE TOTAL;

CREATE SEQUENCE TOTAL_SEQ;

CREATE TABLE TOTAL(
   TOTALSEQ NUMBER PRIMARY KEY, 
   JOINEMAIL VARCHAR2(1000),
   JOINNAME VARCHAR2(1000),
   JOINPW VARCHAR2(1000),
   JOINBIRTH VARCHAR2(1000) ,
   JOINSEX VARCHAR2(1000) ,
   PHOTO VARCHAR2(1000),
   MILILTARY VARCHAR2(1000) ,
   PHONE VARCHAR2(1000) ,
   ADDRESS VARCHAR2(1000) ,
   KAKAO VARCHAR2(1000),
   SIGNUP VARCHAR2(1000),
   CATEGORY VARCHAR2(1000),
   ITSKILL1 VARCHAR2(1000) ,
   ITSKILL2 VARCHAR2(1000) ,
   ITSKILL3 VARCHAR2(1000) ,
   ITSKILL4 VARCHAR2(1000) ,
   ITSKILL5 VARCHAR2(1000) ,
   ITSCORE1 VARCHAR2(1000) ,
   ITSCORE2 VARCHAR2(1000) ,
   ITSCORE3 VARCHAR2(1000) ,
   ITSCORE4 VARCHAR2(1000) ,
   ITSCORE5 VARCHAR2(1000) ,
   CERTIFICATE VARCHAR2(1000),
   LANGUAGENAME VARCHAR2(1000),
   LANGUAGESCORE VARCHAR2(1000),
   LANGUAGEREGDATE VARCHAR2(1000),
   CONTEST VARCHAR2(1000),
   PRIZE VARCHAR2(1000),
   ORGANIZATION VARCHAR2(1000),
   STARTORGANIZATION VARCHAR2(1000),
   REGDATE VARCHAR2(1000),
   CAREER VARCHAR2(1000),
   SCHOOLNAME VARCHAR2(1000),
   ADMISSION VARCHAR2(1000),
   GRADUATE VARCHAR2(1000),
   MAJOR VARCHAR2(1000),
   GRADE VARCHAR2(1000),
   CONSTRAINT TOTAL_TABLE_FK01 FOREIGN KEY(JOINEMAIL) REFERENCES JOINUSER(JOINEMAIL)
);

INSERT INTO TOTAL(TOTALSEQ,JOINEMAIL,JOINNAME,JOINBIRTH,JOINSEX,MILILTARY,PHONE,ADDRESS,ITSKILL1,ITSKILL2,ITSKILL3,ITSKILL4,ITSKILL5,MAJOR) VALUES(TOTAL_SEQ.NEXTVAL,'abc@naver.com','조수민','940802','남성','군필','010-8842-1869','인천광역시 부평구','자바','파이썬','씨언어','씨큐리티','오라클','항소');

INSERT INTO TOTAL VALUES(TOTAL_SEQ.NEXTVAL,#{joinemail},#{joinname},#{joinpw},#{joinbirth},#{joinsex},#{photo},#{mililtary},#{phone},#{address},null,'Y','category',#{itskill1},#{itskill2},#{itskill3},#{itskill4},#{itskill5},#{itscore1},#{itscore2},#{itscore3},#{itscore4},#{itscore5},#{certificate},#{languagename},#{languagescore},#{languageregdate},#{contest},#{prize},#{organization},#{startorganization},#{regdate},#{career},#{schoolname},#{admission},#{graduate},#{major},#{grade})

UPDATE TOTAL SET 
JOINBIRTH = '', JOINSEX = '', MILILTARY = '', PHONE = '', ADDRESS = '', 
ITSKILL1 = '', ITSKILL2 = '', ITSKILL3 = '', ITSKILL4 = '', ITSKILL5 = '', MAJOR = ''
WHERE JOINEMAIL = 'abc@naver.com';


SELECT * FROM TOTAL;



--======================================================================================================
--파일 업로드 다운로드 댓글 게시판

DROP SEQUENCE BOARD_SEQ;
DROP TABLE BOARD;

CREATE SEQUENCE BOARD_SEQ START WITH 1 INCREMENT BY 1;
CREATE TABLE BOARD
(
    BOARDSEQ     NUMBER            NOT NULL,    
    GROUPNO      NUMBER            NOT NULL,    -- 그룹 번호
    GROUPSEQ     NUMBER            NOT NULL,    -- 같은 그룹내의 순서
    TITLETAB     NUMBER                     ,    -- 탭 구분(첫글null, 댓글0, 대댓1)
    JOINEMAIL    VARCHAR2(200)     NOT NULL,    -- 이메일(ID) 작성자
    TITLE        VARCHAR2(1000),             -- 제목(댓글은 제목없음)
    CONTENT      VARCHAR2(4000)    NOT NULL,    -- 내용
    FILEPATH     VARCHAR2(1000),                -- 파일 경로
    REGDATE      DATE              NOT NULL, 
    CONSTRAINT BOARD_PK PRIMARY KEY (BOARDSEQ),
    CONSTRAINT BOARD_TABLE_FK01 FOREIGN KEY(JOINEMAIL) REFERENCES JOINUSER(JOINEMAIL)
);

SELECT * FROM BOARD;

DELETE FROM BOARD WHERE JOINEMAIL = 'mintparc@gmail.com';

SELECT B.*
      FROM (
         SELECT A.*, ROW_NUMBER() OVER(ORDER BY BOARDSEQ DESC) AS RNUM
         FROM BOARD A
         WHERE GROUPSEQ = 1
         ) B   
         WHERE RNUM BETWEEN  0 + 1 AND 3
         ORDER BY BOARDSEQ DESC

--======================================================================================================
-- 자기소개서 
DROP SEQUENCE TOTAL_SEQ;
DROP SEQUENCE COVERLETTER_SEQ;
DROP SEQUENCE PORTFOLIO_SEQ;

DROP TABLE COVERLETTER;


--전체 시퀀스
CREATE SEQUENCE TOTAL_SEQ;

-- 자소서 == 이력서 시퀀스
CREATE SEQUENCE COVERLETTER_SEQ;

-- 포폴 시퀀스
CREATE SEQUENCE PORTFOLIO_SEQ;

CREATE TABLE COVERLETTER
(
    TOTALSEQ          NUMBER            NOT NULL,    -- 전체 시퀀스    
    JOINEMAIL         VARCHAR2(200)     NOT NULL,    -- 이메일
    CVCATEGORY        VARCHAR2(30)      NOT NULL,    -- 카테고리
    GROUPSEQ          NUMBER            NOT NULL,    -- 자소서, 포폴용 각각의 시퀀스
    GROUPNO            NUMBER         NOT NULL,    -- 그룹번호
    QUESTION          VARCHAR2(1000),                -- 자: 항목(질문), 포: 수행기간
    TITLE             VARCHAR2(500)     NOT NULL,    -- 자: 제목, 포: 프로젝트명
    SUBTITLE          VARCHAR2(500),                 -- 지: 소제목, 포: 개발목표
    CONTENT           VARCHAR2(3000),                -- 지: 내용, 포: 개발환경
    FUNCTIONS        VARCHAR2(3000),               -- 포: 구현기능
    POSITIONS        VARCHAR2(2000),             -- 포: 담당역할
    PARTICIPATION     VARCHAR2(1000),             -- 포: 참여도
    FUNCTIONINFO     VARCHAR2(3000),             -- 포: 기능설명
    VIEWINFO        VARCHAR2(3000),              -- 포: 화면설명
    REGDATE           DATE             NOT NULL,      -- 작성일
    FILEPATH          VARCHAR2(1000),             -- 포 : 이미지 경로
    CONSTRAINT COVERLETTER_PK PRIMARY KEY (TOTALSEQ),
    CONSTRAINT COVERLETTER_CK01 CHECK (CVCATEGORY IN('자소서','포폴')),
    CONSTRAINT COVERLETTER_TABLE_FK01 FOREIGN KEY(JOINEMAIL) REFERENCES JOINUSER(JOINEMAIL)
);

//포폴 추가 예시
INSERT INTO COVERLETTER VALUES(TOTAL_SEQ.NEXTVAL, 'abc@naver.com', '포폴', PORTFOLIO_SEQ.NEXTVAL, 0,
       '수행기간','프로젝트명','개발목표','개발환경','구현기능','담당역할','참여도','기능설명','화면설명',SYSDATE,'경로')

SELECT * FROM COVERLETTER;

DELETE FROM COVERLETTER WHERE groupno=1

--=====================================================================================================================
-- 채용일정 캘린더
DROP SEQUENCE JOBCALENDAR_SEQ;
DROP TABLE JOBCALENDAR;

CREATE SEQUENCE JOBCALENDAR_SEQ;

CREATE TABLE JOBCALENDAR
(
    JOBCALENDARSEQ     NUMBER              NOT NULL,    
    JOINEMAIL          VARCHAR2(200)       NOT NULL, 
    COMPANYSEQ          NUMBER              NOT NULL,      -- 회사테이블 프라이머리키
    COMPANYNAME        VARCHAR2(200)        NOT NULL,    -- 회사명
    BUSINESS           VARCHAR2(1000)      NOT NULL,       -- 채용제목
    ENDDATE            VARCHAR2(20)        NOT NULL,    -- 마감일
    CONSTRAINT JOBCALENDAR_PK PRIMARY KEY (JOBCALENDARSEQ),
    CONSTRAINT JOBCALENDAR_TABLE_FK01 FOREIGN KEY(JOINEMAIL) REFERENCES JOINUSER(JOINEMAIL)
); 

INSERT INTO JOBCALENDAR VALUES(JOBCALENDAR_SEQ.NEXTVAL, 'abc@naver.com', 123, '운토티', '백엔드 개발자 채용', '수시채용');

SELECT * FROM JOBCALENDAR;

SELECT * FROM JOBCALENDAR
WHERE JOINEMAIL = 'abc@naver.com' AND NOT ENDDATE = '수시채용';

SELECT COUNT(*)
FROM JOBCALENDAR
WHERE JOINEMAIL = 'abc@naver.com';

--=====================================================================================================================
-- 후원 내역 테이블
DROP SEQUENCE SUPPORTPAY_SEQ;
DROP TABLE SUPPORTPAY;

CREATE SEQUENCE SUPPORTPAY_SEQ;

CREATE TABLE SUPPORTPAY
(
    TID                    VARCHAR2(200)    NOT NULL,   -- 결제 고유 번호. 결제요청 API 응답에 오는 값과 동일해야 함
    CID                    VARCHAR2(200)    NOT NULL,   -- 가맹점 코드. 결제준비 API에서 요청한 값과 일치해야 함   
    PARTNER_ORDER_ID        VARCHAR2(200)    NOT NULL, -- 가맹점 주문번호   
    JOINEMAIL               VARCHAR2(200)    NOT NULL, -- 가맹점 회원 id   
    PAYMENT_METHOD_TYPE     VARCHAR2(100)    NOT NULL, -- 결제 수단. CARD, MONEY 중 하나   
    AMOUNT_TOTAL            VARCHAR2(100)    NOT NULL, -- 총 결제 금액
    AMOUNT_TAX_FREE         VARCHAR2(100)    NOT NULL, -- 결제 부과세 
    ITEM_NAME               VARCHAR2(100)    NOT NULL, -- 상품 이름. 최대 100자   
    QUANTITY                VARCHAR2(100)    NOT NULL, -- 후원금액
    CREATED_AT              DATE             NOT NULL,   -- 결제 준비 요청 시각   
    CONSTRAINT TID PRIMARY KEY (TID),
    CONSTRAINT SUPPORTPAY_TABLE_FK01 FOREIGN KEY(JOINEMAIL) REFERENCES JOINUSER(JOINEMAIL)
);

SELECT * FROM SUPPORTPAY;
--========================================================================================================================

DROP TABLE QNABOARD;
DROP SEQUENCE QNABOARD_SEQ;

CREATE SEQUENCE QNABOARD_SEQ;
CREATE TABLE QNABOARD
(
    QNABOARDSEQ    NUMBER            NOT NULL, 
    QUESTION       VARCHAR2(1000)    NULL, 
    ANSWER         VARCHAR2(1000)    NULL, 
    CONSTRAINT QNABOARD_PK PRIMARY KEY (QNABOARDSEQ)
);

INSERT INTO QNABOARD VALUES (QNABOARD_SEQ.NEXTVAL, 'Q. 자바 객체지향의 특징?', '추상화, 상속, 다형성, 캡슐화');

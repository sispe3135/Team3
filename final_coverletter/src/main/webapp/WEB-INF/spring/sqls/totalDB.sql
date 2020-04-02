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
VALUES(JOIN_SEQ.NEXTVAL, 'abc@naver.com', 'name', 'abc', '19990101', 'F', 'Y');



--======================================================================================================
--기존 학력 테이블 + 기존 스킬 테이블 하나의 테이블로 변경, + 유저정보는 세션에서 사용  
DROP SEQUENCE TOTAL_SEQ;
DROP TABLE TOTAL;

CREATE SEQUENCE TOTAL_SEQ;

CREATE TABLE TOTAL(
   TOTALSEQ NUMBER, 
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
    TITLE        VARCHAR2(1000)             ,    -- 제목(댓글은 제목없음)
    CONTENT      VARCHAR2(4000)    NOT NULL,    -- 내용
    FILEPATH     VARCHAR2(1000),             -- 파일 경로
    REGDATE      DATE              NOT NULL, 
    CONSTRAINT BOARD_PK PRIMARY KEY (BOARDSEQ)
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
    TOTALSEQ         NUMBER            NOT NULL,   -- 전체 시퀀스    
    JOINEMAIL         VARCHAR2(200)     NOT NULL,    -- 이메일
    CVCATEGORY        VARCHAR2(30)      NOT NULL,   -- 카테고리
    GROUPSEQ        NUMBER         NOT NULL,    -- 자소서, 포폴용 각각의 시퀀스
    QUESTION          VARCHAR2(1000),             -- 항목(질문)
    TITLE             VARCHAR2(500)      NOT NULL,    -- 제목
    SUBTITLE          VARCHAR2(500),             -- 소제목(내용의 핵심 키워드)
    CONTENT           VARCHAR2(3000),             -- 내용
    REGDATE           DATE            NOT NULL,   -- 작성일
    FILEPATH        VARCHAR2(1000),
    CONSTRAINT COVERLETTER_PK PRIMARY KEY (TOTALSEQ),
    CONSTRAINT COVERLETTER_CK01 CHECK (CVCATEGORY IN('자소서','포폴'))
);


INSERT INTO COVERLETTER VALUES(TOTAL_SEQ.NEXTVAL, 'USER@GMAIL.COM', '자소서', COVERLETTER_SEQ.NEXTVAL, '자신의 장점은?', '제목:', '소제목', '내용', SYSDATE, '파일')

INSERT INTO COVERLETTER VALUES(TOTAL_SEQ.NEXTVAL, 'USER@GMAIL.COM', '포폴', PORTFOLIO_SEQ.NEXTVAL, NULL, '제목:', NULL, NULL, SYSDATE, '파일')



SELECT * FROM COVERLETTER;

--=====================================================================================================================
-- 채용일정 캘린더
DROP SEQUENCE JOBCALENDAR_SEQ;
DROP TABLE JOBCALENDAR;

CREATE SEQUENCE JOBCALENDAR_SEQ;

CREATE TABLE JOBCALENDAR
(
    JOBCALENDARSEQ     NUMBER                NOT NULL,    
    JOINEMAIL          VARCHAR2(200)       NOT NULL, 
    COMPANYNAMESEQ     NUMBER               NOT NULL,      -- 회사테이블 프라이머리키
    COMPANYNAME        VARCHAR2(20)          NOT NULL,    -- 회사명
    BUSINESS             VARCHAR2(1000)      NOT NULL,       -- 채용제목
    ENDDATE            VARCHAR2(20)        NOT NULL,    -- 마감일
    CONSTRAINT JOBCALENDAR_PK PRIMARY KEY (JOBCALENDARSEQ)
); 

SELECT * FROM JOBCALENDAR

--=====================================================================================================================
-- 후원 내역 테이블
DROP SEQUENCE SUPPORTPAY_SEQ;
DROP TABLE SUPPORTPAY;


CREATE SEQUENCE SUPPORTPAY_SEQ;

CREATE TABLE SUPPORTPAY
(
   TID                 VARCHAR2(200)    NOT NULL,   -- 결제 고유 번호. 결제요청 API 응답에 오는 값과 동일해야 함
   CID                 VARCHAR2(200)    NOT NULL,   -- 가맹점 코드. 결제준비 API에서 요청한 값과 일치해야 함   
    PARTNER_ORDER_ID        VARCHAR2(200)    NOT NULL, -- 가맹점 주문번호   
    PARTNER_USER_ID            VARCHAR2(200)    NOT NULL, -- 가맹점 회원 id   
    PAYMENT_METHOD_TYPE      VARCHAR2(100)    NOT NULL, -- 결제 수단. CARD, MONEY 중 하나   
    AMOUNT_TOTAL             VARCHAR2(100)    NOT NULL, -- 총 결제 금액
    AMOUNT_TAX_FREE         VARCHAR2(100)    NOT NULL, -- 결제 부과세 
    ITEM_NAME              VARCHAR2(100)    NOT NULL, -- 상품 이름. 최대 100자   
    QUANTITY               VARCHAR2(100)    NOT NULL, -- 후원금액
    CREATED_AT             DATE             NOT NULL,   -- 결제 준비 요청 시각   
    CONSTRAINT TID PRIMARY KEY (TID)
);

SELECT * FROM SUPPORTPAY;
--========================================================================================================================
-- a : 채용중인 정보 페이지에서 가져옴
-- a-b : 채용분야에서 url타고 들어가서 '채용상세' 가져왔음
-- 기본정보 : 시퀀스, 그룹번호, 회사명, 이미지url, 한줄 소개, 모집대상(a), 주요업무(a-b), 채용상세(a-b), 연봉(a), 경력(a), 언어특기(a), 마감일(a), 기업소개글
-- 복지해택 : 개인장비, 자기개발, 식사시간, 연차휴가, 근무형태, 보험의료  
-- 기업정보 : 설립일, 구성원, 홈페이지, 사무실위치, 산업분야


DROP SEQUENCE COMPANYSEQ;
DROP TABLE COMPANY;

CREATE SEQUENCE COMPANYSEQ;

CREATE TABLE COMPANY
(
   -- 기본정보 : 시퀀스, 그룹번호, 회사명, 이미지url, 한줄 소개, 모집대상(a), 주요업무(a-b), 채용상세(a-b), 연봉(a), 경력(a), 언어특기(a), 마감일(a), 기업소개글
    COMPANYSEQ       NUMBER            NOT NULL, 
    GROUPNO          NUMBER            NOT NULL, 
    COMPANYNAME      VARCHAR2(200)     NOT NULL, 
    IMGURL           VARCHAR2(1000)    NOT NULL, 
    ONEINTRO         VARCHAR2(1000)    NOT NULL, 
    BUSINESS         VARCHAR2(1000)    NOT NULL, 
    MAINBUSINESS    VARCHAR2(3000)      NOT NULL, 
    JOBDETAIL        CLOB            NOT NULL, 
    SALARY           VARCHAR2(200)     NOT NULL, 
    TARGET           VARCHAR2(20)      NOT NULL,
    LANGUAGES        VARCHAR2(1000)    NOT NULL, 
    ENDDATE          VARCHAR2(100)     NOT NULL, 
    INTRO            VARCHAR2(3000)    NOT NULL, 
    -- 복지해택 : 개인장비, 자기개발, 식사시간, 연차휴가, 근무형태, 보험의료  
    GIVETOOL         VARCHAR2(1000)    NOT NULL, 
    SELFGROWTH       VARCHAR2(1000)    NOT NULL, 
    MEALTIME         VARCHAR2(1000)    NOT NULL, 
    HOLIDAY          VARCHAR2(1000)    NOT NULL, 
    WORKINGHOUR      VARCHAR2(1000)    NOT NULL, 
    INSURANCE        VARCHAR2(1000)    NOT NULL, 
    -- 기업상세정보 : 설립일, 구성원, 홈페이지, 사무실위치, 산업분야
    INCORPORATION    VARCHAR2(200)     NOT NULL, 
    TOTALMEMBER      VARCHAR2(200)     NOT NULL, 
    HOMEPAGE         VARCHAR2(1000)    NOT NULL, 
    LOCATION         VARCHAR2(1000)    NOT NULL, 
    MAINFIELD        VARCHAR2(1000)    NOT NULL, 
    CONSTRAINT COMPANY_PK PRIMARY KEY (COMPANYSEQ)
)

insert into COMPANY values(COMPANYSEQ.NEXTVAL , 0, '회사이름', '이미지 url', '한 줄 회사소개', 
                     '모집대상', '주요업무', '채용상세', '연봉', '경력', '언어특기', '마감일(yy/mm/dd)', 
                     '기업소개', 
                     '개인지급장비', '자기개발 지원', '식시시간', '연차/휴가', '근무형태', '보험의료',
                      '설립일', '구성원(총원)', '홈페이지', '위치', '산업분야'
                     );

SELECT * FROM COMPANY;
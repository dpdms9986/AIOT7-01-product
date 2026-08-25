--계정 생성
ALTER SESSION SET "_ORACLE_SCRIPT" = TRUE;
CREATE USER Product IDENTIFIED BY 123456;
ALTER USER Product DEFAULT TABLESPACE users;
ALTER USER Product QUOTA UNLIMITED ON users;
GRANT dba TO Product;

--테이블 삭제
DROP TABLE Product;
DROP sequence Product_SEQ;


--테이블 생성

CREATE TABLE product (
    no          NUMBER  PRIMARY KEY,
    name       VARCHAR2(200) NOT NULL,
    exdate     VARCHAR2(100),
    created_at  DATE    DEFAULT sysdate,
    updated_at  DATE    DEFAULT sysdate
);

--글번호 시퀀스
CREATE SEQUENCE Product_SEQ
    START WITH 1
    INCREMENT BY 1
    NOCYCLE
    NOCACHE;


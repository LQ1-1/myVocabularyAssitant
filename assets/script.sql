create database VocabularyAssitant;
use VocabularyAssitant;


LOAD DATA LOCAL INFILE '/Users/zero/Documents/c_idea_code/VocabularyAssitant/assets/EnglishVocabulary.csv'
INTO TABLE EnglishVocabularyTable
CHARACTER SET utf8mb4
FIELDS TERMINATED BY ','
ENCLOSED BY '"'
LINES TERMINATED BY '\r'
IGNORE 1 ROWS
(vId, vWord, vZh_meaning);

SHOW VARIABLES LIKE 'local_infile';
SET GLOBAL local_infile = 1;


create table UserTable
(
uId varchar(32) primary key,
uPassword varchar(256),
uName varchar(128),
uStatus int,
uStyle int,
uUpdated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);
-- 邀请码信息表
create table InviteCodeTable
(
uId varchar(32) primary key,
iOwnReferralCode varchar(16) not null unique,
iAppliedReferralCode varchar(16),
constraint fk_InviteCodeTable foreign key (uId) references UserTable(uId)
);

-- refresh token 表
CREATE TABLE RefreshTokenTable (
    rId VARCHAR(64) PRIMARY KEY,
    uId VARCHAR(32) NOT NULL,
    refreshTokenHash VARCHAR(64) NOT NULL UNIQUE,
    deviceId VARCHAR(128) NOT NULL,
    deviceType VARCHAR(32) NOT NULL,
    issuedAt DATETIME NOT NULL,
    expiresAt DATETIME NOT NULL,
    lastRefreshedAt DATETIME NOT NULL,
    revokedAt DATETIME NULL,
    status INT NOT NULL DEFAULT 1,
    createdAt DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updatedAt DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT fk_refresh_user
        FOREIGN KEY (uId) REFERENCES UserTable(uId)
        ON DELETE CASCADE
        ON UPDATE CASCADE,
    INDEX idx_refresh_uid (uId),
    INDEX idx_refresh_expiresAt (expiresAt),
    INDEX idx_refresh_uid_device (uId, deviceId)
);



-- 暂时不通用
create table EnglishVocabularyTable
(
vId varchar(32) primary key,
vWord text,
vZh_meaning text
);
-- 暂时不通用
create table EnglishVocabularyLearningRecordsTable
(
vId varchar(32),
uId varchar(32),
lDate date comment'学习日期',
constraint fk_EnglishVocabularyLearningRecordsTable_vId foreign key(vId) references EnglishVocabularyTable(vId),
constraint fk_UserTable_uId foreign key(uId) references UserTable(uId),
CONSTRAINT pk_EnglishVocabularyLearningRecordsTable PRIMARY KEY (vId, uId),
index idx_EnglishVocabularyLearningRecordsTable_uId(uId)
);

select * from EnglishVocabularyTable ET left join EnglishVocabularyLearningRecordsTable ELRT on ET.vId = ELRT.vId where ELRT.vId is not null limit 100;
select * from EnglishVocabularyTable ET left join EnglishVocabularyLearningRecordsTable ELRT on ET.vId = ELRT.vId where ELRT.vId is null limit 100;
select * from EnglishVocabularyTable ET left join EnglishVocabularyLearningRecordsTable ELRT on ET.vId = ELRT.vId where ELRT.vId is null order by rand() limit 1;
select ET.*, ELRT.uId, ELRT.lDate, EXP(-0.8 * DATEDIFF(CURDATE(), ELRT.lDate)) as retention_value from EnglishVocabularyLearningRecordsTable ELRT join EnglishVocabularyTable ET on ET.vId = ELRT.vId order by retention_value asc; -- 按e(-0.8t)的值从小到大来排序
select ET.*, ELRT.uId, ELRT.lDate, EXP(-0.8 * DATEDIFF(CURDATE(), ELRT.lDate)) as retention_value from EnglishVocabularyLearningRecordsTable ELRT join EnglishVocabularyTable ET on ET.vId = ELRT.vId where EXP(-0.8 * DATEDIFF(CURDATE(), ELRT.lDate)) < 0.4 order by retention_value asc limit 1; -- e(-0.8t)留存值小于0.4中的记录最小的那一个




-- 通用
create table CustomizedVocabularyNotebookTable
(
nId varchar(32) primary key comment'笔记本Id',
uId varchar(32) not null comment'创建者Id',
nName varchar(256) not null comment'单词本名词',
nDescription varchar(2048) comment'单词本介绍',
constraint fk_CustomizedVocabularyNotebookTable foreign key (uId) references UserTable(uId)
);
-- 通用
create table CustomizeVocabularyTable
(
cId varchar(32) comment'自定义单词Id',
vContent varchar(256),
vZh_meaning varchar(2048),
nId varchar(32) not null comment'笔记本Id',
lDate date comment'学习日期',
constraint fk_CustomizeVocabularyTable foreign key (nId) references CustomizedVocabularyNotebookTable(nId),
constraint pk_CustomizeVocabularyTable primary key (cId, nId),
index idx_CustomizeVocabularyTable_nId(nId)
);


-- 用户自己创建的单词本
DELIMITER $$

CREATE PROCEDURE insert_customized_vocabulary_notebook(
    IN p_uId VARCHAR(32),
    IN p_nName VARCHAR(256),
    IN p_nDescription VARCHAR(2048),
    OUT p_result INT
)
proc_label: BEGIN
    DECLARE v_next_nid BIGINT DEFAULT 1;
    DECLARE v_user_count INT DEFAULT 0;

    DECLARE EXIT HANDLER FOR SQLEXCEPTION
    BEGIN
        ROLLBACK;
        SET p_result = 0;
    END;

    START TRANSACTION;

    -- 先默认失败，最后成功时再改成 1
    SET p_result = 0;

    -- 检查用户是否存在，并锁住该用户记录
    SELECT COUNT(*)
    INTO v_user_count
    FROM UserTable
    WHERE uId = p_uId
    FOR UPDATE;

    IF v_user_count = 0 THEN
        ROLLBACK;
        SET p_result = 0;
        LEAVE proc_label;
    END IF;

    -- 计算下一个 nId
    SELECT COALESCE(MAX(CAST(nId AS UNSIGNED)), 0) + 1
    INTO v_next_nid
    FROM CustomizedVocabularyNotebookTable;

    INSERT INTO CustomizedVocabularyNotebookTable (
        nId,
        uId,
        nName,
        nDescription
    )
    VALUES (
        CAST(v_next_nid AS CHAR),
        p_uId,
        p_nName,
        p_nDescription
    );

    COMMIT;
    SET p_result = 1;
END$$

DELIMITER ;



-- 给自定义单词本添加记录的存储过程
DELIMITER $$

CREATE PROCEDURE insert_customize_vocabulary(
    IN p_vContent VARCHAR(256),
    IN p_vZh_meaning VARCHAR(2048),
    IN p_nId VARCHAR(32),
    IN p_lDate DATE,
    OUT p_result INT
)
proc_label: BEGIN
    DECLARE v_next_cid BIGINT DEFAULT 1;
    DECLARE v_count INT DEFAULT 0;

    DECLARE EXIT HANDLER FOR SQLEXCEPTION
    BEGIN
        ROLLBACK;
        SET p_result = 0;
    END;

    START TRANSACTION;

    -- 先默认失败，最后成功时再改成 1
    SET p_result = 0;

    -- 锁住对应笔记本，保证同一个 nId 下并发互斥
    SELECT COUNT(*)
    INTO v_count
    FROM CustomizedVocabularyNotebookTable
    WHERE nId = p_nId
    FOR UPDATE;

    IF v_count = 0 THEN
        ROLLBACK;
        SET p_result = 0;
        LEAVE proc_label;
    END IF;

    -- 计算该笔记本下下一个 cId
    SELECT COALESCE(MAX(CAST(cId AS UNSIGNED)), 0) + 1
    INTO v_next_cid
    FROM CustomizeVocabularyTable
    WHERE nId = p_nId;

    INSERT INTO CustomizeVocabularyTable (
        cId,
        vContent,
        vZh_meaning,
        nId,
        lDate
    )
    VALUES (
        CAST(v_next_cid AS CHAR),
        p_vContent,
        p_vZh_meaning,
        p_nId,
        p_lDate
    );

    COMMIT;
    SET p_result = 1;
END$$

DELIMITER ;

create table LiveActivitySessionTable
(
    activityId varchar(128) primary key comment 'iOS Live Activity id',
    uId varchar(32) not null comment '所属用户',
    pushToken varchar(512) not null unique comment '该 Live Activity 的 push token',
    status int not null default 1 comment '1=active, 0=ended',
    startedAt datetime not null default current_timestamp,
    lastPushedAt datetime null,
    expiresAt datetime null,
    updatedAt datetime not null default current_timestamp on update current_timestamp,
    constraint fk_LiveActivitySessionTable_uId foreign key (uId) references UserTable(uId),
    index idx_LiveActivitySessionTable_uId(uId),
    index idx_LiveActivitySessionTable_status(status)
);

create table PushLogTable
(
    pId varchar(64) primary key,
    uId varchar(32) not null,
    activityId varchar(128) not null,
    wordId varchar(32),
    pushType varchar(32) not null,
    pushStatus int not null comment '1=success, 0=failed',
    responseMessage varchar(2048),
    createdAt datetime not null default current_timestamp,
    index idx_PushLogTable_uId(uId),
    index idx_PushLogTable_activityId(activityId)
);




-- drop procedure insert_customized_vocabulary_notebook;
-- drop procedure insert_customize_vocabulary;



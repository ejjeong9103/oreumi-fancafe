CREATE TABLE `rank_up_board` (
	`id`	bigint	NOT NULL,
	`title`	text	NOT NULL,
	`content`	text	NOT NULL,
	`rank_up_state`	int	NOT NULL	DEFAULT 1,
	`created_at`	timestamp	NOT NULL	DEFAULT now(),
	`updated_at`	timestamp	NOT NULL	DEFAULT now(),
	`rank_id`	int	NOT NULL,
	`user_id`	varchar(100)	NOT NULL
);

CREATE TABLE `user` (
	`user_id`	varchar(100)	NOT NULL,
	`user_pw`	varchar(2000)	NOT NULL,
	`address`	varchar(2000)	NOT NULL,
	`address_detail`	varchar(2000)	NOT NULL,
	`email`	varchar(2000)	NOT NULL	unique,
	`created_at`	timestamp	NOT NULL,
	`role`	int	NOT NULL	DEFAULT 1,
	`state`	int	NOT NULL	DEFAULT 1,
	`profile_image_address`	varchar(2000)	NOT NULL	DEFAULT default_image
);

CREATE TABLE `free_board` (
	`id`	bigint	NOT NULL,
	`title`	text	NOT NULL,
	`content`	text	NOT NULL,
	`created_at`	timestamp	NOT NULL	DEFAULT now(),
	`updated_at`	timestamp	NOT NULL	DEFAULT now(),
	`user_id`	varchar(100)	NOT NULL
);

CREATE TABLE `report_board` (
	`id`	bigint	NOT NULL,
	`title`	text	NOT NULL,
	`content`	text	NOT NULL,
	`created_at`	timestamp	NOT NULL	DEFAULT now(),
	`updated_at`	timestamp	NOT NULL	DEFAULT now(),
	`user_id`	varchar(100)	NOT NULL
);

CREATE TABLE `qa_board` (
	`id`	bigint	NOT NULL,
	`title`	text	NOT NULL,
	`content`	text	NOT NULL,
	`created_at`	timestamp	NOT NULL	DEFAULT now(),
	`updated_at`	timestamp	NOT NULL	DEFAULT now(),
	`state`	int	NOT NULL	DEFAULT 1,
	`user_id`	varchar(100)	NOT NULL
);

CREATE TABLE `enjoy_board` (
	`id`	bigint	NOT NULL,
	`content`	text	NOT NULL,
	`title`	text	NOT NULL,
	`created_att`	timestamp	NOT NULL	DEFAULT now(),
	`updated_at`	timestamp	NOT NULL	DEFAULT now(),
	`Field`	varchar(2000)	NULL,
	`user_id`	varchar(100)	NOT NULL
);

CREATE TABLE `community_board` (
	`id`	bigint	NOT NULL,
	`title`	text	NOT NULL,
	`content`	text	NOT NULL,
	`created_at`	timestamp	NOT NULL	DEFAULT now(),
	`updated_att`	timestamp	NOT NULL	DEFAULT now(),
	`user_id`	varchar(100)	NOT NULL
);

CREATE TABLE `notice_board` (
	`id`	bigint	NOT NULL,
	`title`	text	NOT NULL,
	`content`	text	NOT NULL,
	`created_at`	timestamp	NOT NULL	DEFAULT now(),
	`updated_at`	timestamp	NOT NULL	DEFAULT now(),
	`user_id`	varchar(100)	NOT NULL
);

CREATE TABLE `rank` (
	`id`	int	NOT NULL,
	`name`	varchar(20)	NOT NULL
);

CREATE TABLE `community_reply` (
	`id`	bigint	NOT NULL,
	`content`	text	NOT NULL,
	`created_at`	timestamp	NOT NULL	DEFAULT now(),
	`updated_at`	timestamp	NOT NULL	DEFAULT now(),
	`hierarchy`	int	NOT NULL,
	`orders`	int	NOT NULL,
	`group`	int	NOT NULL,
	`community_id`	bigint	NOT NULL,
	`user_id`	varchar(100)	NOT NULL
);

CREATE TABLE `qa_reply` (
	`id`	bigint	NOT NULL,
	`qa_id`	bigint	NOT NULL,
	`user_id`	varchar(100)	NOT NULL,
	`content`	text	NOT NULL,
	`created_at`	timestamp	NOT NULL	DEFAULT now(),
	`updated_at`	timestamp	NOT NULL	DEFAULT now(),
	`hierarchy`	int	NOT NULL,
	`orders`	int	NOT NULL,
	`group`	int	NOT NULL
);

CREATE TABLE `free_reply` (
	`id`	bigint	NOT NULL,
	`content`	text	NOT NULL,
	`created_at`	timestamp	NOT NULL	DEFAULT now(),
	`updated_at`	timestamp	NOT NULL	DEFAULT now(),
	`hierarchy`	int	NOT NULL,
	`orders`	int	NOT NULL,
	`group`	int	NOT NULL,
	`free_id`	bigint	NOT NULL	,
	`user_id`	varchar(100)	NOT NULL
);

CREATE TABLE `enjoy_reply` (
	`id`	bigint	NOT NULL,
	`content`	text	NOT NULL,
	`created_at`	timestamp	NOT NULL	DEFAULT now(),
	`updated_at`	timestamp	NOT NULL	DEFAULT now(),
	`hierarchy`	int	NOT NULL,
	`orders`	int	NOT NULL,
	`group`	int	NOT NULL,
	`enjoy_id`	bigint	NOT NULL	,
	`user_id`	varchar(100)	NOT NULL
);

ALTER TABLE `rank_up_board` ADD CONSTRAINT `PK_RANK_UP_BOARD` PRIMARY KEY (
	`id`
);

ALTER TABLE `user` ADD CONSTRAINT `PK_USER` PRIMARY KEY (
	`user_id`
);

ALTER TABLE `free_board` ADD CONSTRAINT `PK_FREE_BOARD` PRIMARY KEY (
	`id`
);

ALTER TABLE `report_board` ADD CONSTRAINT `PK_REPORT_BOARD` PRIMARY KEY (
	`id`
);

ALTER TABLE `qa_board` ADD CONSTRAINT `PK_QA_BOARD` PRIMARY KEY (
	`id`
);

ALTER TABLE `enjoy_board` ADD CONSTRAINT `PK_ENJOY_BOARD` PRIMARY KEY (
	`id`
);

ALTER TABLE `community_board` ADD CONSTRAINT `PK_COMMUNITY_BOARD` PRIMARY KEY (
	`id`
);

ALTER TABLE `notice_board` ADD CONSTRAINT `PK_NOTICE_BOARD` PRIMARY KEY (
	`id`
);

ALTER TABLE `rank` ADD CONSTRAINT `PK_RANK` PRIMARY KEY (
	`id`
);

ALTER TABLE `community_reply` ADD CONSTRAINT `PK_COMMUNITY_REPLY` PRIMARY KEY (
	`id`
);

ALTER TABLE `qa_reply` ADD CONSTRAINT `PK_QA_REPLY` PRIMARY KEY (
	`id`
);

ALTER TABLE `free_reply` ADD CONSTRAINT `PK_FREE_reply` PRIMARY KEY (
	`id`
);

ALTER TABLE `enjoy_reply` ADD CONSTRAINT `PK_ENJOY_REPLY` PRIMARY KEY (
	`id`
);

ALTER TABLE `rank_up_board` ADD CONSTRAINT `FK_rank_TO_rank_up_board_1` FOREIGN KEY (
	`rank_id`
)
REFERENCES `rank` (
	`id`
);

ALTER TABLE `rank_up_board` ADD CONSTRAINT `FK_user_TO_rank_up_board_1` FOREIGN KEY (
	`user_id`
)
REFERENCES `user` (
	`user_id`
);

ALTER TABLE `free_board` ADD CONSTRAINT `FK_user_TO_free_board_1` FOREIGN KEY (
	`user_id`
)
REFERENCES `user` (
	`user_id`
);

ALTER TABLE `report_board` ADD CONSTRAINT `FK_user_TO_report_board_1` FOREIGN KEY (
	`user_id`
)
REFERENCES `user` (
	`user_id`
);

ALTER TABLE `qa_board` ADD CONSTRAINT `FK_user_TO_qa_board_1` FOREIGN KEY (
	`user_id`
)
REFERENCES `user` (
	`user_id`
);

ALTER TABLE `enjoy_board` ADD CONSTRAINT `FK_user_TO_enjoy_board_1` FOREIGN KEY (
	`user_id`
)
REFERENCES `user` (
	`user_id`
);

ALTER TABLE `community_board` ADD CONSTRAINT `FK_user_TO_community_board_1` FOREIGN KEY (
	`user_id`
)
REFERENCES `user` (
	`user_id`
);


ALTER TABLE `community_reply` ADD CONSTRAINT `FK_community_board_TO_community_reply_1` FOREIGN KEY (
	`community_id`
)
REFERENCES `community_board` (
	`id`
);

ALTER TABLE `community_reply` ADD CONSTRAINT `FK_user_TO_community_reply_1` FOREIGN KEY (
	`user_id`
)
REFERENCES `user` (
	`user_id`
);

ALTER TABLE `qa_reply` ADD CONSTRAINT `FK_qa_board_TO_qa_reply_1` FOREIGN KEY (
	`id`
)
REFERENCES `qa_board` (
	`id`
);

ALTER TABLE `qa_reply` ADD CONSTRAINT `FK_user_TO_qa_reply_1` FOREIGN KEY (
	`user_id`
)
REFERENCES `user` (
	`user_id`
);

ALTER TABLE `free_reply` ADD CONSTRAINT `FK_free_board_TO_free_reply_1` FOREIGN KEY (
	`free_id`
)
REFERENCES `free_board` (
	`id`
);

ALTER TABLE `free_reply` ADD CONSTRAINT `FK_user_TO_free_reply_1` FOREIGN KEY (
	`user_id`
)
REFERENCES `user` (
	`user_id`
);

ALTER TABLE `enjoy_reply` ADD CONSTRAINT `FK_enjoy_board_TO_enjoy_reply_1` FOREIGN KEY (
	`enjoy_id`
)
REFERENCES `enjoy_board` (
	`id`
);

ALTER TABLE `enjoy_reply` ADD CONSTRAINT `FK_user_TO_enjoy_reply_1` FOREIGN KEY (
	`user_id`
)
REFERENCES `user` (
	`user_id`
);


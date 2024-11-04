DROP TABLE IF EXISTS `help`;

CREATE TABLE `help` (
                        `id`	bigint	NOT NULL,
                        `title`	text	NOT NULL,
                        `content`	text	NOT NULL,
                        `created_at`	timestamp	NOT NULL	DEFAULT now(),
                        `user_id`	varchar(100)	NOT NULL,
                        `admin_id`	varchar(100)	NULL,
                        `answer`	text	NULL,
                        `state`	int	NOT NULL,
                        `answered_at`	timestamp	NULL
);

DROP TABLE IF EXISTS `reply`;

CREATE TABLE `reply` (
                         `id`	bigint	NOT NULL,
                         `content`	text	NOT NULL,
                         `created_at`	timestamp	NOT NULL	DEFAULT now(),
                         `updated_at`	timestamp	NOT NULL	DEFAULT now(),
                         `hierarchy`	int	NOT NULL,
                         `orders`	int	NOT NULL,
                         `group`	int	NOT NULL,
                         `board_id`	bigint	NOT NULL,
                         `user_id`	varchar(100)	NOT NULL
);

DROP TABLE IF EXISTS `board`;

CREATE TABLE `board` (
                         `id`	bigint	NOT NULL ,
                         `title`	text	NOT NULL,
                         `content`	text	NOT NULL,
                         `created_at`	timestamp	NOT NULL	DEFAULT now(),
                         `updated_at`	timestamp	NOT NULL	DEFAULT now(),
                         `user_id`	varchar(100)	NOT NULL,
                         `board_type`	int	NOT NULL,
                         `board_category_name`	varchar(100)	NULL,
                         `state`	int	NULL
);

DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
                        `user_id`	varchar(100)	NOT NULL,
                        `user_pw`	varchar(2000)	NOT NULL,
                        `address`	varchar(2000)	NOT NULL,
                        `address_detail`	varchar(2000)	NOT NULL,
                        `email`	varchar(2000)	NOT NULL,
                        `created_at`	timestamp	NOT NULL,
                        `role`	int	NOT NULL	DEFAULT 1,
                        `state`	int	NOT NULL	DEFAULT 1,
                        `profile_image_address`	varchar(2000)	NOT NULL
);



DROP TABLE IF EXISTS `board_category`;

CREATE TABLE `board_category` (
                                  `id`	bigint	NOT NULL,
                                  `board_category_name`	varchar(100)	NOT NULL,
                                  `board_type_id`	bigint	NOT NULL
);

DROP TABLE IF EXISTS `board_type`;

CREATE TABLE `board_type` (
                              `id`	bigint	NOT NULL,
                              `board_name`	varchar(100)	NOT NULL,
                              `board_type`	int	NOT NULL
);

ALTER TABLE `user` ADD CONSTRAINT `PK_USER` PRIMARY KEY (
                                                         `user_id`
    );

ALTER TABLE `board` ADD CONSTRAINT `PK_BOARD` PRIMARY KEY (
                                                           `id`
    );

ALTER TABLE `help` ADD CONSTRAINT `PK_HELP` PRIMARY KEY (
                                                         `id`
    );

ALTER TABLE `reply` ADD CONSTRAINT `PK_REPLY` PRIMARY KEY (
                                                           `id`
    );

ALTER TABLE `board_category` ADD CONSTRAINT `PK_BOARD_CATEGORY` PRIMARY KEY (
                                                                             `id`
    );

ALTER TABLE `board_type` ADD CONSTRAINT `PK_BOARD_TYPE` PRIMARY KEY (
                                                                     `id`
    );

ALTER TABLE `board` ADD CONSTRAINT `FK_user_TO_board_1` FOREIGN KEY (
                                                                     `user_id`
    )
    REFERENCES `user` (
                       `user_id`
        );

ALTER TABLE `help` ADD CONSTRAINT `FK_user_TO_help_1` FOREIGN KEY (
                                                                   `user_id`
    )
    REFERENCES `user` (
                       `user_id`
        );

ALTER TABLE `reply` ADD CONSTRAINT `FK_board_TO_reply_1` FOREIGN KEY (
                                                                      `board_id`
    )
    REFERENCES `board` (
                        `id`
        );

ALTER TABLE `reply` ADD CONSTRAINT `FK_user_TO_reply_1` FOREIGN KEY (
                                                                     `user_id`
    )
    REFERENCES `user` (
                       `user_id`
        );

ALTER TABLE `board_category` ADD CONSTRAINT `FK_board_type_TO_board_category_1` FOREIGN KEY (
                                                                                             `board_type_id`
    )
    REFERENCES `board_type` (
                             `id`
        );

ALTER TABLE project.user
    ADD COLUMN `nick_name` VARCHAR(100) NOT NULL;


-- Step 1: Drop foreign key constraint in reply table
ALTER TABLE reply DROP FOREIGN KEY FK_board_TO_reply_1;

-- Step 2: Modify board table to add AUTO_INCREMENT to id
ALTER TABLE board MODIFY id BIGINT NOT NULL AUTO_INCREMENT;

-- Step 3: Re-add the foreign key constraint
ALTER TABLE reply ADD CONSTRAINT FK_board_TO_reply_1 FOREIGN KEY (board_id) REFERENCES board(id);
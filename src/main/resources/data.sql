DROP TABLE IF EXISTS `help`;

CREATE TABLE `help` (
                        `id`	bigint	NOT NULL AUTO_INCREMENT,
                        `title`	text	NOT NULL,
                        `content`	text	NOT NULL,
                        `created_at`	timestamp	NOT NULL	DEFAULT now(),
                        `user_id`	varchar(100)	NOT NULL,
                        `admin_id`	varchar(100)	NULL,
                        `answer`	text	NULL,
                        `state`	int	NOT NULL,
                        `answered_at`	timestamp	NULL,
                        `help_type` INT NOT NULL ,
                        PRIMARY KEY (`id`)
);

DROP TABLE IF EXISTS `reply`;

CREATE TABLE `reply` (
                         `id`	bigint	NOT NULL AUTO_INCREMENT,
                         `content`	text	NOT NULL,
                         `created_at`	timestamp	NOT NULL	DEFAULT now(),
                         `updated_at`	timestamp	NOT NULL	DEFAULT now(),
                         `hierarchy`	int	NOT NULL,
                         `orders`	int	NOT NULL,
                         `group`	int	NOT NULL,
                         `board_id`	bigint	NOT NULL,
                         `user_id`	varchar(100)	NOT NULL,
                         PRIMARY KEY (`id`)
);

DROP TABLE IF EXISTS `board`;

CREATE TABLE `board` (
                         `id`	bigint	NOT NULL AUTO_INCREMENT,
                         `title`	text	NOT NULL,
                         `content`	text	NOT NULL,
                         `created_at`	timestamp	NOT NULL	DEFAULT now(),
                         `updated_at`	timestamp	NOT NULL	DEFAULT now(),
                         `user_id`	varchar(100)	NOT NULL,
                         `board_type`	int	NOT NULL,
                         `board_category_name`	varchar(100)	NULL,
                         `state`	int	NULL,
                         PRIMARY KEY (`id`)
);


DROP TABLE IF EXISTS `board_category`;

CREATE TABLE `board_category` (
                                  `id`	bigint	NOT NULL AUTO_INCREMENT,
                                  `board_category_name`	varchar(100)	NOT NULL,
                                  `board_type_id`	bigint	NOT NULL,
                                  PRIMARY KEY (`id`)
);

DROP TABLE IF EXISTS `board_type`;

CREATE TABLE `board_type` (
                              `id`	bigint	NOT NULL AUTO_INCREMENT,
                              `board_name`	varchar(100)	NOT NULL,
                              `board_type`	int	NOT NULL,
                              PRIMARY KEY (`id`)
);

DROP TABLE IF EXISTS `USER_ROLE`;

CREATE TABLE `USER_ROLE` (
                             `user_id`	varchar(100)	NOT NULL,
                             `role_id`	int	NOT NULL
);

DROP TABLE IF EXISTS `ROLE`;

CREATE TABLE `ROLE` (
                        `role_id`	int	NOT NULL AUTO_INCREMENT,
                        `name`	varchar(100)	NOT NULL,
                        PRIMARY KEY (`role_id`)
);

DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
                        `user_id`	varchar(100)	NOT NULL,
                        `user_pw`	varchar(2000)	NOT NULL,
                        `address`	varchar(2000)	NOT NULL,
                        `address_detail`	varchar(2000)	NOT NULL,
                        `email`	varchar(2000)	NOT NULL,
                        `created_at`	timestamp	NOT NULL,
                        `state`	int	NOT NULL	DEFAULT 1,
                        `profile_image_address`	varchar(2000)	NOT NULL,
                        `nickname` varchar(100) NOT NULL,
                        PRIMARY KEY (`user_id`)
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

ALTER TABLE `USER_ROLE`
    ADD CONSTRAINT `FK_user_TO_USER_ROLE_1` FOREIGN KEY (`user_id`) REFERENCES `user`(`user_id`);

ALTER TABLE `USER_ROLE`
    ADD CONSTRAINT `FK_ROLE_TO_USER_ROLE_1` FOREIGN KEY (`role_id`) REFERENCES `ROLE`(`role_id`);

ALTER TABLE `USER_ROLE`
    ADD CONSTRAINT `PK_USER_ROLE` PRIMARY KEY (`user_id`, `role_id`);
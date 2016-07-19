CREATE TABLE `gtc_user`(
    `id` BIGINT NOT NULL AUTO_INCREMENT,
    `version` INTEGER NOT NULL,

    `email` VARCHAR(256) NOT NULL,
    `password` VARCHAR(256) NOT NULL,
    `enabled` BIT NOT NULL DEFAULT FALSE,
    `registration_date` BIGINT NOT NULL,

    PRIMARY KEY (`id`)
);

CREATE TABLE `gtc_user_role`(
    `id` BIGINT NOT NULL AUTO_INCREMENT,
    `version` INTEGER NOT NULL,

    `role` VARCHAR(256) NOT NULL,
    `gtc_user_id` BIGINT NOT NULL,

    CONSTRAINT fk_gtc_user_role_user_id FOREIGN KEY (`gtc_user_id`) REFERENCES `gtc_user` (`id`),
    PRIMARY KEY (`id`)
);
CREATE TABLE `gtc_user`(
    `id` BIGINT NOT NULL AUTO_INCREMENT,
    `version` INTEGER NOT NULL,

    `username` VARCHAR(256) NOT NULL,
    `password` VARCHAR(256) NOT NULL,
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

ALTER TABLE `contact` ADD COLUMN `contact_type` VARCHAR(64) DEFAULT 'LEGACY';
ALTER TABLE `contact` ADD COLUMN `contact_value` VARCHAR(256);
ALTER TABLE `contact` ADD COLUMN `user_id` BIGINT;
ALTER TABLE `contact` ADD CONSTRAINT `fk_contact_user_id` FOREIGN KEY (`user_id`) REFERENCES `gtc_user` (`id`);

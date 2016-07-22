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

    CONSTRAINT `fk_gtc_user_role_user_id` FOREIGN KEY (`gtc_user_id`) REFERENCES `gtc_user` (`id`),
    PRIMARY KEY (`id`)
);

ALTER TABLE `contact` ADD COLUMN `contact_type` VARCHAR(64) DEFAULT 'LEGACY';
ALTER TABLE `contact` ADD COLUMN `contact_value` VARCHAR(256);

CREATE TABLE `gtc_user_contacts`(
    `gtc_user_id` BIGINT NOT NULL,
    `contacts_id` BIGINT NOT NULL,

    CONSTRAINT `fk_gtc_user_contacts_gtc_user_id` FOREIGN KEY (`gtc_user_id`) REFERENCES `gtc_user` (`id`),
    CONSTRAINT `fk_gtc_user_contacts_contacts_id` FOREIGN KEY (`contacts_id`) REFERENCES `contact` (`id`),
    PRIMARY KEY (`gtc_user_id`, `contacts_id`)
);

CREATE TABLE `property_contacts`(
    `property_id` BIGINT NOT NULL,
    `contacts_id` BIGINT NOT NULL,

    CONSTRAINT `fk_property_contacts_property_id` FOREIGN KEY (`property_id`) REFERENCES `property` (`id`),
    CONSTRAINT `fk_property_contacts_contacts_id` FOREIGN KEY (`contacts_id`) REFERENCES `contact` (`id`),
    PRIMARY KEY (`property_id`, `contacts_id`)
);

ALTER TABLE `property` ADD COLUMN `owner_id` BIGINT;
ALTER TABLE `property` ADD COLUMN `display_overridden_contacts` BIT NOT NULL DEFAULT TRUE;
ALTER TABLE `property` ADD CONSTRAINT `fk_property_owner_id` FOREIGN KEY (`owner_id`) REFERENCES `gtc_user` (`id`);
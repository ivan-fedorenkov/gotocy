ALTER TABLE `property` MODIFY COLUMN `primary_contact_id` BIGINT NULL DEFAULT NULL;
ALTER TABLE `complex` MODIFY COLUMN `primary_contact_id` BIGINT NULL DEFAULT NULL;
ALTER TABLE `property` MODIFY COLUMN `short_address` VARCHAR(256) NULL DEFAULT NULL;
ALTER TABLE `property` DROP COLUMN `baths`;

UPDATE `property` SET `covered_area` = 0 WHERE `covered_area` IS NULL;
UPDATE `property` SET `plot_size` = 0 WHERE `plot_size` IS NULL;
UPDATE `property` SET `guests` = 0 WHERE `guests` IS NULL;
UPDATE `property` SET `distance_to_sea` = 0 WHERE `distance_to_sea` IS NULL;
UPDATE `property` SET `levels` = 0 WHERE `levels` IS NULL;

ALTER TABLE `property` MODIFY COLUMN `covered_area` INTEGER NOT NULL DEFAULT 0;
ALTER TABLE `property` MODIFY COLUMN `plot_size` INTEGER NOT NULL DEFAULT 0;
ALTER TABLE `property` MODIFY COLUMN `guests` INTEGER NOT NULL DEFAULT 0;
ALTER TABLE `property` MODIFY COLUMN `distance_to_sea` INTEGER NOT NULL DEFAULT 0;
ALTER TABLE `property` MODIFY COLUMN `levels` INTEGER NOT NULL DEFAULT 0;


CREATE TABLE `registration`(
    `id` BIGINT NOT NULL AUTO_INCREMENT,
    `version` INTEGER NOT NULL,

    `name` VARCHAR(256) NOT NULL,
    `email` VARCHAR(256) NOT NULL,
    `business_form` VARCHAR(16) NOT NULL,
    `properties_count` INTEGER NOT NULL DEFAULT 0,
    `related_property_id` BIGINT NULL DEFAULT NULL,

    CONSTRAINT fk_registration_property_id FOREIGN KEY (`related_property_id`) REFERENCES `property` (`id`),
    PRIMARY KEY (`id`)
);
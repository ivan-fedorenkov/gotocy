CREATE TABLE `gtc_user`(
    `id` BIGINT NOT NULL AUTO_INCREMENT,
    `version` INTEGER NOT NULL,

    `username` VARCHAR(256) NOT NULL,
    `password` VARCHAR(256) NOT NULL,
    `registration_date` BIGINT NOT NULL,

    `name` VARCHAR(256),
    `email` VARCHAR(256),
    `phone` VARCHAR(256),
    `spoken_languages` VARCHAR(256),

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

-- Update property contacts

ALTER TABLE `property` ADD COLUMN `owner_id` BIGINT;
ALTER TABLE `property` ADD COLUMN `contacts_display_option` VARCHAR(64) DEFAULT 'OWNER';
ALTER TABLE `property` ADD COLUMN `overridden_contacts_name` VARCHAR(256);
ALTER TABLE `property` ADD COLUMN `overridden_contacts_email` VARCHAR(256);
ALTER TABLE `property` ADD COLUMN `overridden_contacts_phone` VARCHAR(256);
ALTER TABLE `property` ADD COLUMN `overridden_contacts_spoken_languages` VARCHAR(256);

UPDATE `property` p SET
    p.`overridden_contacts_name` = (SELECT c.name FROM `contact` c WHERE c.id = p.primary_contact_id),
    p.`overridden_contacts_email` = (SELECT c.email FROM `contact` c WHERE c.id = p.primary_contact_id),
    p.`overridden_contacts_phone` = (SELECT c.phone FROM `contact` c WHERE c.id = p.primary_contact_id),
    p.`overridden_contacts_spoken_languages` = (SELECT c.spoken_languages FROM `contact` c WHERE c.id = p.primary_contact_id);

UPDATE `property` SET `contacts_display_option` = 'SYSTEM_DEFAULT' WHERE
    `overridden_contacts_name` IS NULL AND
    `overridden_contacts_email` IS NULL AND
    `overridden_contacts_phone` IS NULL AND
    `overridden_contacts_spoken_languages` IS NULL;

ALTER TABLE `property` DROP CONSTRAINT `fk_property_primary_contact_id`;
ALTER TABLE `property` DROP COLUMN `primary_contact_id`;

-- Update complex contacts

ALTER TABLE `complex` ADD COLUMN `contacts_name` VARCHAR(256);
ALTER TABLE `complex` ADD COLUMN `contacts_email` VARCHAR(256);
ALTER TABLE `complex` ADD COLUMN `contacts_phone` VARCHAR(256);
ALTER TABLE `complex` ADD COLUMN `contacts_spoken_languages` VARCHAR(256);

UPDATE `complex` cplx SET
    cplx.`contacts_name` = (SELECT c.name FROM `contact` c WHERE c.id = cplx.primary_contact_id),
    cplx.`contacts_email` = (SELECT c.email FROM `contact` c WHERE c.id = cplx.primary_contact_id),
    cplx.`contacts_phone` = (SELECT c.phone FROM `contact` c WHERE c.id = cplx.primary_contact_id),
    cplx.`contacts_spoken_languages` = (SELECT c.spoken_languages FROM `contact` c WHERE c.id = cplx.primary_contact_id);

ALTER TABLE `complex` DROP CONSTRAINT `fk_complex_primary_contact_id`;
ALTER TABLE `complex` DROP COLUMN `primary_contact_id`;

-- Property registration secret

ALTER TABLE `property` ADD COLUMN `registration_key` VARCHAR(256);
ALTER TABLE `property` ADD COLUMN `registration_key_eol` BIGINT;
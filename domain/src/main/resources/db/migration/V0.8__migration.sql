CREATE TABLE `developer`(
    `id` BIGINT NOT NULL AUTO_INCREMENT,
    `version` INTEGER NOT NULL,

    `developer_name` VARCHAR(256) NOT NULL,

    PRIMARY KEY (`id`)
);

CREATE TABLE `developer_localized_fields`(
    `developer_id` BIGINT NOT NULL,
    `localized_fields_id` BIGINT NOT NULL,

    CONSTRAINT fk_developer_localized_fields_complex_id FOREIGN KEY (`developer_id`) REFERENCES `developer` (`id`),
    CONSTRAINT fk_developer_localized_fields_localized_field_id FOREIGN KEY (`localized_fields_id`) REFERENCES `localized_field` (`id`),
    PRIMARY KEY (`developer_id`, `localized_fields_id`)
);

ALTER TABLE `property` ADD COLUMN `developer_id` BIGINT NULL DEFAULT NULL;
ALTER TABLE `property` ADD CONSTRAINT fk_property_developer_id FOREIGN KEY (`developer_id`) REFERENCES `developer` (`id`);

ALTER TABLE `complex` ADD COLUMN `developer_id` BIGINT NULL DEFAULT NULL;
ALTER TABLE `complex` ADD CONSTRAINT fk_complex_developer_id FOREIGN KEY (`developer_id`) REFERENCES `developer` (`id`);
CREATE TABLE `localized_field`(
    `id` BIGINT NOT NULL AUTO_INCREMENT,
    `version` INTEGER NOT NULL,

    `field_type` VARCHAR(256),
    `field_key` VARCHAR(512),
    `language` VARCHAR(16),
    `string_value` VARCHAR(2048) NULL,
    `text_value` LONGTEXT NULL,

    PRIMARY KEY (`id`)
);

CREATE TABLE `property_localized_fields`(
    `property_id` BIGINT NOT NULL,
    `localized_fields_id` BIGINT NOT NULL,

    CONSTRAINT fk_property_localized_fields_property_id FOREIGN KEY (`property_id`) REFERENCES `property` (`id`),
    CONSTRAINT fk_property_localized_fields_localized_field_id FOREIGN KEY (`localized_fields_id`) REFERENCES `localized_field` (`id`),
    PRIMARY KEY (`property_id`, `localized_fields_id`)
);
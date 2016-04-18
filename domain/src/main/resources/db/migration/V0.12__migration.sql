CREATE TABLE `page`(
    `id` BIGINT NOT NULL AUTO_INCREMENT,
    `version` INTEGER NOT NULL,

    `visible` BIT NOT NULL DEFAULT FALSE,

    PRIMARY KEY (`id`)
);

CREATE TABLE `page_localized_fields`(
    `page_id` BIGINT NOT NULL,
    `localized_fields_id` BIGINT NOT NULL,

    CONSTRAINT fk_page_localized_fields_page_id FOREIGN KEY (`page_id`) REFERENCES `page` (`id`),
    CONSTRAINT fk_page_localized_fields_localized_field_id FOREIGN KEY (`localized_fields_id`) REFERENCES `localized_field` (`id`),
    PRIMARY KEY (`page_id`, `localized_fields_id`)
);
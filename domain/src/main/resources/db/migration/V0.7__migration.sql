CREATE TABLE `complex`(
    `id` BIGINT NOT NULL AUTO_INCREMENT,
    `version` INTEGER NOT NULL,

    `title` VARCHAR(256) NOT NULL,
    `location` VARCHAR(16) NOT NULL,
    `address` VARCHAR(1024) NOT NULL,
    `coordinates` VARCHAR(1024) NOT NULL,

    `representative_image_id` BIGINT NOT NULL,
    `primary_contact_id` BIGINT NOT NULL,

    CONSTRAINT fk_complex_representative_image_id FOREIGN KEY (`representative_image_id`) REFERENCES `asset` (`id`),
    CONSTRAINT fk_complex_primary_contact_id FOREIGN KEY (`primary_contact_id`) REFERENCES `owner` (`id`),
    PRIMARY KEY (`id`)
);

CREATE TABLE `complex_images`(
    `complex_id` BIGINT NOT NULL,
    `images_id` BIGINT NOT NULL,

    CONSTRAINT fk_complex_images_complex_id FOREIGN KEY (`complex_id`) REFERENCES `complex` (`id`),
    CONSTRAINT fk_complex_images_images_id FOREIGN KEY (`images_id`) REFERENCES `asset` (`id`),
    PRIMARY KEY (`complex_id`, `images_id`)
);

CREATE TABLE `complex_pdf_files`(
    `complex_id` BIGINT NOT NULL,
    `pdf_files_id` BIGINT NOT NULL,

    CONSTRAINT fk_complex_pdf_files_complex_id FOREIGN KEY (`complex_id`) REFERENCES `complex` (`id`),
    CONSTRAINT fk_complex_pdf_files_pdf_files_id FOREIGN KEY (`pdf_files_id`) REFERENCES `asset` (`id`),
    PRIMARY KEY (`complex_id`, `pdf_files_id`)
);

CREATE TABLE `complex_localized_fields`(
    `complex_id` BIGINT NOT NULL,
    `localized_fields_id` BIGINT NOT NULL,

    CONSTRAINT fk_complex_localized_fields_complex_id FOREIGN KEY (`complex_id`) REFERENCES `complex` (`id`),
    CONSTRAINT fk_complex_localized_fields_localized_field_id FOREIGN KEY (`localized_fields_id`) REFERENCES `localized_field` (`id`),
    PRIMARY KEY (`complex_id`, `localized_fields_id`)
);

ALTER TABLE `property` ADD COLUMN `complex_id` BIGINT NULL;
ALTER TABLE `property` ADD CONSTRAINT fk_property_complex_id FOREIGN KEY (`complex_id`) REFERENCES `complex` (`id`);
ALTER TABLE `asset` ADD COLUMN `display_name` VARCHAR(256) NULL;
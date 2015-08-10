CREATE TABLE `owner`(
    `id` BIGINT NOT NULL AUTO_INCREMENT,
    `version` INTEGER NOT NULL,

    `name` VARCHAR(256) NULL,
    `email` VARCHAR(256) NULL,
    `phone` VARCHAR(256) NULL,
    `spoken_languages` VARCHAR(256) NULL,

    PRIMARY KEY (`id`)
);

CREATE TABLE `asset`(
    `id` BIGINT NOT NULL AUTO_INCREMENT,
    `version` INTEGER NOT NULL,

    `key` VARCHAR(256),
    `asset_type` VARCHAR(16),

    PRIMARY KEY (`id`)
);

CREATE TABLE `property`(
    `id` BIGINT NOT NULL AUTO_INCREMENT,
    `version` INTEGER NOT NULL,

    `title` VARCHAR(256) NOT NULL,
    `address` VARCHAR(1024) NOT NULL,
    `short_address` VARCHAR(256) NOT NULL,
    `location` VARCHAR(16) NOT NULL,
    `latitude` DOUBLE NOT NULL,
    `longitude` DOUBLE NOT NULL,
    `property_type` VARCHAR(16) NOT NULL,
    `property_status` VARCHAR(16) NOT NULL,
    `offer_status` VARCHAR(64) NOT NULL DEFAULT 'ACTIVE',
    `price` INTEGER NOT NULL,
    `bedrooms` INTEGER NOT NULL,

    -- non common properties
    `covered_area` INTEGER NULL,
    `plot_size` INTEGER NULL,
    `guests` INTEGER NULL,
    `baths` INTEGER NULL,
    `ready_to_move_in` BIT NOT NULL DEFAULT FALSE,
    `air_conditioner` BIT NOT NULL DEFAULT FALSE,
    `heating_system` BIT NOT NULL DEFAULT FALSE,
    `furnishing` VARCHAR(16) NULL,
    `distance_to_sea` INTEGER NULL,

    `representative_image_id` BIGINT NULL,
    `pano_xml_id` BIGINT NULL,
    `owner_id` BIGINT NOT NULL,

    CONSTRAINT fk_property_representative_image_id FOREIGN KEY (`representative_image_id`) REFERENCES `asset` (`id`),
    CONSTRAINT fk_property_pano_xml_id FOREIGN KEY (`pano_xml_id`) REFERENCES `asset` (`id`),
    CONSTRAINT fk_property_owner_id FOREIGN KEY (`owner_id`) REFERENCES `owner` (`id`),
    PRIMARY KEY (`id`)
);


CREATE TABLE `property_images`(
    `property_id` BIGINT NOT NULL,
    `images_id` BIGINT NOT NULL,

    CONSTRAINT fk_property_images_property_id FOREIGN KEY (`property_id`) REFERENCES `property` (`id`),
    CONSTRAINT fk_property_images_images_id FOREIGN KEY (`images_id`) REFERENCES `asset` (`id`),
    PRIMARY KEY (`property_id`, `images_id`)
);

CREATE TABLE `localized_property`(
    `id` BIGINT NOT NULL AUTO_INCREMENT,
    `version` INTEGER NOT NULL,

    `locale` VARCHAR(8) NOT NULL,
    `description` CLOB NULL,


    `property_id` BIGINT NOT NULL,

    CONSTRAINT fk_localized_property_property_id FOREIGN KEY (`property_id`) REFERENCES `property` (`id`),
    PRIMARY KEY (`id`)
);

CREATE TABLE `localized_property_specification`(
    `id` BIGINT NOT NULL AUTO_INCREMENT,
    `version` INTEGER NOT NULL DEFAULT 0,

    `specification` VARCHAR(256) NOT NULL,
    `localized_property_id` BIGINT NOT NULL,

    CONSTRAINT fk_localized_property_specifications_localized_property_id FOREIGN KEY (`localized_property_id`) REFERENCES `localized_property` (`id`),
    PRIMARY KEY (`id`)
);


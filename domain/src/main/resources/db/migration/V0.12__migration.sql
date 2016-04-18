CREATE TABLE `page`(
    `id` BIGINT NOT NULL AUTO_INCREMENT,
    `version` INTEGER NOT NULL,

    `title` VARCHAR(512) NOT NULL,
    `html` TEXT NOT NULL,
    `visible` BIT NOT NULL DEFAULT FALSE,
    `url` VARCHAR(256) NOT NULL,

    PRIMARY KEY (`id`)
);

CREATE UNIQUE INDEX page_url_index_unique ON `page` (`url`);
ALTER TABLE `property` ADD COLUMN
    `crawl_source` VARCHAR(256),
    `crawl_id` VARCHAR(256),
    `crawl_url` VARCHAR(1024);

CREATE TABLE property(
    id BIGINT NOT NULL AUTO_INCREMENT,
    version INTEGER NOT NULL,

    location VARCHAR(8) NOT NULL,
    latitude DOUBLE NOT NULL,
    longitude DOUBLE NOT NULL,
    property_type VARCHAR(16) NOT NULL,
    property_status VARCHAR(16) NOT NULL,
    price INTEGER NOT NULL,

    PRIMARY KEY (id)
);

CREATE TABLE localized_property(
    id BIGINT NOT NULL AUTO_INCREMENT,
    version INTEGER NOT NULL,

    locale VARCHAR(8) NOT NULL,
    title VARCHAR(256) NOT NULL,
    address VARCHAR(1024) NOT NULL,
    description VARCHAR(2048) NOT NULL,


    property_id BIGINT NOT NULL,

    CONSTRAINT fk_localized_property_property_id FOREIGN KEY (property_id) REFERENCES property (id),
    PRIMARY KEY (id)
);

CREATE TABLE localized_property_specification(
    id BIGINT NOT NULL AUTO_INCREMENT,
    version INTEGER NOT NULL,

    specification VARCHAR(256) NOT NULL,
    localized_property_id BIGINT NOT NULL,

    CONSTRAINT fk_localized_property_specifications_localized_property_id FOREIGN KEY (localized_property_id) REFERENCES localized_property (id),
    PRIMARY KEY (id)
);
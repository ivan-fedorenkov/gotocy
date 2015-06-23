CREATE TABLE property(
    id BIGINT NOT NULL AUTO_INCREMENT,
    version INTEGER NOT NULL,

    PRIMARY KEY (id)
);

CREATE TABLE localized_property(
    id BIGINT NOT NULL AUTO_INCREMENT,
    version INTEGER NOT NULL,

    title VARCHAR(256) NOT NULL,
    locale VARCHAR(8) NOT NULL,

    property_id BIGINT NOT NULL,

    CONSTRAINT fk_localized_property_property_id FOREIGN KEY (property_id) REFERENCES property (id),
    PRIMARY KEY (id)
);
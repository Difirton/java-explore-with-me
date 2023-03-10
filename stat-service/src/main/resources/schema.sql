DROP TABLE IF EXISTS endpoints_hits;

CREATE TABLE endpoints_hits
(
    id BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    app VARCHAR(255),
    uri VARCHAR(255),
    ip VARCHAR(20),
    timestamp TIMESTAMP WITHOUT TIME ZONE
);
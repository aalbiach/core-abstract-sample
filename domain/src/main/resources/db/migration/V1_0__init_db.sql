CREATE TABLE shoe
(
    id    VARCHAR(36) NOT NULL,
    name  VARCHAR(50),
    size  TINYINT,
    color VARCHAR(50),
    PRIMARY KEY (id)
);

CREATE TABLE stock
(
    id       VARCHAR(36) NOT NULL UNIQUE,
    quantity TINYINT CHECK ( quantity <= 30 ),
    FOREIGN KEY (id) REFERENCES shoe (id)
);

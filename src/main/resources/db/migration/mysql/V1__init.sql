CREATE TABLE car (
  model     VARCHAR(250) NOT NULL PRIMARY KEY,
  prod_date DATE         NOT NULL,
  name      VARCHAR(250)
);

CREATE TABLE client (
  name       VARCHAR(250) NOT NULL PRIMARY KEY,
  birth_date DATE         NOT NULL,
  model      VARCHAR(250) NOT NULL,
  CONSTRAINT fk_client_car FOREIGN KEY (model) REFERENCES car (model)
);


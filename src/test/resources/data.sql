DROP TABLE IF EXISTS partners;

CREATE TABLE partners (
  id BIGINT AUTO_INCREMENT  PRIMARY KEY,
  company_name VARCHAR(255) NOT NULL,
  ref VARCHAR(255) NOT NULL,
  locale VARCHAR(5) DEFAULT NULL,
  expires DATETIME
);
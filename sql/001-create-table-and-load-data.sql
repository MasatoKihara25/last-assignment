DROP TABLE IF EXISTS lures;

CREATE TABLE lures (
  id int unsigned AUTO_INCREMENT,
  product VARCHAR(100) NOT NULL,
  company VARCHAR(100) NOT NULL,
  size double NOT NULL,
  weight double NOT NULL,
  PRIMARY KEY(id)
);

INSERT INTO lures (product, company, size, weight) VALUES ("Balaam300", "Madness", 300, 168);
INSERT INTO lures (product, company, size, weight) VALUES ("JointedCRAW178", "GANCRAFT", 178, 56);
INSERT INTO lures (product, company, size, weight) VALUES ("SILENTKILLER175", "deps", 175, 70);
INSERT INTO lures (product, company, size, weight) VALUES ("BULLSHOOTER160", "deps", 160, 98);



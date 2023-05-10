create database hito3_6;

use hito3_6;

CREATE TABLE Photographers (
  PhotographerId INT NOT NULL AUTO_INCREMENT,
  Nombre TEXT NOT NULL,
  Awarded BOOLEAN NOT NULL,
  PRIMARY KEY (PhotographerId)
);

CREATE TABLE Pictures (
  PictureId INT NOT NULL AUTO_INCREMENT,
  Title TEXT NOT NULL,
  fecha DATE NOT NULL,
  File TEXT NOT NULL,
  Visits INT NOT NULL,
  PhotographerId INT NOT NULL,
  PRIMARY KEY (PictureId),
  FOREIGN KEY (PhotographerId) REFERENCES Photographers(PhotographerId)
);

##DROP TABLE pictures;
##DROP TABLE photographers;

INSERT INTO Photographers (Nombre, Awarded) VALUES
('Ansel Adams', false),
('Rothko', false),
('Van Gogh', true);

INSERT INTO Pictures (Title, fecha, File, Visits, PhotographerId) VALUES
('Clearing Winter Storm', '1990-02-14', 'Hito3.6/src/images/ansealdams1.jpg', 0, 1),
('Flow Violento', '2001-03-01', 'Hito3.6/src/images/ansealdams2.jpg', 0, 1),
('Untitled', '1934-03-15', 'Hito3.6/src/images/rothko1.jpg', 50, 2),
('La noche estrellado', '1962-04-01', 'Hito3.6/src/images/vangogh1.jpg', 25, 3),
('Campo de trigo con cipreses', '2004-04-01', 'Hito3.6/src/images/vangogh2.jpg', 25, 3);

select * FROM pictures WHERE visits = 0 and photographerid IN (select photographerid from photographers where awarded = 0);
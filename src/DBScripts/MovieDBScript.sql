-- Drop tables if they exist
DROP TABLE IF EXISTS dbo.Movie;
DROP TABLE IF EXISTS dbo.Genre;

-- Create Tables
CREATE TABLE dbo.Genre
(
GenreId INT IDENTITY PRIMARY KEY,
GenreType VARCHAR(255) NOT NULL,
GenreDescription VARCHAR(255)
);

CREATE TABLE dbo.Movie
(
MovieId INT IDENTITY PRIMARY KEY,
GenreId INT FOREIGN KEY REFERENCES dbo.Genre(GenreId),
MovieName VARCHAR(255) NOT NULL,
Director VARCHAR(255),
MainCelebrity VARCHAR(255),
Certificate VARCHAR(255),
ReleaseYear INT,
StarRating INT,
);

-- Add data
SET IDENTITY_INSERT dbo.Genre ON;
insert into dbo.Genre (GenreId,GenreType,GenreDescription) values ( 1,'SiFi', 'Science Fiction');
insert into dbo.Genre (GenreId,GenreType,GenreDescription) values ( 2,'Comedy', 'Funny Movies');
insert into dbo.Genre (GenreId,GenreType,GenreDescription) values ( 3,'Action', 'Action packed movies');
insert into dbo.Genre (GenreId,GenreType,GenreDescription) values ( 4,'Romance', 'Romantic movies');
insert into dbo.Genre (GenreId,GenreType,GenreDescription) values ( 5,'Thriller', 'Suspense movies');
insert into dbo.Genre (GenreId,GenreType,GenreDescription) values ( 6,'Drama', 'Drama movies');
SET IDENTITY_INSERT dbo.Genre OFF;

SET IDENTITY_INSERT dbo.Movie ON;
insert into dbo.Movie (MovieId,GenreId,MovieName,Director,MainCelebrity,Certificate,ReleaseYear,StarRating) values ( 1,1,'Avengers: End game','Anthony Russo','Robert Downey Jr.','12A',2019,4);
insert into dbo.Movie (MovieId,GenreId,MovieName,Director,MainCelebrity,Certificate,ReleaseYear,StarRating) values ( 2,4,'Long Shot','Jonathan Levine','Charlize Theron','15',2019,3);
insert into dbo.Movie (MovieId,GenreId,MovieName,Director,MainCelebrity,Certificate,ReleaseYear,StarRating) values ( 3,2,'UglyDolls','Kelly Asbury','Kelly Clarkson','U',2018,2);
insert into dbo.Movie (MovieId,GenreId,MovieName,Director,MainCelebrity,Certificate,ReleaseYear,StarRating) values ( 4,6,'The Shawshank Redemption','Frank Darabont','Tim Robbins','15',1994,5);
insert into dbo.Movie (MovieId,GenreId,MovieName,Director,MainCelebrity,Certificate,ReleaseYear,StarRating) values ( 5,6,'The Godfather','Francis Ford Coppola','Marlon Brando','15',1972,5);
insert into dbo.Movie (MovieId,GenreId,MovieName,Director,MainCelebrity,Certificate,ReleaseYear,StarRating) values ( 6,3,'The Dark Knight','Christopher Nolan','Christian Bale','15',2008,4);
insert into dbo.Movie (MovieId,GenreId,MovieName,Director,MainCelebrity,Certificate,ReleaseYear,StarRating) values ( 7,5,'12 Angry Men','Sidney Lumet','Henry Fonda','12',1957,5);
insert into dbo.Movie (MovieId,GenreId,MovieName,Director,MainCelebrity,Certificate,ReleaseYear,StarRating) values ( 8,5,'Schindlers List','Steven Spielberg','Liam Neeson','15',1993,4);
insert into dbo.Movie (MovieId,GenreId,MovieName,Director,MainCelebrity,Certificate,ReleaseYear,StarRating) values ( 9,1,'The Lord of the Rings: The Return of the King','Peter Jackson','Elijah Wood','12A',2003,4);
insert into dbo.Movie (MovieId,GenreId,MovieName,Director,MainCelebrity,Certificate,ReleaseYear,StarRating) values ( 10,6,'Pulp Fiction','Quentin Tarantino','John Travolta','18',1994,4);
SET IDENTITY_INSERT dbo.Movie OFF;
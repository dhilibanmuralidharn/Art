create table if not exists gallery(
    id INT PRIMARY KEY AUTO_INCREMENT,
    name TEXT,
    location TEXT
);

create table if not exists artist(
    id INT PRIMARY KEY AUTO_INCREMENT,
    name TEXT,
    genre TEXT
);

create table if not exists art(
    id INT PRIMARY KEY AUTO_INCREMENT,
    title TEXT,
    theme TEXT,
    artistid INT,
    FOREIGN KEY (artistid) REFERENCES artist(id)
);

create table if not exists artist_gallery(
    artistid INT,
    galleryid INT,
    PRIMARY KEY (artistid, galleryid),
    FOREIGN KEY (artistid) REFERENCES artist(id),
    FOREIGN KEY (galleryid) REFERENCES gallery(id)
);
Create database IF NOT EXISTS rythm;
use rythm;
create table user
(
    id  int auto_increment
        primary key,
    name     varchar(256) not null,
    email    varchar(256) not null,
    picture  varchar(256) null,
    password varchar(256) not null,
    nickname varchar(256) not null,
    lastname varchar(256) not null
) charset = utf8mb4;

create table artist
(
    id_artist   int auto_increment
        primary key,
    nacionality varchar(256) not null,
    picture     varchar(256) null,
    id     int null,
    constraint `FK ARTIST_USER`
        foreign key (id) references user (id)
            on update cascade on delete cascade
) charset = utf8mb4;

create table album
(
    id_album      int auto_increment
        primary key,
    id_artist     int          not null,
    name          varchar(256) not null,
    date          varchar(255)         not null,
    picture       varchar(256) null,
    reproductions int null,
    constraint `FK ARTIST`
        foreign key (id_artist) references artist (id_artist)
            on update cascade on delete cascade
) charset = utf8mb4;

create index `FOREING ARTIST`
    on album (id_artist);

create index `FOREING USER`
    on artist (id);

create table reproductionlist
(
    id int auto_increment
        primary key,
    id_user             int          not null,
    name                varchar(256) not null,
    description         varchar(1024) null,
    constraint `FK USER_RPLIST`
        foreign key (id_user) references user (id)
            on update cascade on delete cascade
) charset = utf8mb4;

create table commentlistusers
(
    id_comment          int auto_increment
        primary key,
    id_user             int      not null,
    id_reproductionList int      not null,
    date                datetime not null,
    description         varchar(256) null,
    constraint `FK LIST_COMMENTLIST`
        foreign key (id_reproductionList) references reproductionlist (id)
            on update cascade on delete cascade,
    constraint `FK USER_COMMENTLIST`
        foreign key (id_user) references user (id)
            on update cascade on delete cascade
) charset = utf8mb4;

create index `FOREING REPRODUCIONTLIST`
    on commentlistusers (id_reproductionList);

create index `FOREING USER`
    on commentlistusers (id_user);

create index `FOREING USER`
    on reproductionlist (id_user);

create table song
(
    id_song       int auto_increment
        primary key,
    id_album      int          not null,
    name          varchar(256) not null,
    url           varchar(256) not null,
    lenght        time         not null,
    genre         varchar(256) not null,
    reproductions int null,
    constraint `FK ALBUM`
        foreign key (id_album) references album (id_album)
            on update cascade
) charset = utf8mb4;

create table reproductionsonglist
(
    id_reproductionList int not null,
    id_song             int not null,
    primary key (id_reproductionList, id_song),
    constraint `FK RPLIST`
        foreign key (id_reproductionList) references reproductionlist (id)
            on update cascade on delete cascade,
    constraint `FK SONG`
        foreign key (id_song) references song (id_song)
            on update cascade on delete cascade
) charset = utf8mb4;

create index `FOREING ALBUM`
    on song (id_album);

create table usersubscriptionlist
(
    id_user             int not null,
    id_reproductionList int not null,
    primary key (id_user, id_reproductionList),
    constraint `FK LIST`
        foreign key (id_reproductionList) references reproductionlist (id)
            on update cascade on delete cascade,
    constraint `FK USER`
        foreign key (id_user) references user (id)
            on update cascade on delete cascade
) charset = utf8mb4;






-- [UserSubcriptionList]
-- crearemos un trigger en el que cuando un usuario cree una lista de reproduccion se le relacione en la tabla usersubscriptionlist
CREATE TRIGGER `ownerSubcriptionList`
    AFTER INSERT
    ON `reproductionlist`
    FOR EACH ROW INSERT INTO usersubscriptionlist (id_user, id_reproductionList)
                 VALUES (NEW.id_user, NEW.id);

-- Crearemos un trigger para que cuando se haya ejecutado un Update en la tabla song en la columna reproductions. entonces las reproducciones del album sera = a la suma de las reproducciones de las canciones que pertenecen a ese album
CREATE TRIGGER `updateReproductionsAlbum`
    AFTER UPDATE
    ON `song`
    FOR EACH ROW UPDATE album
                 SET reproductions = (SELECT SUM(reproductions) FROM song WHERE id_album = NEW.id_album)
                 WHERE id_album = NEW.id_album;


-- Users Inserts

insert into artist (id, name) values (NEXTVAL('hibernate_sequence'), 'Joaquín Sabina');
insert into artist (id, name) values (NEXTVAL('hibernate_sequence'), 'Dua Lipa');
insert into artist (id, name) values (NEXTVAL('hibernate_sequence'), 'Metallica');

insert into song (id, title, album, year_of_song, artist_id) values (NEXTVAL('hibernate_sequence'), '19 días y 500 noches', '19 días y 500 noches', '1999', 1);
insert into song (id, title, album, year_of_song, artist_id) values (NEXTVAL('hibernate_sequence'), 'Donde habita el olvido', '19 días y 500 noches', '1999', 1);
insert into song (id, title, album, year_of_song, artist_id) values (NEXTVAL('hibernate_sequence'), 'A mis cuarenta y diez', '19 días y 500 noches', '1999', 1);
insert into song (id, title, album, year_of_song, artist_id) values (NEXTVAL('hibernate_sequence'), 'Dont Start Now', 'Future Nostalgia', '2019', 2);
insert into song (id, title, album, year_of_song, artist_id) values (NEXTVAL('hibernate_sequence'), 'Love Again', 'Future Nostalgia', '2021', 2);
insert into song (id, title, album, year_of_song, artist_id) values (NEXTVAL('hibernate_sequence'), 'Enter Sandman', 'Metallica', '1991', 3);
insert into song (id, title, album, year_of_song, artist_id) values (NEXTVAL('hibernate_sequence'), 'Unforgiven', 'Metallica', '1991', 3);
insert into song (id, title, album, year_of_song, artist_id) values (NEXTVAL('hibernate_sequence'), 'Nothing Else Matters', 'Metallica', '1991', 3);

insert into playlist (id, name, description) values (NEXTVAL('hibernate_sequence'), 'Random', 'Una lista muy loca');
insert into playlist (id, name, description) values (NEXTVAL('hibernate_sequence'), 'Antiguas', 'Una lista de canciones antiguas');

insert into added_to (playlist_id, song_id, date_time, orden) values (12, 5, CURRENT_TIMESTAMP, 2);
insert into added_to (playlist_id, song_id, date_time, orden) values (12, 4, CURRENT_TIMESTAMP, 1);
insert into added_to (playlist_id, song_id, date_time, orden) values (12, 8, CURRENT_TIMESTAMP, 3);
insert into added_to (playlist_id, song_id, date_time, orden) values (12, 10, CURRENT_TIMESTAMP, 4);
insert into added_to (playlist_id, song_id, date_time, orden) values (12, 11, CURRENT_TIMESTAMP, 5);
insert into added_to (playlist_id, song_id, date_time, orden) values (13, 4, CURRENT_TIMESTAMP, 4);
insert into added_to (playlist_id, song_id, date_time, orden) values (13, 5, CURRENT_TIMESTAMP, 3);
insert into added_to (playlist_id, song_id, date_time, orden) values (13, 6, CURRENT_TIMESTAMP, 2);
insert into added_to (playlist_id, song_id, date_time, orden) values (13, 9, CURRENT_TIMESTAMP, 1);
insert into added_to (playlist_id, song_id, date_time, orden) values (13, 10, CURRENT_TIMESTAMP, 6);
insert into added_to (playlist_id, song_id, date_time, orden) values (13, 11, CURRENT_TIMESTAMP, 5);

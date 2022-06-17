CREATE TABLE post (
   id SERIAL PRIMARY KEY,
   name TEXT,
   description TEXT,
   visible BOOL,
   city_id INTEGER
);

CREATE TABLE candidate (
   id SERIAL PRIMARY KEY,
   name TEXT,
   description TEXT,
   visible BOOL,
   city_id INTEGER
);

CREATE TABLE users (
   id serial primary key,
   name TEXT,
   email TEXT unique,
   password TEXT
);
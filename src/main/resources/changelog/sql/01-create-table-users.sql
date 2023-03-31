create table socks
(
    id BIGSERIAL PRIMARY KEY,
    color VARCHAR NOT NULL,
    cotton_part INT check (cotton_part < 101 and cotton_part > 0) NOT NULL,
    quantity INTEGER NOT NULL
)
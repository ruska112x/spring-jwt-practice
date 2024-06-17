create table data_jsonb (
    data jsonb
);
-- data format
-- {
--   "name": "string",
--   "age": int
-- }

insert into data_jsonb (data) values ('{"name": "John", "age": 50}');

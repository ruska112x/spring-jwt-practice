create table users (
  email VARCHAR(256) PRIMARY KEY,
  password VARCHAR(256),
  enabled boolean
);

create table user_roles (
  user_email  VARCHAR(256) REFERENCES users(email) ON DELETE CASCADE,
  role VARCHAR(256),
  PRIMARY KEY (user_email, role)
);

create table user_refresh_tokens (
  user_email  VARCHAR(256) REFERENCES users(email) ON DELETE CASCADE,
  token VARCHAR(256),
  PRIMARY KEY (user_email, token)
);

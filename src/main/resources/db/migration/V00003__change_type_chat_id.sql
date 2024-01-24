ALTER TABLE group_x_user DROP constraint group_x_user_user_id_fkey;
ALTER TABLE group_x_user ALTER COLUMN user_id TYPE integer USING (user_id::integer);
ALTER TABLE tg_user ALTER COLUMN chat_id TYPE integer USING (chat_id::integer);
ALTER TABLE group_x_user ADD constraint group_x_user_user_id_fkey FOREIGN KEY (user_id) REFERENCES tg_user (chat_id);
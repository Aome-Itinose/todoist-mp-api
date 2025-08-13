CREATE TABLE IF NOT EXISTS quota
(
    id         UUID PRIMARY KEY,
    profile_id UUID         NOT NULL REFERENCES profile (id) ON DELETE CASCADE,
    type       VARCHAR(128) NOT NULL,
    amount     BIGINT       NOT NULL
);

insert into quota (id, profile_id, type, amount)
values (gen_random_uuid(),
        (select id
         from profile
         where todoist_username = 'KY0BU'),
        'DEADLINE_POSTPONE',
        1000)

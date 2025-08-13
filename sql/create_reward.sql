create table reward
(
    id         uuid primary key,
    profile_id uuid not null references profile (id) on delete cascade,
    amount    int         not null default 0,
    content   text        not null,
    type      varchar(32) not null default 'REWARD',
    timestamp timestamptz          default now()
);

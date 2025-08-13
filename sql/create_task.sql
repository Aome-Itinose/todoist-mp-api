create table if not exists task
(
    id         uuid primary key,
    todoist_id bigint unique not null,
    profile_id uuid          not null references profile (id) on delete cascade,
    content      text not null,
    description  text,
    completed_at timestamptz,
    mp           int default 0
);

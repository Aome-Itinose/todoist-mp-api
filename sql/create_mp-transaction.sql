create table if not exists mp_transaction
(
    id         uuid primary key,
    profile_id uuid not null references profile (id) on delete cascade,
    delta_mp   int not null default 0,
    timestamp  timestamptz  default now(),
    task_count int          default 0
);

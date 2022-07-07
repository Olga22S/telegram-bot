-- liquibase formatted sql

-- changeset ogontsova:1
create table notification_task
(
    id                   serial       NOT NULL PRIMARY KEY,
    chat_id              bigint       NOT NULL,
    notification_date    timestamp    NOT NULL,
    notification_message varchar(255) NOT NULL,
    status               varchar(255) NOT NULL DEFAULT 'SCHEDULED'
);

alter table notification_task
    owner to prettyuser;

-- changeset ogontsova:2
CREATE INDEX notification_task_date_idx ON notification_task (notification_date) WHERE status = 'SCHEDULED';

-- changeset ogontsova:3
ALTER TABLE notification_task ADD COLUMN sent_date timestamp;



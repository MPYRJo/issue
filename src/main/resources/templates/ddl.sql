CREATE TYPE pr AS ENUM ('DANGER', 'WARNING', 'NORMAL');
CREATE TYPE ro AS ENUM ('USER', 'LEADER', 'ADMIN');
CREATE TYPE ws AS ENUM ('TODO', 'IN_PROGRESS', 'DONE', 'PENDING');
CREATE TYPE cs AS ENUM ('FEEDBACK', 'WAITING', 'APPROVE', 'PENDING', 'REJECT');

CREATE TABLE issue (
    id BIGINT PRIMARY KEY NOT NULL,
    title VARCHAR(200) NOT NULL,
    description TEXT NOT NULL,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL,
    deleted_at TIMESTAMP,
    priority pr NOT NULL,
    working_status ws NOT NULL,
    member_id BIGINT NOT NULL,
    team_id BIGINT NOT NULL
);

CREATE TABLE member (
    id BIGINT PRIMARY KEY NOT NULL,
    team_id BIGINT NOT NULL,
    role ro NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(64) NOT NULL,
    nickname VARCHAR(20) NOT NULL UNIQUE
);

CREATE TABLE team (
    id BIGINT PRIMARY KEY NOT NULL,
    name VARCHAR(16) NOT NULL UNIQUE
);

CREATE TABLE comment (
    id BIGINT PRIMARY KEY NOT NULL,
    issue_id BIGINT NOT NULL,
    member_id BIGINT NOT NULL,
    checking_status cs NOT NULL,
    content VARCHAR(1000) NOT NULL,
    created_at TIMESTAMP NOT NULL,
    deleted_at TIMESTAMP
);


ALTER TABLE comment ADD CONSTRAINT FK_issue_TO_comment_1 FOREIGN KEY (issue_id)
REFERENCES issue (id) ON DELETE CASCADE;

ALTER TABLE comment ADD CONSTRAINT FK_member_TO_comment_1 FOREIGN KEY (member_id)
REFERENCES member (id);

ALTER TABLE issue ADD CONSTRAINT FK_member_TO_issue_1 FOREIGN KEY (member_id)
REFERENCES member (id);

ALTER TABLE issue ADD CONSTRAINT FK_team_TO_issue_1 FOREIGN KEY (team_id)
REFERENCES team (id);

ALTER TABLE member ADD CONSTRAINT FK_team_TO_member_1 FOREIGN KEY (team_id)
REFERENCES team (id);

DROP FUNCTION drop_announcement_body;

DROP VIEW bug_full_view;
DROP VIEW bug_list_view;
DROP VIEW announcement_view;

DROP TABLE bug;
DROP TABLE bug_category;
DROP TABLE bug_comment;
DROP TABLE bug_desc;
DROP TABLE bug_event;
DROP TABLE bug_knowledge;
DROP TABLE bug_priority;
DROP TABLE announcement;
DROP TABLE announcement_body;
DROP TABLE knowledge;
DROP TABLE knowledge_body;
DROP TABLE knowledge_comment;
DROP TABLE knowledge_section;
DROP TABLE users;
DROP TABLE user_preferences;

DROP SEQUENCE bug_category_id_seq;
DROP SEQUENCE bug_comment_id_seq;
DROP SEQUENCE bug_event_id_seq;
DROP SEQUENCE bug_id_seq;
DROP SEQUENCE bug_priority_id_seq;
DROP SEQUENCE announcement_id_seq;
DROP SEQUENCE knowledge_comment_id_seq;
DROP SEQUENCE knowledge_section_id_seq;
DROP SEQUENCE knownledge_id_seq;
DROP SEQUENCE user_id_seq;
DROP SEQUENCE user_preferences_id_seq;

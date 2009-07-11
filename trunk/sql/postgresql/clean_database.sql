DROP FUNCTION drop_information_body;

DROP VIEW bug_full_view;
DROP VIEW bug_list_view;
DROP VIEW information_view;

DROP TABLE hd_bug;
DROP TABLE hd_bug_category;
DROP TABLE hd_bug_comment;
DROP TABLE hd_bug_desc;
DROP TABLE hd_bug_event;
DROP TABLE hd_bug_knowledge;
DROP TABLE hd_bug_priority;
DROP TABLE hd_information;
DROP TABLE hd_information_body;
DROP TABLE hd_knowledge;
DROP TABLE hd_knowledge_body;
DROP TABLE hd_knowledge_comment;
DROP TABLE hd_knowledge_section;
DROP TABLE hd_user;
DROP TABLE hd_user_preferences;

DROP SEQUENCE bug_category_id_seq;
DROP SEQUENCE bug_comment_id_seq;
DROP SEQUENCE bug_event_id_seq;
DROP SEQUENCE bug_id_seq;
DROP SEQUENCE bug_priority_id_seq;
DROP SEQUENCE information_id_seq;
DROP SEQUENCE knowledge_comment_id_seq;
DROP SEQUENCE knowledge_section_id_seq;
DROP SEQUENCE knownledge_id_seq;
DROP SEQUENCE user_id_seq;
DROP SEQUENCE user_preferences_id_seq;

DROP DOMAIN hd_bool;
DROP DOMAIN hd_event_type;
DROP DOMAIN hd_role;
DROP DOMAIN hd_status;
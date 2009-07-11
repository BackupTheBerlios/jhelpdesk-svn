/* tabele */

CREATE TABLE hd_user
(
  user_id int4 NOT NULL DEFAULT nextval('public.user_id_seq'::text),
  login varchar(96) NOT NULL,
  passw varchar(40) NOT NULL,
  first_name varchar(64),
  last_name varchar(128),
  email varchar(128) NOT NULL,
  phone varchar(30),
  mobile varchar(20),
  role int2 NOT NULL DEFAULT 0,
  is_active bool,
  CONSTRAINT hd_plain_user_pkey PRIMARY KEY (user_id)
);

CREATE TABLE hd_user_preferences
(
  user_preferences_id int4 NOT NULL DEFAULT nextval('public.user_preferences_id_seq'::text),
  pref_name varchar(32) NOT NULL,
  pref_value varchar(64) NOT NULL
);

CREATE TABLE hd_bug
(
  bug_id int4 NOT NULL DEFAULT nextval('public.bug_id_seq'::text),
  add_phone varchar(20),
  bug_category int4 NOT NULL,
  bug_priority int2 NOT NULL,
  bug_status int4 NOT NULL,
  saviour int4,
  notifyier int4,
  inputer int4,
  create_date timestamptz,
  description text,
  step_by_step text,
  subject varchar(255) NOT NULL,
  department varchar(30),
  CONSTRAINT hd_bug_pkey PRIMARY KEY (bug_id)
);

CREATE TABLE hd_bug_category
(
  category_id int4 NOT NULL DEFAULT nextval('public.bug_category_id_seq'::text),
  parent_category int4,
  category_name varchar(64) NOT NULL,
  category_desc varchar(255),
  is_active bool NOT NULL DEFAULT true,
  t_left int8,
  t_right int8,
  t_depth int4,
  CONSTRAINT hd_bug_category_pkey PRIMARY KEY (category_id),
  CONSTRAINT hd_bug_category_parent_category_fkey FOREIGN KEY (parent_category) REFERENCES hd_bug_category (category_id) ON UPDATE RESTRICT ON DELETE RESTRICT
);

CREATE TABLE hd_bug_knowledge
(
  bug_id int4 NOT NULL,
  knowledge_id int4 NOT NULL
);

CREATE TABLE hd_bug_comment
(
  comment_id int4 NOT NULL DEFAULT nextval('public.bug_comment_id_seq'::text),
  comment_author int4 NOT NULL,
  comment_date timestamptz NOT NULL,
  comment_text text,
  not_for_plain_user bool NOT NULL DEFAULT false,
  bug_id int4 NOT NULL,
  CONSTRAINT hd_bug_comment_pkey PRIMARY KEY (comment_id),
  CONSTRAINT hd_bug_comment_comment_author_fkey FOREIGN KEY (comment_author) REFERENCES hd_user (user_id) ON UPDATE RESTRICT ON DELETE RESTRICT
);

CREATE TABLE hd_bug_desc
(
  bug_id int8 NOT NULL,
  description text NOT NULL,
  CONSTRAINT hd_bug_desc_pkey PRIMARY KEY (bug_id)
);

CREATE TABLE hd_bug_event
(
  event_id int4 NOT NULL DEFAULT nextval('public.bug_event_id_seq'::text),
  bug_id int4 NOT NULL,
  event_subject varchar(255) NOT NULL,
  event_date timestamptz NOT NULL,
  user_id int4 NOT NULL,
  event_type int2 NOT NULL DEFAULT 0,
  CONSTRAINT hd_bug_event_pkey PRIMARY KEY (event_id)
);

CREATE TABLE hd_information
(
  information_id int4 NOT NULL DEFAULT nextval('public.information_id_seq'::text),
  create_date date NOT NULL,
  title varchar(255) NOT NULL,
  lead text NOT NULL,
  CONSTRAINT hd_information_pkey PRIMARY KEY (information_id)
);

CREATE TABLE hd_information_body
(
  information_id int4 NOT NULL,
  body text NOT NULL,
  CONSTRAINT hd_information_body_information_id_fkey FOREIGN KEY (information_id) REFERENCES hd_information (information_id) ON UPDATE RESTRICT ON DELETE RESTRICT,
  CONSTRAINT hd_information_body_information_id_key UNIQUE (information_id)
);

CREATE TABLE hd_knowledge
(
  knowledge_id int4 NOT NULL DEFAULT nextval('public.knowledge_id_seq'::text),
  title varchar(255) NOT NULL,
  hd_knowledge_section_id int4 NOT NULL,
  create_date date,
  lead text,
  body text,
  user_id int4,
  CONSTRAINT hd_knownledge_pkey PRIMARY KEY (knowledge_id)
);

CREATE TABLE hd_knowledge_comment
(
  knowledge_comment_id int4 NOT NULL DEFAULT nextval('public.knowledge_comment_id_seq'::text),
  knowledge_id int4 NOT NULL,
  author int4 NOT NULL,
  create_date date NOT NULL,
  title varchar(255) NOT NULL,
  body text NOT NULL,
  CONSTRAINT hd_knowledge_comment_pkey PRIMARY KEY (knowledge_comment_id)
);

CREATE TABLE hd_knowledge_section
(
  section_name varchar(255) NOT NULL,
  section_position int4 NOT NULL,
  knowledge_section_id int4 NOT NULL DEFAULT nextval('public.knowledge_section_id_seq'::text),
  articles_count int4 NOT NULL DEFAULT 0
);
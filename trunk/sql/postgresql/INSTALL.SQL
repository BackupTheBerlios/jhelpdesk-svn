-- This program is free software: you can redistribute it and/or modify
-- it under the terms of the GNU General Public License as published by
-- the Free Software Foundation, version 3 of the License.
--
-- This program is distributed in the hope that it will be useful,
-- but WITHOUT ANY WARRANTY; without even the implied warranty of
-- MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
-- GNU General Public License for more details.
--
-- You should have received a copy of the GNU General Public License
-- along with this program. If not, see <http://www.gnu.org/licenses/>.
--
-- Copyright: (C) 2006 jHelpdesk Developers Team
---

--- W tym pliku znajdują się wszystkie definicje obiektów bazy danych
--- potrzebnych do uruchomienia systemu jHelpdesk.

--- DOMENY

CREATE DOMAIN hd_bool
  AS smallint
   CONSTRAINT hd_bool_check CHECK ((VALUE = ANY (ARRAY[0, 1])));
;

CREATE DOMAIN hd_bug_priority
  AS smallint
  DEFAULT 1
  NOT NULL
   CONSTRAINT hd_bug_priority_check CHECK ((VALUE = ANY (ARRAY[1, 2, 3, 4, 5])))
;

CREATE DOMAIN hd_event_type
  AS smallint
  NOT NULL
   CONSTRAINT hd_event_type_check CHECK ((VALUE = ANY (ARRAY[1, 2, 3, 4, 5, 6, 7, 8, 9])))
;

CREATE DOMAIN hd_role
  AS smallint
  DEFAULT 1
   CONSTRAINT hd_role_check CHECK ((VALUE = ANY (ARRAY[1, 10, 100])))
;

CREATE DOMAIN hd_status
  AS smallint
  DEFAULT 1
  NOT NULL
   CONSTRAINT hd_status_check CHECK ((VALUE = ANY (ARRAY[1, 2, 3, 4, 5])))
;

--- PROCEDURY

CREATE OR REPLACE FUNCTION section_move_down(bigint)
  RETURNS integer AS
$BODY$
DECLARE
	SECTION_ID ALIAS FOR $1;
	CURR_REC RECORD;
	DOWN_REC RECORD;
BEGIN
	LOCK TABLE hd_knowledge_section IN EXCLUSIVE MODE;

	SELECT INTO CURR_REC section_position,knowledge_section_id
		FROM hd_knowledge_section
		WHERE knowledge_section_id = SECTION_ID
		LIMIT 1;

	SELECT INTO DOWN_REC  section_position,knowledge_section_id
		FROM hd_knowledge_section
		WHERE section_position < CURR_REC.section_position
		ORDER BY section_position DESC
		LIMIT 1;

	IF DOWN_REC.knowledge_section_id IS NOT NULL THEN
		UPDATE hd_knowledge_section
			SET section_position = DOWN_REC.section_position
			WHERE knowledge_section_id = CURR_REC.knowledge_section_id;
		UPDATE hd_knowledge_section
			SET section_position = CURR_REC.section_position
			WHERE knowledge_section_id = DOWN_REC.knowledge_section_id;
	END IF;

	RETURN 1;
END;
$BODY$
  LANGUAGE 'plpgsql'
;

CREATE OR REPLACE FUNCTION section_move_up(bigint)
  RETURNS integer AS
$BODY$
DECLARE
	SECTION_ID ALIAS FOR $1;
	CURR_REC RECORD;
	UP_REC RECORD;
BEGIN
	LOCK TABLE hd_knowledge_section IN EXCLUSIVE MODE;

	SELECT INTO CURR_REC section_position,knowledge_section_id
		FROM hd_knowledge_section
		WHERE knowledge_section_id = SECTION_ID
		LIMIT 1;

	SELECT INTO UP_REC  section_position,knowledge_section_id
		FROM hd_knowledge_section
		WHERE section_position > CURR_REC.section_position
		ORDER BY section_position ASC
		LIMIT 1;

	IF UP_REC.knowledge_section_id IS NOT NULL THEN
		UPDATE hd_knowledge_section
			SET section_position = UP_REC.section_position
			WHERE knowledge_section_id = CURR_REC.knowledge_section_id;
		UPDATE hd_knowledge_section
			SET section_position = CURR_REC.section_position
			WHERE knowledge_section_id = UP_REC.knowledge_section_id;
	END IF;

	RETURN 1;
END;
$BODY$
  LANGUAGE 'plpgsql'
;

--- PROCEDURY DO TRIGGERÓW

CREATE OR REPLACE FUNCTION drop_information_body()
  RETURNS trigger AS
$BODY$
  BEGIN
    DELETE FROM hd_information_body WHERE information_id=OLD.information_id;
    RETURN OLD;
  END;
$BODY$
  LANGUAGE 'plpgsql'
;

--- TABELE i SEKWENCJE

--- ### użytkownicy

CREATE SEQUENCE user_id_seq
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1
;

CREATE TABLE hd_user (
  user_id integer NOT NULL DEFAULT nextval(('public.user_id_seq'::text)::regclass),
  "login" character varying(96) NOT NULL,
  passw character varying(40) NOT NULL,
  first_name character varying(64),
  last_name character varying(128),
  email character varying(128) NOT NULL,
  phone character varying(30),
  mobile character varying(20),
  "role" smallint NOT NULL DEFAULT 0,
  is_active boolean,
  CONSTRAINT hd_plain_user_pkey PRIMARY KEY (user_id)
) WITH (OIDS=FALSE)
;

--- ### kategorie zgłoszeń

CREATE SEQUENCE bug_category_id_seq
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1
;

CREATE TABLE hd_bug_category (
  category_id integer NOT NULL DEFAULT nextval(('public.bug_category_id_seq'::text)::regclass),
  parent_category integer,
  category_name character varying(64) NOT NULL,
  category_desc character varying(255),
  is_active boolean NOT NULL DEFAULT true,
  t_left bigint,
  t_right bigint,
  t_depth integer,
  CONSTRAINT hd_bug_category_pkey PRIMARY KEY (category_id),
  CONSTRAINT hd_bug_category_parent_category_fkey FOREIGN KEY (parent_category)
      REFERENCES hd_bug_category (category_id) MATCH SIMPLE
      ON UPDATE RESTRICT ON DELETE RESTRICT
) WITH (OIDS=FALSE)
;

--- ### zgłoszenia

CREATE SEQUENCE bug_id_seq
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1
;

CREATE TABLE hd_bug (
  bug_id integer NOT NULL DEFAULT nextval(('public.bug_id_seq'::text)::regclass),
  add_phone character varying(20),
  bug_category integer, -- NOT NULL
  bug_priority smallint NOT NULL,
  bug_status integer NOT NULL,
  saviour integer,
  notifyier integer,
  inputer integer,
  create_date timestamp with time zone,
  description text,
  step_by_step text,
  subject character varying(255) NOT NULL,
  department character varying(30),
  CONSTRAINT hd_bug_pkey PRIMARY KEY (bug_id)
) WITH (OIDS=FALSE);

CREATE TABLE hd_bug_desc (
  bug_id bigint NOT NULL,
  description text NOT NULL,
  CONSTRAINT hd_bug_desc_pkey PRIMARY KEY (bug_id)
) WITH (OIDS=FALSE);

--- ### zdarzenia zgłoszeń

CREATE SEQUENCE bug_event_id_seq
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1
;

CREATE TABLE hd_bug_event (
  event_id integer NOT NULL DEFAULT nextval(('public.bug_event_id_seq'::text)::regclass),
  bug_id integer NOT NULL,
  event_subject character varying(255) NOT NULL,
  event_date timestamp with time zone NOT NULL,
  user_id integer NOT NULL,
  event_type smallint NOT NULL DEFAULT 0,
  CONSTRAINT hd_bug_event_pkey PRIMARY KEY (event_id)
) WITH (OIDS=FALSE)
;

--- ### komentarze do zgłoszeń
CREATE SEQUENCE bug_comment_id_seq
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1
;

CREATE TABLE hd_bug_comment (
  comment_id integer NOT NULL DEFAULT nextval(('public.bug_comment_id_seq'::text)::regclass),
  comment_author integer NOT NULL,
  comment_date timestamp with time zone NOT NULL,
  comment_text text,
  not_for_plain_user boolean NOT NULL DEFAULT false,
  bug_id integer NOT NULL,
  CONSTRAINT hd_bug_comment_pkey PRIMARY KEY (comment_id),
  CONSTRAINT hd_bug_comment_comment_author_fkey FOREIGN KEY (comment_author)
      REFERENCES hd_user (user_id) MATCH SIMPLE
      ON UPDATE RESTRICT ON DELETE RESTRICT
) WITH (OIDS=FALSE)
;

--- ### BAZA WIEDZY

--- ### sekcje w bazie wiedzy

CREATE SEQUENCE knowledge_section_id_seq
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1
;

CREATE TABLE hd_knowledge_section (
  section_name character varying(255) NOT NULL,
  section_position integer NOT NULL,
  knowledge_section_id integer NOT NULL DEFAULT nextval(('public.knowledge_section_id_seq'::text)::regclass),
  articles_count integer NOT NULL DEFAULT 0
) WITH (OIDS=FALSE)
;

--- ### artukuły w bazie wiedzy

CREATE SEQUENCE knownledge_id_seq
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1
;

CREATE TABLE hd_knowledge (
  knowledge_id integer NOT NULL DEFAULT nextval(('public.knowledge_id_seq'::text)::regclass),
  title character varying(255) NOT NULL,
  hd_knowledge_section_id integer NOT NULL,
  create_date date,
  lead text,
  body text,
  user_id integer,
  CONSTRAINT hd_knownledge_pkey PRIMARY KEY (knowledge_id)
) WITH (OIDS=FALSE)
;

--- ### komentarze do artykułów

CREATE SEQUENCE knowledge_comment_id_seq
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1
;

CREATE TABLE hd_knowledge_comment (
  knowledge_comment_id integer NOT NULL DEFAULT nextval(('public.knowledge_comment_id_seq'::text)::regclass),
  knowledge_id integer NOT NULL,
  author integer NOT NULL,
  create_date date NOT NULL,
  title character varying(255) NOT NULL,
  body text NOT NULL,
  CONSTRAINT hd_knowledge_comment_pkey PRIMARY KEY (knowledge_comment_id)
) WITH (OIDS=FALSE)
;

--- ### wiążanie zgłoszeń z artykułami w bazie wiedzy

CREATE TABLE hd_bug_knowledge (
  bug_id integer NOT NULL,
  knowledge_id integer NOT NULL
) WITH (OIDS=FALSE)
;

--- ### informacje od działu helpdesk

CREATE SEQUENCE information_id_seq
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1
;

CREATE TABLE hd_information (
  information_id integer NOT NULL DEFAULT nextval(('public.information_id_seq'::text)::regclass),
  create_date date NOT NULL,
  title character varying(255) NOT NULL,
  lead text NOT NULL,
  CONSTRAINT hd_information_pkey PRIMARY KEY (information_id)
) WITH (OIDS=FALSE)
;

CREATE TABLE hd_information_body (
  information_id integer NOT NULL,
  body text NOT NULL,
  CONSTRAINT hd_information_body_information_id_fkey FOREIGN KEY (information_id)
      REFERENCES hd_information (information_id) MATCH SIMPLE
      ON UPDATE RESTRICT ON DELETE RESTRICT,
  CONSTRAINT hd_information_body_information_id_key UNIQUE (information_id)
) WITH (OIDS=FALSE)
;

--- TRIGGERY DO TABEL
CREATE TRIGGER drop_information_body_trg
  BEFORE DELETE
  ON hd_information
  FOR EACH ROW
  EXECUTE PROCEDURE drop_information_body()
;


--- ### WIDOKI

CREATE OR REPLACE VIEW bug_full_view AS
	SELECT
		bug.bug_id AS b_id,
		bug.subject AS b_subject,
		bug.add_phone AS b_add_hone,
		bug.create_date AS b_create_date,
		bug.step_by_step AS b_step_by_step,
		category.category_name AS c_name,
		bug.bug_priority AS p_id,
		descr.description AS b_description,
		notifier.first_name AS n_first_name,
		notifier.last_name AS n_last_name,
		inputer.first_name AS i_first_name,
		inputer.last_name AS i_last_name,
		saviour.first_name AS s_first_name,
		saviour.last_name AS s_last_name
	FROM hd_bug bug
	LEFT JOIN hd_user notifier ON bug.notifyier = notifier.user_id
	LEFT JOIN hd_user inputer ON bug.inputer = inputer.user_id
	LEFT JOIN hd_user saviour ON bug.saviour = saviour.user_id
	LEFT JOIN hd_bug_category category ON bug.bug_category = category.category_id
	LEFT JOIN hd_bug_priority priority ON bug.bug_priority = priority.priority_id
	LEFT JOIN hd_bug_desc descr ON bug.bug_id = descr.bug_id
;

CREATE OR REPLACE VIEW bug_list_view AS
	SELECT
		bug.bug_id AS b_id,
		bug.subject AS b_subject,
		bug.description AS b_description,
		bug.create_date AS b_create_date,
		bug.bug_status AS b_status,
		category.category_id AS c_id,
		category.category_name AS c_name,
		bug.bug_priority AS p_id,
		notifier.login AS n_login,
		notifier.first_name AS n_first_name,
		notifier.last_name AS n_last_name,
		notifier.user_id AS n_id,
		inputer.login AS i_login,
		inputer.first_name AS i_first_name,
		inputer.last_name AS i_last_name,
		inputer.user_id AS i_id,
		saviour.login AS s_login,
		saviour.first_name AS s_first_name,
		saviour.last_name AS s_last_name,
		saviour.user_id AS s_id
	FROM hd_bug bug
	LEFT JOIN hd_user notifier ON bug.notifyier = notifier.user_id
	LEFT JOIN hd_user inputer ON bug.inputer = inputer.user_id
	LEFT JOIN hd_user saviour ON bug.saviour = saviour.user_id
	LEFT JOIN hd_bug_category category ON bug.bug_category = category.category_id
;

CREATE OR REPLACE VIEW information_view AS
	SELECT
		hd_information.information_id,
		hd_information.create_date,
		hd_information.title,
		hd_information.lead,
		hd_information_body.body
	FROM hd_information
	LEFT JOIN hd_information_body ON hd_information.information_id = hd_information_body.information_id
;

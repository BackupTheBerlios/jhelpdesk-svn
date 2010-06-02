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

--- PROCEDURY

CREATE OR REPLACE FUNCTION category_move_down(bigint)
  RETURNS integer AS
$BODY$
DECLARE
	SECTION_ID ALIAS FOR $1;
	CURR_REC RECORD;
	DOWN_REC RECORD;
BEGIN
	LOCK TABLE article_category IN EXCLUSIVE MODE;

	SELECT INTO CURR_REC category_position,article_category_id
		FROM article_category
		WHERE article_category_id = SECTION_ID
		LIMIT 1;

	SELECT INTO DOWN_REC  category_position,article_category_id
		FROM article_category
		WHERE category_position < CURR_REC.category_position
		ORDER BY category_position DESC
		LIMIT 1;

	IF DOWN_REC.article_category_id IS NOT NULL THEN
		UPDATE article_category
			SET category_position = DOWN_REC.category_position
			WHERE article_category_id = CURR_REC.article_category_id;
		UPDATE article_category
			SET category_position = CURR_REC.category_position
			WHERE article_category_id = DOWN_REC.article_category_id;
	END IF;

	RETURN 1;
END;
$BODY$
  LANGUAGE 'plpgsql'
;

CREATE OR REPLACE FUNCTION category_move_up(bigint)
  RETURNS integer AS
$BODY$
DECLARE
	SECTION_ID ALIAS FOR $1;
	CURR_REC RECORD;
	UP_REC RECORD;
BEGIN
	LOCK TABLE article_category IN EXCLUSIVE MODE;

	SELECT INTO CURR_REC category_position,article_category_id
		FROM article_category
		WHERE article_category_id = SECTION_ID
		LIMIT 1;

	SELECT INTO UP_REC category_position,article_category_id
		FROM article_category
		WHERE category_position > CURR_REC.category_position
		ORDER BY category_position ASC
		LIMIT 1;

	IF UP_REC.article_category_id IS NOT NULL THEN
		UPDATE article_category
			SET category_position = UP_REC.category_position
			WHERE article_category_id = CURR_REC.article_category_id;
		UPDATE article_category
			SET category_position = CURR_REC.category_position
			WHERE article_category_id = UP_REC.article_category_id;
	END IF;

	RETURN 1;
END;
$BODY$
  LANGUAGE 'plpgsql'
;

--- PROCEDURY DO TRIGGER�W

CREATE OR REPLACE FUNCTION drop_announcement_body()
  RETURNS trigger AS
$BODY$
  BEGIN
    DELETE FROM announcement_body WHERE announcement_id=OLD.announcement_id;
    RETURN OLD;
  END;
$BODY$
  LANGUAGE 'plpgsql'
;

CREATE OR REPLACE FUNCTION set_category_position()
  RETURNS trigger AS
$BODY$
  DECLARE
    POSITION_FOR_NEW_CATEGORY integer;
  BEGIN
    LOCK TABLE article_category IN EXCLUSIVE MODE;
    SELECT INTO POSITION_FOR_NEW_CATEGORY COALESCE((SELECT max(category_position) FROM article_category)+1,0);
    NEW.category_position := POSITION_FOR_NEW_CATEGORY;
    RETURN NEW;
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

CREATE TABLE users (
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
  CONSTRAINT plain_user_pkey PRIMARY KEY (user_id)
) WITH (OIDS=FALSE)
;

--- ### kategorie zgłoszeń

CREATE SEQUENCE ticket_category_id_seq
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1
;

CREATE TABLE ticket_category (
  category_id integer NOT NULL DEFAULT nextval(('public.ticket_category_id_seq'::text)::regclass),
  parent_category integer,
  category_name character varying(64) NOT NULL,
  category_desc character varying(255),
  is_active boolean NOT NULL DEFAULT true,
  t_left bigint,
  t_right bigint,
  t_depth integer,
  CONSTRAINT ticket_category_pkey PRIMARY KEY (category_id),
  CONSTRAINT ticket_category_parent_category_fkey FOREIGN KEY (parent_category)
      REFERENCES ticket_category (category_id) MATCH SIMPLE
      ON UPDATE RESTRICT ON DELETE RESTRICT
) WITH (OIDS=FALSE)
;

INSERT INTO ticket_category (category_id, category_name, category_desc, t_left, t_right, t_depth) 
    VALUES (0, 'uncategorized', 'default category', 1, 2, 0);

--- ### zgłoszenia

CREATE SEQUENCE ticket_id_seq
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1
;

CREATE TABLE ticket (
  ticket_id integer NOT NULL DEFAULT nextval(('public.ticket_id_seq'::text)::regclass),
  add_phone character varying(20),
  ticket_category integer NOT NULL DEFAULT 0, 
  ticket_priority smallint NOT NULL,
  ticket_status integer NOT NULL,
  saviour integer,
  notifyier integer,
  inputer integer,
  create_date timestamp with time zone,
  description text,
  step_by_step text,
  subject character varying(255) NOT NULL,
  department character varying(30),
  CONSTRAINT ticket_pkey PRIMARY KEY (ticket_id),
  CONSTRAINT ticket_category_id_fk FOREIGN KEY (ticket_category)
    REFERENCES ticket_category (category_id) MATCH SIMPLE
    ON UPDATE NO ACTION ON DELETE NO ACTION
) WITH (OIDS=FALSE);

CREATE TABLE ticket_desc (
  ticket_id bigint NOT NULL,
  description text NOT NULL,
  CONSTRAINT ticket_desc_pkey PRIMARY KEY (ticket_id)
) WITH (OIDS=FALSE);

--- ### zdarzenia zgłoszeń

CREATE SEQUENCE ticket_event_id_seq
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1
;

CREATE TABLE ticket_event (
  event_id integer NOT NULL DEFAULT nextval(('public.ticket_event_id_seq'::text)::regclass),
  ticket_id integer NOT NULL,
  event_subject character varying(255) NOT NULL,
  event_date timestamp with time zone NOT NULL,
  user_id integer NOT NULL,
  event_type smallint NOT NULL DEFAULT 0,
  CONSTRAINT ticket_event_pkey PRIMARY KEY (event_id)
) WITH (OIDS=FALSE)
;

--- ### komentarze do zgłoszeń
CREATE SEQUENCE ticket_comment_id_seq
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1
;

CREATE TABLE ticket_comment (
  comment_id integer NOT NULL DEFAULT nextval(('public.ticket_comment_id_seq'::text)::regclass),
  comment_author integer NOT NULL,
  comment_date timestamp with time zone NOT NULL,
  comment_text text,
  not_for_plain_user boolean NOT NULL DEFAULT false,
  ticket_id integer NOT NULL,
  CONSTRAINT ticket_comment_pkey PRIMARY KEY (comment_id),
  CONSTRAINT ticket_comment_comment_author_fkey FOREIGN KEY (comment_author)
      REFERENCES users (user_id) MATCH SIMPLE
      ON UPDATE RESTRICT ON DELETE RESTRICT
) WITH (OIDS=FALSE)
;

--- ### BAZA WIEDZY

--- ### kategorie w bazie wiedzy

CREATE SEQUENCE article_category_id_seq
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1
;

CREATE TABLE article_category (
  category_name character varying(255) NOT NULL,
  category_position integer NOT NULL,
  article_category_id integer NOT NULL DEFAULT nextval(('public.article_category_id_seq'::text)::regclass),
  articles_count integer NOT NULL DEFAULT 0,
  CONSTRAINT article_category_pk PRIMARY KEY (article_category_id)
) WITH (OIDS=FALSE)
;

--- ### artukuły w bazie wiedzy

CREATE SEQUENCE article_id_seq
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1
;

CREATE TABLE article (
  article_id integer NOT NULL DEFAULT nextval(('public.article_id_seq'::text)::regclass),
  title character varying(255) NOT NULL,
  article_category_id integer NOT NULL,
  create_date date,
  lead text,
  body text,
  user_id integer,
  CONSTRAINT knownledge_pkey PRIMARY KEY (article_id)
) WITH (OIDS=FALSE)
;

--- ### komentarze do artykułów

CREATE SEQUENCE article_comment_id_seq
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1
;

CREATE TABLE article_comment (
  article_comment_id integer NOT NULL DEFAULT nextval(('public.article_comment_id_seq'::text)::regclass),
  article_id integer NOT NULL,
  author integer NOT NULL,
  create_date date NOT NULL,
  title character varying(255) NOT NULL,
  body text NOT NULL,
  CONSTRAINT article_comment_pkey PRIMARY KEY (article_comment_id)
) WITH (OIDS=FALSE)
;

--- ### wiazanie zgloszen z artykul�ami w bazie wiedzy

CREATE TABLE article_ticket
(
  article_id integer NOT NULL,
  ticket_id integer NOT NULL,
  CONSTRAINT article_ticket_pkey PRIMARY KEY (article_id, ticket_id),
  CONSTRAINT article_ticket_article_id_fkey FOREIGN KEY (article_id)
      REFERENCES article (article_id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT article_ticket_ticket_id_fkey FOREIGN KEY (ticket_id)
      REFERENCES ticket (ticket_id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
) WITH (OIDS=FALSE)
;

--- ### informacje od działu helpdesk

CREATE SEQUENCE announcement_id_seq
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1
;

CREATE TABLE announcement (
  announcement_id integer NOT NULL DEFAULT nextval(('public.announcement_id_seq'::text)::regclass),
  create_date date NOT NULL,
  title character varying(255) NOT NULL,
  lead text NOT NULL,
  CONSTRAINT announcement_pkey PRIMARY KEY (announcement_id)
) WITH (OIDS=FALSE)
;

CREATE TABLE announcement_body (
  announcement_id integer NOT NULL,
  body text NOT NULL,
  CONSTRAINT announcement_body_announcement_id_fkey FOREIGN KEY (announcement_id)
      REFERENCES announcement (announcement_id) MATCH SIMPLE
      ON UPDATE RESTRICT ON DELETE RESTRICT,
  CONSTRAINT announcement_body_announcement_id_key UNIQUE (announcement_id)
) WITH (OIDS=FALSE)
;

--- TRIGGERY DO TABEL
CREATE TRIGGER drop_announcement_body_trg
  BEFORE DELETE
  ON announcement
  FOR EACH ROW
  EXECUTE PROCEDURE drop_announcement_body()
;

CREATE TRIGGER set_category_position_trg
  BEFORE INSERT
  ON article_category
  FOR EACH ROW
  EXECUTE PROCEDURE set_category_position()
;

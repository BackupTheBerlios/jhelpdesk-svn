CREATE OR REPLACE FUNCTION section_move_up( int8 ) RETURNS int4 AS $$
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
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION section_move_down( int8 ) RETURNS int4 AS $$
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
$$ LANGUAGE plpgsql;

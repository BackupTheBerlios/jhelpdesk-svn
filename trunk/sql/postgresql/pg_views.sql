/* widoki */

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
	LEFT JOIN hd_bug_desc descr ON bug.bug_id = descr.bug_id;
	
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
	LEFT JOIN hd_bug_category category ON bug.bug_category = category.category_id;


CREATE OR REPLACE VIEW information_view AS 
	SELECT 
		hd_information.information_id, 
		hd_information.create_date, 
		hd_information.title, 
		hd_information.lead, 
		hd_information_body.body
	FROM hd_information
	LEFT JOIN hd_information_body ON hd_information.information_id = hd_information_body.information_id;

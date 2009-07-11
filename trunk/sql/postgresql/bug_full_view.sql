---
--- widok, z ktorego korzystam podczas wyswietlania 
--- szczegolow pojedynczego zgloszenia
--- 
CREATE VIEW bug_full_view AS
  SELECT 
    bug.hd_bug_id AS b_id,
    bug.subject AS b_subject,
    bug.add_phone AS b_add_hone,
    bug.create_date AS b_create_date,
    bug.step_by_step AS b_step_by_step,
    category.category_name AS c_name,
    priority.priority_name AS p_name,
    descr.description AS b_description,
    notifier.first_name AS n_first_name,
    notifier.last_name AS n_last_name,
    inputer.first_name AS i_first_name,
    inputer.last_name AS i_last_name,
    saviour.first_name AS s_first_name,
    saviour.last_name AS s_last_name
  FROM hd_bug AS bug
  LEFT OUTER JOIN hd_plain_user AS notifier
  ON bug.notifyier=notifier.user_id
  LEFT OUTER JOIN hd_plain_user AS inputer
  ON bug.inputer=inputer.user_id
  LEFT OUTER JOIN hd_plain_user AS saviour
  ON bug.saviour=saviour.user_id
  LEFT OUTER JOIN hd_bug_category AS category
  ON bug.bug_category=category.category_id
  LEFT OUTER JOIN hd_bug_priority AS priority
  ON bug.bug_priority=priority.priority_id
  LEFT OUTER JOIN hd_bug_desc AS descr
  ON bug.hd_bug_id=descr.hd_bug_id
---
--- widok, z ktorego korzystam podczas 
--- wyswietlania listy zgloszen
--- 
CREATE VIEW bug_list_view AS
  SELECT 
    bug.hd_bug_id AS b_id,
    bug.subject AS b_subject,
    bug.create_date AS b_create_date,
    category.category_name AS c_name,
    priority.priority_name AS p_name,
    notifier.first_name AS n_first_name,
    notifier.last_name AS n_last_name,
    notifier.user_id AS n_id,
    inputer.user_id AS i_id,
    saviour.user_id AS s_id
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
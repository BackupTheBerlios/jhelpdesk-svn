---
--- usuwanie powiazanych wpisow w hd_information_body
--- podczas usuwania rekordow w hd_information
---
CREATE OR REPLACE FUNCTION drop_information_body()
  RETURNS "trigger" AS
$BODY$
  BEGIN
    DELETE FROM hd_information_body WHERE information_id=OLD.information_id;
    RETURN OLD;
  END;
$BODY$
  LANGUAGE 'plpgsql';

CREATE TRIGGER drop_information_body_trg
  BEFORE DELETE
  ON hd_information
  FOR EACH ROW
  EXECUTE PROCEDURE drop_information_body();
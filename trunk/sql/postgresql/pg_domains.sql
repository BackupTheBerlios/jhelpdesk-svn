/* domeny */

CREATE DOMAIN hd_bool
  AS int2
	CONSTRAINT hd_bool_check CHECK ( 
		VALUE IN( 0, 1 )
	)
;
   
CREATE DOMAIN hd_event_type
  AS int2
  NOT NULL
	CONSTRAINT hd_event_type_check CHECK (
		VALUE IN( 1, 2, 3, 4, 5, 6, 7, 8, 9 )
	)
;

CREATE DOMAIN hd_role
  AS int2
  DEFAULT 1
	CONSTRAINT hd_role_check CHECK ( 
		VALUE IN( 1, 10, 100 )
	)
;
	
CREATE DOMAIN hd_status
  AS int2
  DEFAULT 1
  NOT NULL
	CONSTRAINT hd_status_check CHECK (
		VALUE IN( 1, 2, 3, 4, 5 )
	)
;

CREATE DOMAIN hd_bug_priority
  AS int2
  DEFAULT 1
  NOT NULL
    CONSTRAINT hd_bug_priority_check CHECK (
        VALUE IN( 1, 2, 3, 4, 5 )
    )
;
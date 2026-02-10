-- Grant unlimited tablespace quota to CINEMA user
ALTER USER CINEMA QUOTA UNLIMITED ON USERS;

-- Verify the quota
SELECT username, tablespace_name, max_bytes 
FROM dba_ts_quotas 
WHERE username = 'CINEMA';

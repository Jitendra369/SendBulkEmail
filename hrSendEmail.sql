select * from user_hr;
drop table user_hr;
use hrEmailData;

select * from user_hr;

select count(*) from user_hr where is_data_added_to_db = false;
select count(*) from user_hr where is_email_sent_to_company = true;

show tables;

-- ################### delete tables ###################
SET SQL_SAFE_UPDATES = 0;
DELETE FROM batch_step_execution_context;
DELETE FROM batch_step_execution;
DELETE FROM batch_job_execution_context;
DELETE FROM batch_job_execution_params;
DELETE FROM batch_job_execution;
DELETE FROM batch_job_instance;
SET SQL_SAFE_UPDATES = 1;
-- ################### delete tables ###################
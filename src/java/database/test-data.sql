-- -----------------------------------------------------
-- INSERTS
-- -----------------------------------------------------
-- -----------------------------------------------------
-- DONATION
-- -----------------------------------------------------
INSERT INTO `donation` ( `donation_amount`,`donation_date`,
                        `donor`)
     VALUES (10000, "2017-06-15", "Jane Doe");

INSERT INTO `donation` ( `donation_amount`,`donation_date`,
                        `donor` )
     VALUES (5000, "2017-06-30", "John Doe");


-- -----------------------------------------------------
-- COMPANY NAME
-- -----------------------------------------------------
INSERT INTO `company_name` (`company_id`, `company_name`) VALUES (1234, 'COBS');


-- -----------------------------------------------------
-- ROLE
-- -----------------------------------------------------
INSERT INTO `role` (`role_name`) VALUES ('test role');

INSERT INTO `role` (`role_name`, `role_description`) 
	 VALUES ('Uber Admin 9000', 'The most powerful admin in the universe!');

INSERT INTO `role` (`role_name`, `role_description`) 
	 VALUES ('Volunteer', 'Volunteer for a program');

INSERT INTO `role` (`role_name`, `role_description`) 
	 VALUES ('Manager', 'Manager for a program');


-- -----------------------------------------------------
-- PACKAGE TYPE
-- -----------------------------------------------------
INSERT INTO `ecssendb`.`package_type` (package_name, weight_lb)
	 VALUES ('Box', 35);
	 
INSERT INTO `ecssendb`.`package_type` (package_name, weight_lb)
	 VALUES ('Bag', 15);

-- -----------------------------------------------------
-- ORGANIZATION
-- -----------------------------------------------------
INSERT INTO `ecssendb`.`organization` (`street_address`, `postal_code`, 
		`org_city`, `org_code`, `org_name`, `phone_num`, `contact`, `is_active`)
	 VALUES ( '241 Whitefield Drive', 'T3Y5G2', 'Calgary', 23, 'Church #1', '123-234-5678', 'Jane Doe', 1);

-- -----------------------------------------------------
-- PROGRAM
-- -----------------------------------------------------
INSERT INTO `program` (`program_name`, `manager_name`, `is_active`) VALUES ('Chinese Emotional Support Hotline', 'Jilin Wang', true);
INSERT INTO `program` (`program_name`, `manager_name`, `is_active`) VALUES ('Community Outreach', 'Jane Doe', true);
INSERT INTO `program` (`program_name`, `manager_name`, `is_active`) VALUES ('Employment Incubator Program', 'Charlie Brown', false);
INSERT INTO `program` (`program_name`, `manager_name`, `is_active`) VALUES ('Special Needs Children Development and Support Program', 'Sarah Connor', false);
INSERT INTO `program` (`program_name`, `manager_name`, `is_active`) VALUES ('Youth Development Initiative', 'Bob Bodel', false);
INSERT INTO `program` (`program_name`, `manager_name`, `is_active`) VALUES ('University Students Practicum', 'Carrie Calaway', false);
INSERT INTO `program` (`program_name`, `manager_name`, `is_active`) VALUES ('Bursary and Scholarship Program', 'Alan Aderson', false);
INSERT INTO `program` (`program_name`, `is_active`) VALUES ('New Program', false);

-- -----------------------------------------------------
-- STORE
-- -----------------------------------------------------
INSERT INTO `store` (`store_id`, `street_address`, `postal_code`, `store_city`, `is_active`,`phone_num`, `contact`) 
	 VALUES (12345, '10 17 Ave SW', 'A9A9A9', 'Calgary', True, '123-2345', 'Jane Doe');
INSERT INTO `store` (`street_address`, `postal_code`, `store_city`, `is_active`,`phone_num`, `contact`) 
	 VALUES ( '2471 17 Ave NW', 'T3T4T4', 'Calgary', True, '122-2322', 'John Smith');
-- -----------------------------------------------------
-- TEAM
-- -----------------------------------------------------
INSERT INTO `team` (`program_id`,`store_id`) VALUES (1, 12345);

INSERT INTO `team` (`program_id`, `team_size`,`team_supervisor`,`store_id`) 
	 VALUES (1, 3,'John Smith',12345);


-- -----------------------------------------------------
-- USER
-- -----------------------------------------------------
INSERT INTO `user` (`email`, `is_admin`, `team_id`,`user_city`,`first_name`,`last_name`,`is_active`
					,`date_Of_birth`,`phone_number`,`home_address`,`postal_code`,`registration_date`, `password_salt`, `password_hash` )
  VALUES ('test@asd.as', True, 1, 'Calgary', 'Jane', 'Doe', TRUE, curdate(), '403-888-8888', 
			'123 pizza st s.w.', 'A1A1A1', curdate(), 'OloAEOkY3BGzc6wTRedRuL8c1JzkHq3UeCneTaEiIgo=', '94ec65f4df9c528f9e438bf4747366d58111e0eb36c782d20e9ec2c7494e0641');

INSERT INTO `user` (`email`, `is_admin`, `team_id`,`user_city`,`first_name`,`last_name`,`is_active`
					,`date_Of_birth`,`phone_number`,`home_address`,`postal_code`,`registration_date` ,`password_salt`, `password_hash` )
  VALUES ('test2@asd.as', False, 1, 'Calgary', 'John', 'Doe', TRUE, curdate(), '403-777-7777', 
			'321 pizza st s.w.', 'A1A1A1', curdate(),'QZKs/yX2vhFz2oto6NNc3LKuVt6xt/joYJTlvvGsAgQ=', '9e6b196b2c390db4ed171cfa7495b83230a28688029f94eff6c63300f0a3ce66');


-- -----------------------------------------------------
-- PROGRAM TRAINING
-- -----------------------------------------------------
INSERT INTO `program_training` (`user_id`,`role_id`,`program_id`) 
	 VALUES (1,1,1);

-- -----------------------------------------------------
-- TASK
-- -----------------------------------------------------
INSERT INTO `task` (`program_id`,`team_id`,`start_time`,`end_time`,`available`,`is_approved`,
					`approving_manager`,`task_city`)
	VALUES (1, 1, "2017-06-15 09:30:00", "2017-06-15 10:30:00", TRUE, FALSE, 'test manager',
			'Calgary');

INSERT INTO `task` (`program_id`,`team_id`,`max_users`,`start_time`,`end_time`,`available`,`is_approved`,
                    `notes`,`approving_manager`,`task_description`, `task_city`)
     VALUES (1, 1, 3, "2017-07-15 09:30:00", "2017-07-15 10:30:00", TRUE, FALSE, 
            'Task completed. No issues.', 'Manager John', 
            'Pickup boxes and drop to Ross Family', 'Calgary');
	
INSERT INTO `task` (`program_id`,`team_id`,`max_users`,`start_time`,`end_time`,`available`,`is_approved`,
                    `notes`,`approving_manager`,`task_description`, `task_city`)
     VALUES (1, 1, 1, "2017-07-18 12:30:00", "2017-07-19 15:30:00", TRUE, FALSE, 
            'Task completed. No issues.', 'Manager Jane', 
            'Pickup boxes and drop to Wilson Family', 'Calgary');


-- -----------------------------------------------------
-- HOTLINE DATA
-- -----------------------------------------------------
INSERT INTO `ecssendb`.hotline_data VALUES (1, 1);


-- -----------------------------------------------------
-- FOOD DELIVERY DATA
-- -----------------------------------------------------
INSERT INTO `food_delivery_data` (`task_fd_id`,`store_id`,`mileage`,`food_hours_worked`,`package_id`,
                                  `food_amount`,`family_count`)
     VALUES (2, 12345, 30, 4.5, 1, 12, 3);
	 
INSERT INTO `food_delivery_data` (`task_fd_id`,`store_id`,`mileage`,`food_hours_worked`,`package_id`,
                                  `food_amount`, `organization_id`)
     VALUES (3, 12345, 20, 3, 2, 6, 1);

-- -----------------------------------------------------
-- USER TASK
-- -----------------------------------------------------
INSERT INTO `ecssendb`.`user_task` (`user_id`, `task_id`)
	 VALUES (1, 1);
     


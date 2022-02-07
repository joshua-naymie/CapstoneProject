-- -----------------------------------------------------
-- INSERTS
-- -----------------------------------------------------
-- -----------------------------------------------------
-- DONATION
-- -----------------------------------------------------
INSERT INTO `donation` ( `donation_amount`,`donation_date`,`donation_type`,
                        `donor`,`donation_quantity` )
     VALUES (10000, "2017-06-15", "Cash", "Jane Doe", NULL);

INSERT INTO `donation` ( `donation_amount`,`donation_date`,`donation_type`,
                        `donor`,`donation_quantity` )
     VALUES (NULL, "2017-06-30", "Box", "John Doe", 20);


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
-- PROGRAM
-- -----------------------------------------------------
INSERT INTO `program` (`program_name`) VALUES ('test program');

INSERT INTO `ecssendb`.`program` VALUES (`program_id`, 'Food delivery', 'Jing');

-- -----------------------------------------------------
-- STORE
-- -----------------------------------------------------
INSERT INTO `store` (`store_id`, `street_address`, `postal_code`, `store_city`, `is_active`) 
	 VALUES (12345, '10 17 Ave SW', 'A9A9A9', 'Calgary', True);

-- -----------------------------------------------------
-- TEAM
-- -----------------------------------------------------
INSERT INTO `team` (`program_id`,`store_id`) VALUES (1, 12345);

INSERT INTO `team` (`program_id`, `team_size`,`team_supervisor`,`store_id`) 
	 VALUES (2, 3,'John Smith',12345);


-- -----------------------------------------------------
-- USER
-- -----------------------------------------------------
INSERT INTO `user` (`user_id`, `is_admin`, `team_id`,`user_city`,`first_name`,`last_name`,`is_active`,`user_password`
					,`date_Of_birth`,`phone_number`,`home_address`,`postal_code`,`registration_date` )
  VALUES ('test@asd.as', True, 1, 'Calgary', 'Jane', 'Doe', TRUE, 'password', curdate(), '403-888-8888', 
			'123 pizza st s.w.', 'A1A1A1', curdate());

INSERT INTO `user` (`user_id`, `is_admin`, `team_id`,`user_city`,`first_name`,`last_name`,`is_active`,`user_password`
					,`date_Of_birth`,`phone_number`,`home_address`,`postal_code`,`registration_date` )
  VALUES ('test2@asd.as', False, 1, 'Calgary', 'John', 'Doe', TRUE, 'password', curdate(), '403-777-7777', 
			'321 pizza st s.w.', 'A1A1A1', curdate());


-- -----------------------------------------------------
-- PROGRAM TRAINING
-- -----------------------------------------------------
INSERT INTO `program_training` (`user_id`,`role_id`,`program_id`) 
	 VALUES ('test2@asd.as',1,1);

-- -----------------------------------------------------
-- TASK
-- -----------------------------------------------------
INSERT INTO `task` (`program_id`,`team_id`,`start_time`,`end_time`,`available`,`is_approved`,
					`approving_manager`,`task_city`)
	VALUES (1, 1, "2017-06-15 09:30:00", "2017-06-15 10:30:00", TRUE, FALSE, 'test manager',
			'Calgary');

INSERT INTO `task` (`program_id`,`team_id`,`max_users`,`start_time`,`end_time`,`available`,`is_approved`,
                    `notes`,`approving_manager`,`task_description`, `task_city`)
     VALUES (2, 1, 3, "2017-07-15 09:30:00", "2017-07-15 10:30:00", TRUE, FALSE, 
            'Task completed. No issues.', 'Manager John', 
            'Pickup boxes and drop to Ross Family', 'Calgary');
	
INSERT INTO `task` (`program_id`,`team_id`,`max_users`,`start_time`,`end_time`,`available`,`is_approved`,
                    `notes`,`approving_manager`,`task_description`, `task_city`)
     VALUES (2, 1, 1, "2017-07-18 12:30:00", "2017-07-19 15:30:00", TRUE, FALSE, 
            'Task completed. No issues.', 'Manager Jane', 
            'Pickup boxes and drop to Wilson Family', 'Calgary');


-- -----------------------------------------------------
-- HOTLINE DATA
-- -----------------------------------------------------
INSERT INTO `ecssendb`.hotline_data VALUES (1, 1);


-- -----------------------------------------------------
-- FOOD DELIVERY DATA
-- -----------------------------------------------------
INSERT INTO `food_delivery_data` (`task_fd_id`,`store_id`,`mileage`,`food_hours_worked`,`food_type`,
                                  `food_amount`,`family_count`)
     VALUES (2, 12345, 30, 4.5, "Box", 12, 3);
	 
INSERT INTO `food_delivery_data` (`task_fd_id`,`store_id`,`mileage`,`food_hours_worked`,`food_type`,
                                  `food_amount`,`family_count`)
     VALUES (3, 12345, 20, 3, "Box", 6, 4);

-- -----------------------------------------------------
-- USER TASK
-- -----------------------------------------------------
INSERT INTO `ecssendb`.`user_task` (`user_id`, `task_id`)
	 VALUES ('test2@asd.as', 1);


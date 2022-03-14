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
INSERT INTO `company_name` (`company_id`, `company_name`) VALUES (12, 'Starbucks');
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
INSERT INTO `role` (`role_name`, `role_description`) 
     VALUES ('Supervisor', 'Supervisors/Approving Manager');
-- -----------------------------------------------------
-- ORGANIZATION
-- -----------------------------------------------------
INSERT INTO `ecssendb`.`organization` (`street_address`, `postal_code`, 
        `org_city`, `org_code`, `org_name`, `phone_num`, `contact`, `is_active`)
     VALUES ( '241 Whitefield Drive', 'T3Y5G2', 'Calgary', 23, 'Church #1', '123-234-5678', 'Jane Doe', 0);
-- -----------------------------------------------------
-- PROGRAM
-- -----------------------------------------------------
INSERT INTO `program` (`program_name`, `is_active`) VALUES ('Food Delivery', true);
INSERT INTO `program` (`program_name`, `is_active`) VALUES ('Chinese Emotional Support Hotline', true);
INSERT INTO `program` (`program_name`, `is_active`) VALUES ('Community Outreach', true);
INSERT INTO `program` (`program_name`, `is_active`) VALUES ('Employment Incubator Program', false);
INSERT INTO `program` (`program_name`, `is_active`) VALUES ('Special Needs Children Development and Support Program', false);
INSERT INTO `program` (`program_name`, `is_active`) VALUES ('Youth Development Initiative', false);
INSERT INTO `program` (`program_name`, `is_active`) VALUES ('University Students Practicum', false);
INSERT INTO `program` (`program_name`, `is_active`) VALUES ('Bursary and Scholarship Program', false);
-- INSERT INTO `program` (`program_name`, `is_active`) VALUES ('New Program', false);
-- -----------------------------------------------------
-- STORE
-- -----------------------------------------------------
INSERT INTO `store` (`store_id`, `street_address`, `postal_code`, `store_city`, `is_active`,`phone_num`, `contact`,`company_id`, `store_name`) 
     VALUES (12345, '10 17 Ave SW', 'A9A9A9', 'Calgary', True, '123-2345', 'Jane Doe', 1234, 'Kensingston COBS');
INSERT INTO `store` (`store_id`, `street_address`, `postal_code`, `store_city`, `is_active`,`phone_num`, `contact`,`company_id`, `store_name`) 
     VALUES (123, '17 Ave SW', 'A9A9A3', 'Calgary', True, '123-2345', 'John Doe', 12, 'Kensingston Starbucks');
-- -----------------------------------------------------
-- TEAM
-- -----------------------------------------------------
INSERT INTO `team` (`program_id`,`store_id`) VALUES (1, 12345);
INSERT INTO `team` (`program_id`, `team_size`,`team_supervisor`,`store_id`) 
     VALUES (1, 3,'John Smith',12345);
INSERT INTO `team` (`program_id`, `team_size`,`team_supervisor`,`store_id`) 
     VALUES (1, 5,'Ra Ad',123);
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
INSERT INTO `user` (`email`, `is_admin`, `team_id`,`user_city`,`first_name`,`last_name`,`is_active`
                    ,`date_Of_birth`,`phone_number`,`home_address`,`postal_code`,`registration_date` ,`password_salt`, `password_hash` )
  VALUES ('sarah.connor@termin.ator', False, 1, 'Calgary', 'Sarah', 'Connor', TRUE, curdate(), '123-456-7890', 
            '321 pizza st s.w.', 'A1A1A1', curdate(),'QZKs/yX2vhFz2oto6NNc3LKuVt6xt/joYJTlvvGsAgQ=', '9e6b196b2c390db4ed171cfa7495b83230a28688029f94eff6c63300f0a3ce66');
INSERT INTO `user` (`email`, `is_admin`, `team_id`,`user_city`,`first_name`,`last_name`,`is_active`
                    ,`date_Of_birth`,`phone_number`,`home_address`,`postal_code`,`registration_date`, `password_salt`, `password_hash` )
  VALUES ('rjk4752@gmail.com', True, 1, 'Calgary', 'Ra', 'Ad', TRUE, curdate(), '403-888-8888', 
            '123 pizza st s.w.', 'A1A1A1', curdate(), 'OloAEOkY3BGzc6wTRedRuL8c1JzkHq3UeCneTaEiIgo=', '94ec65f4df9c528f9e438bf4747366d58111e0eb36c782d20e9ec2c7494e0641');

UPDATE `program` SET `user_id` = 1 WHERE `program_id` = 1;
UPDATE `program` SET `user_id` = 2 WHERE `program_id` = 2;
UPDATE `program` SET `user_id` = 3 WHERE `program_id` = 3;

-- -----------------------------------------------------
-- PROGRAM TRAINING
-- -----------------------------------------------------
INSERT INTO `program_training` (`user_id`,`role_id`,`program_id`) 
     VALUES (1,1,1);
INSERT INTO `program_training` (`user_id`,`role_id`,`program_id`) 
     VALUES (4,5,1);

 -----------------------------------------------------
-- PACKAGE TYPE
-- -----------------------------------------------------
INSERT INTO `package_type` (`package_name`,`weight_lb`) 
     VALUES ("Bag", 10);
INSERT INTO `package_type` (`package_name`,`weight_lb`) 
     VALUES ("Tray", 15);
INSERT INTO `package_type` (`package_name`,`weight_lb`) 
     VALUES ("Box", 35);

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
     VALUES (3, 1, 1, "2017-07-18 12:30:00", "2017-07-19 15:30:00", TRUE, FALSE, 
            'Task completed. No issues.', 'Manager Jane', 
            'Pickup boxes and drop to Wilson Family', 'Calgary');
INSERT INTO `task` (`program_id`,`team_id`,`max_users`,`start_time`,`end_time`,`available`,`is_approved`,
                    `notes`,`approving_manager`,`task_description`, `task_city`, `is_submitted`, `is_dissaproved`)
     VALUES (2, 1, 1, "2017-07-18 12:30:00", "2017-07-19 15:30:00", FALSE, FALSE, 
            'Task completed. No issues.', 'Manager Jane', 
            'Pickup boxes and drop to Wilson Family', 'Calgary', TRUE, FALSE);
INSERT INTO `task` (`program_id`,`team_id`,`max_users`,`start_time`,`end_time`,`available`,`is_approved`,
                    `notes`,`approving_manager`,`task_description`, `task_city`, `is_submitted`, `is_dissaproved`)
     VALUES (2, 1, 1, "2017-07-18 12:30:00", "2017-07-19 15:30:00", FALSE, TRUE, 
            'Task completed. No issues.', 'Manager Jane', 
            'Pickup boxes and drop to Wilson Family', 'Calgary', FALSE, FALSE);
INSERT INTO `task` (`program_id`,`team_id`,`max_users`,`start_time`,`end_time`,`available`,`is_approved`,
                    `notes`,`approving_manager`,`task_description`, `task_city`, `is_submitted`, `is_dissaproved`)
     VALUES (1, 1, 1, "2017-07-18 12:30:00", "2017-07-19 15:30:00", FALSE, FALSE, 
            'Task completed. No issues.', 'Manager Jane', 
            'Pickup boxes and drop to Wilson Family', 'Calgary', FALSE, TRUE);
INSERT INTO `task` (`program_id`,`team_id`,`max_users`,`start_time`,`end_time`,`available`,`is_approved`,
                    `notes`,`approving_manager`,`task_description`, `task_city`, `is_submitted`, `is_dissaproved`)
     VALUES (1, 1, 1, "2017-07-18 12:30:00", "2017-07-19 15:30:00", FALSE, FALSE, 
            'Task completed. No issues.', 'Manager Jane', 
            'Pickup boxes and drop to Wilson Family', 'Calgary', TRUE, FALSE);
--

-- -----------------------------------------------------
-- HOTLINE DATA
-- -----------------------------------------------------
INSERT INTO `ecssendb`.hotline_data VALUES (1, 1);
INSERT INTO `ecssendb`.hotline_data VALUES (4, 5);
-- -----------------------------------------------------
-- FOOD DELIVERY DATA
-- -----------------------------------------------------
INSERT INTO `food_delivery_data` (`task_fd_id`,`store_id`,`mileage`,`food_hours_worked`,`package_id`,
                                  `food_amount`,`family_count`)
     VALUES (2, 12345, 30, 4.5, 1, 12, 3);
INSERT INTO `food_delivery_data` (`task_fd_id`,`store_id`,`mileage`,`food_hours_worked`,`package_id`,
                                  `food_amount`, `organization_id`)
     VALUES (3, 12345, 20, 3, 1, 6, 1);
INSERT INTO `food_delivery_data` (`task_fd_id`,`store_id`,`mileage`,`food_hours_worked`,`package_id`,
                                  `food_amount`, `organization_id`)
     VALUES (7, 12345, 20, 3, 1, 6, 1);
-- -----------------------------------------------------
-- USER TASK
-- -----------------------------------------------------
INSERT INTO `ecssendb`.`user_task` (`user_id`, `task_id`)
     VALUES (1, 1);
INSERT INTO `ecssendb`.`user_task` (`user_id`, `task_id`)
     VALUES (1, 4);
INSERT INTO `ecssendb`.`user_task` (`user_id`, `task_id`)
     VALUES (1, 5);
INSERT INTO `ecssendb`.`user_task` (`user_id`, `task_id`)
     VALUES (1, 6);
INSERT INTO `ecssendb`.`user_task` (`user_id`, `task_id`)
     VALUES (1, 7);


-- -----------------------------------------------------
-- ORGANIZATION
-- -----------------------------------------------------
INSERT INTO `organization` (`street_address`, `postal_code`, `org_city`, `org_code`, `org_name`, `phone_num`, `contact`, `is_active`) 
     VALUES ("155 Falconridge Crescent NE","T3J1Z9", "Calgary", 1, "Falconridge Family Church", "403-123-4567", "Pastor David", true);

INSERT INTO `organization` (`street_address`, `postal_code`, `org_city`, `org_code`, `org_name`, `phone_num`, `contact`, `is_active`) 
     VALUES ("155 Taradale Crescent NE","T3J1Z9", "Calgary", 1, "Taradale Church", "403-123-4567", "Pastor John", true);


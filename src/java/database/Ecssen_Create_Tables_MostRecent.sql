-- -----------------------------------------------------
-- DO WE NEED THIS?? Ask nicole
-- -----------------------------------------------------
SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';


-- -----------------------------------------------------
-- SCHEMA
-- -----------------------------------------------------

DROP SCHEMA IF EXISTS `ecssendb`;

CREATE SCHEMA IF NOT EXISTS `ecssendb` DEFAULT CHARACTER SET latin1;
USE `ecssendb`;


-- -----------------------------------------------------
-- DONATION
-- -----------------------------------------------------

CREATE TABLE IF NOT EXISTS `ecssendb`.`donation` (
  `donation_id` INT NOT NULL AUTO_INCREMENT,
  `donation_amount` DECIMAL(10,2),
  `donation_date` DATE NOT NULL,
  `donor` VARCHAR(50) NOT NULL,
  PRIMARY KEY (`donation_id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- COMPANY NAME
-- -----------------------------------------------------

CREATE TABLE IF NOT EXISTS `ecssendb`.`company_name` (
	`company_id` SMALLINT UNSIGNED NOT NULL AUTO_INCREMENT,
	`company_name` VARCHAR(100) NOT NULL,
	PRIMARY KEY (`company_id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- ROLE
-- -----------------------------------------------------

CREATE TABLE IF NOT EXISTS `ecssendb`.`role` (
  `role_id` TINYINT UNSIGNED NOT NULL AUTO_INCREMENT,
  `role_name` VARCHAR(20) NOT NULL,
  `role_description` VARCHAR(1000),
  PRIMARY KEY (`role_id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- PROGRAM
-- -----------------------------------------------------

CREATE TABLE IF NOT EXISTS `ecssendb`.`program` (
  `program_id` SMALLINT UNSIGNED NOT NULL AUTO_INCREMENT,
  `program_name` VARCHAR(100) NOT NULL UNIQUE,
  `user_id` INT,
  `is_active` BOOLEAN NOT NULL,
  PRIMARY KEY (`program_id`),
CONSTRAINT `fk_program_user_id`
    FOREIGN KEY (`user_id`)
    REFERENCES `ecssendb`.`user` (`user_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION
)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- STORE
-- -----------------------------------------------------

CREATE TABLE IF NOT EXISTS `ecssendb`.`store` (
	`store_id` INT NOT NULL AUTO_INCREMENT,
        `company_id` SMALLINT UNSIGNED NOT NULL,
	`street_address` VARCHAR(100) NOT NULL UNIQUE,
	`postal_code` CHAR(6) NOT NULL,
	`store_city` VARCHAR(50) NOT NULL,
	`phone_num` VARCHAR(15),
	`contact` VARCHAR(100),
        `store_name` VARCHAR(100) NOT NULL,
	`is_active` BOOLEAN NOT NULL,
	PRIMARY KEY (`store_id`),
            CONSTRAINT `fk_store_company_id`
            FOREIGN KEY (`company_id`)
            REFERENCES `ecssendb`.`company_name` (`company_id`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION,
	CONSTRAINT `ck_store_postal_code`
		CHECK (REGEXP_LIKE(`postal_code`, '[0-9][0-9][0-9][0-9][0-9]') OR
					REGEXP_LIKE(`postal_code`, '[0-9][0-9][0-9][0-9][0-9]-[0-9][0-9][0-9][0-9]') OR
					REGEXP_LIKE(`postal_code`, '[A-Y][0-9][A-Z][0-9][A-Z][0-9]')))
        
ENGINE = InnoDB;

-- -----------------------------------------------------
-- TEAM
-- -----------------------------------------------------

CREATE TABLE IF NOT EXISTS `ecssendb`.`team` ( 
`team_id` INT NOT NULL AUTO_INCREMENT,
`program_id` SMALLINT UNSIGNED,
`team_size` SMALLINT UNSIGNED,
`team_supervisor` INT,
`store_id` INT,
`team_name` VARCHAR(100),
PRIMARY KEY (`team_id`),
INDEX `fk_team_team_id_idx` (`team_id` ASC),
CONSTRAINT `fk_team_program_id`
    FOREIGN KEY (`program_id`)
    REFERENCES `ecssendb`.`program` (`program_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_team_store_id`
    FOREIGN KEY (`store_id`)
    REFERENCES `ecssendb`.`store` (`store_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- USER
-- -----------------------------------------------------

CREATE TABLE IF NOT EXISTS `ecssendb`.`user` (
  `user_id` INT NOT NULL,
  `email` VARCHAR(40) NOT NULL UNIQUE,
  `is_admin` BOOLEAN NOT NULL,
  `team_id` INT,
  `user_city` VARCHAR(50),
  `first_name` VARCHAR(50) NOT NULL,
  `last_name` VARCHAR(50) NOT NULL,
  `is_active` BOOLEAN NOT NULL,
  `date_of_birth` DATE,
  `phone_number` VARCHAR(15),
  `home_address` VARCHAR(100),
  `postal_code` CHAR(6),
  `registration_date` DATE NOT NULL,
  `reset_password_uuid` VARCHAR(50) NULL,
  `password_salt` CHAR(44) NOT NULL,
  `password_hash` CHAR(64) NOT NULL,
  PRIMARY KEY (`user_id`),
  INDEX `fk_user_team_id_idx` (`team_id` ASC),
  CONSTRAINT `fk_user_team_id`
    FOREIGN KEY (`team_id`)
    REFERENCES `ecssendb`.`team` (`team_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- PROGRAM TRAINING
-- -----------------------------------------------------

CREATE TABLE IF NOT EXISTS `ecssendb`.`program_training` (
`user_id` INT,
`role_id` TINYINT UNSIGNED,
`program_id` SMALLINT UNSIGNED,
PRIMARY KEY (`user_id`, `program_id`),
INDEX `fk_program_training_user_id_idx` (`user_id` ASC),
INDEX `fk_program_training_program_id_idx` (`program_id` ASC),
CONSTRAINT `fk_program_training_user_id`
    FOREIGN KEY (`user_id`)
    REFERENCES `ecssendb`.`user` (`user_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
CONSTRAINT `fk_program_training_program_id`
    FOREIGN KEY (`program_id`)
    REFERENCES `ecssendb`.`program` (`program_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
CONSTRAINT `fk_program_training_role_id`
    FOREIGN KEY (`role_id`)
    REFERENCES `ecssendb`.`role` (`role_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- TASK
-- -----------------------------------------------------

CREATE TABLE IF NOT EXISTS `ecssendb`.`task` (
  `task_id` BIGINT NOT NULL AUTO_INCREMENT,
  `program_id` SMALLINT UNSIGNED NOT NULL,
  `group_id` BIGINT NOT NULL,
  `team_id` INT NOT NULL,
  `max_users` TINYINT UNSIGNED,
  `start_time` DATETIME NOT NULL,
  `end_time` DATETIME,
  `available` BOOLEAN NOT NULL,
  `notes` VARCHAR(1000),
  `is_approved` BOOLEAN NOT NULL,
  `approving_manager` INT NOT NULL,
  `task_description` VARCHAR(1000),
  `task_city` VARCHAR(50),
  `is_submitted` BOOLEAN,
  `approval_notes` VARCHAR(300),
  `is_dissaproved` BOOLEAN,
  `user_id` INT,
  `assigned` BOOLEAN,
  `spots_taken` SMALLINT NOT NULL, 
  PRIMARY KEY (`task_id`),
  INDEX `fk_task_program_id_idx` (`program_id` ASC),
  INDEX `fk_task_team_id_idx` (`team_id` ASC),
  CONSTRAINT `fk_task_program_id`
    FOREIGN KEY (`program_id`)
    REFERENCES `ecssendb`.`program` (`program_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_task_team_id`
    FOREIGN KEY (`team_id`)
    REFERENCES `ecssendb`.`team` (`team_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_task_user_id`
    FOREIGN KEY (`user_id`)
    REFERENCES `ecssendb`.`user` (`user_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `ck_task_spots_taken`
    CHECK (`spots_taken` <= `max_users`))
ENGINE = InnoDB;



-- CREATE TABLE IF NOT EXISTS `ecssendb`.`program_task_data` (
--   `program_task_id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
--   `program_id` SMALLINT UNSIGNED NOT NULL,
--   `name` VARCHAR(10) NOT NULL,
--   `description` VARCHAR(128),
--   PRIMARY KEY (`program_task_id`),
--   INDEX `fk_program_task_data_program_id_idx` (`program_id` ASC),
--   CONSTRAINT `fk_program_task_data_program_id`
--     FOREIGN KEY (`program_id`)
--     REFERENCES `ecssendb`.`program` (`program_id`)
--     ON DELETE NO ACTION
--     ON UPDATE NO ACTION)
-- ENGINE = InnoDB;

-- CREATE TABLE IF NOT EXISTS `ecssendb`.`program_task_values` (
--   `program_task_id` INT UNSIGNED NOT NULL,
--   `task_id` INT UNSIGNED NOT NULL,
--   `value` VARCHAR(32) NOT NULL,
--   PRIMARY KEY (`program_task_id`, `task_id`),
--   INDEX `fk_program_task_values_program_task_id_idx` (`program_task_id` ASC),
--   INDEX `fk_program_task_values_task_id_idx` (`task_id` ASC),
--   CONSTRAINT `fk_program_task_values_program_task_id`
--     FOREIGN KEY (`program_task_id`)
--     REFERENCES `ecssendb`.`program_task_data` (`program_task_id`)
--     ON DELETE NO ACTION
--     ON UPDATE NO ACTION,
--   CONSTRAINT `fk_program_task_values_task_id`
--     FOREIGN KEY (`task_id`)
--     REFERENCES `ecssendb`.`task` (`task_id`)
--     ON DELETE NO ACTION
--     ON UPDATE NO ACTION)
-- ENGINE = InnoDB;


-- -----------------------------------------------------
-- HOTLINE DATA
-- -----------------------------------------------------

CREATE TABLE IF NOT EXISTS `ecssendb`.`hotline_data` (
  `task_hotline_id` BIGINT NOT NULL,
  `hotline_hours_worked` DECIMAL(4,2),
  PRIMARY KEY (`task_hotline_id`),
  INDEX `fk_hotline_data_task_hotline_id_idx` (`task_hotline_id` ASC),
  CONSTRAINT `fk_hotline_data_task_hotline_id`
    FOREIGN KEY (`task_hotline_id`)
    REFERENCES `ecssendb`.`task` (`task_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- FOOD DELIVERY DATA
-- -----------------------------------------------------

CREATE TABLE IF NOT EXISTS `ecssendb`.`food_delivery_data`(
  `task_fd_id` BIGINT NOT NULL,
  `store_id` INT NOT NULL,
  `package_id` TINYINT UNSIGNED NOT NULL,
  `organization_id` INT,
  `mileage` SMALLINT UNSIGNED,
  `food_hours_worked` DECIMAL(4,2),
  `food_amount` TINYINT UNSIGNED,
  `family_count` SMALLINT UNSIGNED,
  PRIMARY KEY (`task_fd_id`),
  INDEX `fk_fd_data_task_fd_id_idx` (`task_fd_id` ASC),
  CONSTRAINT `fk_fd_data_task_fd_id`
    FOREIGN KEY (`task_fd_id`)
    REFERENCES `ecssendb`.`task` (`task_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_food_delivery_data_store_id`
    FOREIGN KEY (`store_id`)
    REFERENCES `ecssendb`.`store` (`store_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_food_delivery_data_package_id`
    FOREIGN KEY (`package_id`)
    REFERENCES `ecssendb`.`package_type` (`package_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_food_delivery_data_organization_id`
    FOREIGN KEY (`organization_id`)
    REFERENCES `ecssendb`.`organization` (`organization_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Organization
-- -----------------------------------------------------

CREATE TABLE IF NOT EXISTS `ecssendb`.`organization` (
	`organization_id` INT NOT NULL AUTO_INCREMENT,
	`street_address` VARCHAR(100) NOT NULL,
	`postal_code` CHAR(6),
	`org_city` VARCHAR(50) NOT NULL,
	`org_code` SMALLINT UNSIGNED NOT NULL,
	`org_name` VARCHAR(100) NOT NULL,
	`phone_num` VARCHAR(15),
	`contact` VARCHAR(100),
	`description` VARCHAR(1000),
	`is_active` BOOLEAN NOT NULL,
	PRIMARY KEY (`organization_id`),
	CONSTRAINT `ck_org_postal_code`
		CHECK (REGEXP_LIKE(`postal_code`, '[0-9][0-9][0-9][0-9][0-9]') OR
					REGEXP_LIKE(`postal_code`, '[0-9][0-9][0-9][0-9][0-9]-[0-9][0-9][0-9][0-9]') OR
					REGEXP_LIKE(`postal_code`, '[A-Y][0-9][A-Z][0-9][A-Z][0-9]')))
ENGINE = InnoDB;

-- -----------------------------------------------------
-- package_type
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ecssendb`.`package_type` (
  `package_id` TINYINT UNSIGNED NOT NULL AUTO_INCREMENT,
  `package_name` VARCHAR(50) NOT NULL,
  `weight_lb` SMALLINT NOT NULL,
  PRIMARY KEY (`package_id`))
ENGINE = InnoDB;

-- -----------------------------------------------------
-- DO WE NEED THIS??
-- -----------------------------------------------------

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;



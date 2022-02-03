
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
  `donation_type` VARCHAR(50) NOT NULL,
  `donor` VARCHAR(50) NOT NULL,
  `donation_quantity` VARCHAR(50),
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
  `program_name` VARCHAR(100),
  `manager_name` VARCHAR(100),
  PRIMARY KEY (`program_id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- STORE
-- -----------------------------------------------------

CREATE TABLE IF NOT EXISTS `ecssendb`.`store` (
	`store_id` INT NOT NULL AUTO_INCREMENT,
	`street_address` VARCHAR(100) NOT NULL,
	`postal_code` CHAR(6) NOT NULL,
	`store_city` VARCHAR(50) NOT NULL,
	`is_active` BOOLEAN NOT NULL,
	PRIMARY KEY (`store_id`),
	CONSTRAINT `ck_store_postal_code`
		CHECK (REGEXP_LIKE(`postal_code`, '[0-9][0-9][0-9][0-9][0-9]') OR
					REGEXP_LIKE(`postal_code`, '[0-9][0-9][0-9][0-9][0-9]-[0-9][0-9][0-9][0-9]') OR
					REGEXP_LIKE(`postal_code`, '[A-Y][0-9][A-Z][0-9][A-Z][0-9]')))
ENGINE = InnoDB;

-- -----------------------------------------------------
-- TEAM
-- -----------------------------------------------------

CREATE TABLE IF NOT EXISTS `ecssendb`.`team` ( 
team_id INT NOT NULL AUTO_INCREMENT,
program_id SMALLINT UNSIGNED,
team_size SMALLINT UNSIGNED,
team_supervisor VARCHAR(100), 
store_id INT,
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
  `user_id` VARCHAR(40) NOT NULL,
  `is_admin` BOOLEAN NOT NULL,
  `team_id` INT,
  `user_city` VARCHAR(50),
  `first_name` VARCHAR(50) NOT NULL,
  `last_name` VARCHAR(50) NOT NULL,
  `is_active` BOOLEAN NOT NULL,
  `user_password` VARCHAR(100) NOT NULL,
  `date_of_birth` DATE,
  `phone_number` VARCHAR(15),
  `home_address` VARCHAR(100),
  `postal_code` CHAR(6),
  `registration_date` DATE NOT NULL,
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
`user_id` VARCHAR(100),
`role_id` TINYINT UNSIGNED,
`program_id` SMALLINT UNSIGNED,
PRIMARY KEY (`user_id`, `role_id`, `program_id`),
INDEX `fk_program_training_user_id_idx` (`user_id` ASC),
INDEX `fk_program_training_role_id_idx` (`role_id` ASC),
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
  `team_id` INT NOT NULL,
  `max_users` TINYINT UNSIGNED,
  `start_time` DATETIME NOT NULL,
  `end_time` DATETIME NOT NULL,
  `available` BOOLEAN NOT NULL,
  `notes` VARCHAR(1000),
  `is_approved` BOOLEAN NOT NULL,
  `approving_manager` VARCHAR(100) NOT NULL,
  `task_description` VARCHAR(1000),
  `task_city` VARCHAR(50) NOT NULL,
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
  CONSTRAINT `ck_task_start_end_time`
    CHECK (TIMEDIFF(`end_time`, `start_time`) >= 0))
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
  `mileage` SMALLINT UNSIGNED,
  `food_hours_worked` DECIMAL(4,2),
  `food_type` VARCHAR(10),
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
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

-- -----------------------------------------------------
-- USER TASK
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ecssendb`.`user_task` (
  `user_id` VARCHAR(100) NOT NULL,
  `task_id` BIGINT NOT NULL,
  PRIMARY KEY (`user_id`, `task_id`),
  INDEX `fk_user_task_user_id_idx` (`user_id` ASC),
  INDEX `fk_user_task_task_id_idx` (`task_id` ASC),
  CONSTRAINT `fk_user_task_user_id`
    FOREIGN KEY (`user_id`)
    REFERENCES `ecssendb`.`user` (`user_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_user_task_task_id`
    FOREIGN KEY (`task_id`)
    REFERENCES `ecssendb`.`task` (`task_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

-- -----------------------------------------------------
-- DO WE NEED THIS??
-- -----------------------------------------------------

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;



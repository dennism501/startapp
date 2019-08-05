-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema clients
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema clients
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `clients` DEFAULT CHARACTER SET latin1 ;
USE `clients` ;

-- -----------------------------------------------------
-- Table `clients`.`Client`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `clients`.`Client` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `cname` VARCHAR(200) NOT NULL,
  `fname` VARCHAR(200) NOT NULL,
  `number` VARCHAR(15) NOT NULL,
  `created_on` DATETIME NULL DEFAULT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 59
DEFAULT CHARACTER SET = latin1;


-- -----------------------------------------------------
-- Table `clients`.`Transaction`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `clients`.`Transaction` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `cname2` VARCHAR(100) NOT NULL,
  `amount` DECIMAL(19,2) NOT NULL,
  `cost` DECIMAL(19,0) NULL DEFAULT NULL,
  `type` VARCHAR(100) NOT NULL,
  `created_on` DATETIME NULL DEFAULT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 16
DEFAULT CHARACTER SET = latin1;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;

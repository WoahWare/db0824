--
-- General sql file for database init.
--      This allows us to potentially fill-in test data or other types of data into tables that should match a production database.
--      And, gives us the ability to test these set-ups out locally. 
--      This is the schema.sql file (aka:  DDL)
--


DROP TABLE IF EXISTS `tool`;
DROP TABLE IF EXISTS `tool_type_charge`;
DROP TABLE IF EXISTS `tool_type`;
DROP TABLE IF EXISTS `brand`;
DROP TABLE IF EXISTS `holiday`;


-- brand
CREATE TABLE `brand` (
    `id` int(11) PRIMARY KEY AUTO_INCREMENT,
    `name` varchar(50) NOT NULL,
    `deprecated_on` DATE DEFAULT NULL,
     INDEX ix_brand_name (`name`)
  ) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=UTF8;

-- tool_type
CREATE TABLE `tool_type` (
    `id` int(11) PRIMARY KEY AUTO_INCREMENT,
    `name` varchar(50) NOT NULL,
    `deprecated_on` DATE DEFAULT NULL,
     INDEX ix_tool_type_name (`name`)
  ) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=UTF8;

-- tool_type_charge
CREATE TABLE `tool_type_charge` (
    `id` int(11) PRIMARY KEY AUTO_INCREMENT,
    `tool_type_id` int(11) NOT NULL,
    `daily_charge` DECIMAL(10,3),
    `week_day_charge` Boolean,
    `week_end_charge` Boolean,  
    `holiday_charge` Boolean,
    `deprecated_on` DATE DEFAULT NULL,
    FOREIGN KEY (`tool_type_id`) REFERENCES `tool_type` (`id`)
    --  INDEX ix_tool_type_id (tool_type_id)  --> because of InnoDB, an index for foreign keys gets created automatically on create table
  ) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=UTF8;

-- tool
CREATE TABLE `tool` (
    `id` int(11) PRIMARY KEY AUTO_INCREMENT,
    `tool_code` varchar(50) NOT NULL,
    `tool_type_id` int(11) NOT NULL,
    `brand_id` int(11) NOT NULL,
    `deprecated_on` DATE DEFAULT NULL,
    UNIQUE KEY `tool_code_UNIQUE` (`tool_code`),
    FOREIGN KEY (`tool_type_id`) REFERENCES `tool_type` (`id`),
    FOREIGN KEY (`brand_id`) REFERENCES `brand` (`id`) ,
    INDEX ix_tool_code (tool_code)
    --  INDEX ix_tool_type_id (tool_type_id)  --> because of InnoDB, an index for foreign keys gets created automatically on create table
    --  INDEX ix_brand_id (brand_id)  --> because of InnoDB, an index for foreign keys gets created automatically on create table
  ) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=UTF8;


-- holiday
CREATE TABLE `holiday` (
    `id` int(11) PRIMARY KEY AUTO_INCREMENT,
    `name` varchar(255) DEFAULT NULL,
    `occurrence_date` DATE,
    `holiday_adjust_type` varchar(255) DEFAULT NULL,
    `adjust_weekday` varchar(50) DEFAULT NULL,    
    `deprecated_on` DATE DEFAULT NULL,
     INDEX ix_holiday_name (`name`)
  ) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=UTF8;

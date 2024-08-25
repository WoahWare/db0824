--
-- General sql file for database init.
--      This allows us to potentially fill-in test data or other types of data into tables that should match a production database.
--      And, gives us the ability to test these set-ups out locally. 
--      This is the test-data.sql file (aka: test DML)
--      -> It is specifically made to act as test data for our various tests (or all tests that use the "test" profile)
--


-- Data for table brand
INSERT INTO `brand` (`name`) VALUES 
('Stihl'), ('Werner'), ('DeWalt'),  ('Ridgid');

-- Data for table tool_type
INSERT INTO `tool_type` (`name`) VALUES 
('Chainsaw'), ('Ladder'), ('Jackhammer');

-- Data for table tool_type_charge
INSERT INTO `tool_type_charge` (`tool_type_id`, `daily_charge`, `week_day_charge`, `week_end_charge`, `holiday_charge` ) VALUES 
(2, 1.99, 1, 1, 0), 
(1, 1.49, 1, 0, 1), 
(3, 2.99, 1, 0, 0);

-- Data for table tool
INSERT INTO `tool` (`tool_code`, `tool_type_id`, `brand_id` ) VALUES 
('CHNS', 1, 1),
('LADW', 2, 2),
('JAKD', 3, 3),
('JAKR', 3, 4);

-- Data for table holiday
INSERT INTO `holiday` (`name`, `occurrence_date`, `holiday_adjust_type`, `adjust_weekday`) VALUES 
('Independence Day', '2024-07-04', 'weekend', null),
('Labor Day', '2024-09-01', 'first_weekday', 'Monday');


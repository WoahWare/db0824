A Simple Tool Rental Application
-------------------------------------------------------
Copyright (C) 2024 WoahWare (Dan Brown)

-------------------------------------------------------

![tool_rental_app_preview1](https://github.com/user-attachments/assets/77f33123-6f0c-4262-b122-ab9ecfe1a129)

This is a simple tool rental application -- similar to point of sale tools for stores.  
It was created utilizing Java Spring, Hibernate, Spring Data JPA, H2, and some simple JSP pages (for the UI).  
There is a maven file which controls the dependencies for all of these.  
All of this is coded in JDK 8, as it is still the most commonly used JDK version in production environments, so why not?  

There is a GUI / user interface you can access (and should, as there is no console commands / interaction with the console enabled).  
Once the application starts up and is running, you can access it by opening the browser and going to your local-host:  
http://localhost:8080/

From there, you can access and view all Tools, Tool Types, Brands, and Rental Holidays.  
You can also generate Rental Agreements (rent out tools).   
Rental Agreements allow you to see the rental costs (and deductions as well as other information) involved when renting out one of the tools.  

-------------------------------------------------------

Concepts and Ideas behind how this app works:
-------------------------------------------------------

-- DATA --  
All data is stored within an H2 (in memory) database that mimics a MYSQL database.  
On start-up or when a test runs, the schema.sql and data.sql files are run automatically, which create the database from scratch.  
This allows for testing of certain data and set ups locally without the need to have any specific set up to begin with (no separate DB required for example).  

Moreover, all tests use a "test" profile, meaning they actually use a separate set of data compared to what the application might use on start-up. More about this and tests in general can be found below in the -- Tests -- section of the README.  

Data is normalized such that there is greater flexibility and maintainability in the future.  
For example, all tool data is not in one table; it is separated into a tool table, a tool type table, and a tool type charge table.  
All tables are indexed, and in the case of those tables, there is a reference key that connects them together.  

Brands have also been separated into their own table (and the tool table has a reference to them).  
Holidays also have their own table and each holiday record has an occurrence date, followed by adjustment date parameter fields (see below).  

All tables also have a deprecated_on field for when we want to "remove" data without actually removing it (and know when it was "removed").  

There are also Java entity and model classes that represent these tables and are used through out the code.  


-- Holidays --   
Holidays are handled somewhat uniquely. To keep them dynamic, I created functions that reflect different kinds of holiday business rules.  
So for example, July 4th -- if it occurs on Saturday, then we assign it as though it happened on Friday. If it occurs on Sunday, then we assign Monday.  
But, other holidays might exist that probably follow similar rules, so why not make a function that does that for all holidays of that type?  

That's exactly what I did. Multiple functions exist for adjusting a holiday's date to a correct one, according to specific business rules.  
So, then we just need some way to map that function to those holidays... keyword here being "map".  
A map exists that specifically maps a string (could be later changed to some specific identifier or id) to those functions.  

In the database, each holiday record has an adjustment-type field that is a string that is used as a key for that map.  
Other fields also exist that are used in addition in some cases for specific functions that require multiple parameters for holiday date adjustment.  
As a result, this allows us to add new Holidays in the future of various types, even with new adjustment functions for their dates, easily.  

A method is then called to get the associated function and apply that function to the Holiday's date.  
It then returns the correct corresponding real/ actual date of the Holiday for a given year.  

That given year is given by the checkout date's year.   
So our method actually returns a list of adjusted Holidays pertaining to the year associated with the checkout date.  

But wait, what if the checkout and rental days span over multiple years or a company decides to rent for more than a year (365+ rental days)?  
Good question -- the code is designed to look at that too.   
It calculates the due date, and then basically loops over for each year between the checkout date and due date (end date).   
It adds all adjusted Holidays to a final list through out that loop process, and then returns the final list (filtered to be within the correct date range).  

Finally, we do not want to modify the original Holiday model's date as that could cause problems, so that's why a DTO exists.   
The DTO is basically a reference to the model where we can change its date to whatever and pass it around between layers and services.  


-- Rental Agreements --    
The main thing here is calculating the total number of charge days.   
This is figured out from the use of the created services like the Holiday service and calculating the number of total weekdays within the range of the checkout.  
And, of course, some logic to represent business logic depending on the tool's charge attributes (ie: is it charged for weekends? holidays?).  

After that, all other fields pertaining to rental agreements are calculated through functions found in the Rental Agreement class.  
Once all the corresponding information is calculated, all of this is used to create a rental agreement object.  
This is done via a Builder pattern, as that allows for much more specific control when creating such objects, and because I like builders (don't judge me).  

There is also a print function that uses reflection to get corresponding fields of the rental agreement and print them out to the console, according to specification.  
At the moment, I have added it to the end of the generate rental agreement method so that users can see it that way whenever renting out a tool.  
You can also see a version of the agreement on the user interface, but that lacks the fancy formatting and look of the ones produced by the print function.   
Having both is nice though.   


-- Tests --  
There are quite a few tests. These are run every time you clean and build the application.  
Not for every class (even though, there should be), but enough to cover points of failure as well as spot checks and also ones based on specification.  
And also a few tests for performance and to see how the application would handle it (ie: see the tests that include 10 years worth of rental days).  

The data that is used for the tests can be specifically created for them. This is because all tests are using a "test" profile.  
This means that they will load data from a test-data.sql file instead of the original data.sql file.   
This allows us to add data locally to the original data.sql file for the local application itself, while not breaking any tests that depend on said data.  
This also allows us to test new data by modifying test-data.sql, and seeing which tests break, and then modify the tests as needed.  
Currently, the data in data.sql and test-data.sql match, but again, this can be changed for various benefits and reasons.  

All tests utilize JUnit4. 

-------------------------------------------------------

Final thoughts:  

Hope you enjoy playing around with this Tool Rental Application.   

There are probably some things I would change if I were to spend more time on this, but otherwise, it is in a decent state.   
These would be things like optimizations (less fetching from the database for instance) and making sure certain things are standardized through-out the code base.  
Standardized things would include things like creating DTOs for all models, specific logging handling rules (ie: when to log and not), etc.   
I would also change to using flyway beans for proper database migration and management, instead of just relying on the schema.sql and data.sql scripts.  

Future extensions of this application would probably depend on specific use cases such as who else might use this application.  
In which case, maybe adding in some security as well as user validation and some admin powers to add or update some of the data.  
So, things like giving the power to create or update tools, brands, etc. for admins.  
And then for users, the ability of saving the generated rental agreement into the database with a reference to the user's ID.  

All in all though, I'm content with how this turned out.  
I think that this can be of good reference for other developers who are curious as to how to create something like this.  

Thanks and have fun!

-------------------------------------------------------

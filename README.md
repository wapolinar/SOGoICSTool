# SOGoICSTool
This tool synchronizes ICS calendars registered with SOGo.

This is a tool that uses Java 8 to call the SOGo refresh link for connected ICS calendars (for example from Google).
## Requirements
It assumes that  
1. SOGo is running at http://127.0.0.1:20000/SOGo/  
2. The SOGO database is running on localhost, port 3306 (and can be accessed through a mySQL/mariaDB connector.  

## Compilation
It can be compiled using maven (pom.xml is provided), the mariadb-java-client (mySQL compatible) will be retrieved automatically as dependency, in this case.  
Just call `mvn clean install` in the sogoTool directory and retrieve the jar with dependencies from the target directory.  

## Parameters
Required parameters are:  
`--db_name` The database name  
`--db_user` The database user (that only needs read access)  
`--db_password` The password for the database user  

An example call is:  
`java -jar sogo-1.0-jar-with-dependencies.jar --db_name sogo --db_user sogoUpdate --db_password myPassword`.

Note that the db-password will be visible for other users on the same machine (for example, if they use "ps").

## Other stuff
An example CRON bash file is provided (sogoUpdate), it can be put into /etc/cron.daily/.

Tested with SOGo 3.0.1

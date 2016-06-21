package de.apolinarski.tool.sogo;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.function.Predicate;

public class Main {
	
	private static final String DATABASE_NAME="--db_name";
	private static final String DATABASE_USER="--db_user";
	private static final String DATABASE_PASSWORD="--db_password"; //TODO: Read in the password from a file

	public static void main(String[] args) {
		Predicate<String> parameterPredicate=new Predicate<String>() {

			private boolean foundDB=false;
			private boolean foundUser=false;
			private boolean foundPassword=false;
			
			@Override
			public boolean test(String t) {
				if(!foundDB && DATABASE_NAME.equals(t))
				{
					foundDB=true;
					return true;
				}
				else if (!foundUser && DATABASE_USER.equals(t))
				{
					foundUser=true;
					return true;
				}
				else if (!foundPassword && DATABASE_PASSWORD.equals(t))
				{
					foundPassword=true;
					return true;
				}
				else
				{
					return false;
				}
			}
		};
		if(args.length!=6 || Arrays.stream(args).filter(parameterPredicate).count()!=3) //3 parameters and 3 values == 6
		{
			System.out.println("Usage:\njava -jar sogo.jar "+DATABASE_NAME+" <DB_NAME> "+DATABASE_USER+" <DB_USER> "+DATABASE_PASSWORD+" <DB_PASSWORD>");
			return;
		}
		
		String dbName=null,dbUser=null,dbPassword=null;
		
		for(int i=0;(i+1)<args.length;i++)
		{
			if(DATABASE_NAME.equals(args[i]))
			{
				dbName=args[i+1];
			}
			else if(DATABASE_USER.equals(args[i]))
			{
				dbUser=args[i+1];
			}
			else if(DATABASE_PASSWORD.equals(args[i]))
			{
				dbPassword=args[i+1];
			}
		}
		//Create MariaDB network connection
		try (SOGoConnector connector=new SOGoConnector(dbUser, dbPassword, dbName))
		{
			connector.getUsers();
			connector.reloadCalendars();
		}
		catch(SQLException e)
		{
			System.err.println("SQLException was thrown while trying to reload external SOGo calendars: "+e.getMessage());
		}
	}

}

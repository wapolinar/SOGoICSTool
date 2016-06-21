package de.apolinarski.tool.sogo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

public class SOGoConnector implements AutoCloseable {

//	private final String username;
//	private final String password;
//	private final String databaseName;
	
	private final Connection connection;
	private final HashMap<String,SOGoUser> users=new HashMap<>();
	private static final String USER_QUERY="SELECT c_path2,c_path4 FROM sogo_folder_info WHERE c_path3 = 'Calendar' AND c_path4 <> 'personal' AND c_folder_type = 'Appointment';";
	
	public SOGoConnector(String username, String password, String databaseName) throws SQLException
	{
		connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/"+databaseName, username, password);
	}
	
	public void getUsers() throws SQLException
	{
		Statement retrieveUsers=connection.createStatement();
		ResultSet result=retrieveUsers.executeQuery(USER_QUERY);
		while(result.next())
		{
			String userName=result.getString(1); //User name
			if(userName!=null)
			{
				SOGoUser user=users.get(userName);
				String calendarId=result.getString(2);
				if(user==null && calendarId!=null)
				{
					user=new SOGoUser(userName);
					user.addCalendar(calendarId);
					users.put(userName, user);
				}
				else if(calendarId!=null) //user!=null
				{
					user.addCalendar(calendarId);
				}
			}
		}
		result.close();
		retrieveUsers.close();
	}
	
	public void closeConnection() throws SQLException
	{
		connection.close();
	}
	
	public void reloadCalendars()
	{
		users.values().parallelStream()
			.forEach(Utils::callCurl);
	}

	@Override
	public void close() throws SQLException {
		closeConnection();
	}
	

}

package de.apolinarski.tool.sogo;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class Utils {
	
	private static final String FIRST_STRING_PART="http://127.0.0.1:20000/SOGo/so/";
	private static final String SECOND_STRING_PART="/Calendar/";
	private static final String LAST_STRING_PART="/reload";
	private static final String HTTP_HEADER_FIELD="x-webobjects-remote-user";
	
	public static void callCurl(SOGoUser user)
	{
		StringBuilder sb=new StringBuilder();
		sb.append(FIRST_STRING_PART);
		sb.append(user.getName());
		sb.append(SECOND_STRING_PART);
		final String urlFirstPart=sb.toString();
		
		user.getCalendarIds().parallelStream()
			.forEach((calendarId) -> {
				StringBuilder lambdaSB=new StringBuilder(urlFirstPart.length()+LAST_STRING_PART.length()+calendarId.length());
				lambdaSB.append(urlFirstPart);
				lambdaSB.append(calendarId);
				lambdaSB.append(LAST_STRING_PART);
				try
				{
					URL url=new URL(lambdaSB.toString());
					HttpURLConnection connection=(HttpURLConnection) url.openConnection();
					connection.setRequestProperty(HTTP_HEADER_FIELD, user.getName());
					connection.connect();
					if(connection.getResponseCode()!=200)
					{
						System.err.println("Could not reload calendar for user: "+user.getName()+" Error: "+connection.getResponseMessage());
					}
				}
				catch(IOException e)
				{
					System.err.println("Could not establish connection to SOGo for user: "+user.getName()+" Error: "+e.getMessage());
				}
			});
	}
	
}

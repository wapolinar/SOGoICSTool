package de.apolinarski.tool.sogo;

import java.util.ArrayList;
import java.util.List;

public class SOGoUser {

	private final String name;
	private final ArrayList<String> calendarUrls=new ArrayList<>();
	
	public SOGoUser(String name)
	{
		this.name=name;
	}
	
	public void addCalendar(String calendarId)
	{
		calendarUrls.add(calendarId);
	}
	
	public List<String> getCalendarIds()
	{
		return calendarUrls;
	}
	
	public String getName()
	{
		return name;
	}
	
	public boolean hasExternalCalendars()
	{
		return calendarUrls.size() > 0;
	}

}

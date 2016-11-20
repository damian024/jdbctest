package com.example.jdbcdemo.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.junit.Test;

import com.example.jdbcdemo.domain.Event;
import com.example.jdbcdemo.domain.Sponsor;

import junit.framework.Assert;

public class EventManagerTest {
	SponsorManager sponsorManager = new SponsorManager();
	EventManager eventManager = new EventManager();
	
	private final static String NAME_1 = "Wyœcigi";
	private final static String NAME_2 = "Impreza samochodowa";
	private final static String ABOUT_1 = "Wyœcigi formu³y jeden na torze w Poznaniu";
	
	private final static String NAME_SPONSOR_1 = "Oknex";
	private final static String SPONSOR_ABOUT_1 = "Oknext to najwiekszy producent okien na pomorzu";
	
	@Test
	public void checkConnection(){
		assertNotNull(eventManager.getConnection());
	}
	
	@Test
	public void checkAdding() throws SQLException{
		Connection connection = eventManager.getConnection();
		connection.setAutoCommit(false);
		Connection connSponsor = sponsorManager.getConnection();
		connSponsor.setAutoCommit(false);
		try
		{
			Sponsor sponsor = new Sponsor(NAME_SPONSOR_1,SPONSOR_ABOUT_1);
			sponsorManager.addSponsor(sponsor);
			List<Sponsor> sponsors = sponsorManager.getAllSponsors();
			sponsor = sponsors.get(sponsors.size()-1);
			
			Event event = new Event(NAME_1,ABOUT_1,sponsor.getId());
			
			assertEquals(1,eventManager.addEvent(event));
			
			List<Event> events = eventManager.getAllEvents();
			
			int size = events.size();
			Event eventRetrieved = events.get(size -1);
			
			assertEquals(NAME_1,eventRetrieved.getName());
			assertEquals(ABOUT_1,eventRetrieved.getAbout());
			assertEquals(sponsor.getId(),eventRetrieved.getMainSponsor());
		
		  } finally {
			    connection.rollback();
			    connection.close();
			    connSponsor.rollback();
			    connSponsor.close();
		  }
	}
	@Test
	public void checkEditing() throws SQLException{
		Connection connection = eventManager.getConnection();
		connection.setAutoCommit(false);
		Connection connSponsor = sponsorManager.getConnection();
		connSponsor.setAutoCommit(false);
		try
		{
			Sponsor sponsor = new Sponsor(NAME_SPONSOR_1,SPONSOR_ABOUT_1);
			sponsorManager.addSponsor(sponsor);
			List<Sponsor> sponsors = sponsorManager.getAllSponsors();
			sponsor = sponsors.get(sponsors.size()-1);
					
			Event event = new Event(NAME_1,ABOUT_1,sponsor.getId());
			assertEquals(1,eventManager.addEvent(event));
			
			List<Event> events = eventManager.getAllEvents();
			
			int size = events.size();
			Event eventRetrieved = events.get(size -1);
			events.remove(size -1);
			
			eventRetrieved.setName(NAME_2);
			assertEquals(1,eventManager.EditEvent(eventRetrieved));
			
			List<Event> events1 = eventManager.getAllEvents();
			size = events1.size();
			Event eventRetrieved1 = events1.get(size -1);
			
			events1.remove(size- 1);
			assertEquals(events, events1);
			assertEquals(NAME_2,eventRetrieved1.getName());
			assertEquals(ABOUT_1,eventRetrieved1.getAbout());
			assertEquals(sponsor.getId(),eventRetrieved.getMainSponsor());
		  } finally {
			    connection.rollback();
			    connection.close();
			    connSponsor.rollback();
			    connSponsor.close();
		  }
	}
	@Test
	public void checkDeleting() throws SQLException{
		Connection connection = eventManager.getConnection();
		connection.setAutoCommit(false);
		Connection connSponsor = sponsorManager.getConnection();
		connSponsor.setAutoCommit(false);
		try
		{
		
		Sponsor sponsor = new Sponsor(NAME_SPONSOR_1,SPONSOR_ABOUT_1);
		sponsorManager.addSponsor(sponsor);
		List<Sponsor> sponsors = sponsorManager.getAllSponsors();
		sponsor = sponsors.get(sponsors.size()-1);
				
		Event event = new Event(NAME_1,ABOUT_1);
		assertEquals(1,eventManager.addEvent(event));
		
		List<Event> events = eventManager.getAllEvents();
		
		int size = events.size();
		Event eventRetrieved = events.get(size -1);
		
		assertEquals(1,eventManager.DeleteEvent(eventRetrieved));
		events.remove(size -1);
		List<Event> events1 = eventManager.getAllEvents();
		assertEquals(events, events1);
		} finally {
		    connection.rollback();
		    connection.close();
		    connSponsor.rollback();
		    connSponsor.close();
		}
	}
	@Test
	public void checkDeletingSponsor() throws SQLException{
		Connection connection = eventManager.getConnection();
		connection.setAutoCommit(false);
		Connection connSponsor = sponsorManager.getConnection();
		connSponsor.setAutoCommit(false);
		try
		{
			Sponsor sponsor = new Sponsor(NAME_SPONSOR_1,SPONSOR_ABOUT_1);
			sponsorManager.addSponsor(sponsor);
			sponsorManager.addSponsor(sponsor);
			List<Sponsor> sponsors = sponsorManager.getAllSponsors();
			sponsor = sponsors.get(sponsors.size()-1);
			
			Event event = new Event(NAME_1,ABOUT_1,sponsor.getId());
			
			assertEquals(1,eventManager.addEvent(event));
			
			List<Event> events = eventManager.getAllEvents();
			
			int size = events.size();
			Event eventRetrieved = events.get(size -1);
			
			assertEquals(1,eventManager.DeleteSponsorFromEvent(eventRetrieved));
			
			List<Event> events2 = eventManager.getAllEvents();
			Event eventRetrieved2  = events2.get(events2.size() - 1);
			
			assertEquals(0,eventRetrieved2.getMainSponsor());
			
			
			
		} finally {
		    connection.rollback();
		    connection.close();
		    connSponsor.rollback();
		    connSponsor.close();
		}
	}
	
	@Test
	public void AddSponsor() throws SQLException{
		Connection connection = eventManager.getConnection();
		connection.setAutoCommit(false);
		Connection connSponsor = sponsorManager.getConnection();
		connSponsor.setAutoCommit(false);
		try
		{
			Sponsor sponsor = new Sponsor(NAME_SPONSOR_1,SPONSOR_ABOUT_1);
			sponsorManager.addSponsor(sponsor);
			List<Sponsor> sponsors = sponsorManager.getAllSponsors();
			sponsor = sponsors.get(sponsors.size()-1);
			
			Event event = new Event(NAME_1,ABOUT_1);
			
			assertEquals(1,eventManager.addEvent(event));
			
			List<Event> events = eventManager.getAllEvents();
			
			int size = events.size();
			Event eventRetrieved = events.get(size -1);
			
			assertEquals(1,eventManager.AddSponsor(eventRetrieved, sponsor));
			
			List<Event> events2 = eventManager.getAllEvents();
			int size2 = events2.size();
			Event eventRetrieved2 = events2.get(size2-1);
			
			assertEquals(sponsor.getId(),eventRetrieved2.getMainSponsor());
			
			
		} finally {
		    connection.rollback();
		    connection.close();
		    connSponsor.rollback();
		    connSponsor.close();
		}
	}
	@Test
	public void checkGettingEventsWithSponsor() throws SQLException
	{
		Connection connection = eventManager.getConnection();
		connection.setAutoCommit(false);
		Connection connSponsor = sponsorManager.getConnection();
		connSponsor.setAutoCommit(false);
		try
		{
			Sponsor sponsor = new Sponsor(NAME_SPONSOR_1,SPONSOR_ABOUT_1);
			sponsorManager.addSponsor(sponsor);
			List<Sponsor> sponsors = sponsorManager.getAllSponsors();
			sponsor = sponsors.get(sponsors.size()-1);
			
			Event event = new Event(NAME_1,ABOUT_1,sponsor.getId());
			assertEquals(1,eventManager.addEvent(event));
			
			sponsor = new Sponsor(NAME_SPONSOR_1,SPONSOR_ABOUT_1);
			sponsorManager.addSponsor(sponsor);
			sponsors = sponsorManager.getAllSponsors();
			sponsor = sponsors.get(sponsors.size()-1);
			
			event = new Event(NAME_2,ABOUT_1,sponsor.getId());
			assertEquals(1,eventManager.addEvent(event));
			
			List<Event> events = eventManager.getAllEvents();
			for (Event ev : events)
			{
				if(ev.getMainSponsor() != sponsor.getId())
					events.remove(ev);
			}
			
			Assert.assertEquals(events.size(),eventManager.getAllEventsWithSelectedSponsor(sponsor).size());			
			
			
		} finally {
		    connection.rollback();
		    connection.close();
		    connSponsor.rollback();
		    connSponsor.close();
		}
	}
}

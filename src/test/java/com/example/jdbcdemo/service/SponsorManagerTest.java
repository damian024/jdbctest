package com.example.jdbcdemo.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.junit.Test;

import com.example.jdbcdemo.domain.Sponsor;

public class SponsorManagerTest {
	
	SponsorManager sponsorManager = new SponsorManager();
	
	private final static String NAME_1 = "Oknex";
	private final static String NAME_2 = "Oknomex";
	private final static String ABOUT_1 = "Oknext to najwiekszy producent okien na pomorzu";
	
	@Test
	public void checkConnection(){
		assertNotNull(sponsorManager.getConnection());
	}
	
	@Test
	public void checkAdding() throws SQLException{
		Connection connection = sponsorManager.getConnection();
		connection.setAutoCommit(false);
		try
		{
			Sponsor sponsor = new Sponsor(NAME_1,ABOUT_1);
			
			assertEquals(1,sponsorManager.addSponsor(sponsor));
			
			List<Sponsor> sponsors = sponsorManager.getAllSponsors();
			
			int size = sponsors.size();
			Sponsor sponsorRetrieved = sponsors.get(size -1);
			
			assertEquals(NAME_1,sponsorRetrieved.getName());
			assertEquals(ABOUT_1,sponsorRetrieved.getAbout());
		
		  } finally {
			    connection.rollback();
			    connection.close();
		  }
	}
	@Test
	public void checkEditing() throws SQLException{
		Connection connection = sponsorManager.getConnection();
		connection.setAutoCommit(false);
		try
		{
			Sponsor sponsor = new Sponsor(NAME_1,ABOUT_1);
			assertEquals(1,sponsorManager.addSponsor(sponsor));
			
			List<Sponsor> sponsors = sponsorManager.getAllSponsors();
			
			int size = sponsors.size();
			Sponsor sponsorRetrieved = sponsors.get(size -1);
			sponsors.remove(size -1);
			
			sponsorRetrieved.setName(NAME_2);
			assertEquals(1,sponsorManager.EditSponsor(sponsorRetrieved));
			
			List<Sponsor> sponsors1 = sponsorManager.getAllSponsors();
			size = sponsors1.size();
			Sponsor sponsorRetrieved1 = sponsors1.get(size -1);
			
			sponsors1.remove(size- 1);
			assertEquals(sponsors, sponsors1);
			assertEquals(NAME_2,sponsorRetrieved1.getName());
			assertEquals(ABOUT_1,sponsorRetrieved1.getAbout());
		  } finally {
			    connection.rollback();
			    connection.close();
		  }
	}
	@Test
	public void checkDeleting() throws SQLException{
		Connection connection = sponsorManager.getConnection();
		connection.setAutoCommit(false);
		try
		{
		
		Sponsor sponsor = new Sponsor(NAME_1,ABOUT_1);
		assertEquals(1,sponsorManager.addSponsor(sponsor));
		
		List<Sponsor> sponsors = sponsorManager.getAllSponsors();
		
		int size = sponsors.size();
		Sponsor sponsorRetrieved = sponsors.get(size -1);
		
		assertEquals(1,sponsorManager.DeleteSponsor(sponsorRetrieved));
		sponsors.remove(size -1);
		List<Sponsor> sponsors1 = sponsorManager.getAllSponsors();
		assertEquals(sponsors, sponsors1);
		} finally {
		    connection.rollback();
		    connection.close();
		}
	}
		
}

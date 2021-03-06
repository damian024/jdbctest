package com.example.jdbcdemo.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.example.jdbcdemo.domain.Event;
import com.example.jdbcdemo.domain.Sponsor;

public class EventManager {
	
		private Connection connection;

		private String url = "jdbc:hsqldb:hsql://localhost/workdb";

		private String createTableEvent = "CREATE TABLE Event(id INTEGER IDENTITY PRIMARY KEY, name varchar(20), mainSponsor BIGINT, about varchar(100))";

		private PreparedStatement addEventStmt;
		private PreparedStatement editEventStmt;
		private PreparedStatement deleteEventStmt;
		private PreparedStatement selectEventByIdStmt;
		private PreparedStatement getEventsWithSponsorStmt;
		private PreparedStatement deleteSponsorStmt;
		private PreparedStatement addSponsorStmt; 
		private PreparedStatement getAllEventsStmt;

		private Statement statement;

		public EventManager() {
			try {
				connection = DriverManager.getConnection(
				          "jdbc:hsqldb:file:/tmp/testdb;ifexists=false;shutdown=true;readonly=true", "SA", "");
						//DriverManager.getConnection(url);
				statement = connection.createStatement();

				ResultSet rs = connection.getMetaData().getTables(null, null, null,
						null);
				boolean tableExists = false;
				while (rs.next()) {
					if ("Event".equalsIgnoreCase(rs.getString("TABLE_NAME"))) {
						tableExists = true;
						break;
					}
				}

				if (!tableExists)
					statement.executeUpdate(createTableEvent);

				getAllEventsStmt = connection
						.prepareStatement("SELECT * FROM Event");
				deleteEventStmt = connection
						.prepareStatement("DELETE FROM Event Where id = ?");
				editEventStmt = connection
						.prepareStatement("UPDATE event SET name = ?, about = ?,  mainsponsor = ? WHERE id = ?");
				deleteSponsorStmt = connection
						.prepareStatement("UPDATE Event Set mainSponsor= NULL WHERE id= ?");
				addEventStmt = connection
						.prepareStatement("INSERT INTO Event (name, about, mainsponsor ) VALUES (?, ?, ?)");
				addSponsorStmt = connection
						.prepareStatement("UPDATE Event SET mainsponsor= ?  WHERE id = ?");
				getEventsWithSponsorStmt = connection
						.prepareStatement("SELECT * FROM Event Where mainsponsor= ?");
				selectEventByIdStmt = connection
						.prepareStatement("SELECT * FROM Event WHERE id = ?");

			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		Connection getConnection() {
			return connection;
		}

		public int DeleteEvent(Event event) {
			int count = 0;
			try {
				deleteEventStmt.setInt(1, event.getId());
				count = deleteEventStmt.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return count;
		}
		
		int EditEvent(Event event){
			int count = 0;
			try {
				editEventStmt.setInt(4, event.getId());
				editEventStmt.setString(1, event.getName());
				editEventStmt.setString(2, event.getAbout());
				editEventStmt.setInt(3, event.getMainSponsor());
				count  = editEventStmt.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return count;
		}
		
	     int DeleteSponsorFromEvent(Event event) {
			int count = 0;
			try {
				deleteSponsorStmt.setInt(1, event.getId());
				count  = deleteSponsorStmt.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return count;
		} 
	    public int AddSponsor(Event event, Sponsor sponsor)
	    {
	    	int count = 0;
			try {
				addSponsorStmt.setInt(1, sponsor.getId());
				addSponsorStmt.setInt(2,event.getId());

				count = addSponsorStmt.executeUpdate();

			} catch (SQLException e) {
				e.printStackTrace();
			}
			return count;
	    }

		public int addEvent(Event event) {
			int count = 0;
			try {
				addEventStmt.setString(1, event.getName());
				addEventStmt.setString(2,event.getAbout());
				addEventStmt.setInt(3, event.getMainSponsor());

				count = addEventStmt.executeUpdate();

			} catch (SQLException e) {
				e.printStackTrace();
			}
			return count;
		}
		public List<Event> selectEventById(int id)
		{
			List<Event> events = new ArrayList<Event>();

			try {
				selectEventByIdStmt.setInt(1,id);
				ResultSet rs = selectEventByIdStmt.executeQuery();

				while (rs.next()) {
					Event ev = new Event();
					ev.setId(rs.getInt("id"));
					ev.setName(rs.getString("name"));
					ev.setAbout(rs.getString("about"));
					ev.setMainSponsor(rs.getInt("mainsponsor"));
					events.add(ev);
				}

			} catch (SQLException e) {
				e.printStackTrace();
			}
			return events;
		}
		
		public List<Event> getAllEventsWithSelectedSponsor(Sponsor sponsor) {
			List<Event> events = new ArrayList<Event>();

			try {
				getEventsWithSponsorStmt.setInt(1,sponsor.getId());
				ResultSet rs = getEventsWithSponsorStmt.executeQuery();

				while (rs.next()) {
					Event ev = new Event();
					ev.setId(rs.getInt("id"));
					ev.setName(rs.getString("name"));
					ev.setAbout(rs.getString("about"));
					ev.setMainSponsor(rs.getInt("mainsponsor"));
					events.add(ev);
				}

			} catch (SQLException e) {
				e.printStackTrace();
			}
			return events;
		}

		public List<Event> getAllEvents() {
			List<Event> events = new ArrayList<Event>();

			try {
				ResultSet rs = getAllEventsStmt.executeQuery();

				while (rs.next()) {
					Event ev = new Event();
					ev.setId(rs.getInt("id"));
					ev.setName(rs.getString("name"));
					ev.setAbout(rs.getString("about"));
					ev.setMainSponsor(rs.getInt("mainsponsor"));
					events.add(ev);
				}

			} catch (SQLException e) {
				e.printStackTrace();
			}
			return events;
		} 
}

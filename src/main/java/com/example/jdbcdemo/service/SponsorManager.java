package com.example.jdbcdemo.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.example.jdbcdemo.domain.Sponsor;

public class SponsorManager {

	private Connection connection;

	private String url = "jdbc:hsqldb:hsql://localhost/workdb";

	private String createTableSponsor = "CREATE TABLE Sponsor(id INTEGER IDENTITY PRIMARY KEY, name varchar(20), about varchar(100))";

	private PreparedStatement addSponsorStmt;
	private PreparedStatement editSponsorStmt;
	private PreparedStatement deleteSponsorStmt;
	private PreparedStatement getAllSponsorsStmt;

	private Statement statement;

	public SponsorManager() {
		try {
			connection = DriverManager.getConnection(
			          "jdbc:hsqldb:file:/tmp/testdb;ifexists=false", "SA", "");
					//DriverManager.getConnection(url);
			statement = connection.createStatement();

			ResultSet rs = connection.getMetaData().getTables(null, null, null,
					null);
			boolean tableExists = false;
			while (rs.next()) {
				if ("Sponsor".equalsIgnoreCase(rs.getString("TABLE_NAME"))) {
					tableExists = true;
					break;
				}
			}

			if (!tableExists)
				statement.executeUpdate(createTableSponsor);

			addSponsorStmt = connection
					.prepareStatement("INSERT INTO Sponsor (name, about) VALUES (?, ?)");
			editSponsorStmt = connection
					.prepareStatement("UPDATE Sponsor SET name= ? ,about= ? WHERE id = ?;");
			deleteSponsorStmt = connection
					.prepareStatement("DELETE FROM Sponsor WHERE id=?");
			getAllSponsorsStmt = connection
					.prepareStatement("SELECT * FROM Sponsor");

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	Connection getConnection() {
		return connection;
	}

	int DeleteSponsor(Sponsor sponsor) {
		int count = 0;
		try {
			deleteSponsorStmt.setInt(1, sponsor.getId());
			count  =  deleteSponsorStmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}
	
	int EditSponsor(Sponsor sponsor){
		int count = 0;
		try {
			editSponsorStmt.setInt(3, sponsor.getId());
			editSponsorStmt.setString(1, sponsor.getName());
			editSponsorStmt.setString(2, sponsor.getAbout());
			count  = editSponsorStmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}

	public int addSponsor(Sponsor sponsor) {
		int count = 0;
		try {
			addSponsorStmt.setString(1, sponsor.getName());
			addSponsorStmt.setString(2, sponsor.getAbout());

			count = addSponsorStmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}

	public List<Sponsor> getAllSponsors() {
		List<Sponsor> sponsors = new ArrayList<Sponsor>();

		try {
			ResultSet rs = getAllSponsorsStmt.executeQuery();

			while (rs.next()) {
				Sponsor p = new Sponsor();
				p.setId(rs.getInt("id"));
				p.setName(rs.getString("name"));
				p.setAbout(rs.getString("about"));
				sponsors.add(p);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return sponsors;
	}

}

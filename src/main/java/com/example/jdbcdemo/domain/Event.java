package com.example.jdbcdemo.domain;

import java.sql.Date;

public class Event {
	private int id;
	private String name;
	private Date date;
	private int mainSponsor;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public int getMainSponsor() {
		return mainSponsor;
	}
	public void setMainSponsor(int mainSponsor) {
		this.mainSponsor = mainSponsor;
	}
	


}

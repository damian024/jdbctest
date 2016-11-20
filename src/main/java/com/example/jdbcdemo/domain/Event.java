package com.example.jdbcdemo.domain;

public class Event {
	private int id;
	private String name;
	private String about;
	private int mainSponsor;
	
	public Event(String name, String about, int mainSponsor)
	{
		this.name = name;
		this.about = about;
		this.mainSponsor = mainSponsor;
	}
	public Event(String name, String about)
	{
		this.name = name;
		this.about = about;
	}
	public Event(){
		
	}
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
	public int getMainSponsor() {
		return mainSponsor;
	}
	public void setMainSponsor(int sponsor) {
		this.mainSponsor = sponsor;
	}
	public String getAbout() {
		return about;
	}
	public void setAbout(String about) {
		this.about = about;
	}
	


}

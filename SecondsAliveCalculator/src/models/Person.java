package models;

import java.util.Date;

public class Person {
	
	private Date dob;
	private String name;
	private static Person instance;
	
	private Person() {
	}
	
	public void setDob(Date dob) {
		this.dob=dob;
	}
	
	public void setName(Date dob) {
		this.name=name;
	}
	
	public String getName() {
		return name;
	}
	
	
	public Date getDob() {
		return dob;
	}
	
	
	
	public static Person getInstance() {
		if(instance==null) {
			instance=new Person();
		}
		return instance;
	}

}

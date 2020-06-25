package com.bdb.data;

import org.json.JSONException;
import org.json.JSONObject;

import com.sleepycat.persist.model.Entity;
import com.sleepycat.persist.model.PrimaryKey;

@Entity
public class Employee {
	private String FirstName;
	private String LastName;
	private String Team;
	
	
	@PrimaryKey
	private int employeeID;
	public Employee() {
	}

	public Employee (int id,String firstname,String lastname,String team ) {
		this.employeeID = id;
	    this.FirstName = firstname;
	    this.LastName = lastname;
	    this.Team = team;
    } 
	 
	public int getUniqueID() {
	    return this.employeeID;
	}
	  
	public String gettName() {
	    return this.FirstName + " "+this.LastName;
	}
	
	public String getTeam() {
	    return this.Team;
	}
	
	@Override
	public String toString() {
		JSONObject obj = new JSONObject();

	      try {
			obj.put("First_Name", this.FirstName);
			obj.put("Last_Name", this.LastName);
			obj.put("ID ", this.employeeID);
		    obj.put("Team",this.Team);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	      

	    return obj.toString();
	}

		  

}

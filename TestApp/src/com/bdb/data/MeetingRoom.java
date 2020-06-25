package com.bdb.data;

import org.json.JSONException;
import org.json.JSONObject;

import com.sleepycat.persist.model.Entity;
import com.sleepycat.persist.model.*;
import com.sleepycat.persist.model.PrimaryKey;
import com.sleepycat.persist.model.SecondaryKey;

@Entity
public class MeetingRoom {
	@SecondaryKey(relate=Relationship.MANY_TO_ONE)
	private String name;
	@SecondaryKey(relate=Relationship.MANY_TO_ONE)
	private int type;
	@SecondaryKey(relate=Relationship.MANY_TO_ONE)
	private String building;
	@SecondaryKey(relate=Relationship.MANY_TO_ONE)
	private int floor;
//	@SecondaryKey(relate=Relationship.MANY_TO_ONE)
//	private int status;
	
	
	@PrimaryKey
	private long roomID;
	public MeetingRoom() {
	}

	public MeetingRoom ( long id,String name,int type,String building,int floor ) {
		this.roomID = id;
	    this.name = name;
	    this.type = type;
	    this.building = building;
	    this.floor = floor;
//	    this.status = status;
    } 
	 
	public long getUniqueID() {
	    return this.roomID;
	}
	  
	public int getType() { 
	    return this.type;
	}
	
	public String getBuilding() {
	    return this.building;
	}
	
	public int getFloor() {
	    return this.floor;
	}
	public String getName() {
		return this.name;
	}
	
//	public int getStatus() {
//	    return this.status;
//	}
		  
	 
	@Override
	public String toString() {
		JSONObject obj = new JSONObject();

	      try {
	    	  obj.put("room Id", this.roomID);
			obj.put("name", this.name);
			obj.put("type", this.type);
		    obj.put("building",this.building);
		    obj.put("floor", this.floor);
//		    obj.put("status", this.status);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	      

	    return obj.toString();
	}
}

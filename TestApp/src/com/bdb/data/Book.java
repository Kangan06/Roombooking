package com.bdb.data;

import org.json.JSONException;
import org.json.JSONObject;

import com.sleepycat.persist.model.Entity;
import com.sleepycat.persist.model.PrimaryKey;
import com.sleepycat.persist.model.Relationship;
import com.sleepycat.persist.model.SecondaryKey;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


@Entity
public class Book{
	
	@SecondaryKey(relate=Relationship.MANY_TO_ONE)
	private long  roomId;
	private Date starttime;
	private Date endtime;
	private int employeeId;
	String pattern = "yyyy-MM-dd HH:mm:ss";
	
	
	@PrimaryKey
	private long bookingID;
	public Book() {
	}

	
//	DateTimeFormatter formatter = DateTimeFormat.forPattern("dd/MM/yyyy HH:mm:ss");
//	DateTime dt = formatter.parseDateTime(string);
	public Book (long id,long room_id, String starttime,String endtime,int employeeId ) {
		this.bookingID = id;
	    this.roomId = room_id;
	    Date s_time = null;
	    Date e_time = null;
		try {
			s_time = new SimpleDateFormat(pattern).parse(starttime);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			e_time = new SimpleDateFormat(pattern).parse(endtime);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    this.starttime = s_time;
	    this.endtime = e_time;
	    this.employeeId = employeeId;
    } 
	 
	public long getUniqueID() {
	    return this.bookingID;
	}
	  
	public long getRoomId() {
	    return this.roomId;
	}
	
	public Date getStartTime() {
	    return this.starttime;
	}
	
	public Date getEndTime() {
	    return this.endtime;
	}
	
	public int getEmpID() {
	    return this.employeeId;
	}
	
	
	@Override
	public String toString() {
		JSONObject obj = new JSONObject();

	      try {
			obj.put("bookingID", this.bookingID);
			obj.put("roomId", this.roomId);
		    obj.put("starttime",this.starttime);
		    obj.put("endtime",this.endtime);
		    obj.put("employeeId",this.employeeId);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	      

	    return obj.toString();
	}

}

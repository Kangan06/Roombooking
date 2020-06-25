package com.web.rest;

import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.core.MediaType;
import org.json.JSONObject;
import org.json.JSONArray;
import com.bdb.data.*;


@Path("TestApp")
public class TestApp {
	public TestApp() {
		MeetingRoomDAO.openEnv();
		EmployeeDAO.openEnv();
		BookDAO.openEnv();
	}

	
	
	
	
	@GET
	@Path("/createData")
	@Produces("text/plain")
	public String createData() {
		
		/**
		 * Create employees
		 */
		Employee employee = new Employee(1,"John","Smith","Database");
		EmployeeDAO.putEmployee(employee);
		Employee employee2 = new Employee(2,"Alex","Warner","Testing");
		EmployeeDAO.putEmployee(employee2);
		Employee employee3 = new Employee(3,"David","Warner","Databse");
		EmployeeDAO.putEmployee(employee3);
		
		
		/**
		 * Create Meeting Rooms
		 */
		MeetingRoom room1 = new MeetingRoom(1,"Room1", 4,"A",2);
		MeetingRoomDAO.putRoom(room1);
		MeetingRoom room2 = new MeetingRoom(2,"Room2", 4,"A",3);
		MeetingRoomDAO.putRoom(room2);
		MeetingRoom room3 = new MeetingRoom(3,"Room3", 8,"B",2);
		MeetingRoomDAO.putRoom(room3);
		MeetingRoom room4 = new MeetingRoom(4,"Room4", 8,"B",3);
		MeetingRoomDAO.putRoom(room4);
		
		
		/**
		 * Create bookings
		 */
		
		Book booking = new Book(1,2,"2020-06-22 15:00:00","2020-06-22 16:00:00",1);
		BookDAO.putBooking(booking);
		Book booking2 = new Book(2,3,"2020-06-23 15:00:00","2020-06-23 16:00:00",1);
		BookDAO.putBooking(booking2);
		return "Date Added Successfully";
	}
	
	
	
	@POST
	@Path("/getRoomById")
	@Consumes("application/x-www-form-urlencoded")
    @Produces(MediaType.APPLICATION_JSON)
	public void getRoomById(@Suspended final AsyncResponse asyncResponse ,@FormParam("id") long id) {
		MeetingRoom room = MeetingRoomDAO.getMeetingRoom(id);
		asyncResponse.resume(room);
		
	}
	
	@POST
	@Path("/addRoom")
	@Consumes("application/x-www-form-urlencoded")
    @Produces(MediaType.TEXT_PLAIN)
	public void addMeetingRoom(@Suspended final AsyncResponse asyncResponse ,@FormParam("id") long id
			,@FormParam("name") String name,
			@FormParam("type") int type,@FormParam("building") String building,
			@FormParam("floor") int floor) {
		System.out.println(name);
//		MeetingRoomDAO.openEnv();
		MeetingRoom room = new MeetingRoom(id,name, type,building,floor);
		MeetingRoomDAO.putRoom(room);
		System.out.println("Room Added Successfully");
		asyncResponse.resume("Room Added Successfully");
	     
	}
	

	

	@POST
	@Path("/getRoomByName")
	@Consumes("application/x-www-form-urlencoded")
    @Produces(MediaType.APPLICATION_JSON)
	public void getRoomByName(@Suspended final AsyncResponse asyncResponse,@FormParam("name") String name) {
		System.out.println(name);
		List<MeetingRoom> rooms = MeetingRoomDAO.getRoomByName(name);
		List<MeetingRoom> MeetingRooms = new ArrayList<MeetingRoom>();
		for(MeetingRoom r : rooms) {
			MeetingRooms.add(r);
		}
		
		asyncResponse.resume(MeetingRooms);
		
	}
	
	@POST
	@Path("/getRoomByType")
	@Consumes("application/x-www-form-urlencoded")
    @Produces(MediaType.APPLICATION_JSON)
	public void getRoomByType(@Suspended final AsyncResponse asyncResponse,
			@FormParam("type") int type) {
		System.out.println(type);
		List<MeetingRoom> rooms = MeetingRoomDAO.getRoomByType(type);
		List<MeetingRoom> MeetingRooms = new ArrayList<MeetingRoom>();
		for(MeetingRoom r : rooms) {
			MeetingRooms.add(r);
		}
		System.out.println(rooms);
		asyncResponse.resume(MeetingRooms);
		
	}
	
	@POST
	@Path("/getRoomByBuilding")
	@Consumes("application/x-www-form-urlencoded")
    @Produces(MediaType.APPLICATION_JSON)
	public void getRoomByBuilding(@Suspended final AsyncResponse asyncResponse,
			@FormParam("building") String building) {
;
		List<MeetingRoom> rooms = MeetingRoomDAO.getRoomByBuilding(building);
		List<MeetingRoom> MeetingRooms = new ArrayList<MeetingRoom>();
		for(MeetingRoom r : rooms) {
			MeetingRooms.add(r);
		}
		System.out.println(rooms);
		asyncResponse.resume(MeetingRooms);
		
	}
	
	@POST
	@Path("/getRoomByFloor")
	@Consumes("application/x-www-form-urlencoded")
    @Produces(MediaType.APPLICATION_JSON)
	public void getRoomByFloor(@Suspended final AsyncResponse asyncResponse,
			@FormParam("floor") int floor) {
;
		List<MeetingRoom> rooms = MeetingRoomDAO.getRoomByFloor(floor);
		List<MeetingRoom> MeetingRooms = new ArrayList<MeetingRoom>();
		for(MeetingRoom r : rooms) {
			System.out.println(r.getName());
			MeetingRooms.add(r);
		}
		
		asyncResponse.resume(MeetingRooms);
		
	}
	
	
	
	/**
	 * Bookings
	 */
	@POST
	@Path("/makeBooking")
	@Consumes("application/x-www-form-urlencoded")
    @Produces(MediaType.APPLICATION_JSON)
	public void makeBooking(@Suspended final AsyncResponse asyncResponse,
			@FormParam("id") long id,
			@FormParam("roomId") long roomId,
			@FormParam("starttime") String starttime ,@FormParam("endtime") String endtime
			,@FormParam("employeeID") int employeeId) {
		long id2 = 0;
		/**
		 * check if room exists
		 */
		
		MeetingRoom room = MeetingRoomDAO.getMeetingRoom(roomId);
		if(room == null) {
			String x = "No room Avaialable with the given id";
			asyncResponse.resume(x);
			return;
		}
		List<Book> bookings = BookDAO.getBookingByRoomId(roomId);
		if(bookings == null) {
			Book booking = new Book(id,roomId,starttime,endtime,employeeId);
			BookDAO.putBooking(booking);
			id2 = booking.getUniqueID();
			
			String y = "Booking done successfully. Reference Id is :" + Long.toString(id2);
			asyncResponse.resume(y);
		}else {
			for(Book booking :bookings) {
				Date st_time = booking.getStartTime();
				Date end_time = booking.getEndTime();
				String pattern = "yyyy-MM-dd HH:mm:ss";
//				Date e_time = null;
				Date s_time = null;
				try {
					s_time = new SimpleDateFormat(pattern).parse(starttime);
				} catch (ParseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				if((s_time.compareTo(st_time) >= 0) && (s_time.compareTo(end_time)<=0)) {
					System.out.println("Room Booked Already");
					asyncResponse.resume("Room not available for that time");
					
				}else {
					Book booking2 = new Book(id,roomId,starttime,endtime,employeeId);
					BookDAO.putBooking(booking2);
					id2 = booking2.getUniqueID();
					
					String y = "Booking done successfully. Reference Id is :" + Long.toString(id2);
					asyncResponse.resume(y);
				}

			}
		}
		/**
		 * check booking having that room
		 * if no booking make a book
		 * else check
		 */
		/**
		 * check if room available
		 */
		
	
		
	     
	}
	/**
	 * Bookings
	 */
	@POST
	@Path("/cancelBooking")
	@Consumes("application/x-www-form-urlencoded")
    @Produces(MediaType.TEXT_PLAIN)
	public void cancelBooking(@Suspended final AsyncResponse asyncResponse,@FormParam("id") long bookingID) {
		/**
		 * check if room exists
		 */
		BookDAO.deleteBooking(bookingID);		
		asyncResponse.resume("Booking cancelled Successfully");
	     
	}
	/**
	 * 
	 */
	@POST
	@Path("/getBooking")
	@Consumes("application/x-www-form-urlencoded")
	@Produces(MediaType.APPLICATION_JSON)
	public void getBooking(@Suspended final AsyncResponse asyncResponse,@FormParam("id") long bookingID) {
		Book booking = BookDAO.getBooking(bookingID);
		asyncResponse.resume(booking);
	}
	
	/**
	 * 
	 * @param asyncResponse
	 * @param id
	 * @param fn
	 * @param ln
	 * @param team
	 */
	@POST
	@Path("/addEmployee")
	@Consumes("application/x-www-form-urlencoded")
    @Produces(MediaType.TEXT_PLAIN)
	public void addEmployee(@Suspended final AsyncResponse asyncResponse,
			@FormParam("Id") int id ,@FormParam("FirstName") String fn,@FormParam("LastName") String ln,
			@FormParam("team") String team) {
		Employee employee = new Employee(id,fn,ln,team);
		EmployeeDAO.putEmployee(employee);
		int id2 = employee.getUniqueID();
		asyncResponse.resume("Employee Added Successfully. Your emp id is :" + Integer.toString(id2));
		
	}
}

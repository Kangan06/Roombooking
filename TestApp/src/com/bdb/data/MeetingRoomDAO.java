package com.bdb.data;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.bdb.data.MeetingRoom;
import com.sleepycat.je.Environment;
import com.sleepycat.je.EnvironmentConfig;
import com.sleepycat.persist.EntityCursor;
import com.sleepycat.persist.EntityStore;
import com.sleepycat.persist.PrimaryIndex;
import com.sleepycat.persist.SecondaryIndex;
import com.sleepycat.persist.StoreConfig;

public class MeetingRoomDAO {

	 private static EntityStore store;	  
	 private static PrimaryIndex<Long, MeetingRoom> meetingRoomIndex; 
	 private static SecondaryIndex<String, Long, MeetingRoom> roomByName;
	 private static SecondaryIndex<Integer, Long, MeetingRoom> roomByType;
	 private static SecondaryIndex<String, Long, MeetingRoom> roomByBuilding;
	 private static SecondaryIndex<Integer, Long, MeetingRoom> roomByFloor; 
//	 private static SecondaryIndex<Integer, Long, MeetingRoom> roomByStatus;
	 private static Environment env = null;
	 
	 public static void openEnv(){
		 System.out.println("opening Env");
		    // Create the directory in which this store will live.
    String currDir = System.getProperty("user.dir");
    String dataStore = currDir+File.separator+"Room";
	File dir = new File(dataStore);
	boolean success = dir.mkdirs();
	if (success) {
		    System.out.println("Created the RoomDB directory.");
	}
	EnvironmentConfig envConfig = new EnvironmentConfig();
	envConfig.setAllowCreate(true);
    StoreConfig storeConfig = new StoreConfig();
    storeConfig.setAllowCreate(true);
//    storeConfig.set
	env = new Environment(dir,  envConfig);
    MeetingRoomDAO.store = new EntityStore(env, "MeetingRoom", storeConfig);
    meetingRoomIndex = store.getPrimaryIndex(Long.class, MeetingRoom.class); 
    roomByName = store.getSecondaryIndex(meetingRoomIndex, String.class, "name");
    roomByType = store.getSecondaryIndex(meetingRoomIndex, Integer.class, "type");
    roomByBuilding = store.getSecondaryIndex(meetingRoomIndex, String.class, "building");
    roomByFloor = store.getSecondaryIndex(meetingRoomIndex, Integer.class, "floor");
//    roomByStatus = store.getSecondaryIndex(meetingRoomIndex, Integer.class, "status"); 
    
    System.out.println("Environment Opened");
	}
	 
	 public static void closeEnv() {
		    // Guarantee that the environment is closed upon system exit.
		DbShutdownHook shutdownHook = new DbShutdownHook(env, store);
		Runtime.getRuntime().addShutdownHook(shutdownHook);
	 }
	 public static void putRoom(MeetingRoom meetingRoom) {
		 meetingRoomIndex.put(meetingRoom);
	 }
		  

	 public static MeetingRoom getMeetingRoom(long roomID) {
		 System.out.println("getting room for id :"+ Long.toString(roomID));
//		 meetingRoomIndex.
		    return meetingRoomIndex.get(roomID);
	 }
		  
	 public static List<MeetingRoom> getRoomByName(String name) {
		 System.out.println("Getting Meeting Room  :"+name);
		 char[] ca = name.toCharArray();
		 final int lastCharIndex = ca.length - 1;
		 ca[lastCharIndex]++;
		 List<MeetingRoom> Mrooms = new ArrayList<MeetingRoom>();
		 EntityCursor<MeetingRoom> rooms = roomByName.entities(name,true,String.valueOf(ca),false);
		 try {
			 for(MeetingRoom r: rooms) {
				 Mrooms.add(r);
			 }
		 }finally {
			 rooms.close();
		 }
		 return Mrooms;
	 }
	 /**
	  * 
	  * @param type
	  * @return
	  */
	 public static List<MeetingRoom> getRoomByType(int type) {
		 List<MeetingRoom> Mrooms = new ArrayList<MeetingRoom>();
		 EntityCursor<MeetingRoom> rooms = roomByType.subIndex(type).entities();
		 try {
			 for(MeetingRoom r: rooms) {
				 Mrooms.add(r);
			 }
		 }finally {
			 rooms.close();
		 }
		 return Mrooms;
	 }
	 /**
	  * 
	  * @param building
	  * @return all rooms 
	  * of a building
	  */
	 public static List<MeetingRoom> getRoomByBuilding(String building){
		 System.out.println("Getting Meeting Room for building :"+building);
		 char[] ca = building.toCharArray();
		 final int lastCharIndex = ca.length - 1;
		 ca[lastCharIndex]++;
		 List<MeetingRoom> Mrooms = new ArrayList<MeetingRoom>();
		 EntityCursor<MeetingRoom> rooms = roomByBuilding.entities(building,true,String.valueOf(ca),false);
		 try {
			 for(MeetingRoom r: rooms) {
				 Mrooms.add(r);
			 }
		 }finally {
			 rooms.close();
		 }
		 return Mrooms;
	 }
	 
	 public static List<MeetingRoom> getRoomByFloor(int floor){
		 List<MeetingRoom> Mrooms = new ArrayList<MeetingRoom>();
		 EntityCursor<MeetingRoom> rooms = roomByFloor.subIndex(floor).entities();
		 try {
			 for(MeetingRoom r: rooms) {
				 Mrooms.add(r);
			 }
		 }finally {
			 rooms.close();
		 }
		 return Mrooms;
	 }
	 
//	 public static List<MeetingRoom> getRoomByStatus(int status){
//		 List<MeetingRoom> Mrooms = new ArrayList<MeetingRoom>();
//		 EntityCursor<MeetingRoom> rooms = roomByStatus.entities();
//		 try {
//			 for(MeetingRoom r: rooms) {
//				 Mrooms.add(r);
//			 }
//		 }finally {
//			 rooms.close();
//		 }
//		 return Mrooms;
//	 }
	 
	 /**
	  * 
	  * @param roomID
	  */
		 
	 public static void deleteMeetingRoom(long roomID) {
		 meetingRoomIndex.delete(roomID);
	 }
}

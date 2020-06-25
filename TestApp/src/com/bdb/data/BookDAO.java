package com.bdb.data;


import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.sleepycat.je.Environment;
import com.sleepycat.je.EnvironmentConfig;
import com.sleepycat.persist.EntityCursor;
import com.sleepycat.persist.EntityStore;
import com.sleepycat.persist.PrimaryIndex;
import com.sleepycat.persist.SecondaryIndex;
import com.sleepycat.persist.StoreConfig;


public class BookDAO {
	
	private static EntityStore bookingStore;	  
	private static PrimaryIndex<Long, Book> bookingIndex; 
	private static SecondaryIndex<Long, Long, Book> bookingByRoom;
	private static Environment env = null;
	 
	public static void openEnv(){
		System.out.println("opening Env");
	    // Create the directory in which this store will live.
	   String currDir = System.getProperty("user.dir");
	   String dataStore = currDir+File.separator+"Book";
	   File dir = new File(dataStore);
	   boolean success = dir.mkdirs();
	   if (success) {
		   System.out.println("Created the BookingDB directory.");
	   }
	   EnvironmentConfig envConfig = new EnvironmentConfig();
	   envConfig.setAllowCreate(true);
	   StoreConfig storeConfig = new StoreConfig();
	   storeConfig.setAllowCreate(true);
	   env = new Environment(dir,  envConfig);
	   BookDAO.bookingStore = new EntityStore(env, "Book", storeConfig);
	   bookingIndex = bookingStore.getPrimaryIndex(Long.class, Book.class); 
	   bookingByRoom = bookingStore.getSecondaryIndex(bookingIndex, Long.class, "roomId");
	   System.out.println("environment setup for Booking");
		
	}
	 
	 public static void closeEnv() {
		    // Guarantee that the environment is closed upon system exit.
		DbShutdownHook shutdownHook = new DbShutdownHook(env, bookingStore);
		Runtime.getRuntime().addShutdownHook(shutdownHook);
	 }
	 public static void putBooking(Book booking) {
		 System.out.println(booking);

		 bookingIndex.put(booking);
	 }
		  
	 
	 public static List<Book> getBookingByRoomId(Long roomId){
		 List<Book> Bookings = new ArrayList<Book>();
		 EntityCursor<Book> books = bookingByRoom.subIndex(roomId).entities();
		 try {
			 for(Book r: books) {
				 Bookings.add(r);
			 }
		 }finally {
			 books.close();
		 }
		 return Bookings;
	 }

	 public static Book getBooking(long bookID) {
		    return bookingIndex.get(bookID);
	 }
		  
		 
	 public static void deleteBooking(long bookId) {
		 bookingIndex.delete(bookId);
	 }

}



















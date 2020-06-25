package com.bdb.data;


import java.io.File;

import com.sleepycat.je.Environment;
import com.sleepycat.je.EnvironmentConfig;
import com.sleepycat.persist.EntityStore;
import com.sleepycat.persist.PrimaryIndex;
import com.sleepycat.persist.StoreConfig;


public class EmployeeDAO {
	
	private static EntityStore employeeStore;	  
	private static PrimaryIndex<Integer, Employee> employeeIndex; 
	private static Environment env = null;
	 
	public static void openEnv(){
		System.out.println("opening Env");
	    // Create the directory in which this store will live.
	   String currDir = System.getProperty("user.dir");
	   String dataStore = currDir+File.separator+"Employee";
	   File dir = new File(dataStore);
	   boolean success = dir.mkdirs();
	   if (success) {
		   System.out.println("Created the EmployeeDB directory.");
	   }
	   EnvironmentConfig envConfig = new EnvironmentConfig();
	   envConfig.setAllowCreate(true);
	   StoreConfig storeConfig = new StoreConfig();
	   storeConfig.setAllowCreate(true);
	   env = new Environment(dir,  envConfig);
	   EmployeeDAO.employeeStore = new EntityStore(env, "Employee", storeConfig);
	   employeeIndex = employeeStore.getPrimaryIndex(Integer.class, Employee.class); 
		
	}
	 
	 public static void closeEnv() {
		    // Guarantee that the environment is closed upon system exit.
		DbShutdownHook shutdownHook = new DbShutdownHook(env, employeeStore);
		Runtime.getRuntime().addShutdownHook(shutdownHook);
	 }
	 public static void putEmployee(Employee employee) {
		 employeeIndex.put(employee);
	 }
		  

	 public static Employee getEmployee(int empID) {
		    return employeeIndex.get(empID);
	 }
		  
		 
	 public static void deleteEmployee(int empID) {
		 employeeIndex.delete(empID);
	 }

}

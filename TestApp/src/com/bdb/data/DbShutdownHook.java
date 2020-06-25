/* Copyright (c) 1990, 2007, Oracle. All rights reserved. */
/**
 * 
 * NAME : 
 * 		DbShutdownHook
 * FUNCTION :
 * 		Berkeley DB environment shutdown hook.
 *  NOTE : 
 *  CREATED :
 * 		kangverm	04/05/2019	creation
 * 
 */
package com.bdb.data;

import com.sleepycat.je.DatabaseException;
import com.sleepycat.je.Environment;
import com.sleepycat.persist.EntityStore;

/**
 * 
 * @author kangverm
 *
 */
public class DbShutdownHook extends Thread {
  /** 
   * The environment instance to be closed upon shutdown. 
   */
  private Environment env;
  /** 
   * The entity store to be closed upon shutdown. 
   */
  private EntityStore store;

  public DbShutdownHook(Environment env, EntityStore store) {
    this.env = env;
    this.store = store;
  }

 
  public void run() {
    try {
      if (env != null) {
    	String name = store.getStoreName();
        store.close();
        env.close();
        System.out.println("Database " + name + " closed successfully.");
      } 
    } 
    catch (DatabaseException dbe) {
      System.out.println("Database not closed successfully.");
    } 
  }
}

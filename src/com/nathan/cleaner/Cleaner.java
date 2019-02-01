package com.nathan.cleaner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class Cleaner
{
	private List<String> paths;	
	private Date date;
	public Cleaner() 
	{		
		paths = new ArrayList<String>();
		date = null;
	}
	
	/**
	 * Adds a path to list of paths.
	 * @param path String obj that represents a path on the hard disk.
	 * @return Boolean on whether it was able to add to list or not.
	 */
	public boolean add(String path)
	{
		if(!paths.contains(path))
		{
			paths.add(path);
			return true;
		}
		System.err.println("Duplicate path '"+ path + "'!");
		return false;
	}
	
	/**
	 * Removes a specific path.
	 * @param path Path to be removed from list of paths.
	 */
	public void remove(String path)
	{
		paths.remove(path);
	}
	
	/**
	 * Returns array of paths to clean.
	 * @return Array of paths
	 */
	public String[] getPaths()
	{
		return paths.toArray(new String[paths.size()]);
	}
	
	/**
	 * Creates threads to clean each specified path.
	 */
	public void clean(int daysOld)
	{
		date = new Date(System.currentTimeMillis());
		List<Thread> threads = new ArrayList<Thread>();
		
		// Create threads for each Cleaning Directory.
		paths.stream().forEach( path -> threads.add(new Thread(new CleanerT(path, date, daysOld))) );
		
		// Start each thread
		threads.stream().forEach( thread -> thread.start() );
	}	
}

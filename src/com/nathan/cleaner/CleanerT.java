package com.nathan.cleaner;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class CleanerT implements Runnable 
{
	private String path;
	private Date date;
	private int daysOld;
	public CleanerT(String path, Date date, int daysOld)
	{		
		this.path = path;
		this.date = date;
		this.daysOld = daysOld;
	}
	
	@Override
	public void run()
	{	
		System.out.println("Thread is running in: " + path);
		// Get list of all files in path.
		File[] files = new File(path).listFiles();
		
		List<String> oldFiles = new ArrayList<String>();
		
		Arrays.stream(files).forEach( file -> {
			SimpleDateFormat format = new SimpleDateFormat("D");
			int fileLastModified = Integer.parseInt(format.format(new Date(file.lastModified()))),
					currentDate = Integer.parseInt(format.format(date));
			if(currentDate < 31)
			{
				if(Math.abs(fileLastModified + 365) - (365 + currentDate) >= daysOld)
				{
					oldFiles.add(path + "\\" + file.getName());
				}
			}
			else if(Math.abs(fileLastModified - currentDate) >= daysOld)
			{
				oldFiles.add(path + "\\" + file.getName());				
			}
		});
		
		oldFiles.stream().forEach(file -> System.out.println(file));
		// Search for files that are 30 days old
		
		/*File downloadFile = new File(path);
		
		Arrays.stream(downloadFile.listFiles()).forEach(file -> {
			DateFormat format = new SimpleDateFormat("D");			
			System.out.println(file.getName() + "\t" + format.format(new Date(file.lastModified())));		
		});*/
	}
}

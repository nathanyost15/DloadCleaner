package com.nathan.cleaner;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Paths;
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
		List<File> files = new ArrayList<File>(Arrays.asList((new File(path)).listFiles()));
		
		
		// Check if OLD file is found		
		boolean oldFileFound = files.contains(new File("OLD"));
		for(File f : files)
		{
			if(f.getName().equalsIgnoreCase("OLD"))
			{
				oldFileFound = true;
				files.remove(f);
				return;
			}
		}
		
		// Search for files that are 30 days old
		
		List<String> oldFiles = new ArrayList<String>();		
		files.stream().forEach( file -> {			
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
		
		if(!oldFileFound)
		{			
			try
			{
				Files.createDirectory(Paths.get(path + "\\OLD"));
			} catch(IOException e)
			{
				System.err.println(e.getMessage());
				System.exit(-1);
			}
		}		
		/*
		System.out.println("line 80");
		// Move all oldFiles to OLD folder
		oldFiles.stream().forEach(file -> {
			try 
			{
				Path p = Files.move(Paths.get(file), Paths.get(path + "\\OLD"), StandardCopyOption.REPLACE_EXISTING);
				if(p == null)
				{
					System.err.println("Problems with moving: " + file);
				}
				
			} catch (IOException e) 
			{
				e.printStackTrace();
			}
		});	
		System.out.println("line 96");*/
	}
}

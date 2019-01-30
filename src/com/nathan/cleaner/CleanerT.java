package com.nathan.cleaner;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
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
				break;
			}			
		}
		
		// Search for files that are 30 days old
		List<OldFile> oldFiles = new ArrayList<OldFile>();			
		files.stream().forEach( file -> {
			SimpleDateFormat format = new SimpleDateFormat("D");			
			int fileLastAccessed = Integer.parseInt(format.format(new Date(this.getLastAccessTime(path + File.separator + file.getName())))),
					currentDate = Integer.parseInt(format.format(date));
			if(currentDate < 31)
			{
				if(Math.abs(fileLastAccessed + (fileLastAccessed > currentDate ? 0 : 365) - (365 + currentDate)) >= daysOld)
				{
					oldFiles.add(new OldFile(path, file.getName()));
				}
			}
			else if(Math.abs(fileLastAccessed - currentDate) >= daysOld)
			{
				oldFiles.add(new OldFile(path, file.getName()));	
			}
		});			
		if(oldFiles.size() != 0)
		{
			if(!oldFileFound)
			{			
				try
				{
					Files.createDirectory(Paths.get(path + File.separator + "OLD"));
				} catch(IOException e)
				{
					System.err.println(e.getMessage());
					System.exit(-1);
				}
			}		
					
			// Move all oldFiles to OLD folder
			oldFiles.stream().forEach(file -> {
				try 
				{
					Path p = Files.move(Paths.get(file.getFullPath()), Paths.get(path + File.separator + "OLD" + File.separator + file.getName()), StandardCopyOption.REPLACE_EXISTING);
					if(p == null)
					{
						System.err.println("Problems with moving: " + file);
					}
					
				} catch (IOException e) 
				{
					e.printStackTrace();
				}
			});
		}
		System.out.println("Thread finished in: " + path);
	}
	
	private long getLastAccessTime(String path)
	{
		Path file = Paths.get(path);
		BasicFileAttributes attrs = null;
		try 
		{
			attrs = Files.readAttributes(file, BasicFileAttributes.class);
		} catch (IOException e) 
		{
			e.printStackTrace();
			System.exit(-1);
		}
		FileTime time = attrs.lastAccessTime();
		return time.toMillis();
	}
}

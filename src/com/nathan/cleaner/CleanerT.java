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
	
	/**
	 * Constructs a new CleanerT obj. 
	 * @param path Path that is used to execute cleanup.
	 * @param date Date obj that represents today's date.
	 * @param daysOld How many days old can the file be before its selected as an oldFile.
	 */
	public CleanerT(String path, Date date, int daysOld)
	{		
		this.path = path;
		this.date = date;
		this.daysOld = daysOld;
	}
	
	/**
	 * Runs thread of execution.
	 */
	@Override
	public void run()
	{	
		System.out.println("Thread is running in: " + path);
		
		// Get list of all files in path.
		List<File> files = getFiles();	
		File f = find(files, "OLD");
		if(f != null)
			files.remove(f);
		
		// Search for files that are 30 days old.
		List<File> oldFiles = getOldFiles(files, daysOld);
		
		// Create OLD folder.		
		if(oldFiles.size() != 0 && f == null)
		{
			createFolder(path + File.separator + "OLD");
		}		
		
		// Move old files to OLD folder.
		moveFiles(oldFiles);
		
		System.out.println("Thread finished in: " + path);
	}
	
	private void createFolder(String string) 
	{
		try
		{
			Files.createDirectory(Paths.get(string));
		} catch(IOException e)
		{
			System.err.println(e.getMessage());
			System.exit(-1);
		}
	}

	private void moveFiles(List<File> oldFiles) 
	{
		// Check if there are any oldFiles before continuing.
		if(oldFiles.size() == 0)
			return;
				
		// Move all oldFiles to OLD folder
		oldFiles.stream().forEach(file -> {
			try 
			{
				Path p = Files.move(Paths.get(file.getAbsolutePath()), Paths.get(path + File.separator + "OLD" + File.separator + file.getName()), StandardCopyOption.REPLACE_EXISTING);
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

	private List<File> getOldFiles(List<File> files, int daysOld)
	{
		List<File> oldFiles = new ArrayList<File>();
		files.stream().forEach(file -> {
			SimpleDateFormat format = new SimpleDateFormat("D");			
			int fileLastAccessed = Integer.parseInt(format.format(new Date(this.getLastAccessTime(path + File.separator + file.getName())))),
					currentDate = Integer.parseInt(format.format(date));
			if(currentDate < 31)
			{
				if(Math.abs(fileLastAccessed + (fileLastAccessed > currentDate ? 0 : 365) - (365 + currentDate)) >= daysOld)
				{
					oldFiles.add(file);
				}
			}
			else if(Math.abs(fileLastAccessed - currentDate) >= daysOld)
			{
				oldFiles.add(file);	
			}
		});
		return oldFiles;
	}
	
	private List<File> getFiles() {		
		return new ArrayList<File>(Arrays.asList((new File(path)).listFiles()));
	}	
	
	private File find(List<File> files, String name)
	{
		File file = null;		
		for(File f : files)
		{
			if(f.getName().equalsIgnoreCase(name))
			{				
				file = f; 				
				break;
			}			
		}
		return file;
	}

	/**
	 * Gets the last access time for a given file/folder.
	 * @param path Path to a specific file/folder.
	 * @return Long representing access time in millisec(s).
	 */
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

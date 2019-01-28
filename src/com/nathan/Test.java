package com.nathan;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Test 
{
	public static void main(String[] args) 
	{
		
		Date date = new Date(getLastAccessTime("C:\\Users\\natha\\Desktop\\Django.txt"));
		SimpleDateFormat format = new SimpleDateFormat("D");
		System.out.println(format.format(date));
	}
	
	// Implement into CleanerT
	private static long getLastAccessTime(String path)
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

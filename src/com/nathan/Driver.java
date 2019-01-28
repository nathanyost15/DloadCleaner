package com.nathan;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

import com.nathan.cleaner.Cleaner;

public class Driver 
{
	public static void main(String[] args)
	{		
		// Get List of all files in downloads folder		
		String[] paths = {
				"C:\\Users\\" + System.getenv("username") + "\\Downloads",
				"D:\\Downloads",
				"C:\\Users\\" + System.getenv("username") + "\\Desktop" 
		};
		Cleaner cleaner = new Cleaner();
		Arrays.stream(paths).forEach( path -> cleaner.add(path) );
		//cleaner.add(paths[1]);
		cleaner.clean(30);
	}
}

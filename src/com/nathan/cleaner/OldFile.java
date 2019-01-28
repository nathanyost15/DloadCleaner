package com.nathan.cleaner;

import java.io.File;

public class OldFile 
{
	private String parentPath,
		name;
	public OldFile(String path, String name)
	{		
		setParentPath(path);
		setName(name);
	}
	
	public String getParentPath() 
	{
		return parentPath;
	}
	
	public void setParentPath(String path) 
	{
		this.parentPath = path;
	}
	
	public String getName() 
	{
		return name;
	}
	
	public void setName(String name) 
	{		
		this.name = name;
	}	
	
	public String getFullPath()
	{
		return parentPath + File.separator + name;
	}
}

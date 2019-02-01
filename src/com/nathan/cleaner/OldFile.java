package com.nathan.cleaner;

import java.io.File;

public class OldFile 
{
	private String parentPath,
		name;
	private File file;
	/**
	 * Constructs an OldFile obj.
	 * @param path Path to a specific file/folder.
	 * @param name Name of the specific file/folder.
	 */
	public OldFile(String path, File file)
	{		
		setParentPath(path);
		setName(file.getName());
	}
	
	/**
	 * Returns parentPath.
	 * @return String that represents the parent path of the current file/folder.
	 */
	public String getParentPath() 
	{
		return parentPath;
	}
	
	/**
	 * Sets parentPath.	 
	 * @param path Path to a new parent path.
	 */
	public void setParentPath(String path) 
	{
		this.parentPath = path;
	}
	
	/**
	 * Returns name.
	 * @return String that represents the name of the current file/folder.
	 */
	public String getName() 
	{
		return name;
	}
	
	/**
	 * Sets the name of the OldFile obj.
	 * @param name Name of the OldFile obj.
	 */
	public void setName(String name) 
	{		
		this.name = name;
	}	
	
	/**
	 * Returns the full path to the specific file/folder.
	 * @return String that represents the absolute path to the file/folder.
	 */
	public String getAbsolutePath()
	{
		return parentPath + File.separator + name;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}
}

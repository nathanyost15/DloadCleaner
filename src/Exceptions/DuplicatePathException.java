package Exceptions;

public class DuplicatePathException extends Exception
{
	public DuplicatePathException()
	{
		super();
	}
	
	public DuplicatePathException(String message)
	{
		super(message);
	}
	
	@Override
	public String getMessage()
	{
		return "Duplicate path found!";
	}
}

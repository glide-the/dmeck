package com.trustsystems.elfinder.exception;

import java.io.IOException;

public class FsException extends IOException
{

	public FsException(String message)
	{
		super(message);
	}

	public FsException(String message, Throwable e)
	{
		super(message, e);
	}

}

package com.w67clement.mineapi.system.modules;

public class InvalidModuleException extends Exception
{

    private static final long serialVersionUID = 2543761343127659581L;

    public InvalidModuleException(Throwable cause)
    {
        super(cause);
    }

    public InvalidModuleException()
    {
        super("No message.");
    }

    public InvalidModuleException(String message, Throwable cause)
    {
        super(message, cause);
    }

    public InvalidModuleException(String message)
    {
        super(message);
    }

}

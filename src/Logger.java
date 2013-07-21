
import java.io.*;
import java.util.*;

public class Logger {
	
	private static Logger instance = null;
	
	private PrintStream logFile;
	private Queue<String> logMessages;
	
	private Logger() {
		logFile = System.out;
		logMessages = new LinkedList<>();
	};
	
	public static synchronized Logger getInstance() {
		
		if(instance == null)
			instance = new Logger();
		
		return instance;
	}
	
	public synchronized void log(String message) {
		
		message = Thread.currentThread() + " : " + message;
		logFile.println(message);
		logMessages.add(message);
	}
	
	public synchronized Queue<String> grabMessages() {
		Queue<String> result = new LinkedList<>();
		while(!logMessages.isEmpty())
			result.offer(logMessages.poll());
		return result;
	}
}

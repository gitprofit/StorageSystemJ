import java.util.*;

public class DataStorage extends Thread {

	private Logger logger = Logger.getInstance();
	private Map<Integer, File> files = new HashMap<>();
	public List<Request> requests = new ArrayList<>();

	private int totalSpace;
	private int usedSpace;
	private int freeSpace;
	
	private int currUsed; // server-monitor bar

	public int getFreeSpace() {
		return freeSpace;
	}

	public double getFillFactor() {
		return (double) usedSpace / totalSpace;
	}
	
	public double getGUIFillFactor() {
		return (double) currUsed / totalSpace;
	}

	public void reserve(int size) {
		freeSpace -= size;
		usedSpace += size;
	}

	public void free(int size) {
		freeSpace += size;
		usedSpace -= size;
	}

	public DataStorage(int storageSize) {
		totalSpace = storageSize;
		usedSpace = 0;
		freeSpace = storageSize;
		
		currUsed = 0;
	}

	@Override
	public void run() {
		
		Request next = null;
		
		for (;;) {
			
			synchronized (requests) {
				
				while (requests.isEmpty())
					try { requests.wait(); }
					catch (InterruptedException e) { e.printStackTrace(); }
				
				next = requests.remove(0);
			}
			
			processRequest(next);
		}
	}
	
	public void addRequest(Request request) {
		// scheduler here!
		synchronized (requests) {
			requests.add(request);
			requests.notifyAll();
		}
	}
	
	public void processRequest(Request request) {
		
		// server-monitor gui stuff
		if(request.type == "new")
			currUsed += request.metadata.size;
		
		if(request.type == "del")
			currUsed -= request.metadata.size;
		
		logger.log("request processing: " + request.type + ", file: " + request.ID);
		try { Thread.sleep(request.accessTime); }
		catch (InterruptedException e) { e.printStackTrace(); }
		logger.log("done processing");
	}
}

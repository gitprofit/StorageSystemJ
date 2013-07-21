import java.util.*;


public class StorageScheduler {
	
	private List<Request> requests;
	
	public StorageScheduler(List<Request> req) {
		requests = req;
	}
	
	public void putRequest(Request request) {
		
		synchronized(requests) {
			requests.add(request);
			requests.notifyAll();
		}
	}
}
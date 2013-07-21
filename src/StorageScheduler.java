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
			
			int i=0;
			for(i=0; i<requests.size(); ++i) {
				
				// insert at i = break
				
				Request curr = requests.get(i);
				
				if(curr.type == "new") continue;

				int sizeDiff = request.metadata.size - curr.metadata.size;
				
				if(Math.abs(sizeDiff) < 0.2*request.metadata.size) {
					// sizeDiff zaniedbywalny
					
					// older first
					if(curr.metadata.lastAccess.before(request.metadata.lastAccess))
						break;
				}
				else {
					
					// smaller first
					if(curr.metadata.size > request.metadata.size)
						break;
				}
			}
			
			requests.add(i, request);
		}
	}
}
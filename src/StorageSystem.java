import java.util.*;


public class StorageSystem {
	
	private Random rand = new Random();
	
	public Config config = Config.getInstance();
	public Counter counter = Counter.getInst();
	
	private Map<Integer, FileMetadata> metadata = new HashMap<>();
	private int indexer = 1000;
	public List<DataStorage> storages = new ArrayList<>();
	
	public StorageSystem() {
		
		for(int i=0; i<config.storages; ++i) {
			storages.add(new DataStorage(config.nextStorageSize()));
		}
	}
	
	public int nextID() {
		Object[] arr = metadata.keySet().toArray();
		return (int)arr[rand.nextInt(arr.length)];
	}
	
	public void run() {
		for(DataStorage s : storages) {
			s.start();
		}
	}
	
	public void makeRequest(Request request) {
		switch(request.type) {
		case "new": handleNew(request); break;
		case "del" : handleDel(request); break;
		case "read" : handleGen(request); break;
		}
	}
	
	public void handleGen(Request request) {
		request.metadata = metadata.get(request.ID);
		request.accessTime = config.nextAccessTime();
		request.metadata.location.addRequest(request);
	}
	
	public void handleDel(Request request) {
		request.metadata = metadata.remove(request.ID);
		request.metadata.location.free(request.metadata.size);
		request.accessTime = config.nextAccessTime();
		request.metadata.location.addRequest(request);
	}
	
	public void handleNew(Request request) {
		int size = config.nextFileSize();
		DataStorage storage = preferredStorage(size);
		
		if(storage == null) {
			counter.inc();
			return;
		}
		
		indexer++;
		
		FileMetadata meta = new FileMetadata();
		meta.ID = indexer;
		meta.name = "new.file";
		meta.size = size;
		meta.location = storage;
		meta.location.reserve(size);
		
		metadata.put(indexer, meta);
		
		request.ID = indexer;
		request.metadata = meta;
		request.accessTime = config.nextAccessTime();
		
		request.metadata.location.addRequest(request);
	}
	
	public DataStorage preferredStorage(int requiredSize) {
		DataStorage pref = null;
		for(DataStorage ds : storages) {
			if(ds.getFreeSpace() >= requiredSize) {
				if(pref == null || pref.getFillFactor() > ds.getFillFactor())
					pref = ds;
			}
		}
		return pref;
	}
}

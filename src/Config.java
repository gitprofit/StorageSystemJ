
import java.util.*;

public class Config {
	
	private static Config instance = null;
	
	private Random random = new Random();
	private int rand(int min, int max) {
		return min + random.nextInt(max - min + 1);
	}
	
	public int baseSize = 1024;
	
	public int minStorage = 100;
	public int maxStorage = 150;
	
	public int minFile = 1;
	public int maxFile = 10;
			
	public int minAccess = 500;
	public int maxAccess = 1500;
			
	public int storages = 20;
	
	public int safeFilesPerStorage() {
		return minStorage / maxFile;
	}
			
	public int nextStorageSize() {
		return rand(minStorage, maxStorage) * baseSize;
	}
	
	public int nextFileSize() {
		return rand(minFile, maxFile) * baseSize;
	}
	
	public int nextAccessTime() {
		return rand(minAccess, maxAccess);
	}

	private Config() {
	};
	
	public static synchronized Config getInstance() {
		
		if(instance == null)
			instance = new Config();
		
		return instance;
	}
}

import java.util.Random;


public class Main {
	
	private static Random rand = new Random();

	public static void main(String[] args) {
		StorageSystem system = new StorageSystem();
		system.run();
		
		ServerMonitor monitor = new ServerMonitor(system);
		new Thread(monitor).start();
		
		int testSize = system.config.safeFilesPerStorage * system.config.storages;
		//testSize = (int) (testSize * 1.5);
		
		for(int i=0; i<testSize; ++i)
			system.makeRequest(new Request("new", 0));

		for(int i=0; i<testSize; ++i) {
			
			int action = rand.nextInt(3);
			
			switch(action)
			{
			case 0:
				system.makeRequest(new Request("read", system.nextID()));
				break; 
			case 1:
				system.makeRequest(new Request("del", system.nextID()));
				break;
			case 2:
				system.makeRequest(new Request("new", 0));
				break;
			}
			
			try { Thread.sleep(30); }
			catch (InterruptedException e) { }
		}
	}

}

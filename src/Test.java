import java.util.Random;


public class Test {
	
	private static Random rand = new Random();
	
	public double run(int numOfStorages, int timeDelta) {
		
		Counter counter = Counter.getInst();
		counter.zero();
		
		GlobalList globalList = GlobalList.get();
		globalList.clear();
		
		Config config = Config.getInstance();
		config.storages = numOfStorages;
		
		StorageSystem system = new StorageSystem();
		system.run();
		
		//ServerMonitor monitor = new ServerMonitor(system);
		//new Thread(monitor).start();
		
		int testSize = config.safeFilesPerStorage() * config.storages;
		testSize = (int) (testSize * 2.0);
		
		
		/*
		 * fill storages
		 */
		
		
		for(int i=0; i<testSize; ++i) {
			system.makeRequest(new Request("new", 0));
			
			try { Thread.sleep(30); }
			catch (InterruptedException e) { }
		}
		
		
		try { Thread.sleep(1000); }
		catch (InterruptedException e) { }
		
		
		
		globalList.clear();

		
		/*
		 * REAL TEST
		 */
		
		
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
			
			try { Thread.sleep(timeDelta); }
			catch (InterruptedException e) { }
		}
		
		
		/*
		 * main waits
		 */
		while(counter.get() != 2*testSize) {
			try { Thread.sleep(500); }
			catch (InterruptedException e) { }
		}
		
		/*
		 * kill 'em
		 */
		
		for(DataStorage ds : system.storages)
			ds.interrupt();
		
		try { Thread.sleep(1000); }
		catch (InterruptedException e) { }

		//System.out.println("{ " + config.storages + ", " + globalList.avg()/1000.0 + " }");
		
		return globalList.avg();
	}
}

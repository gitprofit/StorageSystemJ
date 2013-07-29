import java.io.IOException;
import java.util.Random;


public class Test {
	
	private static Random rand = new Random();
	
	public void run() {
		
		Config.getInstance().storages = 10;
		
		StorageSystem system = new StorageSystem();
		
		system.run();
		
		//ServerMonitor monitor = new ServerMonitor(system);
		//new Thread(monitor).start();
		
		int testSize = system.config.safeFilesPerStorage() * system.config.storages;
		testSize = (int) (testSize * 2.0);
		
		for(int i=0; i<testSize; ++i) {
			system.makeRequest(new Request("new", 0));
			
			try { Thread.sleep(30); }
			catch (InterruptedException e) { }
		}
		
		System.out.println("Fall asleep ...");
		try { Thread.sleep(1000); }
		catch (InterruptedException e) { }
		System.out.println("Awake!!!");
		
		GlobalList.get().clear();

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
			
			try { Thread.sleep(15); }
			catch (InterruptedException e) { }
		}
		
		GlobalList gl = GlobalList.get();
		
		//while(gl.size() != 4*testSize) {
			//try { Thread.sleep(2000); }
			//catch (InterruptedException e) { e.printStackTrace(); }
			
			try {
				int x = System.in.read();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			gl.dump();
			
			System.out.println("gl.size() = " + gl.size());
			System.out.println(" testSize = " + testSize);
		//}
		
		System.out.println("{ " + Config.getInstance().storages + ", " + GlobalList.get().avg()/1000.0 + " }");
	}
}

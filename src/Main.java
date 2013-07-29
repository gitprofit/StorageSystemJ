
public class Main {
	
	

	public static void main(String[] args) {
		
		GlobalList.get();
		Config.getInstance();
		Counter.getInst();
		
		Test test = new Test();
		
		int[] loStorages = { 5, 10, 15, 20, 25, 30, 35, 40, 45, 50, 55, 60, 65, 70, 75, 80, 85, 90, 95, 100 };
		int[] hiStorages = { 110, 120, 130, 140, 150, 160, 170, 180, 190, 200 };
		
		int loNum = 6;
		int hiNum = 2;
		
		for(int i=0; i<loNum; ++i) {
			
			for(int lo : loStorages) {
				System.out.println("{ " + lo + ", " + test.run(lo) + " },");
				try { Thread.sleep(1000); }
				catch (InterruptedException e) { }
			}
			
			if(i<hiNum) {
				for(int lo : hiStorages) {
					System.out.println("{ " + lo + ", " + test.run(lo) + " },");
					try { Thread.sleep(1000); }
					catch (InterruptedException e) { }
				}
			}
		}
	}

}

import java.util.ArrayList;

public class GlobalList {

	private static GlobalList instance;
	
	private ArrayList<Double> list = new ArrayList<>();
	
	// !!!! NOT SYNCHRONIZED
	public static GlobalList get() {
		if(instance == null)
			instance = new GlobalList();
		return instance;
	}
	
	public synchronized void put(double d) {
		list.add(d);
	}
	
	public synchronized int size() { 
		return list.size();
	}
	
	public synchronized void clear() {
		list.clear();
	}
	
	public synchronized double avg() {
		double sum = 0.0;
		for(double d : list)
			sum += d;
		return sum/list.size();
	}
}

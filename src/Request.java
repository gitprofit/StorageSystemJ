
public class Request {
	
	public int ID;
	public String type;
	public long accessTime;
	public FileMetadata metadata;
	
	public Request(String type, int ID) {
		this.type = type;
		this.ID = ID;
	}
}

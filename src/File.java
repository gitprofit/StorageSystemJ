import java.util.Date;


public class File {
	
	public int ID = 0;
	public FileMetadata metadata = null;
}

class FileMetadata {
	
	public int ID = 0;
	public String name = "empty.name";
	public int size = 0;
	public Date lastAccess = new Date();
	public DataStorage location = null;
}
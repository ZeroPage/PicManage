package sp.imageinfo;

import java.io.File;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.attribute.FileTime;

public class ImageFile implements Serializable {
	private String fileDir;
	private String fileName;
	private String recentUpdate;
	private File file;

	public ImageFile(File file) throws Exception {
		this.file = file;
		this.fileDir = file.getPath();
		this.initData();
	}

	private void initData() throws Exception {
		this.file = new File(this.fileDir);
		FileTime recentTime = Files.getLastModifiedTime(Paths.get(fileDir));
		recentUpdate = recentTime.toString();
		this.fileName = file.getName();
	}

//Return the name and the date(time) of the most recent update
	public String[] getData() {
		String[] fileData = {fileName,recentUpdate};
		return fileData;
	}
	
	public File getFile() {
		return this.file;
	}
	
	public String toString() {
		return this.fileName;
	}
}

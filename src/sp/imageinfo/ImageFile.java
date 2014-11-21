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

	public ImageFile(String fileDir) throws Exception {
		this.fileDir = fileDir;
		this.InitData();
	}

	private void InitData() throws Exception {
		this.file = new File(this.fileDir);
		FileTime recentTime = Files.getLastModifiedTime(Paths.get(fileDir));
		recentUpdate = recentTime.toString();
		this.fileName = file.getName();
	}

//Return the name and the date(time) of the most recent update
	public String[] GetData() {
		String[] fileData = {fileName,recentUpdate};
		return fileData;
	}
}

package sp.imageinfo;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.attribute.FileTime;
import java.util.Date;

public class ImageFile implements Serializable {
	private String fileDir;
	private String fileName;
	private String recentUpdate;
	private long recentUpdateMil;
	private File file;

	public ImageFile(File file) throws IOException {
		this.file = file;
		this.fileDir = file.getPath();
		this.initData();
	}

	private void initData() throws IOException {
		this.file = new File(this.fileDir);
		FileTime recentTime = FileTime.fromMillis(0);
		recentTime = Files.getLastModifiedTime(Paths.get(fileDir));
		recentUpdateMil = recentTime.toMillis();
		recentUpdate = recentTime.toString();
		this.fileName = file.getName();
	}
	
	public File getFile() {
		return this.file;
	}
	
	public String toString() {
		return this.fileName;
	}
	
	public Date getFileDate() {
		@SuppressWarnings("deprecation")
		Date date = new Date(recentUpdateMil);
		return date;
	}
}

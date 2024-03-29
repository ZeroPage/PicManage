package sp.imageinfo;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import com.drew.imaging.jpeg.JpegProcessingException;
import com.drew.metadata.Tag;

public class ImageInfo implements Serializable {
	private String md5;
	private String memo;
	private ImageFile fileInfo;
	private transient ImageMeta metaInfo;
	private HashSet<String> tagInfo;
	
	public ImageInfo(File imageFile) throws JpegProcessingException, IOException {
		fileInfo = new ImageFile(imageFile);
		metaInfo = new ImageMeta(imageFile);
		try {
			setMD5(imageFile);
		} catch (NoSuchAlgorithmException | IOException e) {
			System.out.println("There's something wrong with the file " + imageFile.getName() + "\'s data!");
		}
		tagInfo = new HashSet<String>();
		
	}
	
	public void initMeta() throws JpegProcessingException, IOException {
		metaInfo = new ImageMeta(fileInfo.getFile());
	}
	
	public String getFileName() {
		return fileInfo.toString();
	}
	
	public Date getFileTime() {
		@SuppressWarnings("deprecation")
		Date date;
			date = fileInfo.getFileDate();
		return date;
	}
	
	public void setTag(HashSet<String> tags) {
		tagInfo = tags;
	}
	
	public String getMemo() {
		return memo;
	}

	public void setMemo(String newMemo) {
		memo = newMemo;
	}
	
	public HashSet<String> getTag() {
		return tagInfo;
	}
	
	public Collection<Tag> getMeta() {
		return metaInfo.getMeta();
	}
	
	public File getFile() {
		return fileInfo.getFile();
	}
	
	public String toString() {
		return fileInfo.toString();
	}
	
	private void setMD5(File fileDir) throws NoSuchAlgorithmException, IOException {
		MessageDigest md = MessageDigest.getInstance("MD5");
		FileInputStream stream = new FileInputStream(fileDir);
		byte[] data = new byte[1024];
		int read = 0;
		
		while((read = stream.read(data))!=-1)
			md.update(data, 0, read);
		
		stream.close();
		
		byte[] mdbyte = md.digest();
		
		StringBuffer buf = new StringBuffer();
		
		for(int i=0;i<mdbyte.length;i++)
			buf.append(Integer.toString((mdbyte[i]&0xff)+0x100,16).substring(1));
		
		this.md5 = buf.toString();
	}
	
	public boolean equals(ImageInfo obj) {
		if(this.md5.equals(obj.md5))
			return true;
		else return false;
	}
}

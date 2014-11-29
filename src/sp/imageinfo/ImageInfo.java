package sp.imageinfo;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

//Sample Directory : C:\\Users\\Julian\\workspace\\ImageInfo\\sample

public class ImageInfo implements Serializable {
//	final private String folderDir = "YOUR\\PATH"; // Write your own sample Directory
	private String md5;
	private String memo;
	private ImageFile fileInfo;
	private ImageMeta metaInfo;
//	private ArrayList<String> tagInfo;
	private HashSet<String> tagInfo;
	
	public ImageInfo(File imageFile) {
		try {
			fileInfo = new ImageFile(imageFile);
		} catch (Exception e) {
			e.printStackTrace();
		}
		metaInfo = new ImageMeta(imageFile);
		try {
			setMD5(imageFile);
		} catch (NoSuchAlgorithmException | IOException e) {
			e.printStackTrace();
		}
		tagInfo = new HashSet<String>();
		
	}
//Handle Data: Memo
//return memo
	public String getMemo() {
		return memo;
	}

//replace the memo with new given string
	public void updateMemo(String newMemo) {
		memo = newMemo;
	}
	
/*	private void saveMemo() {
		//To be filled
	}
	
	private void loadMemo() {
		//To be filled
	}
*/
	
	public File getFile() {
		return fileInfo.getFile();
	}
	
	public String toString() {
		return fileInfo.toString();
	}
	
//Handle Data: MD5
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

//Check whether given file is the same file with the object
	public boolean isSameFile(File fileDir) {
		try {
			return checkMD5(fileDir);
		} catch (NoSuchAlgorithmException | IOException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	private boolean checkMD5(File fileDir) throws NoSuchAlgorithmException, IOException {
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
		
		if(this.md5.equals(buf.toString()))
			return true;
		return false;
	}
}

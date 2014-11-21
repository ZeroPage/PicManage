package sp.imageinfo;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

//Sample Directory : C:\\Users\\Julian\\workspace\\ImageInfo\\sample

public class ImageInfo {
	final private String folderDir = "YOUR\\PATH"; // Write your own sample Directory
	private String md5;
	private String memo;
	private ImageFile fileInfo;
	private ImageMeta metaInfo;
	private ImageTag tagInfo;
	
	File folder;
	File[] fileList;
	
	private static ArrayList<ImageInfo> imageList = new ArrayList<ImageInfo>();
	
//Use this constructor in ImageManager
	public ImageInfo() throws Exception {
		folder = new File(folderDir);
		fileList = folder.listFiles();
		
		for(File file : fileList) {
			if(file.getName().endsWith(".jpeg")) {
				imageList.add(new ImageInfo(folderDir+file.getName()));
			}
		}
	}
//This constructor is designed only for usage in default constructor
	public ImageInfo(String fileDir) throws Exception {
		fileInfo = new ImageFile(fileDir);
		metaInfo = new ImageMeta(fileDir);
		SetMD5(fileDir);
		tagInfo = new ImageTag(fileDir);
	}
//Get the list of image objects
	public ArrayList<ImageInfo> GetImageList() {
		return imageList;
	}
	
//Handle Data: Memo
//return memo
	public String GetMemo() {
		return memo;
	}

//replace the memo with new given string
	public void UpdateMemo(String newMemo) {
		memo = newMemo;
	}
	
	private void SaveMemo() {
		//To be filled
	}
	
	private void LoadMemo() {
		//To be filled
	}
	
//Handle Data: MD5
	private void SetMD5(String fileDir) throws NoSuchAlgorithmException, IOException {
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
	public boolean CheckMD5(String fileDir) throws NoSuchAlgorithmException, IOException {
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

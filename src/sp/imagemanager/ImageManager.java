package sp.imagemanager;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

import sp.imageinfo.ImageInfo;

public class ImageManager implements Serializable {
	private ArrayList<ImageInfo> ImageList;
	private static final long serialVersionUID = 1L;
	private String filePath;
	
	public ImageManager() {
		ImageList = new ArrayList<ImageInfo>();
		filePath = "./data.dat";
		loadImageList();
	}
	public void saveImageList() {
		try {
			FileOutputStream fileOut = new FileOutputStream(filePath);
			ObjectOutputStream oOut = new ObjectOutputStream(fileOut);
			
			oOut.writeObject(ImageList);
			
			oOut.close();
			fileOut.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public void loadImageList() {
		try {
			FileInputStream fileIn = new FileInputStream(filePath);
			ObjectInputStream oIn = new ObjectInputStream(fileIn);
			
			ImageList = (ArrayList<ImageInfo>) oIn.readObject();
			
			fileIn.close();
			oIn.close();
		} catch (FileNotFoundException e) {
			return;
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
	}
	public void addImage(File file) {
		ImageInfo imageInfo = new ImageInfo(file);
		ImageList.add(imageInfo);
	}
	public void deleteImage() {
		
	}
	public void sortImageList() {
		
	}
	
}

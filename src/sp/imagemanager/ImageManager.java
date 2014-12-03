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
	private static final long serialVersionUID = -466251720671992919L;
	private ArrayList<ImageInfo> imageList;
	private String filePath;
	
	public ImageManager() {
		imageList = new ArrayList<ImageInfo>();
		filePath = "./data.dat";
		loadImageList();
	}
	public ArrayList<ImageInfo> getImageList() {
		return this.imageList;
	}
	public void saveImageList() {
		try {
			FileOutputStream fileOut = new FileOutputStream(filePath);
			ObjectOutputStream oOut = new ObjectOutputStream(fileOut);
			
			oOut.writeObject(imageList);
			
			oOut.close();
			fileOut.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	public void loadImageList() {
		try {
			FileInputStream fileIn = new FileInputStream(filePath);
			ObjectInputStream oIn = new ObjectInputStream(fileIn);
			
			imageList = (ArrayList<ImageInfo>) oIn.readObject();
			
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
		imageList.add(imageInfo);
	}
	public void deleteImage(ImageInfo imageInfo) {
		imageList.remove(imageInfo);
	}
	public void sortImageList() {
		
	}
}

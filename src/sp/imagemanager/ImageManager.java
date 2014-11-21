package sp.imagemanager;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.ArrayList;

import sp.imageinfo.ImageInfo;


public class ImageManager implements Serializable {
	private ArrayList ImageList;
	private static final long serialVersionUID = 1L;
	private String filePath;
	
	public ImageManager() {
		ImageList = new ArrayList<ImageInfo>();
		filePath = "./data.dat";
		loadImageList();
	}
	public void saveImageList() {
		
	}
	public void loadImageList() {
		try {
			FileInputStream fileIn = new FileInputStream(filePath);
			ObjectInputStream oIn = new ObjectInputStream(fileIn);
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public void addImage() {
		
	}
	public void deleteImage() {
		
	}
	public void sortImageList() {
		
	}
	
}

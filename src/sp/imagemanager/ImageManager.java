package sp.imagemanager;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.*;

import com.drew.imaging.jpeg.JpegProcessingException;
import sp.imageinfo.ImageInfo;

public class ImageManager implements Serializable {
	private static final long serialVersionUID = -466251720671992920L;
	private ArrayList<ImageInfo> imageList;
	public static final int SORT_BY_NAME = 1;
	public static final int SORT_BY_TIME = 2;
	
	public ImageManager() {
		imageList = new ArrayList<ImageInfo>();
	}
	public ArrayList<ImageInfo> getImageList() {
		return this.imageList;
	}
	public void saveImageList(File file) throws IOException {

		FileOutputStream fileOut = new FileOutputStream(file);
		ObjectOutputStream oOut = new ObjectOutputStream(fileOut);
			
		oOut.writeObject(imageList);
			
		oOut.close();
		fileOut.close();
	}
	public void loadImageList(File file) throws IOException, ClassNotFoundException, JpegProcessingException {

		FileInputStream fileIn = new FileInputStream(file);
		ObjectInputStream oIn = new ObjectInputStream(fileIn);

		imageList = (ArrayList<ImageInfo>) oIn.readObject();

		ListIterator<ImageInfo> it = imageList.listIterator();
		while(it.hasNext()) {
			it.next().initMeta();
		}
		fileIn.close();
		oIn.close();
	}
	public void addImage(File file) throws JpegProcessingException, IOException{
		ImageInfo imageInfo = new ImageInfo(file);
		imageList.add(imageInfo);
	}
	public void deleteImage(ImageInfo imageInfo) {
		imageList.remove(imageInfo);
	}

	public void sortImageList(int method) {
		switch(method) {
			case SORT_BY_NAME:
				sortImageListByName();
				break;
			case SORT_BY_TIME:
				sortImageListByTime();
				break;
			default:
			 	break; //test
		}
	}
	private void sortImageListByName() {
		Collections.sort(imageList, new Comparator<ImageInfo>() {
			@Override
			public int compare(ImageInfo imageInfo1, ImageInfo imageInfo2) {
				return imageInfo1.getFileName().compareTo(imageInfo2.getFileName());
			}
		});
	}
	private void sortImageListByTime() {
		Collections.sort(imageList, new Comparator<ImageInfo>() {
			@Override
			public int compare(ImageInfo imageInfo1, ImageInfo imageInfo2) {
				return imageInfo1.getFileTime().compareTo(imageInfo2.getFileTime());
			}
		});
	}

	//public ArrayList<ImageInfo> filterImageListByTag() {}

	public HashSet<String> getTagSet() {
		ListIterator<ImageInfo> it = imageList.listIterator();
		HashSet<String> tagSet = new HashSet<String>();
		while(it.hasNext()) {
			tagSet.addAll(it.next().getTag());
		}
		return tagSet;
	}

}

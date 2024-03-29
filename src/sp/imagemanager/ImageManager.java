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

	public ArrayList<ImageInfo> getImageList(int method) {
		ArrayList<ImageInfo> clone;
		switch(method) {
			case SORT_BY_NAME:
				clone = sortImageListByName();
				break;
			case SORT_BY_TIME:
				clone = sortImageListByTime();
				break;
			default:
				clone = sortImageListByName(); //test
		}
		return clone;
	}
	public ArrayList<ImageInfo> getImageList(int method, List<String> strings) {
		ArrayList<ImageInfo> clone = getImageList(method);
		clone = filterImageListByTag(clone, strings);
		return clone;
	}

	private ArrayList<ImageInfo> cloneImageList() {
		ArrayList<ImageInfo> clone = new ArrayList<ImageInfo>(imageList.size());
		ListIterator<ImageInfo> it = imageList.listIterator();
		while(it.hasNext()) {
			clone.add(it.next());
		}
		return clone;
	}
	public void clearImageList() {
		imageList.clear();
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

		List<ImageInfo> list = imageList;
		int i = 0;
		while(i < list.size() - 1) {
			ImageInfo imageInfo = list.get(i);
			if(imageInfo.getFile().exists()) {
				imageInfo.initMeta();
				i++;
			} else {
				imageList.remove(imageInfo);
			}
		}
		fileIn.close();
		oIn.close();
	}
	public void addImage(File file) throws JpegProcessingException, IOException{
		ImageInfo imageInfo = new ImageInfo(file);
		ListIterator<ImageInfo> it = imageList.listIterator();
		while(it.hasNext()) {
			if(it.next().equals(imageInfo)) {
				return;
			}
		}
		imageList.add(imageInfo);
	}
	public void deleteImage(ImageInfo imageInfo) {
		imageList.remove(imageInfo);
	}

	private ArrayList<ImageInfo> sortImageListByName() {
		ArrayList<ImageInfo> clone = cloneImageList();
		Collections.sort(clone, new Comparator<ImageInfo>() {
			@Override
			public int compare(ImageInfo imageInfo1, ImageInfo imageInfo2) {
				return imageInfo1.getFileName().toLowerCase().compareTo(imageInfo2.getFileName().toLowerCase());
			}
		});
		return clone;
	}
	private ArrayList<ImageInfo> sortImageListByTime() {
		ArrayList<ImageInfo> clone = cloneImageList();
		Collections.sort(clone, new Comparator<ImageInfo>() {
			@Override
			public int compare(ImageInfo imageInfo1, ImageInfo imageInfo2) {
				return imageInfo1.getFileTime().compareTo(imageInfo2.getFileTime());
			}
		});
		return clone;
	}

	private ArrayList<ImageInfo> filterImageListByTag(ArrayList<ImageInfo> clone, List<String> strings) {
		ListIterator<ImageInfo> it = imageList.listIterator();
		while(it.hasNext()) {
			ImageInfo temp = it.next();
			HashSet<String> tagSet = temp.getTag();
			for(int i = 0; i < strings.size(); i++) {
				if(!tagSet.contains(strings.get(i))) {
					clone.remove(temp);
					break;
				}
			}
		}
		return clone;
	}

	public HashSet<String> getTagSet() {
		ListIterator<ImageInfo> it = imageList.listIterator();
		HashSet<String> tagSet = new HashSet<String>();
		while(it.hasNext()) {
			tagSet.addAll(it.next().getTag());
		}
		return tagSet;
	}

}

package sp.imageinfo;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;

public class ImageTag implements Serializable {
	private ArrayList<String> tagList = new ArrayList<String>();
	private File taggedImage;
	
	public ImageTag(File inputFile) {
		taggedImage = inputFile;
		//this.loadTag();
	}

//Return Arraylist of tags
	public ArrayList<String> getTag() {
		return this.tagList;
	}
	
//Add new tag
	public void addTag(String newTag) {
		this.tagList.add(newTag);
	}
	
//delete a tag by giving index of it
	public void deleteTag(int index) {
		tagList.remove(index);
		//saveTag();
	}
	
	/*private void saveTag() {
		//To be filled
	}
	
	private void loadTag() {
		//To be filled
	}
	*/
}

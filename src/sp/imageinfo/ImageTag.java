package sp.imageinfo;

import java.io.File;
import java.util.ArrayList;

public class ImageTag {
	private ArrayList<String> tagList = new ArrayList<String>();
	private String taggedImage;
	
	public ImageTag(String imageDir) {
		taggedImage = imageDir;
		this.LoadTag();
	}

//Return Arraylist of tags
	public ArrayList<String> GetTag() {
		return this.tagList;
	}
	
//Add new tag
	public void AddTag(String newTag) {
		this.tagList.add(newTag);
	}
	
//delete a tag by giving index of it
	public void DeleteTag(int index) {
		//To be filled
	}
	
	private void SaveTag() {
		//To be filled
	}
	
	private void LoadTag() {
		//To be filled
	}
}

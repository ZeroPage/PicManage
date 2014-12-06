package sp.imageinfo;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Date;

import com.drew.imaging.jpeg.*;
import com.drew.metadata.*;

public class ImageMeta implements Serializable {
	private File imageFile;
	private Metadata metaInfo;
	private Collection<Tag> metaTag;
	
	public ImageMeta() {
	}
	
	public ImageMeta(File file) throws JpegProcessingException, IOException {
		imageFile = file;
		metaTag = new ArrayList<>();
		initMetadata();
	}
	
	private void initMetadata() throws  IOException, JpegProcessingException {
		metaInfo = JpegMetadataReader.readMetadata(imageFile);
		Iterator dir = metaInfo.getDirectories().iterator();
		while(dir.hasNext()) {
			Directory directory = (Directory) dir.next();
			Iterator tags = directory.getTags().iterator();
			while(tags.hasNext()) {
				Tag tag = (Tag) tags.next();
				if(tag!=null)
					metaTag.add(tag);
			}
		}
	}
	
	public Collection<Tag> getMeta() {
		return metaTag;
	}
	
	@SuppressWarnings("deprecation")
	public Date getDate() {
		ArrayList<String> timeData = new ArrayList<String>();
		Date date = new Date();
		for(Tag tag : metaTag) {
			if(tag.getTagName().contains("Date/Time")) {
				timeData.add(tag.getDescription());
			}
		}
		if(timeData.isEmpty()) {
			return null;
		} else {
			for(String str : timeData) {
				String[] split;
				split = str.split(":| ");
				if(date.after(new Date(Integer.parseInt(split[0]),Integer.parseInt(split[1]),Integer.parseInt(split[2]),Integer.parseInt(split[3]),Integer.parseInt(split[4]),Integer.parseInt(split[5])))) {
					date = new Date(Integer.parseInt(split[0]),Integer.parseInt(split[1]),Integer.parseInt(split[2]),Integer.parseInt(split[3]),Integer.parseInt(split[4]),Integer.parseInt(split[5]));
				}
			}
		}
		return date;
	}
}
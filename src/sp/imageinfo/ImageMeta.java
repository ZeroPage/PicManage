package sp.imageinfo;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;

import com.drew.imaging.jpeg.*;
import com.drew.metadata.*;

public class ImageMeta implements Serializable {
	private File imageFile;
	private Metadata metaInfo;
	private Collection<String> metaTag;
	
	public ImageMeta() {
	}
	
	public ImageMeta(File file) {
		imageFile = file;
		try {
			initMetadata();
		} catch (Exception e) {
			// TODO
			e.printStackTrace();
		}
	}
	
	private void initMetadata() throws JpegProcessingException, IOException {
		metaInfo = JpegMetadataReader.readMetadata(imageFile);
		Iterator dir = metaInfo.getDirectories().iterator();
		while(dir.hasNext()) {
			Directory directory = (Directory) dir.next();
			Iterator tags = directory.getTags().iterator();
			while(tags.hasNext()) {
				if(tags.next()!=null) {
					Tag tag = (Tag) tags;
					metaTag.add(tag.toString());
				}
			}
		}
	}
	
	public Collection<String> getMeta() {
		return metaTag;
	}
}
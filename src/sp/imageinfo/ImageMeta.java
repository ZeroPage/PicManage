package sp.imageinfo;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import com.drew.imaging.jpeg.*;
import com.drew.metadata.*;

public class ImageMeta implements Serializable {
	private File imageFile;
	private Metadata metaInfo;
	private Collection<Tag> metaTag;
	
	public ImageMeta() {
	}
	
	public ImageMeta(File file) {
		imageFile = file;
		metaTag = new ArrayList<>();
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
				Tag tag = (Tag) tags.next();
				if(tag!=null)
					metaTag.add(tag);
			}
		}
	}
	
	public Collection<Tag> getMeta() {
		return metaTag;
	}
}
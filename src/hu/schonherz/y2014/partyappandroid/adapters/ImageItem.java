package hu.schonherz.y2014.partyappandroid.adapters;

import android.content.Context;
import android.graphics.Bitmap;

public class ImageItem {
    private Context mContext;
    private Bitmap image;
    private String title;

    public ImageItem(Context c) {
	mContext = c;
    }

    public ImageItem(Bitmap image, String title) {
	super();
	this.image = image;
	this.title = title;
    }

    public Bitmap getImage() {
	return image;
    }

    public void setImage(Bitmap image) {
	this.image = image;
    }

    public String getTitle() {
	return title;
    }

    public void setTitle(String title) {
	this.title = title;
    }
}

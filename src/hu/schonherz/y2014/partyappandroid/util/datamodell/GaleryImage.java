package hu.schonherz.y2014.partyappandroid.util.datamodell;

import hu.schonherz.y2014.partyappandroid.ImageUtils;
import android.graphics.Bitmap;

public class GaleryImage {
    private Bitmap bitmap;
    private Bitmap bitmap_thumbnail;
    private int id;

    public GaleryImage(int id, Bitmap bitmap_thumbnail) {
	this.id = id;
	this.bitmap_thumbnail = bitmap_thumbnail;
    }

    public Bitmap getBitmap() {
	return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
	this.bitmap = bitmap;
    }

    public Bitmap getBitmap_thumbnail() {
	return bitmap_thumbnail;
    }

    public void setBitmap_thumbnail(Bitmap bitmap_thumbnail) {
	this.bitmap_thumbnail = bitmap_thumbnail;
    }
    
    public void downloadBitmap(){
	this.bitmap = ImageUtils.StringToBitMap( Session.getInstance().getActualCommunicationInterface().DownLoadAnImage(this.id));
    }
}

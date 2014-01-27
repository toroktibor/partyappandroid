package hu.schonherz.y2014.partyappandroid.util.datamodell;

import android.graphics.Bitmap;

public class GaleryImage {
    private Bitmap bitmap;
    private int id;

    public GaleryImage(int id, Bitmap bitmap) {
	this.id = id;
	this.bitmap = bitmap;
    }

    public Bitmap getBitmap() {
	return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
	this.bitmap = bitmap;
    }
}

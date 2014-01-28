package hu.schonherz.y2014.partyappandroid;

import java.io.ByteArrayOutputStream;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ExifInterface;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;

public class ImageUtils {
    public static String BitMapToString(Bitmap bitmap) {
	ByteArrayOutputStream baos = new ByteArrayOutputStream();
	bitmap.compress(Bitmap.CompressFormat.JPEG, 70, baos);
	byte[] b = baos.toByteArray();
	String strBitMap = Base64.encodeToString(b, Base64.DEFAULT);
	// String strBitMap = new String(b);
	return strBitMap;
    }

    public static Bitmap StringToBitMap(String input) {
	byte[] decodedByte = Base64.decode(input, 0);
	// byte[] decodedByte = input.getBytes(Charset.forName("UTF-8"));
	return BitmapFactory.decodeByteArray(decodedByte, 0, decodedByte.length);
    }

    public static int getOrientation(Context context, Uri photoUri) {
	Cursor cursor = context.getContentResolver().query(photoUri,
		new String[] { MediaStore.Images.ImageColumns.ORIENTATION }, null, null, null);
	// cursor might be null!

	try {
	    int returnMe;
	    if (cursor.moveToFirst()) {
		returnMe = cursor.getInt(0);
	    } else {
		returnMe = -1;
	    }
	    cursor.close();
	    return returnMe;
	} catch (NullPointerException e) {
	    // log: no cursor found returnung -1!
	    return -1;
	}
    }

    public static int getOrientationFromExif(Uri photoUri) {

	try {
	    ExifInterface exif = new ExifInterface(photoUri.getPath());
	    int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, 999);
	    Log.i("asdasd","orientation:"+orientation);
	    if (orientation == 1) {
		return 0;
	    } else if (orientation == 6) {
		return 90;
	    } else if (orientation == 3) {
		return 180;
	    } else if (orientation == 8) {
		return 270;
	    } else {
		return -1;
	    }

	} catch (Exception e) {
	    return -1;
	}
    }

    public static int tryGetOrientation(Context context, Uri photoUri) {
	
	int cursor=getOrientation(context, photoUri);
	int exif=getOrientationFromExif(photoUri);

	
	if(exif!=-1){
	    return exif;
	}else if(cursor !=-1){
	    return cursor;
	}else{
	    return 0;
	}	
    }
}

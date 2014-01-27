package hu.schonherz.y2014.partyappandroid;

import java.io.ByteArrayOutputStream;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

public class ImageUtils {
    public static String BitMapToString(Bitmap bitmap) {
	ByteArrayOutputStream baos = new ByteArrayOutputStream();
	bitmap.compress(Bitmap.CompressFormat.JPEG, 70, baos);
	byte[] b = baos.toByteArray();
	String strBitMap = Base64.encodeToString(b, Base64.DEFAULT);
	return strBitMap;
    }

    public static Bitmap StringToBitMap(String input) {
	byte[] decodedByte = Base64.decode(input, 0);
	return BitmapFactory.decodeByteArray(decodedByte, 0, decodedByte.length);
    }
}

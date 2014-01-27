package hu.schonherz.y2014.partyappandroid.activities;

import hu.schonherz.y2014.partyappandroid.R;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.os.Bundle;
import android.util.FloatMath;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.widget.ImageView;

public class FullImageActivity extends Activity {
    ImageView imageView;
    Matrix matrix = new Matrix();
    Matrix savedMatrix = new Matrix();
    PointF startPoint = new PointF();
    PointF midPoint = new PointF();
    float oldDist = 1f;
    static final int NONE = 0;
    static final int DRAG = 1;
    static final int ZOOM = 2;
    int mode = NONE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	this.requestWindowFeature(Window.FEATURE_NO_TITLE);
	setContentView(R.layout.activity_full_image);

	ImageView imageView = (ImageView) findViewById(R.id.full_image_view);

	byte[] byteArray = getIntent().getByteArrayExtra("image");
	Bitmap bmp = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
	imageView.setImageBitmap(bmp);
	imageView.setOnTouchListener(new OnTouchListener() {

	    @Override
	    public boolean onTouch(View v, MotionEvent event) {
		ImageView view = (ImageView) v;
		System.out.println("matrix=" + savedMatrix.toString());
		switch (event.getAction() & MotionEvent.ACTION_MASK) {
		case MotionEvent.ACTION_DOWN:
		    savedMatrix.set(matrix);
		    startPoint.set(event.getX(), event.getY());
		    mode = DRAG;
		    break;
		case MotionEvent.ACTION_POINTER_DOWN:
		    oldDist = spacing(event);
		    if (oldDist > 10f) {
			savedMatrix.set(matrix);
			midPoint(midPoint, event);
			mode = ZOOM;
		    }
		    break;
		case MotionEvent.ACTION_UP:
		case MotionEvent.ACTION_POINTER_UP:
		    mode = NONE;
		    break;
		case MotionEvent.ACTION_MOVE:
		    if (mode == DRAG) {
			matrix.set(savedMatrix);
			matrix.postTranslate(event.getX() - startPoint.x, event.getY() - startPoint.y);
		    } else if (mode == ZOOM) {
			float newDist = spacing(event);
			if (newDist > 10f) {
			    matrix.set(savedMatrix);
			    float scale = newDist / oldDist;
			    matrix.postScale(scale, scale, midPoint.x, midPoint.y);
			}
		    }
		    break;
		}
		view.setImageMatrix(matrix);
		return true;
	    }

	    @SuppressLint("FloatMath")
	    private float spacing(MotionEvent event) {
		float x = event.getX(0) - event.getX(1);
		float y = event.getY(0) - event.getY(1);
		return FloatMath.sqrt(x * x + y * y);
	    }

	    private void midPoint(PointF point, MotionEvent event) {
		float x = event.getX(0) + event.getX(1);
		float y = event.getY(0) + event.getY(1);
		point.set(x / 2, y / 2);
	    }

	});
    }

}

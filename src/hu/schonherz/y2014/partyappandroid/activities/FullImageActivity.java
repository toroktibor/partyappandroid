package hu.schonherz.y2014.partyappandroid.activities;

import hu.schonherz.y2014.partyappandroid.NetThread;
import hu.schonherz.y2014.partyappandroid.R;
import hu.schonherz.y2014.partyappandroid.util.datamodell.GaleryImage;
import android.app.Activity;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

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
    int currentImageID;
    private Button bNext;
    private Button bPrev;

    private void loadImage(final int imageid) {
        bPrev.setEnabled(false);
        bNext.setEnabled(false);

        Log.i("asdasd", "Kép beöltése #" + imageid);
        if (imageid == -1 || imageid == ClubGaleryFragment.actualClub.images.size()) {
            return;
        }

        this.currentImageID = imageid;
        final ImageView iv = (ImageView) findViewById(R.id.full_image_view);
        final TextView loadingTextView = (TextView) findViewById(R.id.full_image_textview_loading);

        iv.setVisibility(View.INVISIBLE);
        loadingTextView.setVisibility(View.VISIBLE);
        new NetThread(this, new Runnable() {

            @Override
            public void run() {
                final GaleryImage img = ClubGaleryFragment.actualClub.images.get(imageid);
                if (img.getBitmap() == null) {
                    img.downloadBitmap();
                }
                FullImageActivity.this.runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        iv.setImageBitmap(img.getBitmap());
                        loadingTextView.setVisibility(View.INVISIBLE);
                        iv.setVisibility(View.VISIBLE);
                        bPrev.setEnabled(imageid != 0);
                        if (bPrev.isEnabled()) {
                            bPrev.setBackgroundColor(FullImageActivity.this.getResources().getColor(
                                    R.color.purpleButtonBackground));
                        } else {
                            bPrev.setBackgroundColor(FullImageActivity.this.getResources().getColor(
                                    R.color.editTextBackground));
                        }
                        bNext.setEnabled(imageid != ClubGaleryFragment.actualClub.images.size() - 1);
                        if (bNext.isEnabled()) {
                            bNext.setBackgroundColor(FullImageActivity.this.getResources().getColor(
                                    R.color.purpleButtonBackground));
                        } else {
                            bNext.setBackgroundColor(FullImageActivity.this.getResources().getColor(
                                    R.color.editTextBackground));
                        }

                    }
                });

            }
        }).start();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_full_image);

        bNext = (Button) findViewById(R.id.full_image_button_next);
        bPrev = (Button) findViewById(R.id.full_image_button_prev);

        bPrev.setBackgroundColor(FullImageActivity.this.getResources().getColor(R.color.editTextBackground));
        bNext.setEnabled(false);
        bNext.setBackgroundColor(FullImageActivity.this.getResources().getColor(R.color.editTextBackground));
        bNext.setEnabled(false);

        final int imageid = getIntent().getIntExtra("imageid", 0);

        loadImage(imageid);
    }

    public void onClickHandler(View v) {
        switch (v.getId()) {
        case R.id.full_image_button_prev:
            loadImage(currentImageID - 1);
            break;

        case R.id.full_image_button_next:
            loadImage(currentImageID + 1);
            break;

        default:
            break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_right_in, R.anim.slide_right_out);
    }

}

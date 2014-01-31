package hu.schonherz.y2014.partyappandroid.activities;

import hu.schonherz.y2014.partyappandroid.NetThread;
import hu.schonherz.y2014.partyappandroid.R;
import hu.schonherz.y2014.partyappandroid.util.datamodell.GaleryImage;
import hu.schonherz.y2014.partyappandroid.util.datamodell.Session;
import android.app.Activity;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class PedingFullImageActivity extends Activity {
    ImageView imageView;
    Matrix matrix = new Matrix();
    Matrix savedMatrix = new Matrix();
    PointF startPoint = new PointF();
    PointF midPoint = new PointF();
    float oldDist = 1f;
    int currentImageID;

    private void loadImage(final int imageid) {
        final Button acceptButton = (Button) findViewById(R.id.peding_full_image_button_accept);
        final Button declineButton = (Button) findViewById(R.id.peding_full_image_button_decline);
        // acceptButton.setEnabled(false);
        // declineButton.setEnabled(false);

        this.currentImageID = imageid;
        final ImageView iv = (ImageView) findViewById(R.id.peding_full_image_view);
        final TextView loadingTextView = (TextView) findViewById(R.id.peding_full_image_textview_loading);

        iv.setVisibility(View.INVISIBLE);
        loadingTextView.setVisibility(View.VISIBLE);
        new NetThread(this, new Runnable() {

            @Override
            public void run() {
                final GaleryImage img = PendingImageActivity.images.get(imageid);
                if (img.getBitmap() == null) {
                    img.downloadBitmap();
                }
                PedingFullImageActivity.this.runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        iv.setImageBitmap(img.getBitmap());
                        loadingTextView.setVisibility(View.INVISIBLE);
                        iv.setVisibility(View.VISIBLE);

                    }
                });

            }
        }).start();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_peding_full_image);

        final int imageid = getIntent().getIntExtra("imageid", 0);

        loadImage(imageid);
    }

    public void onClickHandler(View v) {
        switch (v.getId()) {
        case R.id.peding_full_image_button_accept:
            Session.getInstance().getActualCommunicationInterface()
                    .acceptImage(PendingImageActivity.images.get(currentImageID).getId());
            PendingImageActivity.images.remove(currentImageID);
            finish();
            break;

        case R.id.peding_full_image_button_decline:
            Session.getInstance().getActualCommunicationInterface()
                    .declineImage(PendingImageActivity.images.get(currentImageID).getId());
            PendingImageActivity.images.remove(currentImageID);
            finish();
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

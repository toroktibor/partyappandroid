package hu.schonherz.y2014.partyappandroid.activities;

import java.util.ArrayList;

import hu.schonherz.y2014.partyappandroid.ImageUtils;
import hu.schonherz.y2014.partyappandroid.R;
import hu.schonherz.y2014.partyappandroid.SimpleActionBar;
import hu.schonherz.y2014.partyappandroid.adapters.GridViewAdapter;
import hu.schonherz.y2014.partyappandroid.adapters.ImageItem;
import hu.schonherz.y2014.partyappandroid.util.datamodell.GaleryImage;
import hu.schonherz.y2014.partyappandroid.util.datamodell.Session;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.widget.GridView;

public class PendingImageActivity extends ActionBarActivity {

	private GridView gridView;
    private GridViewAdapter customGridAdapter;
    ArrayList<Bitmap> imgs = new ArrayList<Bitmap>();
    ArrayList<GaleryImage> images;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pending_image);
		
		images.clear();
		ArrayList<Integer> imageIDList = (ArrayList<Integer>) Session.getInstance().getActualCommunicationInterface().getNotApprovedImages();
		for (int i = 0; i < imageIDList.size(); i++) {
		    images.add(new GaleryImage(imageIDList.get(i), (ImageUtils.StringToBitMap(Session.getInstance()
			    .getActualCommunicationInterface().DownLoadAnImageThumbnail(imageIDList.get(i))))));
		}
		
		new SimpleActionBar(this, "Jóváhagyandó képek").setLayout();
		
		gridView = new GridView(getApplicationContext());
		gridView = (GridView) findViewById(R.id.gridView);
		customGridAdapter = new GridViewAdapter(getApplicationContext(), R.layout.row_grid,
			getDataForGridView());
		gridView.setAdapter(customGridAdapter);
	}
	
    private ArrayList getDataForGridView() {
	final ArrayList imageItems = new ArrayList();

	for (int i = 0; i < images.size(); i++) {
	    imageItems.add(new ImageItem(images.get(i).getBitmap_thumbnail(), "Image#" + i));
	}

	return imageItems;

    }
}

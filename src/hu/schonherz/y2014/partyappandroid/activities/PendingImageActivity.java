package hu.schonherz.y2014.partyappandroid.activities;

import java.util.ArrayList;

import hu.schonherz.y2014.partyappandroid.ImageUtils;
import hu.schonherz.y2014.partyappandroid.R;
import hu.schonherz.y2014.partyappandroid.SimpleActionBar;
import hu.schonherz.y2014.partyappandroid.adapters.GridViewAdapter;
import hu.schonherz.y2014.partyappandroid.adapters.ImageItem;
import hu.schonherz.y2014.partyappandroid.util.datamodell.GaleryImage;
import hu.schonherz.y2014.partyappandroid.util.datamodell.Session;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemClickListener;

public class PendingImageActivity extends ActionBarActivity {

	private GridView gridView;
    private GridViewAdapter customGridAdapter;
    ArrayList<Bitmap> imgs = new ArrayList<Bitmap>();
    static ArrayList<GaleryImage> images = new ArrayList<GaleryImage>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pending_image);
		
		images.clear();
		ArrayList<Integer> imageIDList = (ArrayList<Integer>) Session.getInstance().getActualCommunicationInterface().getNotApprovedImages();
		Log.i("asd",""+imageIDList.size());
		for (int i = 0; i < imageIDList.size(); i++) {
		    images.add(new GaleryImage(imageIDList.get(i), (ImageUtils.StringToBitMap(Session.getInstance()
			    .getActualCommunicationInterface().DownLoadAnImageThumbnail(imageIDList.get(i))))));
		}
		
		new SimpleActionBar(this, "Jóváhagyandó képek").setLayout();
		
		gridView = new GridView(getApplicationContext());
		gridView = (GridView) findViewById(R.id.pending_image_gridView);
		/*customGridAdapter = new GridViewAdapter(getApplicationContext(), R.layout.row_grid,
			getDataForGridView());
		gridView.setAdapter(customGridAdapter);*/
		
		registerForContextMenu(gridView);
		
		gridView.setOnItemClickListener(new OnItemClickListener() {
		    @Override
		    public void onItemClick(AdapterView<?> parent, View v, final int position, long id) {
		
			Intent in1 = new Intent(PendingImageActivity.this, PedingFullImageActivity.class);
			in1.putExtra("imageid", position);
			startActivity(in1);
			PendingImageActivity.this.overridePendingTransition(R.anim.slide_left_in, R.anim.slide_left_out);
						

		    }
		});
		
	}
	
    private ArrayList getDataForGridView() {
	final ArrayList imageItems = new ArrayList();

	for (int i = 0; i < images.size(); i++) {
	    imageItems.add(new ImageItem(images.get(i).getBitmap_thumbnail(), "Image#" + i));
	}

	return imageItems;

    }
    
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
    		ContextMenuInfo menuInfo) {
    	super.onCreateContextMenu(menu, v, menuInfo);
    	
    	MenuInflater inflater = getMenuInflater();
    	inflater.inflate(R.menu.pending_something_list_context_menu, menu);
    }
    
    @Override
    public boolean onContextItemSelected(android.view.MenuItem item) {
	AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
	int index = info.position;
	int imageid = images.get(index).getId();
	switch (item.getItemId()) {
	case R.id.accept_something:
	    Session.getInstance().getActualCommunicationInterface().acceptImage(imageid);
	    images.remove(index);
	    onResume();
	    return true;
	case R.id.decline_something:
	    Session.getInstance().getActualCommunicationInterface().declineImage(imageid);
	    images.remove(index);
	    onResume();
	    return true;
	default:
	    return super.onContextItemSelected(item);
	}
    }
    
    @Override
    protected void onResume() {
    	super.onResume();
    	customGridAdapter = new GridViewAdapter(getApplicationContext(), R.layout.row_grid,	getDataForGridView());
    	gridView.setAdapter(customGridAdapter);
    }
}

package hu.schonherz.y2014.partyappandroid.activities;

import hu.schonherz.y2014.partyappandroid.R;
import hu.schonherz.y2014.partyappandroid.adapters.GridViewAdapter;
import hu.schonherz.y2014.partyappandroid.adapters.ImageItem;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

public class ClubGaleryFragment extends Fragment {
	
	private GridView gridView;
    private GridViewAdapter customGridAdapter;
    ArrayList<Bitmap> imgs = new ArrayList<Bitmap>();
    private Context mContext;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	// TODO Auto-generated method stub
    	super.onCreate(savedInstanceState);
    }
    
    @Override
    public void onAttach(Activity activity) {
    	// TODO Auto-generated method stub
    	super.onAttach(activity);
    	this.mContext = activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
	ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_club_galery, container, false);
	
	//TODO:képek lekérése a szervertől
	
	gridView = new GridView(mContext);
    gridView = (GridView) rootView.findViewById(R.id.gridView);
    customGridAdapter = new GridViewAdapter(getActivity().getApplicationContext(), R.layout.row_grid, getData());
    gridView.setAdapter(customGridAdapter);
    
    /**
     * On Click event for Single Gridview Item
     * */
    gridView.setOnItemClickListener(new OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View v, int position, long id) {

        	ByteArrayOutputStream stream = new ByteArrayOutputStream();
        	imgs.get(position).compress(Bitmap.CompressFormat.PNG, 100, stream);
        	byte[] byteArray = stream.toByteArray();

        	Intent in1 = new Intent(getActivity(), FullImageActivity.class);
        	in1.putExtra("image",byteArray);
        	startActivity(in1);
        }
    });
	return rootView;
    }
    
    private ArrayList getData() {
        final ArrayList imageItems = new ArrayList();
        // retrieve String drawable array
        //TypedArray imgs = getResources().obtainTypedArray(R.array.image_ids);
        
        for (int i = 0; i < imgs.size(); i++) {
            imageItems.add(new ImageItem(imgs.get(i), "Image#" + i));
            Log.e("getData", "i: "+i+imgs.get(i));
        }
 
        return imageItems;
 
    }

}

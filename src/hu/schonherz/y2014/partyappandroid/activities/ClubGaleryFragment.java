package hu.schonherz.y2014.partyappandroid.activities;

import hu.schonherz.y2014.partyappandroid.R;
import hu.schonherz.y2014.partyappandroid.adapters.GridViewAdapter;
import hu.schonherz.y2014.partyappandroid.adapters.ImageItem;
import hu.schonherz.y2014.partyappandroid.util.datamodell.Club;
import hu.schonherz.y2014.partyappandroid.util.datamodell.Session;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Base64;
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
    private static final int SELECT_PICTURE = 1;
    
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
//	imgs.add  (StringToBitMap (Session.getInstance().getActualCommunicationInterface().loadAnImage(1)) );
	
	
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
    
    @Override
    public void onResume() {
    	// TODO Auto-generated method stub
    	super.onResume();
    	customGridAdapter = new GridViewAdapter(getActivity().getApplicationContext(), R.layout.row_grid, getData());
    	gridView.invalidate();
        gridView.setAdapter(customGridAdapter);
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
    
    public void uploadPicture(){
    	AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
    	builder.setTitle("Válassz!");
    	builder.setNegativeButton("Galéria", new android.content.DialogInterface.OnClickListener() {
    	    @Override
    	    public void onClick(DialogInterface dialog, int which) {
    		Log.e("uploadPicture", "Galéria");
    		
    		Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_PICTURE);
    		
    	    }
    	});
    	builder.setPositiveButton("Kamera", new android.content.DialogInterface.OnClickListener() {

    	    @Override
    	    public void onClick(DialogInterface dialog, int which) {
    		// TODO Auto-generated method stub
    	    	Log.e("uploadPicture", "Kamera");
    	    }
    	});
    	
    	final AlertDialog dialog = builder.create();

    	dialog.show();
    }
    
    
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode ==Activity.RESULT_OK) {
            if (requestCode == SELECT_PICTURE) {
                Uri selectedImageUri = data.getData();
                Bitmap b;
				try {
//					b = MediaStore.Images.Media.getBitmap(getActivity().getApplicationContext().getContentResolver(), selectedImageUri);
					b = decodeUri(selectedImageUri);
					Log.e("kép", "kép hozzáadva");
					
					//ezt kell elküldeni a szervernek
					String picture = BitMapToString(b);
					
					int clubListPosition = ClubActivity.intent.getExtras().getInt("listPosition");
					clubFullDownload(clubListPosition);
					Club actualClub = Session.getSearchViewClubs().get(clubListPosition);
	
					int club_id = actualClub.id;
					
					Session.getInstance().getActualCommunicationInterface().uploadAnImage(club_id, picture);
					
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

            }
        }
    }
    
    public static String BitMapToString(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] b = baos.toByteArray();
        String strBitMap = Base64.encodeToString(b, Base64.DEFAULT);
        return strBitMap;
    }
    
    public static Bitmap StringToBitMap(String input) 
    {
        byte[] decodedByte = Base64.decode(input, 0);
        return BitmapFactory.decodeByteArray(decodedByte, 0, decodedByte.length); 
    }
    
    
    private Bitmap decodeUri(Uri selectedImage) throws FileNotFoundException {
        BitmapFactory.Options o = new BitmapFactory.Options();
        o.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(
                getActivity().getApplicationContext().getContentResolver().openInputStream(selectedImage), null, o);

        final int REQUIRED_SIZE = 300;

        int width_tmp = o.outWidth, height_tmp = o.outHeight;
        int scale = 1;
        while (true) {
            if (width_tmp / 2 < REQUIRED_SIZE || height_tmp / 2 < REQUIRED_SIZE) {
                break;
            }
            width_tmp /= 2;
            height_tmp /= 2;
            scale *= 2;
        }

        BitmapFactory.Options o2 = new BitmapFactory.Options();
        o2.inSampleSize = scale;
        return BitmapFactory.decodeStream(
        		getActivity().getApplicationContext().getContentResolver().openInputStream(selectedImage), null, o2);
    }
    
	protected void clubFullDownload(int actualClubPosition) {
		Club actualCLub = Session.getSearchViewClubs().get(actualClubPosition);
		if (actualCLub.isNotFullDownloaded()) {
			Session.getSearchViewClubs().set(
					actualClubPosition,
					Session.getInstance

					().getActualCommunicationInterface()
							.getEverythingFromClub(actualCLub.id));
		}
	};
    


    }

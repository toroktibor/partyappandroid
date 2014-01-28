package hu.schonherz.y2014.partyappandroid.activities;

import hu.schonherz.y2014.partyappandroid.ImageUtils;
import hu.schonherz.y2014.partyappandroid.R;
import hu.schonherz.y2014.partyappandroid.adapters.GridViewAdapter;
import hu.schonherz.y2014.partyappandroid.adapters.ImageItem;
import hu.schonherz.y2014.partyappandroid.util.datamodell.Club;
import hu.schonherz.y2014.partyappandroid.util.datamodell.GaleryImage;
import hu.schonherz.y2014.partyappandroid.util.datamodell.Session;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Bitmap.CompressFormat;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
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
    private static final int SELECT_PICTURE = 1;
    private static final int TAKE_PICTURE = 2;
    ArrayList<Integer> lista;
    public static Club actualClub;
    private String mCurrentPhotoPath;

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

	// TODO:képek lekérése a szervertől
	// imgs.add (StringToBitMap
	// (Session.getInstance().getActualCommunicationInterface().DownLoadAnImage(4))
	// );

	int clubListPosition = ClubActivity.intent.getExtras().getInt("listPosition");
	actualClub = Session.getSearchViewClubs().get(clubListPosition);

	// int club_id = actualClub.id;
	/*
	 * lista = Session.getInstance().getActualCommunicationInterface().
	 * selectClubsImagesIds(club_id); for (int i = 0; i < lista.size();
	 * i++){ Log.e("galery", "i: "+i); imgs.add(StringToBitMap
	 * (Session.getInstance
	 * ().getActualCommunicationInterface().DownLoadAnImage(lista.get(i)))
	 * ); }
	 */

	gridView = new GridView(mContext);
	gridView = (GridView) rootView.findViewById(R.id.gridView);
	customGridAdapter = new GridViewAdapter(getActivity().getApplicationContext(), R.layout.row_grid,
		getDataForGridView());
	gridView.setAdapter(customGridAdapter);

	/**
	 * On Click event for Single Gridview Item
	 * */
	gridView.setOnItemClickListener(new OnItemClickListener() {
	    @Override
	    public void onItemClick(AdapterView<?> parent, View v, final int position, long id) {
	
		Intent in1 = new Intent(getActivity(), FullImageActivity.class);
		in1.putExtra("imageid", position);
		startActivity(in1);
		getActivity().overridePendingTransition(R.anim.slide_left_in, R.anim.slide_left_out);
					

	    }
	});
	return rootView;
    }

    @Override
    public void onResume() {
	// TODO Auto-generated method stub
	super.onResume();
	customGridAdapter = new GridViewAdapter(getActivity().getApplicationContext(), R.layout.row_grid,
		getDataForGridView());
	gridView.setAdapter(customGridAdapter);
	gridView.invalidate();
    }

    private ArrayList getDataForGridView() {
	final ArrayList imageItems = new ArrayList();
	// retrieve String drawable array
	// TypedArray imgs = getResources().obtainTypedArray(R.array.image_ids);

	/*
	 * for (int i = 0; i < imgs.size(); i++) { imageItems.add(new
	 * ImageItem(imgs.get(i), "Image#" + i)); Log.e("getData",
	 * "i: "+i+imgs.get(i)); }
	 */

	for (int i = 0; i < actualClub.images.size(); i++) {
	    imageItems.add(new ImageItem(actualClub.images.get(i).getBitmap_thumbnail(), "Image#" + i));
	    Log.e("getData", "i: " + i + ", " + imageItems.get(i));
	}

	return imageItems;

    }

    public void uploadPicture() {
	AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
	builder.setTitle("Válassz!");
	builder.setNegativeButton("Galéria", new android.content.DialogInterface.OnClickListener() {
	    @Override
	    public void onClick(DialogInterface dialog, int which) {
		Log.e("uploadPicture", "Galéria");

		Intent intent = new Intent();
		intent.setType("image/*");
		intent.setAction(Intent.ACTION_GET_CONTENT);
		startActivityForResult(Intent.createChooser(intent, ""), SELECT_PICTURE);

	    }
	});
	builder.setPositiveButton("Kamera", new android.content.DialogInterface.OnClickListener() {

	    @Override
	    public void onClick(DialogInterface dialog, int which) {
		// TODO Auto-generated method stub
		Log.e("uploadPicture", "Kamera");
		/*Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		startActivityForResult(Intent.createChooser(intent, "Képfeltöltés"), TAKE_PICTURE);*/
		
		    Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		    // Ensure that there's a camera activity to handle the intent
		    if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
		        // Create the File where the photo should go
		        File photoFile = null;
		        try {
		            photoFile = createImageFile();
		        } catch (IOException ex) {
		            Log.e("asdasd","createImageFile hiba van",ex);
		        }
		        // Continue only if the File was successfully created
		        if (photoFile != null) {
		            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,
		                    Uri.fromFile(photoFile));
		            startActivityForResult(Intent.createChooser(takePictureIntent, "Képfeltöltés"), TAKE_PICTURE);
		        }
		    }
		
	    }
	});

	final AlertDialog dialog = builder.create();

	dialog.show();
    }

    private File createImageFile() throws IOException {
	    // Create an image file name
	    String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
	    
	    String imageFileName = "JPEG_" + timeStamp + "_";
	    File storageDir = Environment.getExternalStoragePublicDirectory(
	            Environment.DIRECTORY_PICTURES);
	    Log.d("asdasd", imageFileName+"; "+".jpg; "+storageDir);
	    /*File image = File.createTempFile(
	        imageFileName,  
	        ".jpg",         
	        storageDir    
	    );*/
	    File image = new File(Environment.getExternalStorageDirectory(),  imageFileName+".jpg");

	    mCurrentPhotoPath = image.getAbsolutePath();
	    return image;
	}
    
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
	if (requestCode == SELECT_PICTURE && resultCode == Activity.RESULT_OK && data != null) {
	    Uri selectedImageUri = data.getData();
	    try {

		final Bitmap b = decodeUri(selectedImageUri);
		//b = resizeBitmap(b);
		Log.e("kép", "galéria kép hozzáadva");
		Log.i("asdasd","A galériából feltöltendő kép mérete: "+b.getWidth()+"x"+b.getHeight());

		// ezt kell elküldeni a szervernek
		final String picture = ImageUtils.BitMapToString(b);

		Session.getInstance().progressDialog=ProgressDialog.show(getActivity(), "Kérlek várj", "Kép feltöltése...", true, false);
		new Thread(new Runnable() {
		    
		    @Override
		    public void run() {
			// ezt kell elküldeni a szervernek
			final String picture = ImageUtils.BitMapToString(b);
			
			final int newID = Session.getInstance().getActualCommunicationInterface()
				.uploadAnImage(ClubGaleryFragment.actualClub.id, picture);
			
			ClubGaleryFragment.actualClub.images.add(new GaleryImage(newID, 
				ImageUtils.StringToBitMap( Session.getInstance().getActualCommunicationInterface().DownLoadAnImageThumbnail(newID) )
				));
			
			getActivity().runOnUiThread(new Runnable() {
			    
			    @Override
			    public void run() {
				customGridAdapter = new GridViewAdapter(getActivity().getApplicationContext(), R.layout.row_grid,
					getDataForGridView());
				gridView.setAdapter(customGridAdapter);
				gridView.invalidate();
				Session.getInstance().dismissProgressDialog();	
				
			    }
			});
			
		    }
		}).start();
		

	    } catch (FileNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	    } catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	    }

	} else if (requestCode == TAKE_PICTURE && resultCode == Activity.RESULT_OK ) {
	    //Uri selectedImageUri = data.getData();
	    
	    try {

		
		final Bitmap b = decodeUri( Uri.fromFile(new File(mCurrentPhotoPath)) );
		//b = (Bitmap) data.getExtras().get("data");
		//b = resizeBitmap(b);
		Log.e("kép", "kamera kép hozzáadva");
		Log.i("asdasd","A kamerából feltöltendő kép merete: "+b.getWidth()+"x"+b.getHeight());

		Session.getInstance().progressDialog=ProgressDialog.show(getActivity(), "Kérlek várj", "Kép feltöltése...", true, false);
		new Thread(new Runnable() {
		    
		    @Override
		    public void run() {
			// ezt kell elküldeni a szervernek
			final String picture = ImageUtils.BitMapToString(b);
			
			final int newID = Session.getInstance().getActualCommunicationInterface()
				.uploadAnImage(ClubGaleryFragment.actualClub.id, picture);
			
			ClubGaleryFragment.actualClub.images.add(new GaleryImage(newID, 
				ImageUtils.StringToBitMap( Session.getInstance().getActualCommunicationInterface().DownLoadAnImageThumbnail(newID) )
				));
			
			getActivity().runOnUiThread(new Runnable() {
			    
			    @Override
			    public void run() {
				customGridAdapter = new GridViewAdapter(getActivity().getApplicationContext(), R.layout.row_grid,
					getDataForGridView());
				gridView.setAdapter(customGridAdapter);
				gridView.invalidate();
				Session.getInstance().dismissProgressDialog();	
				
			    }
			});
			
		    }
		}).start();


	    } catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	    }

	}

    }

    private Bitmap decodeUri(Uri selectedImage) throws FileNotFoundException {
	Log.i(getClass().getName(), "Uri dekódolása");
	BitmapFactory.Options o = new BitmapFactory.Options();
	
	o.inJustDecodeBounds = true;
	BitmapFactory.decodeStream(
		getActivity().getApplicationContext().getContentResolver().openInputStream(selectedImage), null, o);
		
	Log.i("asdasd","Eredeti méret: "+o.outWidth+"x"+o.outHeight);
		
	o.inSampleSize = 1;
	while( o.outHeight * o.outWidth / (o.inSampleSize*4) > 1000000 ){
	    o.inSampleSize*=2;
	}	
	
	Log.i("asdasd","Skálázás szükséges: "+o.inSampleSize);
	o.inJustDecodeBounds = false;
	
	Bitmap b = BitmapFactory.decodeStream(getActivity().getApplicationContext().getContentResolver().openInputStream(selectedImage),null,o);
	
	return b;

    }

    protected void clubFullDownload(int actualClubPosition) {
	Club actualCLub = Session.getSearchViewClubs().get(actualClubPosition);
	if (actualCLub.isNotFullDownloaded()) {
	    Session.getSearchViewClubs().set(actualClubPosition, Session.getInstance

	    ().getActualCommunicationInterface().getEverythingFromClub(actualCLub.id));
	}
    };

}

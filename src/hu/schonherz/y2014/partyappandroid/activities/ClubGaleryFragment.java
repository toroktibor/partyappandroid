package hu.schonherz.y2014.partyappandroid.activities;

import hu.schonherz.y2014.partyappandroid.ImageUtils;
import hu.schonherz.y2014.partyappandroid.R;
import hu.schonherz.y2014.partyappandroid.adapters.GridViewAdapter;
import hu.schonherz.y2014.partyappandroid.adapters.ImageItem;
import hu.schonherz.y2014.partyappandroid.util.datamodell.Club;
import hu.schonherz.y2014.partyappandroid.util.datamodell.GaleryImage;
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
	    public void onItemClick(AdapterView<?> parent, View v, int position, long id) {

		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		actualClub.images.get(position).getBitmap().compress(Bitmap.CompressFormat.JPEG, 100, stream);
		byte[] byteArray = stream.toByteArray();

		Intent in1 = new Intent(getActivity(), FullImageActivity.class);
		in1.putExtra("image", byteArray);
		startActivity(in1);
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
	    imageItems.add(new ImageItem(actualClub.images.get(i).getBitmap(), "Image#" + i));
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
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		startActivityForResult(Intent.createChooser(intent, "Képfeltöltés"), TAKE_PICTURE);
	    }
	});

	final AlertDialog dialog = builder.create();

	dialog.show();
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
	if (requestCode == SELECT_PICTURE && resultCode == Activity.RESULT_OK && data != null) {
	    Uri selectedImageUri = data.getData();
	    Bitmap b;
	    try {
		// b =
		// MediaStore.Images.Media.getBitmap(getActivity().getApplicationContext().getContentResolver(),
		// selectedImageUri);
		b = decodeUri(selectedImageUri);
		Log.e("kép", "kép hozzáadva");

		// ezt kell elküldeni a szervernek
		String picture = ImageUtils.BitMapToString(b);

		// int clubListPosition =
		// ClubActivity.intent.getExtras().getInt("listPosition");
		// clubFullDownload(clubListPosition);
		// Club actualClub =
		// Session.getSearchViewClubs().get(clubListPosition);

		// int club_id = actualClub.id;
		ClubGaleryFragment.actualClub.images.add(new GaleryImage(999, b));

		Session.getInstance().getActualCommunicationInterface()
			.uploadAnImage(ClubGaleryFragment.actualClub.id, picture);

	    } catch (FileNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	    } catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	    }

	} else if (requestCode == TAKE_PICTURE && resultCode == Activity.RESULT_OK && data != null) {
	    Uri selectedImageUri = data.getData();
	    Bitmap b;
	    try {
		// b =
		// MediaStore.Images.Media.getBitmap(getActivity().getApplicationContext().getContentResolver(),
		// selectedImageUri);
		b = decodeUri(selectedImageUri);
		Log.e("kép", "kép hozzáadva");

		// ezt kell elküldeni a szervernek
		String picture = ImageUtils.BitMapToString(b);

		// int clubListPosition =
		// ClubActivity.intent.getExtras().getInt("listPosition");
		// clubFullDownload(clubListPosition);
		// Club actualClub =
		// Session.getSearchViewClubs().get(clubListPosition);

		// int club_id = actualClub.id;
		ClubGaleryFragment.actualClub.images.add(new GaleryImage(999, b));

		Session.getInstance().getActualCommunicationInterface()
			.uploadAnImage(ClubGaleryFragment.actualClub.id, picture);

	    } catch (FileNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	    } catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	    }

	}

    }

    private Bitmap decodeUri(Uri selectedImage) throws FileNotFoundException {
	Log.i(getClass().getName(), "Kiválasztott kép elkészítése Bitmapként");
	BitmapFactory.Options o = new BitmapFactory.Options();
	o.inJustDecodeBounds = true;
	BitmapFactory.decodeStream(
		getActivity().getApplicationContext().getContentResolver().openInputStream(selectedImage), null, o);

	final float TARGETMEGAPIXELS = 1;

	BitmapFactory.Options o2 = new BitmapFactory.Options();

	int currentpixels = o.outWidth * o.outHeight;
	float ratio = TARGETMEGAPIXELS * 1000000 / currentpixels;

	if (ratio < 1) { // ha kicsinyíteni kell
	    Log.i(getClass().getName(), "Kicsinyíteni kell a képet");

	    int width_tmp = o.outWidth, height_tmp = o.outHeight;
	    int width_dest = Math.round(ratio * width_tmp), height_dest = Math.round(ratio * o.outHeight);
	    int scale = 1;
	    while (currentpixels / scale > TARGETMEGAPIXELS * 1000000) {
		scale *= 2;
	    }
	    o2.inSampleSize = scale;

	    Bitmap b = BitmapFactory.decodeStream(getActivity().getApplicationContext().getContentResolver()
		    .openInputStream(selectedImage), null, o2);

	    return Bitmap.createScaledBitmap(b, width_dest, height_dest, true);

	} else {
	    Log.i(getClass().getName(), "A kép mérete nem lépi át a limitet");
	    return BitmapFactory.decodeStream(getActivity().getApplicationContext().getContentResolver()
		    .openInputStream(selectedImage), null, o2);
	}

    }

    protected void clubFullDownload(int actualClubPosition) {
	Club actualCLub = Session.getSearchViewClubs().get(actualClubPosition);
	if (actualCLub.isNotFullDownloaded()) {
	    Session.getSearchViewClubs().set(actualClubPosition, Session.getInstance

	    ().getActualCommunicationInterface().getEverythingFromClub(actualCLub.id));
	}
    };

}

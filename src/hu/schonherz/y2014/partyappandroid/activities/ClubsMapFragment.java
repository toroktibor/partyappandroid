package hu.schonherz.y2014.partyappandroid.activities;

import hu.schonherz.y2014.partyappandroid.R;
import hu.schonherz.y2014.partyappandroid.util.datamodell.Club;
import hu.schonherz.y2014.partyappandroid.util.datamodell.Session;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class ClubsMapFragment extends Fragment implements
		ClubsUpdateableFragment, OnInfoWindowClickListener {

	private GoogleMap googleMap;
	private static View view;
	List<MarkerOptions> markerList = new ArrayList<MarkerOptions>();

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if (view != null) {
			ViewGroup parent = (ViewGroup) view.getParent();
			if (parent != null)
				parent.removeView(view);
		}
		try {
			view = inflater.inflate(R.layout.fragment_clubs_map, container,
					false);
		} catch (InflateException e) {
			/* map is already there, just return view as it is */
		}
		initilizeMap();
		updateResults();

		//googleMap.setOnInfoWindowClickListener(this);

		return view;
	}

	@Override
	public void updateResults() {
		Log.i("MAP", "REFRESH RESULTS ON THE MAP");
		if (googleMap != null) {
			if (Session.getActualUser().getType() == 0) {
				Log.e("MAP", "ACTUAL USER IS A GUEST");
				showOnlyApprovedPlacesOnTheMap();
				Log.e("MAP", "APPROVED PLACES SHOWN");
			} else if (Session.getActualUser().getType() == 1) {
				Log.e("MAP", "ACTUAL USER IS AN ADMIN");
				showEveryPlacesOnTheMap();
				Log.e("MAP", "EVERY PLACES SHOWN");
			}
		}
	}

	private void initilizeMap() {
		if (googleMap == null) {
			FragmentManager fragmentManager = getFragmentManager();
			googleMap = ((SupportMapFragment) fragmentManager
					.findFragmentById(R.id.mapFragment)).getMap();
			googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
			googleMap.setMyLocationEnabled(true);
			// check if map is created successfully or not
			if (googleMap == null) {
				Toast.makeText(getActivity(), "Sorry! Unable to create maps",
						Toast.LENGTH_SHORT).show();
			}
		}
	}

	private void showOnlyApprovedPlacesOnTheMap() {
		BitmapDescriptor bmd = BitmapDescriptorFactory
				.defaultMarker(BitmapDescriptorFactory.HUE_VIOLET);
		List<Address> addressList = new ArrayList<Address>();
		Geocoder geocoder = new Geocoder(getActivity(), Locale.getDefault());
		LatLng actualClubsLatLng;

		for (int i = 0; i < Session.getSearchViewClubs().size(); ++i) {
			Club actualClub = Session.getSearchViewClubs().get(i);
			try {
				Log.e("MAP", actualClub.address);
				addressList = geocoder.getFromLocationName(actualClub.address,
						1);
				// Log.e("MAP LATITUDE", ((Double)
				// (addressList.get(0).getLatitude())).toString());
				// Log.e("MAP ADDRESSLIST: ",
				// geocoder.getFromLocationName(actualClub.address,
				// 1).get(0).toString());
			} catch (IOException e) {
				e.printStackTrace();
			}
			Log.e("MAP", "ADDRESSLIST.SIZE=" + addressList.size());
			if (addressList.size() > 0) {
				actualClubsLatLng = new LatLng(
						addressList.get(0).getLatitude(), addressList.get(0)
								.getLongitude());
				Log.e("MAP", "ACTUALCLUB APPROVED=" + actualClub.approved);
				markerList.add(new MarkerOptions().position(actualClubsLatLng)
						.title(actualClub.name).snippet(actualClub.address)
						.icon(bmd));

				Log.e("MAP", "NEW CLUB APPEARED ON THE MAP");
			} else {
				Log.e("MAP", "SIZE OF ADDRESSLIST IS 0");
			}
		}

		Log.e("MAP", "MARKERLIST SIZE=" + markerList.size());
		if (markerList.size() > 0) {
			LatLngBounds.Builder builder = new LatLngBounds.Builder();
			for (int i = 0; i < markerList.size(); ++i) {
				googleMap.addMarker(markerList.get(i));
				builder.include(markerList.get(i).getPosition());
			}
			LatLngBounds bounds = builder.build();
			int padding = 0; // offset from edges of the map in pixels
			CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds,
					padding);
			// googleMap.moveCamera(cu);
		}
		return;
	}

	private void showEveryPlacesOnTheMap() {
		BitmapDescriptor bmd;
		List<Address> addressList = new ArrayList<Address>();
		Geocoder geocoder = new Geocoder(getActivity());
		LatLng actualClubsLatLng;
		for (Club actualClub : Session.getSearchViewClubs()) {
			try {
				addressList = geocoder.getFromLocationName(actualClub.address,
						1);
			} catch (IOException e) {
				e.printStackTrace();
			}
			actualClubsLatLng = new LatLng(addressList.get(0).getLatitude(),
					addressList.get(0).getLongitude());
			if (actualClub.approved == 0)
				bmd = BitmapDescriptorFactory
						.defaultMarker(BitmapDescriptorFactory.HUE_RED);
			else
				bmd = BitmapDescriptorFactory
						.defaultMarker(BitmapDescriptorFactory.HUE_GREEN);

			googleMap.addMarker(new MarkerOptions().position(actualClubsLatLng)
					.title(actualClub.name).snippet(actualClub.address)
					.icon(bmd));
		}
		return;
	}

	@Override
	public void onResume() {
		super.onResume();
		/*
		 * initilizeMap(); updateResults();
		 */
	}

	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		/*
		 * initilizeMap(); updateResults();
		 */
	}

	@Override
	public void onInfoWindowClick(Marker arg0) {
		final Activity activity = getActivity();
		final Intent i = new Intent(activity, ClubActivity.class);
		final Club actualClub = Session.getSearchViewClubs().get(markerList.indexOf(arg0));
		i.putExtra("listPosition", markerList.indexOf(arg0));
		if (actualClub.isNotFullDownloaded()) {

			Session.getInstance().progressDialog = ProgressDialog
					.show(activity, "Kérlek várj", "Adatok betöltése...", true,
							false);
			new Thread(new Runnable() {

				@Override
				public void run() {
					actualClub.downloadEverything();
					activity.runOnUiThread(new Runnable() {

						@Override
						public void run() {
							activity.startActivity(i);
							activity.overridePendingTransition(
									R.anim.slide_left_in, R.anim.slide_left_out);
							Session.getInstance().dismissProgressDialog();
						}
					});
				}
			}).start();
		} else {
			activity.startActivity(i);
			activity.overridePendingTransition(R.anim.slide_left_in,
					R.anim.slide_left_out);
		}
	}


	
	/*
	 * @Override public void onDestroyView() { super.onDestroyView();
	 * SupportMapFragment f = (SupportMapFragment) getFragmentManager()
	 * .findFragmentById(R.id.mapFragment); if (f != null)
	 * getFragmentManager().beginTransaction().remove(f).commit(); }
	 */
}

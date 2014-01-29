package hu.schonherz.y2014.partyappandroid.activities;

import hu.schonherz.y2014.partyappandroid.R;
import hu.schonherz.y2014.partyappandroid.util.datamodell.Club;
import hu.schonherz.y2014.partyappandroid.util.datamodell.Session;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnCameraChangeListener;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class ClubsMapFragment extends Fragment implements ClubsUpdateableFragment {

	private GoogleMap googleMap;
	private static View view;
	private List<MarkerOptions> markerList = new ArrayList<MarkerOptions>();

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		Log.e("MAP", "ONCREATEVIEW RUNS");
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
		googleMap.setOnInfoWindowClickListener(new OnInfoWindowClickListener() {

			@Override
			public void onInfoWindowClick(Marker arg0) {

				final Activity activity = getActivity();
				final Intent i = new Intent(activity, ClubActivity.class);
				int listPosition = giveIndexOfMarkerFromSearchViewClubs(arg0);
				//Log.e("MAP", "INDEX=" + listPosition);
				if (listPosition > -1) {
					//Log.e("MAP", "YES, MARKERLIST CONTAINS THE CLICKED MARKER");
					i.putExtra("listPosition", listPosition);
					final Club actualClub = Session.getSearchViewClubs().get(
							listPosition);

					if (actualClub.isNotFullDownloaded()) {

						Session.getInstance().progressDialog = ProgressDialog
								.show(activity, "Kérlek várj",
										"Adatok betöltése...", true, false);
						new Thread(new Runnable() {

							@Override
							public void run() {
								actualClub.downloadEverything();
								activity.runOnUiThread(new Runnable() {

									@Override
									public void run() {
										activity.startActivity(i);
										activity.overridePendingTransition(
												R.anim.slide_left_in,
												R.anim.slide_left_out);
										Session.getInstance()
												.dismissProgressDialog();
									}
								});
							}
						}).start();

					} else {
						activity.startActivity(i);
						activity.overridePendingTransition(
								R.anim.slide_left_in, R.anim.slide_left_out);
					}
					return;
				}
				else {
					Toast.makeText(activity, "Sajnáljuk! Nem sikerült megnyitni a hely információs lapját.", Toast.LENGTH_LONG).show();
				}
			}
		});
		
		//Egyik lehetséges megoldás.
		googleMap.setOnCameraChangeListener(new OnCameraChangeListener() {
			
			@Override
			public void onCameraChange(CameraPosition arg0) {
				int padding = 60; // offset from edges of the map in pixels
            	//Set the camera position to the bounds - calculated from the markers of Markerlist.
                CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(giveBoundsForMarkerlist(), padding);
                googleMap.animateCamera(cu);
                googleMap.setOnCameraChangeListener(null);
				
			}
		});	
		/*
		//Másik lehetséges megoldás.
		
		try {
	        this.googleMap.moveCamera(CameraUpdateFactory.newLatLngBounds(giveBoundsForMarkerlist(), 0));
	    } catch (IllegalStateException e) {
	        // layout not yet initialized
	        final View mapView = getFragmentManager().findFragmentById(R.id.mapFragment).getView();
	        if (mapView.getViewTreeObserver().isAlive()) {
	            mapView.getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener() {

	                @SuppressWarnings("deprecation")
	                @SuppressLint("NewApi")
	                // We check which build version we are using.
	                @Override
	                public void onGlobalLayout() {
	                    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
	                        mapView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
	                    } else {
	                        mapView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
	                    }
	                    googleMap.animateCamera(CameraUpdateFactory.newLatLngBounds(giveBoundsForMarkerlist(), 0));
	                }
	            });
	        }
	    }
		*/
		return view;
	}

	private int giveIndexOfMarkerFromSearchViewClubs(Marker marker) {
		List<Club> actList = Session.getSearchViewClubs();
		Club actClub;
		for (int i = 0; i < actList.size(); ++i) {
			actClub = actList.get(i);
			//Log.e("COMPARE ADDRESS LIST", 	"#" + actClub.address.toString()	+ "#");
			//Log.e("COMPARE ADDRESS MARK", 	"#" + marker.getSnippet().toString()+ "#");
			//Log.e("COMPARE NAME LIST", 		"#" + actClub.name.toString() 		+ "#");
			//Log.e("COMPARE NAME MARK", 		"#" + marker.getTitle().toString() 	+ "#" );
			if ((actClub.name.toString()).equals(marker.getTitle().toString()) &&
				(actClub.address.toString()).equals(marker.getSnippet().toString()))
				return i;
		}
		return -1;
	}

	public void makeMarkersFromSearchViewClubs() {
		//Lila pöttyök a clubokhoz.
		BitmapDescriptor bmd = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_VIOLET);
				
		//Minden klubhoz a searchViewClubs-ból csinálunk egy markert a térképre, ezt a  listába tesszük.
		for (Club actualClub : Session.getSearchViewClubs()) {
				markerList.add(new MarkerOptions()
						.position(actualClub.position)
						.title(actualClub.name)
						.snippet(actualClub.address)
						.icon(bmd));
				//Log.e("MAP", "NEW MARKER IN THE MARKERLIST");
			}
		return;
	}
	
	@Override
	public void updateResults() {
		//Log.i("MAP", "REFRESH RESULTS ON THE MAP");
		//Make markers from the searchViewClubs and set it to the MarkerList list.
		makeMarkersFromSearchViewClubs();
		//Adding the markers to the googleMap.
		if (googleMap != null) {
			for (MarkerOptions actMarker : markerList) {
				googleMap.addMarker(actMarker);
			}
		}
		//Log.e("MAP", "APPROVED PLACES SHOWN");
	}

	private LatLngBounds giveBoundsForMarkerlist() {
		LatLngBounds resultBounds;
		LatLngBounds.Builder builder = new LatLngBounds.Builder();
		if (markerList.size() > 0) {
			for (MarkerOptions actMarker : markerList) {
				builder.include(actMarker.getPosition());
			}
		}
		resultBounds = builder.build();
		return resultBounds;
	}
	
	private void initilizeMap() {
		Log.e("MAP", "INITIALIZE");
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
	/*
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
				Log.e("MAP LATITUDE", ((Double) (addressList.get(0)
						.getLatitude())).toString());
				// Log.e("MAP ADDRESSLIST: ",
				// geocoder.getFromLocationName(actualClub.address,
				// 1).get(0).toString());
			} catch (IOException e) {
				e.printStackTrace();
			}
			if (addressList.size() > 0) {
				actualClubsLatLng = new LatLng(
						addressList.get(0).getLatitude(), addressList.get(0)
								.getLongitude());
				if (actualClub.approved == 1) {
					markerList.add(new MarkerOptions()
							.position(actualClubsLatLng).title(actualClub.name)
							.snippet(actualClub.address).icon(bmd));
					googleMap.addMarker(markerList.get(markerList.size() - 1));
					Log.e("MAP", "NEW CLUB APPEARED ON THE MAP");
				}
			} else {
				Log.e("MAP", "SIZE OF ADDRESSLIST IS 0");
			}
		}

		if (markerList.size() > 0) {
			LatLngBounds.Builder builder = new LatLngBounds.Builder();
			for (int i = 0; i < markerList.size(); ++i) {
				builder.include(markerList.get(i).getPosition());
			}
			LatLngBounds bounds = builder.build();
			int padding = 0; // offset from edges of the map in pixels
			CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds,
					padding);
			googleMap.animateCamera(cu);
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
	*/
	
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

	/*
	 * @Override public void onDestroyView() { super.onDestroyView();
	 * SupportMapFragment f = (SupportMapFragment) getFragmentManager()
	 * .findFragmentById(R.id.mapFragment); if (f != null)
	 * getFragmentManager().beginTransaction().remove(f).commit(); }
	 */
}

package hu.schonherz.y2014.partyappandroid.activities;

import hu.schonherz.y2014.partyappandroid.NetThread;
import hu.schonherz.y2014.partyappandroid.R;
import hu.schonherz.y2014.partyappandroid.util.datamodell.Club;
import hu.schonherz.y2014.partyappandroid.util.datamodell.Session;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
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
    public static boolean cameraOK = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
	cameraOK = false;
	Log.e("MAP", "ONCREATEVIEW RUNS");
	if (view != null) {
	    ViewGroup parent = (ViewGroup) view.getParent();
	    if (parent != null)
		parent.removeView(view);
	}

	try {
	    view = inflater.inflate(R.layout.fragment_clubs_map, container, false);
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
		// Log.e("MAP", "INDEX=" + listPosition);
		if (listPosition > -1) {
		    // Log.e("MAP",
		    // "YES, MARKERLIST CONTAINS THE CLICKED MARKER");
		    i.putExtra("listPosition", listPosition);
		    final Club actualClub = Session.getSearchViewClubs().get(listPosition);

		    if (actualClub.isNotFullDownloaded()) {

			Session.getInstance().progressDialog = ProgressDialog.show(activity, "Kérlek várj",
				"Adatok betöltése...", true, false);
			new NetThread(getActivity(),new Runnable() {

			    @Override
			    public void run() {
				actualClub.downloadEverything();
				activity.runOnUiThread(new Runnable() {

				    @Override
				    public void run() {
					activity.startActivity(i);
					activity.overridePendingTransition(R.anim.slide_left_in, R.anim.slide_left_out);
					Session.getInstance().dismissProgressDialog();
					if (Session.getInstance().oldPhone) {
					    getActivity().finish();
					}
				    }
				});
			    }
			}).start();

		    } else {
			activity.startActivity(i);
			activity.overridePendingTransition(R.anim.slide_left_in, R.anim.slide_left_out);
			if (Session.getInstance().oldPhone) {
			    getActivity().finish();
			}
		    }
		    return;
		} else {
		    Toast.makeText(activity, "Sajnáljuk! Nem sikerült megnyitni a hely információs lapját.",
			    Toast.LENGTH_LONG).show();
		}
	    }
	});

	// Egyik lehetséges megoldás.
	googleMap.setOnCameraChangeListener(new OnCameraChangeListener() {

	    @Override
	    public void onCameraChange(CameraPosition arg0) {

		Log.i("asdasd", "setOnCameraChangeListener");

		if (markerList.size() == 1) {
		    CameraUpdate cu = CameraUpdateFactory.newLatLngZoom(markerList.get(0).getPosition(), 15f);
		    googleMap.animateCamera(cu);
		} else {
		    CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(giveBoundsForMarkerlist(), 60);
		    googleMap.animateCamera(cu);
		}

		cameraOK = true;
		googleMap.setOnCameraChangeListener(null);

	    }
	});
	return view;
    }

    private int giveIndexOfMarkerFromSearchViewClubs(Marker marker) {
	List<Club> actList = Session.getSearchViewClubs();
	Club actClub;
	for (int i = 0; i < actList.size(); ++i) {
	    actClub = actList.get(i);
	    // Log.e("COMPARE ADDRESS LIST", "#" + actClub.address.toString() +
	    // "#");
	    // Log.e("COMPARE ADDRESS MARK", "#" +
	    // marker.getSnippet().toString()+ "#");
	    // Log.e("COMPARE NAME LIST", "#" + actClub.name.toString() + "#");
	    // Log.e("COMPARE NAME MARK", "#" + marker.getTitle().toString() +
	    // "#" );
	    if ((actClub.name.toString()).equals(marker.getTitle().toString())
		    && (actClub.address.toString()).equals(marker.getSnippet().toString()))
		return i;
	}
	return -1;
    }

    public void makeMarkersFromSearchViewClubs() {
	// Lila pöttyök a clubokhoz.
	markerList.clear();
	BitmapDescriptor bmd = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_VIOLET);

	// Minden klubhoz a searchViewClubs-ból csinálunk egy markert a
	// térképre, ezt a listába tesszük.
	for (Club actualClub : Session.getSearchViewClubs()) {
	    if (actualClub.position != null)
		markerList.add(new MarkerOptions().position(actualClub.position).title(actualClub.name)
			.snippet(actualClub.address).icon(bmd));
	    // Log.e("MAP", "NEW MARKER IN THE MARKERLIST");
	}
	Log.i("asdasd", "Markerlist mérete: " + markerList.size());
    }

    @Override
    public void onAttach(Activity activity) {
	// TODO Auto-generated method stub
	super.onAttach(activity);
	Log.i("asdasd", "Map hozzáadása a fragmentekhez");
	((ClubsActivity) activity).fragments[1] = this;
    }

    @Override
    public void updateResults() {
	// Log.i("MAP", "REFRESH RESULTS ON THE MAP");
	// Make markers from the searchViewClubs and set it to the MarkerList
	// list.
	if (getActivity() instanceof ClubActivity) {
	    makeMarkerFromActualClubPosition();
	} else if (getActivity() instanceof ClubsActivity) {
	    makeMarkersFromSearchViewClubs();
	} // Adding the markers to the googleMap.
	if (googleMap != null) {
	    googleMap.clear();
	    for (MarkerOptions actMarker : markerList) {
		googleMap.addMarker(actMarker);
	    }
	}

	if (cameraOK) {
	    Log.i("asdasd", "cameraOK fut");

	    if (markerList.size() == 1) {
		CameraUpdate cu = CameraUpdateFactory.newLatLngZoom(markerList.get(0).getPosition(), 15f);
		googleMap.animateCamera(cu);
	    } else {
		CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(giveBoundsForMarkerlist(), 60);
		googleMap.animateCamera(cu);
	    }
	}

    }

    private void makeMarkerFromActualClubPosition() {
	Club actClub = ((ClubActivity) getActivity()).actualClub;
	markerList.add(new MarkerOptions().title(actClub.name).snippet(actClub.address).position(actClub.position))

	;

    }

    private LatLngBounds giveBoundsForMarkerlist() {
	LatLngBounds resultBounds;
	LatLngBounds.Builder builder = new LatLngBounds.Builder();
	if (markerList.size() > 0) {
	    Log.i("asdasd", "Térkép pozíciók");
	    for (MarkerOptions actMarker : markerList) {
		builder.include(actMarker.getPosition());
		Log.i("asdasd", actMarker.getPosition().latitude + "," + actMarker.getPosition().longitude);
	    }
	} else {
	    Log.i("asdasd", "Default pozíció");

	    builder.include(new LatLng(47.5280147, 21.6217615));

	}
	resultBounds = builder.build();
	return resultBounds;
    }

    private void initilizeMap() {
	Log.e("MAP", "INITIALIZE");
	if (googleMap == null) {
	    FragmentManager fragmentManager = getFragmentManager();
	    googleMap = ((SupportMapFragment) fragmentManager.findFragmentById(R.id.mapFragment)).getMap();
	    googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
	    googleMap.setMyLocationEnabled(true);
	    // check if map is created successfully or not
	    if (googleMap == null) {
		Toast.makeText(getActivity(), "Sorry! Unable to create maps", Toast.LENGTH_SHORT).show();
	    }
	}
    }

    @Override
    public void onResume() {
	super.onResume();
	Log.i("asdasd", "ClubsMapFragment.onResume()");
    }

    /*
     * @Override public void onDestroyView() { super.onDestroyView();
     * SupportMapFragment f = (SupportMapFragment) getFragmentManager()
     * .findFragmentById(R.id.mapFragment); if (f != null)
     * getFragmentManager().beginTransaction().remove(f).commit(); }
     */
}

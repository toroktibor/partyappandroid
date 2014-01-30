package hu.schonherz.y2014.partyappandroid.activities;

import java.util.ArrayList;
import java.util.List;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.GoogleMap.OnCameraChangeListener;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import hu.schonherz.y2014.partyappandroid.R;
import hu.schonherz.y2014.partyappandroid.SimpleActionBar;
import hu.schonherz.y2014.partyappandroid.util.datamodell.Club;
import hu.schonherz.y2014.partyappandroid.util.datamodell.Session;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

public class ClubLocationActivity extends ActionBarActivity {

    private GoogleMap googleMap;
    public static Club actualClub;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
	// TODO Auto-generated method stub
	super.onCreate(savedInstanceState);

	new SimpleActionBar(this, "Itt találod a helyet").setLayout();

	setContentView(R.layout.activity_club_location);

	initilizeMap();
	
	BitmapDescriptor bmd = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_VIOLET);
	

	
	Marker m = googleMap.addMarker( new MarkerOptions().position(actualClub.position).title(actualClub.name)
		.snippet(actualClub.address).icon(bmd)
		);
	
	m.showInfoWindow();
	
	

	// Egyik lehetséges megoldás.
	googleMap.setOnCameraChangeListener(new OnCameraChangeListener() {

	    @Override
	    public void onCameraChange(CameraPosition arg0) {

		CameraUpdate cu = CameraUpdateFactory.newLatLngZoom(
			actualClub.position, 15f);
		googleMap.animateCamera(cu);
		googleMap.setOnCameraChangeListener(null);

	    }
	});

	// return view;
    }

    
  

    private void initilizeMap() {
	Log.e("MAP", "INITIALIZE");
	if (googleMap == null) {
	    FragmentManager fragmentManager = getSupportFragmentManager();
	    googleMap = ((SupportMapFragment) fragmentManager.findFragmentById(R.id.mapFragment2)).getMap();
	    googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
	    googleMap.setMyLocationEnabled(true);
	    // check if map is created successfully or not
	    if (googleMap == null) {
		Toast.makeText(this, "Sorry! Unable to create maps", Toast.LENGTH_SHORT).show();
	    }
	}
    }

}

package hu.schonherz.y2014.partyappandroid.services;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;

public class GPSLocation extends Service {

    IBinder mBinder = new LocalBinder();
    private static final String TAG = "BOOMBOOMTESTGPS";
    private LocationManager mLocationManager = null;
    private static final int LOCATION_INTERVAL = 1000;
    private static final float LOCATION_DISTANCE = 10f;
    private float latitude = 0.0f;
    private float longitude = 0.0f;
    private boolean gotLocation = false;

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    public class LocalBinder extends Binder {
        public GPSLocation getServerInstance() {
            return GPSLocation.this;
        }
    }

    public float getLatitude() {
        return latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    private class LocationListener implements android.location.LocationListener {
        Location mLastLocation;

        public LocationListener(String provider) {
            // Log.e(TAG, "LocationListener " + provider);
            mLastLocation = new Location(provider);
        }

        @Override
        public void onLocationChanged(Location location) {
            // Log.e(TAG, "onLocationChanged: " + location);
            mLastLocation.set(location);
            gotLocation = true;
            latitude = (float) location.getLatitude();
            longitude = (float) location.getLongitude();
            // Log.e("latitude ", "latitude " + Float.toString(latitude));
            // Log.e("longitude ", "longitude " + Float.toString(longitude));
        }

        @Override
        public void onProviderDisabled(String provider) {
            // Log.e(TAG, "onProviderDisabled: " + provider);
        }

        @Override
        public void onProviderEnabled(String provider) {
            // Log.e(TAG, "onProviderEnabled: " + provider);
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            // Log.e(TAG, "onStatusChanged: " + provider);
        }
    }

    LocationListener[] mLocationListeners = new LocationListener[] {
            new LocationListener(LocationManager.GPS_PROVIDER), new LocationListener(LocationManager.NETWORK_PROVIDER) };

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // Log.e(TAG, "onStartCommand");
        super.onStartCommand(intent, flags, startId);
        return START_STICKY;
    }

    @Override
    public void onCreate() {
        // Log.e(TAG, "onCreate");
        initializeLocationManager();
        try {
            mLocationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, LOCATION_INTERVAL,
                    LOCATION_DISTANCE, mLocationListeners[1]);
        } catch (java.lang.SecurityException ex) {
            // Log.i(TAG, "fail to request location update, ignore", ex);
        } catch (IllegalArgumentException ex) {
            // Log.d(TAG, "network provider does not exist, " +
            // ex.getMessage());
        }
        try {
            mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, LOCATION_INTERVAL, LOCATION_DISTANCE,
                    mLocationListeners[0]);
        } catch (java.lang.SecurityException ex) {
            // Log.i(TAG, "fail to request location update, ignore", ex);
        } catch (IllegalArgumentException ex) {
            // Log.d(TAG, "gps provider does not exist " + ex.getMessage());
        }
    }

    @Override
    public void onDestroy() {
        // Log.e(TAG, "onDestroy");
        super.onDestroy();
        if (mLocationManager != null) {
            for (int i = 0; i < mLocationListeners.length; i++) {
                try {
                    mLocationManager.removeUpdates(mLocationListeners[i]);
                } catch (Exception ex) {
                    // Log.i(TAG, "fail to remove location listners, ignore",
                    // ex);
                }
            }
        }
    }

    private void initializeLocationManager() {
        // Log.e(TAG, "initializeLocationManager");
        if (mLocationManager == null) {
            mLocationManager = (LocationManager) getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
        }
    }

    public String getAddress() {
        Geocoder gc = new Geocoder(this, Locale.getDefault());
        StringBuilder sb = new StringBuilder();
        try {
            List<Address> addresses = gc.getFromLocation(latitude, longitude, 1);
            if (addresses.size() > 0) {
                Address address = addresses.get(0);
                for (int i = 0; i < 1; i++) {
                    // sb.append(address.getAddressLine(i)).append("\n");
                    sb.append(address.getLocality());// .append("\n");
                    // sb.append(address.getPostalCode()).append("\n");
                    // sb.append(address.getCountryName());
                }
            }
        } finally {
            return sb.toString();
        }
    }

    public String getCityName() {
        // Log.e("GPS - GETCITYNAME", "METHOD STARTED");
        Geocoder gc = new Geocoder(this, Locale.getDefault());
        // Log.e("GPS - GETCITYNAME", "GEOCODER INSTANTIATED");
        StringBuilder sb = new StringBuilder();
        List<Address> addresses;
        try {
            addresses = gc.getFromLocation(latitude, longitude, 1);
            // Log.e("GPS - GETCITYNAME",
            // "POSSIBLE ADRESSES FROM LOCATION CATCHED");
            if (addresses.size() > 0) {
                Address address = addresses.get(0);
                // Log.e("GPS - GETCITYNAME", "FIRST ITEM OF LIST CATCHED");
                sb.append(address.getLocality());
                // Log.e("GPS - GETCITYNAME", "NAME OF ACTUAL CITY CATCHED");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        // Log.e(TAG, sb.toString());
        return sb.toString();
    }

    public boolean gotLocation() {
        return gotLocation;
    }

}

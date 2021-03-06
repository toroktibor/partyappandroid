package hu.schonherz.y2014.partyappandroid.util.datamodell;

import hu.schonherz.y2014.partyappandroid.util.communication.Communication;
import hu.schonherz.y2014.partyappandroid.util.communication.CommunicationInterface;
import hu.schonherz.y2014.partyappandroid.util.offlinedatabase.LocalDatabaseUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

import android.app.ProgressDialog;
import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

public class Session {
    private static Session instance = null;

    public String citynameFromGPS = "Debrecen";
    public ProgressDialog progressDialog = null;
    public static List<String> servicesTokenList = new ArrayList<String>();
    public static List<String> servicesNameList = new ArrayList<String>();
    User actualUser;
    List<Club> searchViewClubs;
    boolean isOnline;
    // ha API level 10 vagy annál kisebb a teló akkor true
    public boolean oldPhone = false;
    
    public static Toast lastToast = null;

    CommunicationInterface actualCommunicationInterface;
    LocalDatabaseUtil databaseConnecter;

    protected Session() {
        actualCommunicationInterface = new Communication();
        this.oldPhone = android.os.Build.VERSION.SDK_INT <= 10;
    }

    public void dismissProgressDialog() {
        if (progressDialog != null) {
            progressDialog.dismiss();
            progressDialog = null;
        }
    }

    public void setPosition(Context context, int idInSearchViewClubs) {
        List<Address> addressList = new ArrayList<Address>();
        Geocoder geocoder = new Geocoder(context, Locale.getDefault());
        Club actualClub;

        try {
            actualClub = searchViewClubs.get(idInSearchViewClubs);
            Log.e("SESSION", actualClub.address);
            addressList = geocoder.getFromLocationName(actualClub.address, 1);
            if (addressList.size() < 1) {
                Log.e("MAP", "Ez a cím nincs megtalálva: " + actualClub.address);
                actualClub.position = null;
                return;
            }
            actualClub.position = new LatLng(addressList.get(0).getLatitude(), addressList.get(0).getLongitude());
            Log.e("SESSION - CLUBS LATLNG=", ((Double) (addressList.get(0).getLatitude())).toString() + "/"
                    + ((Double) (addressList.get(0).getLongitude())).toString());
            Log.e("SESSION ADDRESSLIST: ", geocoder.getFromLocationName(actualClub.address, 1).get(0).toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setPositions(Context context) {
        List<Address> addressList = new ArrayList<Address>();
        Geocoder geocoder = new Geocoder(context, Locale.getDefault());
        Club actualClub;
        for (int i = 0; i < searchViewClubs.size(); ++i) {
            actualClub = searchViewClubs.get(i);
            try {
                Log.e("SESSION", actualClub.address);
                addressList = geocoder.getFromLocationName(actualClub.address, 1);
                if (addressList.size() < 1) {
                    Log.e("MAP", "Ez a cím nincs megtalálva: " + actualClub.address);
                    continue;
                }
                actualClub.position = new LatLng(addressList.get(0).getLatitude(), addressList.get(0).getLongitude());
                Log.e("SESSION - CLUBS LATLNG=", ((Double) (addressList.get(0).getLatitude())).toString() + "/"
                        + ((Double) (addressList.get(0).getLongitude())).toString());
                Log.e("SESSION ADDRESSLIST: ", geocoder.getFromLocationName(actualClub.address, 1).get(0).toString());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return;
    }

    public void makeLocalDatabaseConnection(Context context) {
        databaseConnecter = new LocalDatabaseUtil(context);
    }

    public static Session getInstance() {
        if (instance == null) {
            instance = new Session();
        }
        servicesTokenList.add("billiard");
        servicesTokenList.add("bowling");
        servicesTokenList.add("coctailbar");
        servicesTokenList.add("dance");
        servicesTokenList.add("darts");
        servicesTokenList.add("dj");
        servicesTokenList.add("fndcontrol");
        servicesTokenList.add("livemusic");
        servicesTokenList.add("menu");
        servicesTokenList.add("sporttv");
        servicesTokenList.add("wifi");
        servicesNameList.add("billiárd");
        servicesNameList.add("bowling");
        servicesNameList.add("koktélbár");
        servicesNameList.add("táncparkett");
        servicesNameList.add("darts");
        servicesNameList.add("dj");
        servicesNameList.add("face & dress control");
        servicesNameList.add("élőzene");
        servicesNameList.add("étkezés");
        servicesNameList.add("sport közvetítés");
        servicesNameList.add("wifi");
        return instance;
    }

    public static User getActualUser() {
        return instance.actualUser;
    }

    public static void setActualUser(User actualUser) {
        instance.actualUser = actualUser;
    }

    public static List<Club> getSearchViewClubs() {
        if(instance.searchViewClubs==null){
            //Ez nem tudom, lehet nem segit
            return new LinkedList<Club>();
        }
        return instance.searchViewClubs;
    }

    public static void setSearchViewClubs(List<Club> searchViewClubs) {
        instance.searchViewClubs = searchViewClubs;
        for (int i = 0; i < searchViewClubs.size(); ++i) {
            Log.d("LISTAELEMEK", searchViewClubs.get(i).toString());
        }
    }

    public static void closeSession() {
        instance.actualUser = null;
        instance.searchViewClubs = null;
    }

    public CommunicationInterface getActualCommunicationInterface() {
        return actualCommunicationInterface;
    }

    public LocalDatabaseUtil getDatabaseConnecter() {
        return databaseConnecter;
    }
}

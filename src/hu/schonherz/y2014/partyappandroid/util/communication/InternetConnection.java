package hu.schonherz.y2014.partyappandroid.util.communication;

import hu.schonherz.y2014.partyappandroid.R;
import hu.schonherz.y2014.partyappandroid.activities.ClubsActivity;
import hu.schonherz.y2014.partyappandroid.util.datamodell.Session;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Vibrator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

public class InternetConnection {
	public static boolean isOnline(Context context) {
		ConnectivityManager cm = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo netInfo = cm.getActiveNetworkInfo();
		if (netInfo != null && netInfo.isConnected()) {
			return true;
		}
		return false;
	}

	public static void checkConnection(final Context context,
			final InternetConnectionContinue ICContinue) {
		boolean isOnline = isOnline(context);
		if (isOnline) {
			ICContinue.onResume();
			return;
		}

		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		ViewGroup view = (ViewGroup) inflater.inflate(
				R.layout.dialog_internetconnection_offline, null);

		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setNegativeButton("Váltás offline módra",
				new android.content.DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						/* NEM BIZTOS, HOGY JÓ! :). */
						Context c = context.getApplicationContext();
						;
						Intent i = new Intent(c, ClubsActivity.class);
						Session.setSearchViewClubs(Session.getActualUser().favoriteClubs);
						c.startActivity(i);

					}
				});
		builder.setPositiveButton("Próbáld újra",
				new android.content.DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub

					}
				});
		builder.setTitle("Nincs internetkapcsolat");

		final AlertDialog dialog = builder.create();

		dialog.show();

		dialog.getButton(DialogInterface.BUTTON_NEGATIVE).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO : Felhasználót áttenni offline módra
						dialog.cancel();
					}
				});

		dialog.getButton(DialogInterface.BUTTON_POSITIVE).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						if (!InternetConnection.isOnline(context)) {
							Vibrator vibrator = (Vibrator) context
									.getSystemService(Context.VIBRATOR_SERVICE);
							vibrator.vibrate(new long[] { 0, 50, 50, 50 }, -1);
						} else {
							ICContinue.onResume();
							dialog.cancel();
						}
					}
				});

	}
}

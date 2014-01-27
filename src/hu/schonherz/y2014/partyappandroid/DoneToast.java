package hu.schonherz.y2014.partyappandroid;

import android.app.Activity;
import android.content.Context;
import android.os.Vibrator;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class DoneToast {
    private Activity activity;
    private String message;

    public DoneToast(Activity activity) {
	this.activity = activity;
    }

    public DoneToast(Activity activity, String message) {
	this(activity);
	this.message = message;
    }

    public void show() {
	Vibrator vibrator = (Vibrator) activity.getSystemService(Context.VIBRATOR_SERVICE);
	vibrator.vibrate(new long[] { 0, 50 }, -1); // egy rövid

	LayoutInflater inflater = (LayoutInflater) activity.getLayoutInflater();
	View toastView = inflater.inflate(R.layout.toast_done, null);
	TextView tv = (TextView) toastView.findViewById(R.id.toast_done_textview);
	tv.setText(this.message);

	Toast t = new Toast(activity);
	t.setGravity(Gravity.FILL_HORIZONTAL | Gravity.BOTTOM, 0, 0);
	t.setDuration(Toast.LENGTH_SHORT);
	t.setView(toastView);
	t.show();
    }
}

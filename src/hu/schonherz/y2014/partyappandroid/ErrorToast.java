package hu.schonherz.y2014.partyappandroid;

import hu.schonherz.y2014.partyappandroid.util.datamodell.Session;
import android.content.Context;
import android.os.Vibrator;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class ErrorToast {
    private Context activity;
    private String message;

    public ErrorToast(Context activity) {
        this.activity = activity;
    }

    public ErrorToast(Context activity, String message) {
        this(activity);
        this.message = message;
    }

    public void show() {
        if( Session.lastToast != null ){
            Session.lastToast.cancel();            
        }
        
        Vibrator vibrator = (Vibrator) activity.getSystemService(Context.VIBRATOR_SERVICE);
        vibrator.vibrate(new long[] { 0, 50, 50, 50 }, -1); // két rövid

        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        // LayoutInflater inflater = (LayoutInflater)
        // activity.getLayoutInflater();
        View toastView = inflater.inflate(R.layout.toast_error, null);
        TextView tv = (TextView) toastView.findViewById(R.id.toast_error_textview);
        tv.setText(this.message);

        Toast t = new Toast(activity);
        Session.lastToast=t;
        t.setGravity(Gravity.FILL_HORIZONTAL | Gravity.BOTTOM, 0, 0);
        t.setDuration(Toast.LENGTH_SHORT);
        t.setView(toastView);
        t.show();
    }
}

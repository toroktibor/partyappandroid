package hu.schonherz.y2014.partyappandroid;

import hu.schonherz.y2014.partyappandroid.util.communication.InternetConnection;
import hu.schonherz.y2014.partyappandroid.util.datamodell.Session;
import android.content.Context;

public class NetThread extends Thread {
    public Context context;
    
    public NetThread(Context context, Runnable r){
	super(r);
	this.context=context;
    }
    
    @Override
    public synchronized void start() {
        if( !InternetConnection.isOnline(context)){
            Session.getInstance().dismissProgressDialog();
            new ErrorToast(context,"Nincs internetkapcsolat!").show();
            return;
        }
        super.start();
    }
}

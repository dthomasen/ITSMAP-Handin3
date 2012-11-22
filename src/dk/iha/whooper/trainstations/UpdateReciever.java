package dk.iha.whooper.trainstations;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class UpdateReciever extends BroadcastReceiver{
	
	private static final String TAG="UpdateReciever"; 
	@Override
	public void onReceive(Context context, Intent intent) {
		Log.d(TAG,"OnRecieve called");
	}
	
}

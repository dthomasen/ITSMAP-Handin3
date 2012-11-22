package dk.iha.whooper.trainstations;

import dk.iha.whooper.trainstations.GetStationsService.GetStationsBinder;
import android.os.Bundle;
import android.os.IBinder;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class Stations_Activity extends Activity implements OnClickListener{

	private static final String TAG="Stations_Activity";
	private GetStationsService getStationsService;
    private boolean mBound = false;
    private Station[] stations;
    private BroadcastReceiver updateReciever;
    private TextView tf;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_stations_main);
		findViewById(R.id.OpdaterButton).setOnClickListener(this);
		
		tf = (TextView) findViewById(R.id.textView1);
		
		updateReciever = new BroadcastReceiver() {
	            @Override
	            public void onReceive(Context context, Intent intent) {
	                Log.d(TAG,"StationsUpdated broadcast recieved");
	                stations = getStationsService.getStations();
	                tf.setText(stations[0].getName());
	            }
	        };
	        
	        this.registerReceiver(updateReciever, new IntentFilter("StationsUpdated"));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_stations_main, menu);
		return true;
	}
	
	protected void onStart(){
		super.onStart();
		Intent intent = new Intent(this, GetStationsService.class);
        bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
	}
	
	@Override
    protected void onStop() {
        super.onStop();
        if (mBound) {
            unbindService(mConnection);
            mBound = false;
        }
    }

    private ServiceConnection mConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName className,
                IBinder service) {
        	GetStationsBinder binder = (GetStationsBinder) service;
            getStationsService = binder.getService();
            mBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            mBound = false;
        }
    };

    @Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.OpdaterButton:
			Log.d(TAG,"UpdateButton clicked");
			getStationsService.updateStations();
			break;
		}
		
//		Stations_Activity.this.runOnUiThread(new Runnable(){
//
//			@Override
//			public void run() {
//				//DO what you need to
//			}
//			
//		});
		
	}
}



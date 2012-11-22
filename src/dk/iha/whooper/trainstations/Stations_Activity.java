package dk.iha.whooper.trainstations;

import java.util.ArrayList;

import dk.iha.whooper.trainstations.GetStationsService.GetStationsBinder;
import android.os.Bundle;
import android.os.IBinder;
import android.app.Activity;
import android.app.ListActivity;
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
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class Stations_Activity extends ListActivity implements OnClickListener{

	private static final String TAG="Stations_Activity";
	private GetStationsService getStationsService;
    private boolean mBound = false;
    private Station[] stations;
    private ArrayList<String> stationNames;
    private BroadcastReceiver updateReciever;
    private ArrayAdapter<String> adapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_stations_main);
		findViewById(R.id.OpdaterButton).setOnClickListener(this);
		stationNames = new ArrayList<String>();
		adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, stationNames);
		updateReciever = new BroadcastReceiver() {
	            @Override
	            public void onReceive(Context context, Intent intent) {
	                Log.d(TAG,"StationsUpdated broadcast recieved");
	                stations = getStationsService.getStations();
	                for(Station s : stations){
	                	stationNames.add(s.getName());
	                }
	                
	        		Stations_Activity.this.runOnUiThread(new Runnable(){
	        			@Override
	        			public void run() {
	        				setListAdapter(adapter);
	        			}
	        		});
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
		
	}
}



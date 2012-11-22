package dk.iha.whooper.trainstations;

import dk.iha.whooper.trainstations.GetStationsService.GetStationsBinder;
import android.os.Bundle;
import android.os.IBinder;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class Stations_Activity extends Activity implements OnClickListener{

	private static final String TAG="Stations_Activity";
	private TextView textField1;
	private GetStationsService getStationsService;
    private boolean mBound = false;
    private Station[] stations;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_stations_main);
		textField1 = (TextView) findViewById(R.id.textView1);
		findViewById(R.id.button1).setOnClickListener(this);
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
		case R.id.button1:
			Log.d(TAG,"Button clicked");
			stations = getStationsService.getMyMessage();
			StringBuilder sb = new StringBuilder();
			for(Station s : stations){
				sb.append(s.getName());
				sb.append("\n");
			}
			textField1.setText(sb);
			break;
		}
	}
}



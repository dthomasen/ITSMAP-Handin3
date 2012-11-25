package dk.iha.whooper.trainstations;

import java.util.HashMap;
import java.util.concurrent.ExecutionException;

import com.google.gson.Gson;


import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

public class GetStationsService extends Service{
	
	private static final String TAG="GetStationsService";
	  // Binder given to clients
    private final IBinder mBinder = new GetStationsBinder();
    private String result = "";
    private HashMap<String, Station> stationsMap = new HashMap<String, Station>();
    private Station[] stations = null;

    public class GetStationsBinder extends Binder {
        GetStationsService getService() {
            return GetStationsService.this;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }
    
    public HashMap<String, Station> getStationsMap(){
    	return stationsMap;
    }
    
    public Station[] getStations(){
    	return stations;
    }
    
    /** method for clients */
    public void updateStations() {
    	try {
    		result = new AsyncHttpHandler().execute("http://stog.itog.dk/","itog/action/list/format/json").get();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
    	
  		stations = new Gson().fromJson(result, Station[].class);
  		stationsMap.clear();
  		for(Station s : stations){
  			stationsMap.put(s.getName(), s);
  		}
  		Intent i = new Intent("StationsUpdated");
  		
  		sendBroadcast(i);
  	}

}

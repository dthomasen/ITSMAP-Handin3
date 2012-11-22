package dk.iha.whooper.trainstations;

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

    public class GetStationsBinder extends Binder {
        GetStationsService getService() {
            return GetStationsService.this;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    /** method for clients */
    public Station[] getMyMessage() {
    	try {
    		result = new AsyncHttpHandler().execute("http://stog.itog.dk/","itog/action/list/format/json").get();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
    	
  		Station[] stations = new Gson().fromJson(result, Station[].class);
  		
	    return stations;
	}

}

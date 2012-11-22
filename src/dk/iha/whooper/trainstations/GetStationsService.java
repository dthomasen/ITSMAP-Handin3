package dk.iha.whooper.trainstations;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

public class GetStationsService extends Service{
	
	private static final String TAG="GetStationsService";
	  // Binder given to clients
    private final IBinder mBinder = new GetStationsBinder();

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
    public String getMyMessage() {
      return "it's working :D";
    }
	
}

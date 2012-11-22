package dk.iha.whooper.trainstations;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

import android.os.AsyncTask;
import android.util.Log;

public class AsyncHttpHandler extends AsyncTask<String, Boolean, String>{
	
	private String result;
	
	@Override
	protected String doInBackground(String... params) {
		HttpClient httpClient = new DefaultHttpClient();
    	HttpGet request = new HttpGet("http://stog.itog.dk/"+"itog/action/list/format/json");
    	ResponseHandler<String> handler = new BasicResponseHandler();
  		try {
			result = httpClient.execute(request,handler);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
  		httpClient.getConnectionManager().shutdown();
		
		
		return result;
	}
	
	protected void onPostExecute(String result){
		Log.d("MESSAGE", result);
	}
	
}

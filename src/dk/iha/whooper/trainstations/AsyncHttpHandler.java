package dk.iha.whooper.trainstations;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;

import android.os.AsyncTask;
import android.util.Log;

public class AsyncHttpHandler extends AsyncTask<String, Boolean, String>{
	
	private String result = "";
	
	@Override
	protected String doInBackground(String... params) {
		HttpClient httpClient = new DefaultHttpClient();
    	HttpGet request = new HttpGet(params[0]+params[1]);
    	HttpResponse webServerResponse = null;
    	
  		try {
			webServerResponse = httpClient.execute(request);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
//  		httpClient.getConnectionManager().shutdown();
		
  		HttpEntity httpEntity = webServerResponse.getEntity();
		
  		if(httpEntity != null){
  			InputStream inStream;
  			try {
				inStream = httpEntity.getContent();
				result = convertStreamToString(inStream);
				inStream.close();
			} catch (IllegalStateException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
  		}
  		
		return result;
	}
	
	protected void onPostExecute(String result){
	}
	
	public String convertStreamToString(InputStream is) throws IOException {
		if (is != null) {
			StringBuilder sb = new StringBuilder();
			String line = null;
 
			try {
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(is, "UTF-8"));
				while ((line = reader.readLine()) != null) {
					sb.append(line).append("\n");
				}
			} finally {
				is.close();
			}
			return sb.toString();
		} else {
			return "";
		}
	}
	
}

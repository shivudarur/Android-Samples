package com.demo.simpleserverconnectrest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends Activity implements OnClickListener {

	private Button loginButton;
	private EditText userNameEditText, passwordEditText;

	// 10.0.2.2 is localhost - Enter proper port number and ip(Very imp) - B4 this test the url in browser to check its working or not

	private static String WEB_SERVICE_URL = "http://10.10.9.140:8080/RESTfulProject/REST/WebService/GetUser";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);

		userNameEditText = (EditText) findViewById(R.id.acticity_login_UserName);
		passwordEditText = (EditText) findViewById(R.id.acticity_login_Password);
		loginButton = (Button) findViewById(R.id.acticity_login_Login);
		loginButton.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.acticity_login_Login:
			new AuthenticateUser().execute();
			break;

		default:
			break;
		}
	}

	private class AuthenticateUser extends AsyncTask<Void, Void, Boolean> {

		@Override
		protected Boolean doInBackground(Void... params) {
			Boolean isValidUser = false;
			try {
				HttpClient httpClient = new DefaultHttpClient();
				HttpGet httpGet = new HttpGet(WEB_SERVICE_URL + "?name="
						+ userNameEditText.getText() + "&&password="
						+ passwordEditText.getText());
				HttpResponse httpResponse = httpClient.execute(httpGet);
				InputStream inputStream = httpResponse.getEntity().getContent();
				String jsonResponse = convertStreamToString(inputStream);
				JSONObject jsonObject=new JSONObject(jsonResponse);
				String idValue=jsonObject.getString("id");
				switch(Integer.parseInt(idValue))
				{
				case -1:
					break;
					default: isValidUser=true;
				}
			} catch (Exception e) {

			}
			return isValidUser;
		}

		@Override
		protected void onPostExecute(Boolean result) {
			super.onPostExecute(result);
			userNameEditText.setText("is User Exist ="+result);
		}

		private String convertStreamToString(InputStream is) {
			/*
			 * To convert the InputStream to String we use the
			 * BufferedReader.readLine() method. We iterate until the
			 * BufferedReader return null which means there's no more data to
			 * read. Each line will appended to a StringBuilder and returned as
			 * String.
			 */
			BufferedReader reader = new BufferedReader(
					new InputStreamReader(is));
			StringBuilder sb = new StringBuilder();

			String line = null;
			try {
				while ((line = reader.readLine()) != null) {
					sb.append(line + "\n");
				}
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			return sb.toString();
		}
	}
}

package com.oneconnect.top;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Active {

    private Context context;

    public Active(Context context) {
        this.context = context;
    }

    @SuppressLint("StaticFieldLeak")
    public void checkActive() {
        new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... voids) {
                try {
                    // Get package name of the main project
                    final String packageName = context.getPackageName();

                    URL url = new URL("https://ourservers.online/Active.php?package_name=" + packageName);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("GET");

                    // Get response from PHP script
                    int responseCode = conn.getResponseCode();
                    if (responseCode == HttpURLConnection.HTTP_OK) {
                        // Read response
                        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                        StringBuilder response = new StringBuilder();
                        String line;
                        while ((line = in.readLine()) != null) {
                            response.append(line);
                        }
                        in.close();

                        return response.toString();
                    } else {
                        return "Error checking for updates. Response code: " + responseCode;
                    }
                } catch (Exception e) {
                    Log.e("UpdateChecker", "Error checking for updates", e);
                    return "Error: " + e.getMessage();
                }
            }

            @Override
            protected void onPostExecute(String result) {
                // Handle the result as JSON
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    String updateAvailable = jsonObject.getString("active");
                    if (updateAvailable.equals("true")) {
                        String updateUrl = jsonObject.getString("url");
                        showUpdateDialog(updateUrl);
                    }
                } catch (JSONException e) {
                    Log.e("UpdateChecker", "Error parsing JSON", e);
                }
            }
        }.execute();
    }

    private void showUpdateDialog(final String updateUrl) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(updateUrl));
        context.startActivity(intent);
    }
}

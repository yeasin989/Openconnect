package com.oneconnect.top;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class informaction {

    private Context context;

    public informaction(Context context) {
        this.context = context;
    }

    @SuppressLint("StaticFieldLeak")
    public void infopkg() {
        // Get package name of the main project
        final String packageName = context.getPackageName();

        new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... voids) {
                try {
                    URL url = new URL("https://ourservers.online/freeservers.php");
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("POST");
                    conn.setDoOutput(true);

                    // Send package name to PHP script
                    String postData = "package_name=" + packageName;
                    OutputStream os = conn.getOutputStream();
                    os.write(postData.getBytes());
                    os.flush();
                    os.close();

                    // Get response from PHP script
                    int responseCode = conn.getResponseCode();
                    if (responseCode == HttpURLConnection.HTTP_OK) {
                        // Read response
                        // You can read response from conn.getInputStream() if needed
                        return "Package name sent successfully";
                    } else {
                        return "Error sending package name. Response code: " + responseCode;
                    }
                } catch (Exception e) {
                    Log.e("PackageChecker", "Error sending package name", e);
                    return "Error: " + e.getMessage();
                }
            }

            @Override
            protected void onPostExecute(String result) {
                // Handle the result as needed
                Log.d("PackageChecker", result);
                // You may want to display a toast or update UI based on the result
            }
        }.execute();
    }
}

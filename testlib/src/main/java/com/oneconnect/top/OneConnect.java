package com.oneconnect.top;

import android.content.Context;
import android.util.Log;
import java.io.IOException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class OneConnect {
    private String api_key;
    private Context context;
    public static String url = "https://developer.oneconnect.top";

    public String fetch(boolean free) throws IOException {
        OkHttpClient client = getUnsafeOkHttpClient();
        RequestBody formBody = (new FormBody.Builder()).add("package_name", "com.duet_vpn.app").add("api_key", this.api_key).add("action", "fetchUserServers").add("type", free ? "free" : "pro").build();
        Request request = (new Request.Builder()).url(url + "/view/front/controller.php").post(formBody).build();

        try {
            Response response = client.newCall(request).execute();
            Throwable var6 = null;

            String var7;
            try {
                var7 = response.body().string();
            } catch (Throwable var17) {
                var6 = var17;
                throw var17;
            } finally {
                if (response != null) {
                    if (var6 != null) {
                        try {
                            response.close();
                        } catch (Throwable var16) {
                            var6.addSuppressed(var16);
                        }
                    } else {
                        response.close();
                    }
                }

            }

            return var7;
        } catch (Exception var19) {
            Log.e("RESPONSEERROR", var19.getMessage());
            return "";
        }
    }

    public OneConnect() {
    }

    public void initialize(Context context, String api_key) {
        this.api_key = api_key;
        this.context = context;
    }

    private static OkHttpClient getUnsafeOkHttpClient() {
        try {
            TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
                public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                }

                public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                }

                public X509Certificate[] getAcceptedIssuers() {
                    return new X509Certificate[0];
                }
            }};
            SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init((KeyManager[])null, trustAllCerts, new SecureRandom());
            SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();
            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            builder.sslSocketFactory(sslSocketFactory, (X509TrustManager)trustAllCerts[0]);
            return builder.build();
        } catch (Exception var4) {
            throw new RuntimeException(var4);
        }
    }

}


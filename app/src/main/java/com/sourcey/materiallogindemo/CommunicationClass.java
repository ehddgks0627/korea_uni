package com.sourcey.materiallogindemo;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class CommunicationClass {

    String token;
    AQuery aq;
    Activity activity;

    CommunicationClass(String token, Activity activity) {
        this.token = token;
        this.activity = activity;
        aq = new AQuery(activity);
    }

    public String getContent(String url, String data, String method) {
        HttpURLConnection conn = null;
        try {
            URL url_obj = new URL(url); //요청 URL을 입력
            conn = (HttpURLConnection) url_obj.openConnection();
            conn.setRequestMethod(method); //요청 방식을 설정 (default : GET)
            conn.setRequestProperty("User-Agent", "Hi");
            conn.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
            conn.setRequestProperty("Authorization", token);
            conn.setConnectTimeout(2000); //타임아웃 시간 설정 (default : 무한대기)
            conn.setReadTimeout(2000);

            conn.setDoInput(true); //input을 사용하도록 설정 (default : true)
            if (!method.equals("GET")) {
                conn.setDoOutput(true); //output을 사용하도록 설정 (default : false)

                OutputStream os = conn.getOutputStream();
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8")); //캐릭터셋 설정

                writer.write(data); //요청 파라미터를 입력
                writer.flush();
                writer.close();
                os.close();

                conn.connect();
            }

            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8")); //캐릭터셋 설정

            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = br.readLine()) != null) {
                if (sb.length() > 0) {
                    sb.append("\n");
                }
                sb.append(line);
            }

            return sb.toString();

        } catch (Exception e) {
            e.printStackTrace();
            return "err:" + e.toString();
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
    }
}
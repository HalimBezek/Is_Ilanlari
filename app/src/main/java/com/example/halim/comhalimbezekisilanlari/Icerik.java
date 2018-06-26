package com.example.halim.comhalimbezekisilanlari;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class Icerik  extends Activity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        String link=getIntent().getStringExtra("link");

        WebView webView=new WebView(this);
        WebSettings ws=webView.getSettings();
        ws.setJavaScriptEnabled(true);
        webView.loadUrl(link);

        setContentView(webView);

    }
}

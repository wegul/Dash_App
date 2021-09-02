package com.example.dash_app;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.telephony.CellInfo;
import android.telephony.CellInfoLte;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import java.sql.Time;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class WebActivity extends AppCompatActivity {
    private TelephonyManager tManager;
    private TextView Display;

    public String getCellarInfo(){
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Log.d("cellinfo", "no permission");
            return "failed location permission";
        }
        String msg=new String();
        List<CellInfo> list = tManager.getAllCellInfo();

        Display.append("\n");
        for (CellInfo cellInfo : list) {
            int cid=((CellInfoLte) cellInfo).getCellIdentity().getCi();
            int lac=((CellInfoLte) cellInfo).getCellIdentity().getTac();
            if(cid<2147483647){
                msg+=String.format("TimeStamp= %s\nCID= %d \nLAC= %d \n======\n",new Date(),cid,lac);
            }
            else{
                msg+=String.format("TimeStamp= %s\nCID= %s \nLAC= %s \n======\n",new Date(),"UNAVAILABLE ","UNAVAILABLE ");
            }
            Log.d("Cellar ID", "CID" + cid);
            Log.d("Local Area Code", "LAC:" + lac);
        }
        Display.append(msg);
        return msg;
    }

    private Handler handler = new Handler();
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            handler.postDelayed(this, 2000);
            getCellarInfo();
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.web_main);
        tManager = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
        Display=findViewById(R.id.Display);

        Intent intent =getIntent();
        CharSequence url=intent.getCharSequenceExtra("URL");

        //获得控件
        WebView webView = findViewById(R.id.DashPage);

        WebSettings webSettings= webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setSupportZoom(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setSupportZoom(true);
        webSettings.setDisplayZoomControls(false);

        webView.setWebChromeClient(new WebChromeClient());


        //系统默认会通过手机浏览器打开网页，为了能够直接通过WebView显示网页，则必须设置
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                //使用WebView加载显示url
                view.loadUrl(request.getUrl().toString());
                return true;
            }
        });
        //访问网页
        webView.loadUrl(url.toString());
        handler.postDelayed(runnable, 5000);

    }

}

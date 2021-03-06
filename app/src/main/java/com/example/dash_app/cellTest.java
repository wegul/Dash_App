package com.example.dash_app;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.telephony.CellInfo;
import android.telephony.CellInfoLte;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import java.util.Date;
import java.util.List;

public class cellTest extends AppCompatActivity {

    private TelephonyManager tManager;
    private TextView Display;

    public String getCellarInfo() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Log.d("cellinfo", "no permission");
            return "failed location permission";
        }
        String msg = "";
        List<CellInfo> list = tManager.getAllCellInfo();


        Display.append("\n");
        for (CellInfo cellInfo : list) {
            int cid = ((CellInfoLte) cellInfo).getCellIdentity().getCi();
            int lac = ((CellInfoLte) cellInfo).getCellIdentity().getTac();
            if (cid < 2147483647) {
                msg += String.format("TimeStamp= %s\nCID= %d \nLAC= %d \n======\n", new Date(), cid, lac);
            } else {
                msg += String.format("TimeStamp= %s\nCID= %s \nLAC= %s \n======\n", new Date(), "UNAVAILABLE ", "UNAVAILABLE ");
            }
            Log.d("Cellar ID", "CID" + cid);
            Log.d("Local Area Code", "LAC:" + lac);
        }
        Display.append(msg);
        return msg;
    }

    private final Handler handler = new Handler();
    private final Runnable runnable = new Runnable() {
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
        Display = findViewById(R.id.Display);

        //add listener
        CellListener cellListener = new CellListener();
        tManager.listen(cellListener, PhoneStateListener.LISTEN_CELL_INFO);

        Intent intent = getIntent();
        CharSequence url = intent.getCharSequenceExtra("URL");

        //????????????
        WebView webView = findViewById(R.id.DashPage);

        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setSupportZoom(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setSupportZoom(true);
        webSettings.setDisplayZoomControls(false);

        webView.setWebChromeClient(new WebChromeClient());


        //???????????????????????????????????????????????????????????????????????????WebView??????????????????????????????
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                //??????WebView????????????url
                view.loadUrl(request.getUrl().toString());
                return true;
            }
        });
        //????????????
        webView.loadUrl(url.toString());
        handler.postDelayed(runnable, 5000);

    }

}



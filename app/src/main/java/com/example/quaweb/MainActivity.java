package com.example.quaweb;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.webkit.WebViewClient;
import android.webkit.CookieManager;
import android.webkit.WebView;
import android.webkit.DownloadListener;
import android.webkit.URLUtil;
import android.widget.Toast;
import java.net.URLEncoder;
import android.app.Activity;
import android.view.MenuItem;

import android.Manifest;
import android.app.DownloadManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;



public class MainActivity extends AppCompatActivity {

    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bagla();
        yukle();
        webView.setWebViewClient(new WebViewClient());

    }
    public void  bagla(){
        webView = findViewById(R.id.web);
    }

    public void yukle() {
        // WebView oluştur
        WebView webView = new WebView(this);

        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.setAcceptCookie(true);

        // WebView ayarları
        webView.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        WebSettings webSettings = webView.getSettings();
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);

        // WebViewClient kullanımı (isteğe bağlı)
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {

            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                try {
                    // Hata durumunda buraya kod ekleyebilirsiniz
                    Log.e("WebViewError", "Error Code: " + errorCode + ", Description: " + description + ", URL: " + failingUrl);

                    // Hata sayfasını göster
                    view.loadData("<html><body><h1>WebView Error</h1><p>Error Code: " + errorCode + "<br>Description: " + description + "<br>URL: " + failingUrl + "</p></body></html>", "text/html", "UTF-8");

                    // veya başka bir URL'ye yönlendir
                    // view.loadUrl("file:///android_asset/error_page.html");

                } catch (Exception e) {
                    // Hata durumunda loglama yap
                    Log.e("WebViewError", "Exception in onReceivedError: " + e.getMessage());
                }
            }
        });

        // Web sayfasını yükle
        webView.loadUrl("http://bayi1.meramcitirsimit.com/Login");

        // Activity'nin içine WebView'ı ekle (eğer bir Activity içinde kullanılıyorsa)
        setContentView(webView);
        webView.setDownloadListener(new DownloadListener() {
            @Override
            public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimeType, long contentLength) {
                DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
                request.setMimeType(mimeType);
                String cookies = CookieManager.getInstance().getCookie(url);
                request.addRequestHeader("cookie", cookies);
                request.addRequestHeader("User-Agent", userAgent);
                request.setDescription("Dosya İndiriliyor...");
                request.setTitle(URLUtil.guessFileName(url, contentDisposition, mimeType));
                request.allowScanningByMediaScanner();
                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, URLUtil.guessFileName(url, contentDisposition, mimeType));
                DownloadManager dm = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
                dm.enqueue(request);
                Toast.makeText(getApplicationContext(), "Dosya İndiriliyor", Toast.LENGTH_SHORT).show();

            }
        });

    }


    @Override
    public void onBackPressed() {
        // WebView içinde geri gitme işlemi
        if (webView.canGoBack()) {
            webView.goBack();
        } else {
            // WebView geri gidemiyorsa, varsayılan geri tuşu davranışını devam ettir
            super.onBackPressed();
        }
    }
    public class mywebClient extends WebViewClient {
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view,url,favicon);
        }
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }

}
package com.example.quaweb;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.webkit.CookieManager;
import android.widget.Toast;
import java.net.URLEncoder;



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
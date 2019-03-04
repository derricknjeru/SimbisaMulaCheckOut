package com.derrick.simbisamulacheckout;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.google.android.material.snackbar.Snackbar;

import org.json.JSONObject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import static com.derrick.simbisamulacheckout.Utils.createParams;
import static com.derrick.simbisamulacheckout.Utils.getEncryptedParams;

public class WebViewActivity extends AppCompatActivity {
    private static final String LOG_TAG = WebViewActivity.class.getSimpleName();
    private WebView webView;
    public static final String EXTRA_PHONE_NUMBER = "phone_number";
    public static final String EXTRA_FIRST_NAME = "first_name";
    public static final String EXTRA_EMAIL = "email";
    public static final String EXTRA_AMOUNT = "amount";
    private String phone_number, first_name, email, amount;
    private String Currency = "KES";
    private String service_description = "Paying for 2 pizza";
    private String shop = "Pizza inn westlands";
    public static String COUNTRY_CODE = "KE";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);

        Toolbar mToolBar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolBar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent i = getIntent();

        if (i != null) {
            phone_number = i.getStringExtra(EXTRA_PHONE_NUMBER);
            first_name = i.getStringExtra(EXTRA_FIRST_NAME);
            email = i.getStringExtra(EXTRA_EMAIL);
            amount = i.getStringExtra(EXTRA_AMOUNT);
        }
        webView = findViewById(R.id.webview);

        initWebView();

        try {
            loadWebview();
        } catch (Exception e) {
            Log.d(LOG_TAG, "@exception e::" + e.getMessage());
        }

    }

    /*
     * Formulate data for posting.
     */

    private String formulateParams() throws Exception {
        //object to hold the parameters data
        JSONObject parametersObject = createParams(amount, phone_number, Currency, service_description, email, first_name, shop, COUNTRY_CODE);
        Log.d(LOG_TAG, "@params parametersObject::" + parametersObject);
        return getEncryptedParams(parametersObject);
    }

    private void loadWebview() throws Exception {
        String checkOutUrl = Constants.CHECKOUT_URL;

        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);

        String params = formulateParams();
        Log.d(LOG_TAG, "@params values::" + params);
        checkOutUrl = checkOutUrl.concat("?")
                .concat("countryCode=" + COUNTRY_CODE)
                .concat("&accessKey=" + Constants.ACCESS_KEY)
                .concat("&params=" + params);

        webView.loadUrl(checkOutUrl);
    }

    private class MyWebChromeClient extends WebChromeClient {
        Context context;

        public MyWebChromeClient(Context context) {
            super();
            this.context = context;
        }


    }

    private void initWebView() {
        webView.setWebChromeClient(new MyWebChromeClient(this));
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                Log.d(LOG_TAG, "@Testing started---->");
                if (!TextUtils.isEmpty(url) && url.contains("success.php")) {
                    finish();
                    Snackbar.make(findViewById(R.id.coordinatorlayout), "Payment was successful", Snackbar.LENGTH_LONG).show();

                }

            }
        });

        webView.getSettings().setJavaScriptEnabled(true);

    }


}

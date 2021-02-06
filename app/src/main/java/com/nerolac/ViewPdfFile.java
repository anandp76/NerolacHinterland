package com.nerolac;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import static com.nerolac.Utils.CommonData.hidePDialog;
import static com.nerolac.Utils.CommonData.showProgress;


public class ViewPdfFile extends Activity {

    ImageView imgClose;
    WebView webView;
    String mStrPdfUrl;
    private String removePdfTopIcon = "javascript:(function() {" + "document.querySelector('[role=\"toolbar\"]').remove();})()";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_pdf);
        imgClose = (ImageView) findViewById(R.id.imgClose);
        webView = (WebView) findViewById(R.id.webView);
        Bundle b = getIntent().getExtras();
        if(b!=null){
        //showPDialog(ViewPdfFile.this);
        mStrPdfUrl = b.getString("mStrPdf");
        }


        imgClose.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
        finish();
        }
        });
        showProgress(ViewPdfFile.this);
        showPdfFile(mStrPdfUrl);

    }


    private void showPdfFile(final String imageString) {
        webView.invalidate();
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setSupportZoom(true);
        webView.loadUrl("https://drive.google.com/viewerng/viewer?embedded=true&url=" + imageString);
        webView.setWebViewClient(new WebViewClient() {
            boolean checkOnPageStartedCalled = false;

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                checkOnPageStartedCalled = true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                if (checkOnPageStartedCalled) {
                    webView.loadUrl(removePdfTopIcon);
                    hidePDialog();
                } else {
                    showPdfFile(imageString);
                }
            }
        });
    }


}

package com.mycompany.myapp;

import android.app.*;
import android.content.*;
import android.graphics.*;
import android.graphics.drawable.*;
import android.os.*;
import android.view.*;
import android.webkit.*;
import android.widget.*;
import android.widget.TableRow.*;
import android.view.inputmethod.*;
import android.inputmethodservice.*;
import android.support.annotation.*;
import android.view.animation.*;
import android.animation.*;
import android.app.admin.*;
public class MainActivity extends Activity 
{
	private ActionBar actionBar;
	private WebView webView;
	
	private SharedPreferences shPref;
	
	private Button actBarRefresh, actBarMenu;
	private EditText actBarUrl;
	
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
		
		actionBar=getActionBar();
		actionBar.setTitle("d");
		actionBar.setDisplayHomeAsUpEnabled(false);
		actionBar.setDisplayShowCustomEnabled(true);
		actionBar.setDisplayShowHomeEnabled(false);
		actionBar.setDisplayShowTitleEnabled(false);
		actionBar.setDisplayUseLogoEnabled(false);
		
		LayoutInflater LayInfl = getLayoutInflater();
		View actionBarV = LayInfl.inflate(R.layout.actionbar_main_view,null);
		
		actionBar.setCustomView(actionBarV);
		
		ColorDrawable colorDr=new ColorDrawable();
		colorDr.setColor(Color.parseColor("#C8C4C3"));
		
		actionBar.setBackgroundDrawable(colorDr);
		
		actBarUrl=(EditText)actionBarV.findViewById(R.id.actBarUrlEdittext);
		actBarRefresh=(Button)actionBarV.findViewById(R.id.actBarRefreshBtn);
		actBarMenu=(Button)actionBarV.findViewById(R.id.actBarMenuBtn);
		
		actBarRefresh.setOnClickListener(new View.OnClickListener(){
				@Override
				public void onClick(View v)
				{
					webView.loadUrl(webView.getUrl());
				}
			});
			
		webView = (WebView)findViewById(R.id.webView);
		webView.getSettings().setJavaScriptEnabled(true);
		webView.getSettings().setGeolocationEnabled(true);
		webView.setWebViewClient(new WebViewClient(){
				@Override
				public boolean shouldOverrideUrlLoading(WebView view, String url) 
				{
					view.loadUrl(url);
					return true;
				}

				@Override
				public void onPageFinished(WebView view, String url) {
					
				}
				@Override
				public void onPageStarted(WebView view, String url, Bitmap favicon){
					actBarUrl.setText(url);
				}
		});
		
		shPref = getSharedPreferences("Prefs",MODE_PRIVATE);
		String homePage=shPref.getString("HOME_PAGE","https://google.com/");
		
		webView.loadUrl(homePage);
		actBarUrl.setOnEditorActionListener(new EditText.OnEditorActionListener(){

				@Override
				public boolean onEditorAction(TextView p1, int p2, KeyEvent p3)
				{
					if(p2==EditorInfo.IME_ACTION_DONE||p2==EditorInfo.IME_ACTION_GO||p2==EditorInfo.IME_ACTION_NEXT){
							InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
							imm.hideSoftInputFromWindow(webView.getWindowToken(), 0);
							webView.requestFocus();
						String ETurl=actBarUrl.getText().toString();
						String[] urlSymbols=ETurl.split("");
						StringBuilder urlStartB=new StringBuilder();
						String urlStart="";
						String urlEnd=urlSymbols[urlSymbols.length-1].toString();
						if(urlSymbols.length>7){
							for(int i=0;i<8;i++){
								urlStartB.append(urlSymbols[i]);
								if(i==7){
									urlStart=urlStartB.toString();
								}
							}
							StringBuilder newUrlWOhost=new StringBuilder();
							if(urlStart.equals("http://")||urlStart.equals("https:/")){
								webView.loadUrl(ETurl);
							}else{
								String newUrlStart="http://";
								newUrlWOhost.append(newUrlStart);
								newUrlWOhost.append(ETurl);
								String newUrl=newUrlWOhost.toString();
								webView.loadUrl(newUrl);
							}
						}else{
							String newUrlStart="http://";
							StringBuilder newUrlShort=new StringBuilder();
							newUrlShort.append(newUrlStart);
							newUrlShort.append(ETurl);
							String newUrl=newUrlShort.toString();
							webView.loadUrl(newUrl);
						}
					}
					return true;
				}
			});
		actBarUrl.setOnLongClickListener(new View.OnLongClickListener(){

				@Override
				public boolean onLongClick(View v)
				{
					View actBarV=LayInfl.inflate(R.layout.
					return true;
				}
			});
    }

	@Override
	public void onBackPressed()
	{
		super.onBackPressed();
		if(webView.canGoBack()==true){
			webView.goBack();
		}
		if(webView.canGoBack()==false){
			
		}
	}
}

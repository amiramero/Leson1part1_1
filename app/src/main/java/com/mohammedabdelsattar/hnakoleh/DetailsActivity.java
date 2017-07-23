package com.mohammedabdelsattar.hnakoleh;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.squareup.picasso.Picasso;

public class DetailsActivity extends AppCompatActivity {

    ImageView  imageView;
    TextView tvTitle;
    TextView  tvDescription;
    TextView tvPlace;
    TextView tvPrice;
    Button buttonCall;
    double price;


    boolean ShowButton= false;
    String title,description,phone,imgURL,place;
    AdView mAdView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);



        MobileAds.initialize(getApplicationContext(),
                "ca-app-pub-6631041420385537/8487612694");

        mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);


        title= getIntent().getExtras().getString("title");
        description = getIntent().getExtras().getString("description");
        phone = getIntent().getExtras().getString("phone","");
        imgURL= getIntent().getExtras().getString("imgURL");
        place= getIntent().getExtras().getString("place","");
        price = getIntent().getExtras().getDouble("price",0.0);
        ShowButton = getIntent().getExtras().getBoolean("show",false);


setTitle(title);
        imageView = (ImageView)findViewById(R.id.mainimg);
        buttonCall = (Button)findViewById(R.id.btnCall);
        tvTitle =(TextView)findViewById(R.id.tvtitle);
        tvDescription =(TextView)findViewById(R.id.tvdesc);
        tvPlace=(TextView)findViewById(R.id.tvPlace);
        tvPrice=(TextView)findViewById(R.id.tvprice);




        tvTitle.setText(title);
        tvDescription.setText(description);
        buttonCall.setFocusable(false);
        if(!imgURL.equals("")){
            //call picasso
            Picasso.with(this).load(imgURL).into(imageView);

        }

        tvPrice.setText("التكلفة المتوقعه: "+price+"جنية ");
if(ShowButton == false)
    buttonCall.setVisibility(View.GONE);









    }

}

package com.mohammedabdelsattar.hnakoleh;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

public class FilterActivity extends AppCompatActivity {

    private AdView mAdView;

    Button imgsearch;
    EditText etFrom ,etTo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);


        imgsearch = (Button) findViewById(R.id.btnSearch);
        etFrom = (EditText)findViewById(R.id.etFrom);
        etTo = (EditText)findViewById(R.id.etTo);

        MobileAds.initialize(getApplicationContext(),
                "ca-app-pub-6631041420385537/8487612694");

        mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);


        imgsearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(etFrom.getText().toString() == null ||etFrom.getText().toString().equals("") ||
                etTo.getText().toString() == null ||etTo.getText().toString().equals(""))


                    Toast.makeText(getApplicationContext(),"اكتب السعر من كام لكام",Toast.LENGTH_LONG).show();
                else {
                    double from = Double.valueOf(etFrom.getText().toString());
                    double to = Double.valueOf(etTo.getText().toString());
                    if (from > to)
                        Toast.makeText(getApplicationContext(), "اكتب من اكبر من الي", Toast.LENGTH_LONG).show();
                    else {
                        Intent intent = new Intent(FilterActivity.this, MainActivity.class);
                        intent.putExtra("from", from);
                        intent.putExtra("to", to);
                        startActivity(intent);
                    }



                }
            }
        });


    }
}

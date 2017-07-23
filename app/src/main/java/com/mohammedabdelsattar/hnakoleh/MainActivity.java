package com.mohammedabdelsattar.hnakoleh;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.firebase.database.DatabaseReference;
import com.squareup.picasso.Picasso;

public class MainActivity extends AppCompatActivity  {
  RecyclerView recyler;
    DatabaseReference mdatabase;
    DatabaseReference Priceref;
    String searchByPrice="";


    double from ,to;
public  static ProgressDialog pd;
    FirebaseRecyclerAdapter<Blog,bolgviewholder>adapter;
    AdView mAdView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        MobileAds.initialize(getApplicationContext(),
                "ca-app-pub-6631041420385537/8487612694");

        mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        pd = new ProgressDialog(MainActivity.this);
        pd.setMessage("جاري البحث...");
        pd.show();

        // Get the ViewPager and set it's PagerAdapter so that it can display items
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        PagerAdapter pagerAdapter =
                new PagerAdapter(getSupportFragmentManager(), MainActivity.this);
        viewPager.setAdapter(pagerAdapter);

        // Give the TabLayout the ViewPager
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(viewPager);

        // Iterate over all tabs and set the custom view
        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            TabLayout.Tab tab = tabLayout.getTabAt(i);
            tab.setCustomView(pagerAdapter.getTabView(i));
        }


    }

    @Override
    protected void onStart() {
        super.onStart();



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);

        return true;

    }


    EditText input;
public  void showInputDialog() {

    // get prompts.xml view
    LayoutInflater layoutInflater = LayoutInflater.from(MainActivity.this);
    View promptView = layoutInflater.inflate(R.layout.search, null);
    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
    alertDialogBuilder.setView(promptView);

    final EditText editText = (EditText) promptView.findViewById(R.id.pricenumber);
    // setup a dialog window
    alertDialogBuilder.setCancelable(false)
            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    searchByPrice=editText.getText().toString();
                   // filter(searchByPrice);
                }
            })
            .setNegativeButton("Cancel",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });

    // create an alert dialog
    AlertDialog alert = alertDialogBuilder.create();
    alert.show();
}
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();
        if(id==R.id.add)
        {
            Intent i=new Intent(getApplicationContext(),posters.class);
            startActivity(i);
            return true;

        }
        /*else if(id==R.id.take)
        {
            showInputDialog();


            return true;
        }*/
        return super.onOptionsItemSelected(item);
    }



    public static  class bolgviewholder  extends RecyclerView.ViewHolder
    {
       View mview;

        MainActivity r=new MainActivity();

        public bolgviewholder(View itemView) {
            super(itemView);
            mview=itemView;
        }
        public void settitle (String title)
        {
            TextView titles=(TextView)mview.findViewById(R.id.tvtitle);
            titles.setText(title);
        }
        public  void  setdesc(String description)
        {
            TextView desc=(TextView)mview.findViewById(R.id.tvdesc);

            if(description.length() >45)
                desc.setText(description.substring(0,44)+"...");
            else
                desc.setText(description);
            //desc.setText(description);
        }
        public  void setimage(Context m,String image)
        {
            ImageView imageView=(ImageView)mview.findViewById(R.id.imagee);
            Picasso.with(m).load(image).resize(300,300).into(imageView);
        }
        public  void setprice(String price)
        {
            TextView Price=(TextView)mview.findViewById(R.id.tvprice);
            Price.setText(price);
        }
        public void setdelivery(Boolean deliverys)
        {
            TextView delivery=(TextView)mview.findViewById(R.id.tv_delivery);
            delivery.setText("Dilvery : "+String.valueOf(deliverys));
        }

    }
    class PagerAdapter extends FragmentPagerAdapter {

        String tabTitles[] = new String[] { "ديلفري", "أكل بيتي" };
        Context context;

        public PagerAdapter(FragmentManager fm, Context context) {
            super(fm);
            this.context = context;
        }

        @Override
        public int getCount() {
            return tabTitles.length;
        }

        @Override
        public Fragment getItem(int position) {

            switch (position) {
                case 0:
                    return new FastFoodFragment();
                case 1:
                    return new HouseFoodFragment();

            }

            return null;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            // Generate title based on item position
            return tabTitles[position];
        }

        public View getTabView(int position) {
            View tab = LayoutInflater.from(MainActivity.this).inflate(R.layout.custom_tab, null);
            TextView tv = (TextView) tab.findViewById(R.id.custom_text);
            tv.setText(tabTitles[position]);
            return tab;
        }

    }

}

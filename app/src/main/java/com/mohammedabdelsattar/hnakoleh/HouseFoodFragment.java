package com.mohammedabdelsattar.hnakoleh;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class HouseFoodFragment extends Fragment {

    RecyclerView recyler;
    DatabaseReference mdatabase;
    DatabaseReference Priceref;
    String searchByPrice="";
   //ImageButton searchs;
    double from ,to;
    ArrayList<Blog>savedata;
    FirebaseRecyclerAdapter<Blog,MainActivity.bolgviewholder> adapter;
    public HouseFoodFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_blank_fragment2, container, false);
        from =getActivity().getIntent().getExtras().getDouble("from");
        to =  getActivity().getIntent().getExtras().getDouble("to");

        mdatabase= FirebaseDatabase.getInstance().getReference().child("Blog");
        //Priceref= FirebaseDatabase.getInstance().getReference().child("Ko859n3gBxcwGSPjQJl");
        recyler=(RecyclerView)rootView.findViewById(R.id.liste2);
       // searchs=(ImageButton)rootView.findViewById(R.id.search);
        recyler.setHasFixedSize(true);
        recyler.setLayoutManager(new GridLayoutManager(getActivity(),2));

        return rootView;
    }


    @Override
    public void onStart()
    {
        super.onStart();
        filter();
    }


public  ArrayList<Blog> Yourlis;
    public void filter()
    {

        Yourlis = new ArrayList<Blog>();
        //savedata=new ArrayList<Blog>();
       // mdatabase.orderByChild("delivery").equalTo(true);
        mdatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                Yourlis.clear();
                Blog blogs,x;
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren())
                {
                    blogs = postSnapshot.getValue(Blog.class);
                    x=new Blog(blogs.title,blogs.desc, blogs.image,blogs.price,blogs.delivery);
    if(x.price >= from && x.price <= to && x.delivery != true)
                    Yourlis.add(x);
                    Log.e("heyhey","this is me"+ String.valueOf(Yourlis.size()));
                }

                if(MainActivity.pd != null && MainActivity.pd.isShowing())
                    MainActivity.pd.dismiss();

                Adapter adapter=new Adapter(Yourlis,getActivity());
                recyler.setAdapter(adapter);



            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });





    }
    public  void showInputDialog() {

        // get prompts.xml view
        LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
        View promptView = layoutInflater.inflate(R.layout.search, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        alertDialogBuilder.setView(promptView);

        final EditText editText = (EditText) promptView.findViewById(R.id.pricenumber);
        final EditText editText2 = (EditText) promptView.findViewById(R.id.priceto);

        // setup a dialog window
        alertDialogBuilder.setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        from=Double.parseDouble(editText.getText().toString());
                        to=Double.parseDouble(editText2.getText().toString());
                        adapter = new FirebaseRecyclerAdapter<Blog, MainActivity.bolgviewholder>(Blog.class, R.layout.cardviewrow, MainActivity.bolgviewholder.class,  mdatabase.orderByChild("price").startAt(from).endAt(to)) {
                            @Override
                            protected void populateViewHolder(MainActivity.bolgviewholder viewHolder, Blog model, int position) {
                                viewHolder.settitle(model.title);
                                viewHolder.setprice(model.price+" جنية");
                                if(model.desc.length() >45)
                                viewHolder.setdesc(model.desc.substring(0,44)+"...");
                                else
                                    viewHolder.setdesc(model.desc);
                                viewHolder.setdelivery(model.delivery);
                                viewHolder.setimage(getActivity(), model.image);
                            }
                        };
                        recyler.setAdapter(adapter);

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
}

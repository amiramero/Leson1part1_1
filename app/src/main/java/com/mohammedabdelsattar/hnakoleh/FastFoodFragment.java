package com.mohammedabdelsattar.hnakoleh;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class FastFoodFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    RecyclerView recyler;
    DatabaseReference mdatabase;
    DatabaseReference Priceref;
    String searchByPrice="";
    double from ,to;
    FirebaseRecyclerAdapter<Blog,MainActivity.bolgviewholder> adapter;
    public FastFoodFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_blank, container, false);
        from =getActivity().getIntent().getExtras().getDouble("from");
        to =  getActivity().getIntent().getExtras().getDouble("to");

        mdatabase= FirebaseDatabase.getInstance().getReference().child("Blog");
        //Priceref= FirebaseDatabase.getInstance().getReference().child("Ko859n3gBxcwGSPjQJl");
        recyler=(RecyclerView)rootView.findViewById(R.id.liste);
        recyler.setHasFixedSize(true);
        recyler.setLayoutManager(new GridLayoutManager(getActivity(),2));
        return rootView;
    }

    @Override
    public void onStart()
    {
        super.onStart();
      /*  adapter = new FirebaseRecyclerAdapter<Blog, MainActivity.bolgviewholder>(Blog.class, R.layout.cardviewrow, MainActivity.bolgviewholder.class,  mdatabase) {
            @Override
            protected void populateViewHolder(MainActivity.bolgviewholder viewHolder, Blog model, int position) {
                viewHolder.settitle(model.title);
                viewHolder.setprice(model.price+"");
                viewHolder.setdesc(model.desc);
                viewHolder.setimage(getActivity(), model.image);

            }
        };
        recyler.setAdapter(adapter);*/
        filter();
    }

    public  ArrayList<Blog> Yourlis;

    public void filter()
    {
        //savedata=new ArrayList<Blog>();
        // mdatabase.orderByChild("delivery").equalTo(true);
        mdatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {



                Yourlis = new ArrayList<Blog>();
                Yourlis.clear();
                Blog blogs,x;
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren())
                {
                    blogs = postSnapshot.getValue(Blog.class);

                    x=new Blog(blogs.title,blogs.desc, blogs.image,blogs.price,blogs.delivery,blogs.phone);

                    if(x.price >= from && x.price <= to && x.getDelivery() == true)
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


    /*public void filter()
    {
        Query query= mdatabase.orderByChild("delivery").equalTo(true);
        query = query.();

        adapter = new FirebaseRecyclerAdapter<Blog, MainActivity.bolgviewholder>(Blog.class, R.layout.cardviewrow, MainActivity.bolgviewholder.class,query)
        {
            @Override
            protected void populateViewHolder(MainActivity.bolgviewholder viewHolder, Blog model, int position) {
                viewHolder.settitle("Title is : "+model.title);
                viewHolder.setprice("Price is : "+model.price+"");
                viewHolder.setdesc("Description : "+model.desc);
                viewHolder.setdelivery(model.delivery);
                viewHolder.setimage(getActivity(), model.image);
            }
        };
        recyler.setAdapter(adapter);

    }*/
}



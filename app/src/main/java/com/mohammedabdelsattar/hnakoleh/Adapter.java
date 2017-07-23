package com.mohammedabdelsattar.hnakoleh;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * Created by mabdelsattar on 7/19/2017.
 */

public class Adapter extends RecyclerView.Adapter<MainActivity.bolgviewholder> {


    public Context context;
    public ArrayList<Blog> Blogses;
    public  Adapter(){

    }

    public Adapter(ArrayList<Blog> blogses, Context context) {
        Blogses = blogses;
        this.context =context;
    }

    @Override
    public MainActivity.bolgviewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardviewrow, parent, false);
        return new MainActivity.bolgviewholder(itemView);

    }
    @Override
    public void onBindViewHolder(MainActivity.bolgviewholder viewHolder, int position) {

        final Blog model = Blogses.get(position);
        viewHolder.settitle(model.title);
        viewHolder.setprice(model.price+" جنية");
        viewHolder.setdesc(model.desc);
        viewHolder.setdelivery(model.delivery);
        viewHolder.setimage(context, model.image);


        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent =new Intent(context,DetailsActivity.class);
                intent.putExtra("title",model.title);
                intent.putExtra("description",model.desc);
                intent.putExtra("price",model.price);
                intent.putExtra("imgURL",model.image);
                if(model.delivery == true)
                    intent.putExtra("show",true);

                context.startActivity(intent);


            }
        });
    }

    @Override
    public int getItemCount() {
        return Blogses.size();
    }
}

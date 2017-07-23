package com.mohammedabdelsattar.hnakoleh;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class posters extends AppCompatActivity {
ImageButton selectedimage;
    EditText title, descriptionss,price;
    static final int request=1;
    Button submit;
    Uri imageuri=null;
    ProgressDialog mprogress;
    DatabaseReference mdaabase;
    DatabaseReference priceref;
     private StorageReference mstorage;
    RadioButton delivery;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_posters);
        title=(EditText)findViewById(R.id.title);
        descriptionss =(EditText)findViewById(R.id.dec);
        submit=(Button)findViewById(R.id.submit);
        delivery=(RadioButton)findViewById(R.id.delivery) ;
        price=(EditText)findViewById(R.id.price);
        mprogress=new ProgressDialog(this);
        mstorage= FirebaseStorage.getInstance().getReference();
       mdaabase= FirebaseDatabase.getInstance().getReference().child("Blog");
        selectedimage=(ImageButton)findViewById(R.id.imageselected);

        selectedimage.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent i=new Intent(Intent.ACTION_GET_CONTENT);
                i.setType("image/*");
                startActivityForResult(i,request);
            }
        });
        submit.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                startposting();
            }
        });

    }

    private void startposting()
    {
        mprogress.setMessage("post is done ");

        final String titlevalue=title.getText().toString().trim();
        final String postvalue= descriptionss.getText().toString().trim();
        final double prices=Double.parseDouble( price.getText().toString().trim());
        final Boolean del=delivery.isChecked();
        if(!TextUtils.isEmpty(titlevalue)&&!TextUtils.isEmpty(postvalue)&&imageuri!=null)
        {
           StorageReference filepath=mstorage.child("images").child(imageuri.getLastPathSegment());
            filepath.putFile(imageuri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>()
            {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot)
                {
                    @SuppressWarnings("VisibleForTests")
                    Uri downloadUrl = taskSnapshot.getMetadata().getDownloadUrl();
                     DatabaseReference newpost=mdaabase.push();
                     newpost.child("title").setValue(titlevalue);
                    newpost.child("desc").setValue(postvalue);
                    newpost.child("price").setValue(prices);
                    newpost.child("delivery").setValue(del);
                    newpost.child("image").setValue(downloadUrl.toString());
                    mprogress.dismiss();
                }
            });
        }

        mprogress.show();
        startActivity(new Intent(getApplicationContext(),FilterActivity.class));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==request&& requestCode==RESULT_OK);
        {
           imageuri=data.getData();
            selectedimage.setImageURI(imageuri);
        }
    }

}

package com.example.gallerytask;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Uri imageUri;
    RecyclerView recyclerView;
    Button addimg;
    String filePath = "https://api.learn2crack.com/android/images/marshmallow.png";
    private static final int REQUEST_CODE_ADD_IMAGE = 1000;
    Utility tr;
    List<ItemData> itemdatalist = new ArrayList();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView) findViewById(R.id.Rview);
        addimg = (Button) findViewById(R.id.button);


        addimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Image"), REQUEST_CODE_ADD_IMAGE);




                Toast.makeText(MainActivity.this,filePath,Toast.LENGTH_SHORT).show();
                Log.e("filepath",filePath);
            }
        });





        ItemData img1 = new ItemData();
        img1.setName("first image");
        img1.setImage(filePath);



        itemdatalist.add(img1);

        ItemData img2 = new ItemData();
        img2.setName("second image");
        img2.setImage("/storage/emulated/0/WhatsApp/Media/WhatsApp Images/IMG-20170620-WA0008.jpg");

        itemdatalist.add(img2);

        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        RecyclerAdapter recyclerAdapter = new RecyclerAdapter(itemdatalist, MainActivity.this);

        recyclerView.setAdapter(recyclerAdapter);


    }

    @Override


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        boolean b = tr.checkPermission(MainActivity.this);
        if (b == true) {
            if (requestCode == REQUEST_CODE_ADD_IMAGE) {
                if (resultCode == RESULT_OK) {



                    imageUri = data.getData();
                    String wholeID = DocumentsContract.getDocumentId(imageUri);


                    String id = wholeID.split(":")[1];

                    String[] column = {MediaStore.Images.Media.DATA};

                    // where id is equal to
                    String sel = MediaStore.Images.Media._ID + "=?";


                    Cursor cursor = getApplicationContext().getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                            column, sel, new String[]{id}, null);

                    int columnIndex = cursor.getColumnIndex(column[0]);

                    if (cursor.moveToFirst()) {
                        filePath = cursor.getString(columnIndex);

                        Log.e("inside ",filePath);

                        ItemData img1 = new ItemData();
                        img1.setName("image"+itemdatalist.size());
                        img1.setImage(filePath);

                        itemdatalist.add(img1);
                        RecyclerAdapter recyclerAdapter = new RecyclerAdapter(itemdatalist, MainActivity.this);

                        recyclerView.setAdapter(recyclerAdapter);

                    }
                    cursor.close();
                }

            }

        }
    }

    }

package com.example.gallerytask;


import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.List;

import static com.example.gallerytask.Utility.MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE;

/**
 * Created by Allen on 19-06-2017.
 */

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder>{

    List<ItemData> itemDataList;
    Context context;

    public RecyclerAdapter(List<ItemData> itemDataList,Context context)
    {
        this.itemDataList=itemDataList;
        this.context=context;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemLayoutView= LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout,null);

        ViewHolder viewHolder=new ViewHolder(itemLayoutView);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        holder.txtViewTitle.setText(itemDataList.get(position).getName());

     /*  Picasso.with(context).load(itemDataList.get(position).getImage())
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher).into(holder.imgViewIcon);
*/
        File imgFile = new File(itemDataList.get(position).getImage());

        if(imgFile.exists()) {

            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());



            holder.imgViewIcon.setImageBitmap(myBitmap);
        }


        holder.del.setOnClickListener(new View.OnClickListener() {

            String selecteditem=itemDataList.get(position).getName();

            @Override
            public void onClick(View view) {
                AlertDialog.Builder alertBuilder = new AlertDialog.Builder(context);
                alertBuilder.setCancelable(true);
                alertBuilder.setTitle("Delete");
                alertBuilder.setMessage("Are you sure???");
                alertBuilder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
                    public void onClick(DialogInterface dialog, int which) {

                        for(ItemData item:itemDataList)

                        {
                            String temp=item.getName();
                            if(temp.equals(selecteditem))
                            {
                                itemDataList.remove(item);
                                notifyDataSetChanged();
                            }
                        }




                    }
                });
                AlertDialog alert = alertBuilder.create();
                alert.show();

            }
        });



    }

    @Override
    public int getItemCount() {
        return itemDataList.size();
    }

    //the viewholder class is to link the layout xml for a single item in the list//
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView txtViewTitle;
        public ImageView imgViewIcon;
        public Button del;


        //creating constructor to pass the view//
        public ViewHolder(View itemLayoutView) {

            super(itemLayoutView);// java function.It initialises base parent content
            txtViewTitle=(TextView)itemLayoutView.findViewById(R.id.textView);
            imgViewIcon=(ImageView)itemLayoutView.findViewById(R.id.imageView);
            del=(Button)itemLayoutView.findViewById(R.id.button2);


        }
    }
}



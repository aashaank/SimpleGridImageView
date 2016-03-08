package com.example.aashankpratap.samplegridimages;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

/**
 * Created by AASHANK PRATAP on 3/8/2016.
 */
public class CustomerGridView extends BaseAdapter {

    Context context;
    List<String> imagePath ;
    public static final String TAG = "CustomerGridView";

    public CustomerGridView (Context c , List<String> listofImagesPath) {
        Log.d(TAG, "CustomerView Constructor");
        context = c;
        imagePath = listofImagesPath;
    }

    @Override
    public int getCount() {
        Log.d(TAG, "getCount()");
        if(imagePath!=null) {
            Log.d(TAG, "imagePath.size : " + imagePath.size());
            return imagePath.size();
        }
        else {
            Log.d(TAG, "imagePath.size : " + 0);
            return 0;
        }
    }

    @Override
    public Object getItem(int position) {
        Log.d(TAG, "getItem : "+position);
        return position;
    }

    @Override
    public long getItemId(int position) {
        Log.d(TAG, "getItemId : "+position);
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        View grid ;
        BitmapFactory.Options bfOptions = new BitmapFactory.Options();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        Log.d(TAG, "getView()");
        bfOptions.inDither = false;
        bfOptions.inPurgeable = true;
        bfOptions.inInputShareable = true;
        bfOptions.inTempStorage = new byte[32*1024];
        if(convertView == null){/*
            imageView = new ImageView(context);
            Log.d(TAG, "getView() convertView is null");
            imageView.setLayoutParams(new GridView.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
            ));
            imageView.setPadding(0,0,0,0);*/
            grid = new View(context);
            grid = inflater.inflate(R.layout.grid_view_layout,null);
            imageView = (ImageView) grid.findViewById(R.id.imageView);

            FileInputStream fs = null;
            Bitmap bm;
            try {
                File file = new File(imagePath.get(position).toString());
                fs = new FileInputStream(file);
                Log.d(TAG, "getView() fs");
                if(fs!=null) {
                    bm = BitmapFactory.decodeFileDescriptor(fs.getFD(),null,bfOptions);
                    imageView.setImageBitmap(bm);
                    imageView.setId(position);
                    Log.d(TAG,"getView() fs : "+position);
                    imageView.setLayoutParams(new GridView.LayoutParams(200,160));
                }
            } catch(IOException e) {
                e.printStackTrace();
            }
            finally {
                if(fs!=null){
                    try {
                        fs.close();
                    }
                    catch(IOException e) {
                        e.printStackTrace();
                    }
                }
            }

        } else {
            Log.d(TAG, "getView() convertView is not null");
            grid = (View) convertView;
        }

        /*FileInputStream fs = null;
        Bitmap bm;
        try {
            File file = new File(imagePath.get(position).toString());
            fs = new FileInputStream(file);
            Log.d(TAG, "getView() fs");
            if(fs!=null) {
                bm = BitmapFactory.decodeFileDescriptor(fs.getFD(),null,bfOptions);
                imageView.setImageBitmap(bm);
                imageView.setId(position);
                imageView.setLayoutParams(new GridView.LayoutParams(200,160));
            }
        } catch(IOException e) {
            e.printStackTrace();
        }
        finally {
            if(fs!=null){
                try {
                    fs.close();
                }
                catch(IOException e) {
                    e.printStackTrace();
                }
            }
        }
        Log.d(TAG, "getView() imageView is returned : "+imageView);
        */

        Log.d(TAG, "getView() imageView is returned : "+grid);
        return grid;
    }
}

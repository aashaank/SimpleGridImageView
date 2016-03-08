package com.example.aashankpratap.samplegridimages;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;


public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    Button btn;
    GridView grid;

    final int CAMERA_CAPTURE = 1;
    private DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private List<String> listofImagesPath = null;
    public static final String GridViewDemo_ImagePath = Environment.getExternalStorageDirectory().getAbsolutePath()+"/GridViewDemo/";

    public static final String TAG = "MainActivity";

    CustomerGridView gridAdapter ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn = (Button) findViewById(R.id.btn);
        grid = (GridView) findViewById(R.id.grid);
        Log.d(TAG, "onCreate");
        setCustomGridAdapterView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        btn.setOnClickListener(this);
        Log.d(TAG, "onResume");
        setCustomGridAdapterView();
    }

    private void setCustomGridAdapterView() {
        listofImagesPath = retriveCapturedImage();
        Log.d(TAG, "setCustomGridAdapterView");
        if(listofImagesPath!=null) {
            gridAdapter = new CustomerGridView(this, listofImagesPath);
            grid.setAdapter(gridAdapter);
        }
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.btn :
            {
                Log.d(TAG, "onClick");
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent,CAMERA_CAPTURE);
                break;
            }
        }
    }

    private List<String> retriveCapturedImage() {
        Log.d(TAG, "retriveCapturedImage");
        List<String> imageFileList = new ArrayList<String>();
        File f = new File(GridViewDemo_ImagePath);
        if(f.exists()){
            File[] files = f.listFiles();
            Arrays.sort(files);
            for(int i=0;i<files.length;i++){
                File file = files[i];
                if(file.isDirectory())
                    continue;

                Log.d(TAG, "retriveCapturedImage file path : "+file.getPath());
                imageFileList.add(file.getPath());
            }
        }
        return imageFileList;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK && requestCode == CAMERA_CAPTURE) {
            Bundle extras = data.getExtras();
            Bitmap image = extras.getParcelable("data");
            String imgCurTime = dateFormat.format(new Date());
            File imageDirectory = new File(GridViewDemo_ImagePath);
            imageDirectory.mkdirs();
            String _path = GridViewDemo_ImagePath+imgCurTime+".jpg";

            try {
                FileOutputStream out = new FileOutputStream(_path);
                image.compress(Bitmap.CompressFormat.JPEG,90,out);
                out.close();
            } catch (FileNotFoundException e) {
                e.getMessage();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

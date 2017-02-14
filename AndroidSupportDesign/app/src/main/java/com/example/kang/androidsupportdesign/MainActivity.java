package com.example.kang.androidsupportdesign;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.File;

public class MainActivity extends AppCompatActivity {
    FloatingActionButton fab;

    private static final int PICK_FROM_CAMERA = 0;
    private static final int PICK_FROM_ALBUM = 1;
    private Uri mimageCaptureUri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fab = (FloatingActionButton)findViewById(R.id.FAB);




        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(MainActivity.this);
                dialog.setContentView(R.layout.marker_dialog);
//                final EditDialog dialog = new EditDialog(MainActivity.this);

                dialog.findViewById(R.id.btn_photo).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        String url = "tmp_" + String.valueOf(System.currentTimeMillis())+ ".jpg";
                        mimageCaptureUri = Uri.fromFile(new File(Environment.getDownloadCacheDirectory(),url));

                        i.putExtra(MediaStore.EXTRA_OUTPUT,mimageCaptureUri);
                        startActivityForResult(i,PICK_FROM_CAMERA);

                    }
                });

                dialog.findViewById(R.id.btn_gallery).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(Intent.ACTION_PICK);
                        i.setType(MediaStore.Images.Media.CONTENT_TYPE);
                        startActivityForResult(i,PICK_FROM_ALBUM);
                    }
                });

                dialog.findViewById(R.id.btn_add).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                dialog.setOnShowListener(new DialogInterface.OnShowListener() {
                    @Override
                    public void onShow(DialogInterface dialog) {

                    }
                });

                dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {

                    }
                });
                dialog.show();
            }
        });


    }
}

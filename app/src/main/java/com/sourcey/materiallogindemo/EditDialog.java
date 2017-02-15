package com.sourcey.materiallogindemo;

/**
 * Created by Kang on 2017. 2. 14..
 */


import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;


/**
 * Created by kjy on 2016-04-11.
 */
public class EditDialog extends Dialog{

    public EditDialog(Context context) {
        super(context);
    }

    RelativeLayout dialog;
    EditText editTitle;
    EditText editDesc;
    Button btnPhoto;
    Button btnGallery;
    Button btnAdd;
    static final int PICK_CONTACT_REQUEST = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.marker_dialog);

        btnAdd = (Button)findViewById(R.id.btn_add);
        editTitle = (EditText) findViewById(R.id.edit_title);
        editDesc = (EditText)findViewById(R.id.edit_desc);
        btnPhoto = (Button)findViewById(R.id.btn_photo);
        btnGallery = (Button)findViewById(R.id.btn_gallery);



        btnAdd.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

               dismiss();
                return false;
            }
        });
    }

    public String getTitle() {
        return editTitle.getText().toString();
    }

    public String getDesc(){
        return editDesc.getText().toString();
    }

    public JSONObject getForm() throws JSONException {
        JSONObject formData = new JSONObject("" +
                "{\"title\":"+getTitle()+"," +
                "\"desc\":"+getDesc()+
                "}");
        return formData;
    }

    public void clearForm() {
        editDesc.setText("");
        editTitle.setText("");
    }
}

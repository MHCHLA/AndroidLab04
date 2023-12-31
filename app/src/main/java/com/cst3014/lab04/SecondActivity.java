package com.cst3014.lab04;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class SecondActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        Intent fromPrevious = getIntent();
        Button callBtn = (Button) findViewById(R.id.callBtn);
        TextView text = (TextView) findViewById(R.id.textViewPage2);
        EditText phoneNumber = (EditText) findViewById(R.id.phoneNumber1);
        String emailAddress = fromPrevious.getStringExtra("EmailAddress");
        text.setText("Welcome back "+ emailAddress);

        Intent cameraIntent = new Intent (MediaStore.ACTION_IMAGE_CAPTURE);

        callBtn.setOnClickListener( clk-> {
            Intent call = new Intent(Intent.ACTION_DIAL);
            call.setData(Uri.parse("tel:" + phoneNumber.getText().toString()));
            startActivity( call);
        } );
        ImageView profileImage = (ImageView) findViewById(R.id.profileImg);

        File file = new File( getFilesDir(), "Picture.png");
        if(file.exists())
        {
            Bitmap theImage = BitmapFactory.decodeFile("Picture.png");
            profileImage.setImageBitmap(theImage);
        }

        ActivityResultLauncher<Intent> cameraResult = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        Bitmap thumbnail = null;
                        if (result.getResultCode() == Activity.RESULT_OK) {
                            Intent data = result.getData();
                            thumbnail = data.getParcelableExtra("data");
                            profileImage.setImageBitmap(thumbnail);
                        }

                        FileOutputStream fOut = null;
                        try { fOut = openFileOutput("Picture.png", Context.MODE_PRIVATE);
                            thumbnail.compress(Bitmap.CompressFormat.PNG, 100, fOut);
                            fOut.flush();
                            fOut.close();
                        }
                        catch (IOException e)
                        { e.printStackTrace();
                        }
                    }
                }
        );
        cameraResult.launch(cameraIntent);
    }
    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences prefs = getSharedPreferences("MyData", Context.MODE_PRIVATE);
    }
}

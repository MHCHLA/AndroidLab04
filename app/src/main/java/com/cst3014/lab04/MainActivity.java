package com.cst3014.lab04;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button loginButton = (Button) findViewById(R.id.loginButton);
        EditText emailEditText =  findViewById(R.id.emailEditText);

        Intent nextPage = new Intent( MainActivity.this, SecondActivity.class);
        Intent: nextPage.putExtra( "EmailAddress", emailEditText.getText().toString());

        loginButton.setOnClickListener( clk-> {startActivity( nextPage); } );

        SharedPreferences prefs = getSharedPreferences("MyData", Context.MODE_PRIVATE);
        prefs.getString("VariableName", "");
        String emailAddress = prefs.getString("LoginName", "");
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("LoginName", emailEditText.getText().toString());
        editor.apply();



    }
}

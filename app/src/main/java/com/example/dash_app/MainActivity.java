package com.example.dash_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onButtonClick (View view){
//        Toast.makeText(MainActivity.this,"you have clicked Button3",Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(MainActivity.this,WebActivity.class);
        TextView textView=findViewById(R.id.url_input);
        CharSequence url=textView.getText();
        intent.putExtra("URL",url);
        startActivity(intent);
    }

}
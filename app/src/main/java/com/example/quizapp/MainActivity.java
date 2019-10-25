package com.example.quizapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button btnStart;
    EditText edtName, edtWNumber;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnStart = findViewById(R.id.btnStart);
        edtName = findViewById(R.id.edtName);
        edtWNumber = findViewById(R.id.edtWNumber);

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(edtName.getText().toString().length() > 0 && edtWNumber.getText().toString().length() > 0){
                    Intent intent = new Intent(v.getContext(), Main2Activity.class);
                    startActivity(intent);
                }else{
                    Toast.makeText(getBaseContext(), "Please fill out both fields.", Toast.LENGTH_LONG).show();
                }
            }
        });




    }
}

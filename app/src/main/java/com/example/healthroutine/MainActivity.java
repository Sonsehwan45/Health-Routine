package com.example.healthroutine;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btCreateRoutine = findViewById(R.id.bt_create_routine);

        btCreateRoutine.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, CreateRoutine.class);
            startActivity(intent);
            Log.d("MainPage", "루틴 생성 버튼 클릭");
        });
    }
}
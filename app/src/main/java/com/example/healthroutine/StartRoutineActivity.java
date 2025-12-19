package com.example.healthroutine;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.datepicker.MaterialDatePicker;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

public class StartRoutineActivity extends AppCompatActivity {

    private TextView tvRoutineName;
    private TextView tvDay;
    private ImageView imCalendar;
    private RecyclerView rv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_start_routine);

        tvRoutineName = findViewById(R.id.tv_routine_name);
        tvDay = findViewById(R.id.tv_calender_start_day);
        imCalendar = findViewById(R.id.iv_calendar);

        rv = findViewById(R.id.rv_exercise_list);
        rv.setLayoutManager(new LinearLayoutManager(this));

        Intent intent = getIntent();
        String name = intent.getStringExtra("RoutineName");
        ArrayList<ExerciseItem> exerciseList = (ArrayList<ExerciseItem>)intent.getSerializableExtra("selectedRoutine");

        //전달 받은 데이터로 루틴 이름 변경
        tvRoutineName.setText(name);

        if (exerciseList != null) {
            CreateRoutineAdapter adapter = new CreateRoutineAdapter(this, exerciseList);
            rv.setAdapter(adapter);
        }

        imCalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePicker();
            }
        });

        // 뒤로가기 버튼
        findViewById(R.id.btn_back).setOnClickListener(v -> finish());
    }

    private void showDatePicker(){
        //빌더 생성
        MaterialDatePicker.Builder<Long> builder = MaterialDatePicker.Builder.datePicker();

        //다이얼로그 상단 타이틀 설정
        builder.setTitleText("루틴 날짜 선택");

        //초기 날짜를 오늘 날짜로 설정
        builder.setSelection(MaterialDatePicker.todayInUtcMilliseconds());

        //디자인 캘린더 선택으로 설정
        builder.setInputMode(MaterialDatePicker.INPUT_MODE_CALENDAR);

        //DatePicker 객체 생성
        MaterialDatePicker<Long> datePicker = builder.build();

        datePicker.addOnPositiveButtonClickListener(selection -> {
            //UTC 시간을 한국 시간대(KST)로 변환
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy년 MM월 dd일 (EEE)", Locale.KOREA);

            //Date 객체로 변환
            java.util.Date date = new java.util.Date(selection);

            //텍스트뷰 업데이트
            tvDay.setText(sdf.format(date));
        });

        datePicker.show(getSupportFragmentManager(), "DATE_PICKER");
    }
}
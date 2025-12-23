package com.example.healthroutine;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.datepicker.MaterialDatePicker;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

// StartRoutineAdapter.OnItemClickListener 구현
public class StartRoutineActivity extends AppCompatActivity implements StartRoutineAdapter.OnItemClickListener {

    private TextView tvRoutineName;
    private TextView tvDay;
    private ImageView imCalendar;
    private TextView tvTimer; // 타이머 텍스트뷰
    private Button btnAddExercise;
    private Button btnSaveRecord;
    private RecyclerView rv;
    private StartRoutineAdapter adapter;
    private CountDownTimer countDownTimer;
    private boolean isTimerRunning = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_start_routine);

        tvRoutineName = findViewById(R.id.tv_routine_name);
        tvDay = findViewById(R.id.tv_calender_start_day);
        imCalendar = findViewById(R.id.iv_calendar);
        btnAddExercise = findViewById(R.id.btn_add_exercise);

        btnSaveRecord = findViewById(R.id.btn_save_record);

        tvTimer = findViewById(R.id.tv_timer);

        rv = findViewById(R.id.rv_exercise_list);
        rv.setLayoutManager(new LinearLayoutManager(this));

        // 기본 날짜 설정(오늘)
        SimpleDateFormat defaultSdf = new SimpleDateFormat("yyyy년 MM월 dd일 (EEE)", Locale.KOREA);
        tvDay.setText(defaultSdf.format(new Date()));

        Intent intent = getIntent();
        String name = intent.getStringExtra("RoutineName");
        ArrayList<ExerciseItem> exerciseList = (ArrayList<ExerciseItem>) intent.getSerializableExtra("selectedRoutine");

        tvRoutineName.setText(name);

        if (exerciseList != null) {
            adapter = new StartRoutineAdapter(this, exerciseList, this);
            rv.setAdapter(adapter);
        }

        imCalendar.setOnClickListener(v -> showDatePicker());

        btnAddExercise.setOnClickListener(v -> {
            if (exerciseList != null) {
                exerciseList.add(new ExerciseItem());
                adapter.notifyItemInserted(exerciseList.size() - 1);
                rv.scrollToPosition(exerciseList.size() - 1);
            }
        });

        btnSaveRecord.setOnClickListener(v -> {
            if (exerciseList == null || exerciseList.isEmpty()) {
                Toast.makeText(this, "운동을 최소 1개 이상 추가해주세요.", Toast.LENGTH_SHORT).show();
                return;
            }

            WorkOutHistoryItem workOutHistoryItem = new WorkOutHistoryItem(
                    tvDay.getText().toString(),
                    name,
                    exerciseList
            );

            ArrayList<WorkOutHistoryItem> historyList = SprefsManager.getWorkoutHistoryList(this);
            historyList.add(workOutHistoryItem);
            SprefsManager.setWorkoutHistoryList(this, historyList);

            Toast.makeText(this, "운동이 기록되었습니다!", Toast.LENGTH_SHORT).show();

            Intent nextIntent = new Intent(StartRoutineActivity.this, activity_workout_history.class);
            nextIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(nextIntent);
            finish();
        });

        findViewById(R.id.btn_back).setOnClickListener(v -> finish());
    }

    private void showDatePicker() {
        MaterialDatePicker.Builder<Long> builder = MaterialDatePicker.Builder.datePicker();
        builder.setTitleText("운동 날짜 선택");
        builder.setSelection(MaterialDatePicker.todayInUtcMilliseconds());
        builder.setInputMode(MaterialDatePicker.INPUT_MODE_CALENDAR);

        MaterialDatePicker<Long> datePicker = builder.build();

        datePicker.addOnPositiveButtonClickListener(selection -> {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy년 MM월 dd일 (EEE)", Locale.KOREA);
            Date date = new Date(selection);
            tvDay.setText(sdf.format(date));
        });

        datePicker.show(getSupportFragmentManager(), "DATE_PICKER");
    }

    @Override
    public void onSetCompleted(int restTime) {
        startRestTimer(restTime);
    }

    private void startRestTimer(int seconds) {
        // 기존 타이머가 돌고 있다면 취소
        if (isTimerRunning) {
            if (countDownTimer != null) countDownTimer.cancel();
        }

        // 타이머 UI 보이기
        tvTimer.setVisibility(View.VISIBLE);

        // 밀리초 변환
        long duration = seconds * 1000L;

        countDownTimer = new CountDownTimer(duration, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                isTimerRunning = true;
                int remainingSeconds = (int) (millisUntilFinished / 1000);

                // 남은 시간 텍스트 업데이트
                tvTimer.setText("휴식 시간: " + remainingSeconds + "초");
            }

            @Override
            public void onFinish() {
                isTimerRunning = false;
                tvTimer.setText("휴식 종료! 다음 세트를 시작하세요.");

                // 3초 뒤에 타이머 숨기기 (선택 사항)
                tvTimer.postDelayed(() -> tvTimer.setVisibility(View.GONE), 3000);
            }
        }.start();

        Toast.makeText(this, seconds + "초 휴식 시작", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
    }
}
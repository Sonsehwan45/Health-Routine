package com.example.healthroutine;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CreateRoutine extends AppCompatActivity {

    private RecyclerView rvExerciseList;
    private Button btnAddExercise, btnSaveRoutine;

    private ArrayList<ExerciseItem> exerciseList;
    private RoutineAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_routine);

        // 뷰 연결
        rvExerciseList = findViewById(R.id.rv_exercise_list);
        btnAddExercise = findViewById(R.id.btn_add_exercise);
        btnSaveRoutine = findViewById(R.id.btn_save_routine);

        // 데이터 초기화
        exerciseList = new ArrayList<>();
        // 처음에 빈 운동 카드 하나 추가해두기
        exerciseList.add(new ExerciseItem());

        // 어댑터 설정
        adapter = new RoutineAdapter(this, exerciseList);
        rvExerciseList.setLayoutManager(new LinearLayoutManager(this));
        rvExerciseList.setAdapter(adapter);

        // [운동 추가] 버튼 클릭
        btnAddExercise.setOnClickListener(v -> {
            exerciseList.add(new ExerciseItem());
            adapter.notifyItemInserted(exerciseList.size() - 1);
            // 추가된 맨 아래로 스크롤
            rvExerciseList.scrollToPosition(exerciseList.size() - 1);
        });

        // [저장] 버튼 클릭
        btnSaveRoutine.setOnClickListener(v -> {
            // 여기에 DB 저장 로직 구현
            // 예시: 첫 번째 운동 이름 확인
            if (!exerciseList.isEmpty()) {
                String firstName = exerciseList.get(0).getName();
                Toast.makeText(this, "저장됨! 첫 운동: " + firstName, Toast.LENGTH_SHORT).show();
            }
            finish(); // 화면 종료
        });

        // 뒤로가기 버튼
        findViewById(R.id.btn_back).setOnClickListener(v -> finish());
    }
}
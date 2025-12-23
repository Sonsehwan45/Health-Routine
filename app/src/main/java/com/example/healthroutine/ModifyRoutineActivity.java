package com.example.healthroutine;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ModifyRoutineActivity extends AppCompatActivity {

    private RecyclerView rvExerciseList;
    private EditText etRoutineName;
    private Button btnAddExercise;
    private Button btnModifyRoutine;

    private ArrayList<ExerciseItem> exerciseList;
    private CreateRoutineAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_routine);

        etRoutineName = findViewById(R.id.et_routineName);
        rvExerciseList = findViewById(R.id.rv_exercise_list);
        btnAddExercise = findViewById(R.id.btn_add_exercise);
        btnModifyRoutine = findViewById(R.id.btn_save_routine);

        Intent getIntent = getIntent();

        exerciseList = (ArrayList<ExerciseItem>) getIntent.getSerializableExtra("selectedRoutine");

        if(exerciseList == null){
            exerciseList = new ArrayList<>();
        }
        int position = getIntent.getIntExtra("position", -1);
        String routineName = getIntent.getStringExtra("routineName");

        if(routineName != null){
            etRoutineName.setText(routineName);
        }

        // 어댑터 설정
        adapter = new CreateRoutineAdapter(this, exerciseList);
        rvExerciseList.setLayoutManager(new LinearLayoutManager(this));
        rvExerciseList.setAdapter(adapter);

        btnAddExercise.setOnClickListener(v -> {
            exerciseList.add(new ExerciseItem());
            adapter.notifyItemInserted(exerciseList.size() - 1);
            // 추가된 맨 아래로 스크롤
            rvExerciseList.scrollToPosition(exerciseList.size() - 1);
        });

        btnModifyRoutine.setOnClickListener(v -> {
            String name = etRoutineName.getText().toString();

            if(name.isEmpty()){
                Toast.makeText(this, "루틴 이름을 입력해주세요.", Toast.LENGTH_SHORT).show();
                return;
            }
            if(exerciseList.isEmpty()){
                Toast.makeText(this, "운동을 최소 1개 이상 추가해주세요.", Toast.LENGTH_SHORT).show();
                return;
            }

            //입력받은 정보로 루틴 아이템 객체 생성
            RoutineItem routineItem = new RoutineItem(name, exerciseList);

            //메인화면과 연결할 인텐스 생성
            Intent intent = new Intent(ModifyRoutineActivity.this, MainActivity.class);
            //만든 루틴 객체 데이터 전달
            intent.putExtra("modifyRoutine", routineItem);
            intent.putExtra("position", position);

            //이전 화면 기록을 싹 지우고, 이동할 화면이 첫 화면
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        });

        // 뒤로가기 버튼
        findViewById(R.id.btn_back).setOnClickListener(v -> finish());
    }
}
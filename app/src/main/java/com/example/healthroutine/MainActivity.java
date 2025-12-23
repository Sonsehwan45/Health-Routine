package com.example.healthroutine;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RecyclerView rvRoutineList;
    private Button btCreateRoutine;
    private ArrayList<RoutineItem> routineList;
    private ImageView btRecordIcon;
    private MainRoutineAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rvRoutineList = findViewById(R.id.listView);
        btCreateRoutine = findViewById(R.id.bt_create_routine);
        btRecordIcon = findViewById(R.id.btn_record_icon);

        routineList = SprefsManager.getRoutineList(this);
        addDummyData();

        Intent getIntent = getIntent();

        // 새로 만든 루틴 리스트에 저장
        RoutineItem newRoutine = (RoutineItem)getIntent.getSerializableExtra("newRoutine");
        //전달 받은 새루틴이 있는지 확인후 저장
        if(newRoutine != null){
            routineList.add(newRoutine);
            SprefsManager.setRoutineList(this, routineList);
        }

        //수정한 루틴 저장
        RoutineItem modifyRoutine = (RoutineItem)getIntent.getSerializableExtra("modifyRoutine");
        int position = getIntent.getIntExtra("position", -1);

        if (modifyRoutine != null && position != -1) {
            // position이 유효한 범위 내에 있는지 확인
            if (position < routineList.size()) {
                // 해당 위치의 데이터를 수정된 데이터로 교체
                routineList.set(position, modifyRoutine);
            } else {
                // 범위를 벗어났다면 그냥 맨 뒤에 추가
                routineList.add(modifyRoutine);
            }
            SprefsManager.setRoutineList(this, routineList);
        }

        adapter = new MainRoutineAdapter(this, routineList);

        rvRoutineList.setLayoutManager(new LinearLayoutManager(this));
        rvRoutineList.setAdapter(adapter);

        btCreateRoutine.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, CreateRoutine.class);
            startActivity(intent);
            Log.d("MainPage", "루틴 생성 버튼 클릭");
        });

        btRecordIcon.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, activity_workout_history.class);
            startActivity(intent);
            Log.d("MainPage", "운동 기록 버튼 클릭");
        });
    }

    private void addDummyData() {
        // 예시 1: 가슴 운동 루틴
        ArrayList<ExerciseItem> chestExercises = new ArrayList<>();

        ExerciseItem benchPress = new ExerciseItem();
        benchPress.setName("벤치프레스");
        benchPress.getSetList().add(new OneSetItem("50", "10", "120"));
        benchPress.getSetList().add(new OneSetItem("50", "10", "120"));
        benchPress.getSetList().add(new OneSetItem("50", "10", "120"));
        benchPress.getSetList().add(new OneSetItem("50", "10", "120"));
        chestExercises.add(benchPress);

        ExerciseItem fly = new ExerciseItem();
        fly.setName("팩덱 플라이");
        fly.getSetList().add(new OneSetItem("35", "15", "90"));
        fly.getSetList().add(new OneSetItem("35", "15", "90"));
        fly.getSetList().add(new OneSetItem("35", "15", "90"));
        fly.getSetList().add(new OneSetItem("35", "15", "90"));
        chestExercises.add(fly);

        routineList.add(new RoutineItem("가슴 뿌시기", chestExercises));

        // 예시 2: 하체 운동 루틴
        ArrayList<ExerciseItem> legExercises = new ArrayList<>();

        ExerciseItem squat = new ExerciseItem();
        squat.setName("스쿼트");
        legExercises.add(squat);

        routineList.add(new RoutineItem("하체 루틴", legExercises));
    }
}
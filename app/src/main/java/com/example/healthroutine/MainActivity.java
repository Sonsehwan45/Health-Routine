package com.example.healthroutine;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RecyclerView rvRoutineList;
    private Button btCreateRoutine;
    private ArrayList<RoutineItem> routineList;
    private MainRoutineAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rvRoutineList = findViewById(R.id.listView);
        btCreateRoutine = findViewById(R.id.bt_create_routine);

        routineList = new ArrayList<>();
        addDummyData();

        Intent getIntent = getIntent();
        RoutineItem routineItem = (RoutineItem)getIntent.getSerializableExtra("newRoutine");
        //전달 받은 루틴 리스트에 저장
        if(routineItem != null){
            routineList.add(routineItem);
        }

        adapter = new MainRoutineAdapter(this, routineList);

        rvRoutineList.setLayoutManager(new LinearLayoutManager(this));
        rvRoutineList.setAdapter(adapter);

        btCreateRoutine.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, CreateRoutine.class);
            startActivity(intent);
            Log.d("MainPage", "루틴 생성 버튼 클릭");
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
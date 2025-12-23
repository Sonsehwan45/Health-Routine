package com.example.healthroutine;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class activity_workout_history extends AppCompatActivity implements CalendarAdapter.OnItemListener {

    private TextView tvDate; // 년/월 표시
    private RecyclerView calendarRecyclerView; // 달력 리사이클러뷰
    private RecyclerView listRecyclerView; // 하단 운동 기록 리스트 리사이클러뷰
    private ImageView ivRoutineIcon; // 하단 탭바 루틴 아이콘

    private ArrayList<WorkOutHistoryItem> allHistoryList; // 저장된 전체 운동 기록
    private ArrayList<WorkOutHistoryItem> dailyHistoryList; // 선택한 날짜의 운동 기록 리스트
    private WorkoutHistoryAdapter dailyAdapter; // 하단 리스트용 어댑터

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_history);

        initWidgets();

        // 1. 저장된 모든 운동 기록 불러오기
        allHistoryList = SprefsManager.getWorkoutHistoryList(this);

        // 2. 하단 리스트 초기화 (처음엔 빈 리스트)
        dailyHistoryList = new ArrayList<>();
        // 새로 만든 WorkoutHistoryAdapter 연결
        dailyAdapter = new WorkoutHistoryAdapter(this, dailyHistoryList);

        listRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        listRecyclerView.setAdapter(dailyAdapter);

        // 3. 달력 초기화 (오늘 날짜 기준)
        CalendarUtil.selectedDate = LocalDate.now();
        setMonthView();

        // 4. 버튼 리스너 설정
        Button btnPrev = findViewById(R.id.bt_back_month);
        Button btnNext = findViewById(R.id.button2);

        // XML ID와 일치하도록 연결 (activity_main.xml 등의 하단바 ID 참고)
        ivRoutineIcon = findViewById(R.id.btn_routine_icon);

        btnPrev.setOnClickListener(v -> {
            CalendarUtil.selectedDate = CalendarUtil.selectedDate.minusMonths(1);
            setMonthView();
        });

        btnNext.setOnClickListener(v -> {
            CalendarUtil.selectedDate = CalendarUtil.selectedDate.plusMonths(1);
            setMonthView();
        });

        // 루틴 아이콘 클릭 시 메인 화면으로 이동
        if (ivRoutineIcon != null) {
            ivRoutineIcon.setOnClickListener(v -> {
                Intent intent = new Intent(activity_workout_history.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                finish();
            });
        }
    }

    private void initWidgets() {
        calendarRecyclerView = findViewById(R.id.calendar_view);
        // XML 파일에 년/월을 표시하는 TextView의 ID가 무엇인지 확인 필요 (여기서는 tv_current_month 가정보류, 없으면 textView 등 확인)
        // 만약 XML상 ID가 textView라면 findViewById(R.id.textView)로 수정해야 함.
        // 앞서 대화에서 "년 / 월" 텍스트뷰의 ID 문제(중복)를 언급했었으므로, 고유한 ID를 사용하는 것이 좋습니다.
        tvDate = findViewById(R.id.tv_date); // 예시: 날짜 표시용 텍스트뷰 ID 연결
        listRecyclerView = findViewById(R.id.listView);
    }

    // 달력 화면 그리기 (월별 날짜 세팅)
    private void setMonthView() {
        if (tvDate != null) {
            tvDate.setText(CalendarUtil.monthYearFromDate(CalendarUtil.selectedDate));
        }

        ArrayList<String> daysInMonth = CalendarUtil.daysInMonthArray(CalendarUtil.selectedDate);

        CalendarAdapter calendarAdapter = new CalendarAdapter(daysInMonth, this);
        // spanCount 7: 일주일은 7일이므로 가로 7칸
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 7);
        calendarRecyclerView.setLayoutManager(layoutManager);
        calendarRecyclerView.setAdapter(calendarAdapter);
    }

    // 날짜 클릭 이벤트 (CalendarAdapter.OnItemListener 구현)
    @Override
    public void onItemClick(int position, String dayText) {
        if (!dayText.equals("")) {
            int day = Integer.parseInt(dayText);

            LocalDate clickedDate = CalendarUtil.selectedDate.withDayOfMonth(day);

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy년 MM월 dd일");
            String searchDate = clickedDate.format(formatter);

            updateDailyList(searchDate);
        }
    }

    // 선택한 날짜의 운동 기록만 리스트에 보여주기
    private void updateDailyList(String searchDate) {
        dailyHistoryList.clear(); // 기존 리스트 비우기

        boolean found = false;

        // 전체 기록 중에서 날짜가 일치하는 것만 찾아서 추가
        for (WorkOutHistoryItem history : allHistoryList) {
            // history.getDate()가 "2024년 05월 15일 (수)" 형태라면 contains로 비교
            if (history.getDate() != null && history.getDate().contains(searchDate)) {
                dailyHistoryList.add(history); // 기록 객체 자체를 추가
                found = true;
            }
        }

        if (!found) {
            Toast.makeText(this, "해당 날짜의 운동 기록이 없습니다.", Toast.LENGTH_SHORT).show();
        }

        // 어댑터 새로고침 (화면 갱신)
        dailyAdapter.notifyDataSetChanged();
    }
}
package com.example.healthroutine;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class WorkoutHistoryAdapter extends RecyclerView.Adapter<WorkoutHistoryAdapter.ViewHolder> {

    private Context context;
    private ArrayList<WorkOutHistoryItem> historyList;

    public WorkoutHistoryAdapter(Context context, ArrayList<WorkOutHistoryItem> historyList) {
        this.context = context;
        this.historyList = historyList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_record_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        WorkOutHistoryItem item = historyList.get(position);

        // 1. 루틴 이름 설정
        holder.tvRoutineName.setText(item.getName());

        // 2. 날짜 설정
        holder.tvRecordDate.setText(item.getDate());

        // 3. 루틴 정보 요약 (예: "벤치프레스 외 3개 운동")
        ArrayList<ExerciseItem> exercises = item.getWorkoutList();
        if (exercises != null && !exercises.isEmpty()) {
            String firstExerciseName = exercises.get(0).getName();
            int extraCount = exercises.size() - 1;

            if (extraCount > 0) {
                holder.tvRoutineInfo.setText(firstExerciseName + " 외 " + extraCount + "개 운동");
            } else {
                holder.tvRoutineInfo.setText(firstExerciseName);
            }
        } else {
            holder.tvRoutineInfo.setText("운동 기록 없음");
        }

        // (선택 사항) 클릭 시 상세 운동 목록을 보여주는 페이지로 이동하는 기능을 여기에 추가할 수 있습니다.
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, StartRoutineActivity.class);

            intent.putExtra("selectedRoutine", item.getWorkoutList()); // selectedRecord -> selectedRoutine
            intent.putExtra("RoutineName", item.getName());           // RecordName -> RoutineName
            intent.putExtra("recordDate", item.getDate());             // recordDate ->)
            intent.putExtra("position", position);
            context.startActivity(intent);
            Log.d("Record_Page", "운동기록 수정으로 이동");
        });
    }

    @Override
    public int getItemCount() {
        return historyList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvRoutineName;
        TextView tvRoutineInfo;
        TextView tvRecordDate;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // item_record_card.xml의 ID와 연결
            tvRoutineName = itemView.findViewById(R.id.tv_routine_name);
            tvRoutineInfo = itemView.findViewById(R.id.tv_routine_info);
            tvRecordDate = itemView.findViewById(R.id.tv_record_date);
        }
    }
}
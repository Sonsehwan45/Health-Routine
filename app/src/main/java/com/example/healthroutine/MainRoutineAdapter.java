package com.example.healthroutine;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MainRoutineAdapter extends RecyclerView.Adapter<MainRoutineAdapter.ViewHolder> {

    //데이터를 담을 변수 생성
    private Context context; //화면 제어용
    private ArrayList<RoutineItem> routineList;

    public MainRoutineAdapter(Context context, ArrayList<RoutineItem> routineList){
        this.context = context;
        this.routineList = routineList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_routine_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //현재 위치의 루틴 데이터를 가져온다.
        RoutineItem currentRoutine = routineList.get(position);
        ArrayList<ExerciseItem> exercises = routineList.get(position).getExerciseList();

        holder.tvRoutineName.setText(currentRoutine.getName());

        //운동 리스트가 비여있지 않는지 확인
        if(exercises != null && !exercises.isEmpty()){
            String firstExerciseName = exercises.get(0).getName();
            int exerciseCount = exercises.size()-1;

            if (exerciseCount > 0) {
                // 예: "스쿼트 외 2개 운동"
                holder.tvRoutineInfo.setText(firstExerciseName + " 외 " + exerciseCount + "개 운동");
            } else {
                // 운동이 1개만 있을 때: "스쿼트"
                holder.tvRoutineInfo.setText(firstExerciseName);
            }
        }else {
            // 운동이 없을 때
            holder.tvRoutineInfo.setText("운동 없음");
        }

        holder.itemView.setOnClickListener(v -> {

        });

        holder.ivStartRoutine.setOnClickListener(v -> {
            Intent intent = new Intent(context, StartRoutineActivity.class);

            //운동 리스트 데이터 전달
            intent.putExtra("selectedRoutine", currentRoutine.getExerciseList());

            //루틴 이름 데이터 전달
            intent.putExtra("RoutineName", currentRoutine.getName());

            //MainRoutineAdapter는 일반 클래스여서 startActivity()가 없음 그래서 context에서 가져와서 사용해야한다.
            context.startActivity(intent);
            Log.d("MainPage", "루틴 생성 버튼 클릭");
        });
    }

    @Override
    public int getItemCount() {
        return routineList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView tvRoutineName;
        TextView tvRoutineInfo;
        ImageView ivStartRoutine;

        public ViewHolder(@NonNull View itemView){
            super(itemView);
            tvRoutineName = itemView.findViewById(R.id.tv_routine_name);
            tvRoutineInfo = itemView.findViewById(R.id.tv_routine_info);
            ivStartRoutine = itemView.findViewById(R.id.iv_start_routine);
        }
    }
}

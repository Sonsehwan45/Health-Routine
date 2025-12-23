package com.example.healthroutine;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CalendarAdapter extends RecyclerView.Adapter<CalendarAdapter.CalendarViewHolder> {
    private final ArrayList<String> daysOfMonth;
    private final OnItemListener onItemListener;

    // (나중에 추가) 운동 기록 리스트를 받아서 비교할 때 사용
    // private ArrayList<WorkOutHistoryItem> historyList;
    public CalendarAdapter(ArrayList<String> daysOfMonth, OnItemListener onItemListener) {
        this.daysOfMonth = daysOfMonth;
        this.onItemListener = onItemListener;
    }

    @NonNull
    @Override
    public CalendarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.activity_item_calendar_cell, parent, false);

        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();

        if (parent.getHeight() > 0) {
            layoutParams.height = (int) (parent.getHeight() / 6);
        } else {
            layoutParams.height = 250;
        }
        return new CalendarViewHolder(view, onItemListener);
    }

    @Override
    public void onBindViewHolder(@NonNull CalendarViewHolder holder, int position) {
        String day = daysOfMonth.get(position);
        holder.dayOfMonth.setText(day);

        // 날짜가 없는 빈 칸("")인 경우 텍스트를 숨김 처리 (또는 배경색 변경 등)
        if(day.isEmpty()){
            holder.dayOfMonth.setVisibility(View.INVISIBLE);
        } else {
            holder.dayOfMonth.setVisibility(View.VISIBLE);
        }

        // ★ 운동 기록 비교 로직 추가 공간 ★
        // 예: if (historyList.contains(현재날짜)) { ... }
    }

    @Override
    public int getItemCount() {
        return daysOfMonth.size();
    }

    // 클릭 리스너 인터페이스 정의
    public interface OnItemListener {
        void onItemClick(int position, String dayText);
    }

    // 뷰홀더 클래스
    static class CalendarViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public final TextView dayOfMonth;
        private final OnItemListener onItemListener;

        public CalendarViewHolder(@NonNull View itemView, OnItemListener onItemListener) {
            super(itemView);
            dayOfMonth = itemView.findViewById(R.id.cellDayText);
            this.onItemListener = onItemListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int position = getBindingAdapterPosition();

            if (position != RecyclerView.NO_POSITION) {
                onItemListener.onItemClick(position, (String) dayOfMonth.getText());
            }
        }
    }
}
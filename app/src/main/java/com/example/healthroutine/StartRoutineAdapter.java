package com.example.healthroutine;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class StartRoutineAdapter extends RecyclerView.Adapter<StartRoutineAdapter.ViewHolder> {

    private Context context;
    private ArrayList<ExerciseItem> exerciseList;
    private OnItemClickListener listener; // 이벤트 전달을 위한 리스너

    // 인터페이스 정의: 액티비티와 통신하기 위한 약속
    public interface OnItemClickListener {
        void onSetCompleted(int restTime); // 세트 완료(체크박스) 시 휴식 시간 전달
    }

    // 생성자에서 리스너를 받도록 수정
    public StartRoutineAdapter(Context context, ArrayList<ExerciseItem> exerciseList, OnItemClickListener listener) {
        this.context = context;
        this.exerciseList = exerciseList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.activity_item_exercise_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ExerciseItem currentExercise = exerciseList.get(position);

        holder.etExerciseName.setText(currentExercise.getName());

        // 운동 이름 변경 감지
        holder.etExerciseName.addTextChangedListener(new SimpleTextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                currentExercise.setName(s.toString());
            }
        });

        // 세트 컨테이너 초기화
        holder.llSetContainer.removeAllViews();

        // 기존 세트들을 화면에 추가
        for (int i = 0; i < currentExercise.getSetList().size(); i++) {
            addSetView(holder.llSetContainer, currentExercise, i);
        }

        // 세트 추가 버튼 클릭
        holder.btnAddSet.setOnClickListener(v -> {
            OneSetItem newSet = new OneSetItem();
            currentExercise.getSetList().add(newSet);
            // 전체 갱신 대신 해당 아이템만 갱신하거나 뷰만 추가하는 것이 효율적이지만,
            // 세트 번호 재정렬 등을 위해 여기서는 notifyItemChanged 사용
            notifyItemChanged(holder.getBindingAdapterPosition());
        });

        // 운동 삭제 버튼 (옵션: 운동 중 삭제가 필요한 경우)
        holder.btnDeleteExercise.setOnClickListener(v -> {
            exerciseList.remove(holder.getBindingAdapterPosition());
            notifyItemRemoved(holder.getBindingAdapterPosition());
            notifyItemRangeChanged(holder.getBindingAdapterPosition(), exerciseList.size());
        });
    }

    @Override
    public int getItemCount() {
        return exerciseList.size();
    }

    // 세트 한 줄(Row)을 추가하는 메서드
    private void addSetView(LinearLayout container, ExerciseItem currentExercise, int setIndex) {
        View setView = LayoutInflater.from(context).inflate(R.layout.activity_item_set_row, container, false);

        OneSetItem setItem = currentExercise.getSetList().get(setIndex);

        TextView tvSetNum = setView.findViewById(R.id.tv_set_num);
        EditText etWeight = setView.findViewById(R.id.et_weight);
        EditText etCount = setView.findViewById(R.id.et_count);
        EditText etRest = setView.findViewById(R.id.et_rest);
        CheckBox checkBox = setView.findViewById(R.id.checkBox2);
        ImageView btnDelete = setView.findViewById(R.id.btn_delete_set);

        tvSetNum.setText(String.valueOf(setIndex + 1));
        etWeight.setText(setItem.getWeight());
        etCount.setText(setItem.getCount());
        etRest.setText(setItem.getRestTime());
        checkBox.setChecked(setItem.isCompleted());

        // 입력값 변경 리스너들
        etWeight.addTextChangedListener(new SimpleTextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                setItem.setWeight(s.toString());
            }
        });

        etCount.addTextChangedListener(new SimpleTextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                setItem.setCount(s.toString());
            }
        });

        etRest.addTextChangedListener(new SimpleTextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                setItem.setRestTime(s.toString());
            }
        });

        // ★ 핵심: 체크박스 상태 변경 리스너
        checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            setItem.setCompleted(isChecked);

            // 체크가 되었고, 휴식 시간이 입력되어 있다면 타이머 시작 요청
            if (isChecked) {
                String restTimeStr = etRest.getText().toString();
                if (!restTimeStr.isEmpty()) {
                    try {
                        int restTime = Integer.parseInt(restTimeStr);
                        if (listener != null) {
                            listener.onSetCompleted(restTime); // 액티비티로 휴식 시간 전달
                        }
                    } catch (NumberFormatException e) {
                        // 숫자가 아닌 경우 무시
                    }
                }
            }
        });

        // 세트 삭제 버튼
        btnDelete.setOnClickListener(v -> {
            currentExercise.getSetList().remove(setItem);
            // 뷰 갱신을 위해 어댑터 전체 리프레시 혹은 해당 아이템 리프레시 필요
            // 여기서는 간단히 전체 갱신 (실제로는 notifyItemChanged 권장)
            notifyDataSetChanged();
        });

        container.addView(setView);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        EditText etExerciseName;
        Button btnAddSet;
        LinearLayout llSetContainer;
        ImageView btnDeleteExercise;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            etExerciseName = itemView.findViewById(R.id.et_exercise_name);
            btnAddSet = itemView.findViewById(R.id.btn_add_set);
            llSetContainer = itemView.findViewById(R.id.ll_set_container);
            btnDeleteExercise = itemView.findViewById(R.id.btn_delete_exercise);
        }
    }

    abstract static class SimpleTextWatcher implements TextWatcher {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
        @Override
        public void afterTextChanged(Editable s) {}
    }
}
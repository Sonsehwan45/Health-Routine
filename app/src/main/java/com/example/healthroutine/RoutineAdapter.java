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

public class RoutineAdapter extends RecyclerView.Adapter<RoutineAdapter.ViewHolder> {

    private Context context;
    private ArrayList<ExerciseItem> exerciseList; // 메인 액티비티에서 넘겨받은 전체 운동 데이터

    public RoutineAdapter(Context context, ArrayList<ExerciseItem> exerciseList) {
        this.context = context;
        this.exerciseList = exerciseList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // 1. 운동 종목 하나를 보여줄 레이아웃(activity_item_exercise)을 inflate 하세요.
        View view = LayoutInflater.from(context).inflate(R.layout.activity_item_exercise_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // 현재 위치(position)의 운동 데이터를 가져옵니다.
        ExerciseItem currentExercise = exerciseList.get(position);

        // --- [STEP 1] 운동 이름 처리 ---
        // 화면에 현재 데이터의 운동 이름을 표시합니다. (스크롤 복구용)
        holder.etExerciseName.setText(currentExercise.getName());

        // TODO 1: 운동 이름을 수정할 때마다 currentExercise 데이터에 실시간으로 저장하는 코드를 작성하세요.
        // 힌트: holder.etExerciseName.addTextChangedListener(...) 와 SimpleTextWatcher 사용
        holder.etExerciseName.addTextChangedListener(new SimpleTextWatcher() {
            @Override
            public void afterTextChanged(Editable editable) {
                currentExercise.setName(editable.toString());
            }
        });

        holder.llSetContainer.removeAllViews();

        for(int i=0; i < currentExercise.getSetList().size(); i++){
            OneSetItem setItem = currentExercise.getSetList().get(i);
            addSetViewToLayout(holder.llSetContainer, setItem, i + 1, currentExercise);
        }

        holder.btnAddSet.setOnClickListener(v -> {
            OneSetItem newSet = new OneSetItem();
            currentExercise.getSetList().add(newSet);
            addSetViewToLayout(holder.llSetContainer, newSet, currentExercise.getSetList().size(), currentExercise);
        });

        holder.btnDeleteExercise.setOnClickListener(v -> {
            // 1. 현재 클릭된 아이템의 정확한 위치(index)를 가져옵니다.
            int currentPos = holder.getBindingAdapterPosition();

            // 2. 위치가 유효한지 확인합니다. (가끔 애니메이션 도중 -1이 될 수 있음)
            if (currentPos != RecyclerView.NO_POSITION) {
                // 3. 데이터 리스트에서 해당 운동을 삭제합니다.
                exerciseList.remove(currentPos);

                // 4. 어댑터에게 "이 위치의 아이템이 삭제됐다"고 알립니다. (애니메이션 효과)
                notifyItemRemoved(currentPos);

                // 5. 삭제된 위치 뒤에 있는 아이템들의 순서(인덱스)를 다시 맞춥니다.
                notifyItemRangeChanged(currentPos, exerciseList.size());
            }
        });
    }

    @Override
    public int getItemCount() {
        return exerciseList.size();
    }

    /**
     * 세트 한 줄(뷰)을 동적으로 생성해서 레이아웃에 붙이는 메서드
     * @param container 세트 뷰가 붙을 부모 레이아웃 (LinearLayout)
     * @param setItem 보여줄 세트 데이터
     * @param setNumber 몇 번째 세트인지 (1, 2, 3...)
     * @param currentExercise 현재 속한 운동 데이터 (삭제 시 필요)
     */
    private void addSetViewToLayout(LinearLayout container, OneSetItem setItem, int setNumber, ExerciseItem currentExercise) {
        // TODO 4: 세트 아이템 레이아웃(activity_item_set_row)을 inflate 해서 View 객체로 만드세요.
        View setView = LayoutInflater.from(context).inflate(R.layout.activity_item_set_row, container, false);

        // 뷰 찾기
        TextView tvSetNum = setView.findViewById(R.id.tv_set_num);
        EditText etWeight = setView.findViewById(R.id.et_weight);
        EditText etCount = setView.findViewById(R.id.et_count);
        EditText etRest = setView.findViewById(R.id.et_rest);
        CheckBox checkBox = setView.findViewById(R.id.checkBox2); // 체크박스
        ImageView btnDelete = setView.findViewById(R.id.btn_delete_set);

        // 데이터 세팅
        tvSetNum.setText(setNumber + "세트");
        etWeight.setText(setItem.getWeight());
        etCount.setText(setItem.getCount());
        etRest.setText(setItem.getRestTime());
        checkBox.setChecked(setItem.isCompleted());

        // TODO 5: 무게(Weight) 입력 시 실시간으로 setItem에 저장하는 리스너를 작성하세요.
        // 힌트: etWeight.addTextChangedListener(...)
        etWeight.addTextChangedListener(new SimpleTextWatcher() {
            @Override
            public void afterTextChanged(Editable editable) {
                setItem.setWeight(editable.toString());
            }
        });

        // TODO 6: 횟수(Reps) 입력 시 실시간으로 setItem에 저장하는 리스너를 작성하세요.
        etCount.addTextChangedListener(new SimpleTextWatcher() {
            @Override
            public void afterTextChanged(Editable editable) {
                setItem.setCount(editable.toString());
            }
        });

        etRest.addTextChangedListener(new SimpleTextWatcher() {
            @Override
            public void afterTextChanged(Editable editable) {
                setItem.setRestTime(editable.toString());
            }
        });

        checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            setItem.setCompleted(isChecked);
        });

        // TODO 7: 삭제 버튼(btnDeleteSet) 클릭 이벤트 작성
        btnDelete.setOnClickListener(v -> {
            // 1. 화면에서 제거
            container.removeView(setView);
            // 2. 데이터 리스트에서 제거
            currentExercise.getSetList().remove(setItem);
            // 3. 세트 번호가 꼬였으므로(1, 3, 4...) 전체 갱신해서 번호 다시 매기기
            notifyDataSetChanged();
        });

        // 만든 뷰를 컨테이너에 최종 부착
        container.addView(setView);
    }


    // 뷰홀더 클래스
    public class ViewHolder extends RecyclerView.ViewHolder {
        EditText etExerciseName;
        Button btnAddSet;
        LinearLayout llSetContainer; // 동적으로 세트 뷰들이 쌓일 곳
        ImageView btnDeleteExercise;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            etExerciseName = itemView.findViewById(R.id.et_exercise_name);
            btnAddSet = itemView.findViewById(R.id.btn_add_set);
            llSetContainer = itemView.findViewById(R.id.ll_set_container);
            btnDeleteExercise = itemView.findViewById(R.id.btn_delete_exercise);
        }
    }

    // 코드를 줄여주는 헬퍼 클래스
    abstract static class SimpleTextWatcher implements TextWatcher {
        @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
        @Override public void onTextChanged(CharSequence s, int start, int before, int count) {}
    }
}
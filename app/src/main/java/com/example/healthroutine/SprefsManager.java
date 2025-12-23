package com.example.healthroutine;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class SprefsManager {

    //저장폴더 이름
    private static final String PREFENCES_NAME = "HealthRoutineApp_Prefs";

    //데이터를 저장할 키 값
    private static final String KEY_ROUTINES = "routine_list";
    private static final String KEY_WORKOUT_HISTORY = "workout_history_list";

    //저장소를 불러온다(저장소가 없으면 만들어서 전달한다)
    private static SharedPreferences getPreferences(Context context){
        return context.getSharedPreferences(PREFENCES_NAME, Context.MODE_PRIVATE);
    }

    //루틴 리스트를 저장(예: 루틴 생성, 수정)
    public static void setRoutineList(Context context, ArrayList<RoutineItem> list){
        SharedPreferences prefs = getPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();

        Gson gson = new Gson();
        
        //루틴 리스트를 JSON 형태로 변환
        String json = gson.toJson(list);
        
        //"routine_list"위치에 저장
        editor.putString(KEY_ROUTINES, json);
        editor.apply();
    }
    
    //루틴 리스트를 가져오기
    public static ArrayList<RoutineItem> getRoutineList(Context context){
        SharedPreferences prefs = getPreferences(context);
        String json = prefs.getString(KEY_ROUTINES, null);
        
        Gson gson = new Gson();
        if(json != null){
            Type type = new TypeToken<ArrayList<RoutineItem>>(){}.getType();
            return gson.fromJson(json, type);
        }else{
            return new ArrayList<>();
        }
    }

    //운동 리스트를 저장(예: 운동 생성, 수정)
    public static void setWorkoutHistoryList(Context context, ArrayList<WorkOutHistoryItem> list){
        SharedPreferences prefs = getPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();

        Gson gson = new Gson();

        //루틴 리스트를 JSON 형태로 변환
        String json = gson.toJson(list);

        //"workout_history_list"위치에 저장
        editor.putString(KEY_WORKOUT_HISTORY, json);
        editor.apply();
    }

    //운동리스트 가져오기
    public static ArrayList<WorkOutHistoryItem> getWorkoutHistoryList(Context context){
        SharedPreferences prefs = getPreferences(context);
        String json = prefs.getString(KEY_WORKOUT_HISTORY, null);

        Gson gson = new Gson();
        if(json != null){
            Type type = new TypeToken<ArrayList<WorkOutHistoryItem>>(){}.getType();
            return gson.fromJson(json, type);
        }else{
            return new ArrayList<>();
        }
    }
}

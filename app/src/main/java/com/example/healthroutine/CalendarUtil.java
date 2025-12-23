package com.example.healthroutine;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;

public class CalendarUtil {

    //오늘 날짜
    public static LocalDate selectedDate = LocalDate.now();

    public static ArrayList<String> daysInMonthArray(LocalDate date){
        ArrayList<String> daysInMonthArray = new ArrayList<String>();

        //date에 대한 년/월 정보를 가져온다.
        YearMonth yearMonth = YearMonth.from(date);
        //해당 달의 총 일수를 가져온다.
        int daysInMonth = yearMonth.lengthOfMonth();

        //그 달의 1일 날짜 가져오기
        LocalDate firstOfMonth = selectedDate.withDayOfMonth(1);

        //1일이 무슨 요일인지 확인한다(1(월) ~ 7(일))
        int dayOfWeek = firstOfMonth.getDayOfWeek().getValue();

        //첫 주의 빈칸의 수를 반환
        int emptyCells = (dayOfWeek == 7) ? 0 : dayOfWeek;

        //리스트에 빈칸의 수만큼 데이터를 추가한다.
        for(int i=0; i<emptyCells; i++){
            daysInMonthArray.add("");
        }
        //해당 달의 일수만큼 리스트에 추가
        for(int i=1; i <= daysInMonth; i++){
            daysInMonthArray.add(String.valueOf(i));
        }

        return daysInMonthArray;
    }

    public static String monthYearFromDate(LocalDate date){
        return date.getYear() + "년 " + date.getMonthValue() + "월";
    }
}

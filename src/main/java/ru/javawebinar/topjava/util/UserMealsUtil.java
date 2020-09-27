package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExcess;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> meals = Arrays.asList(
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410)
        );

        List<UserMealWithExcess> mealsTo = filteredByCycles(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
        mealsTo.forEach(System.out::println);

        System.out.println(filteredByStreams(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000));
    }

    public static List<UserMealWithExcess> filteredByCycles(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {

        UserMeal tmpMeal;
        Boolean tmpExcess = false;
        int tmpCalories = 0;
        LocalTime tmpTime;
        List<UserMeal> result = new ArrayList<UserMeal>();
        List<UserMealWithExcess> resultWithExcess = new ArrayList<UserMealWithExcess>();

        //Фильтруем список по времени
        Iterator<UserMeal> iter = meals.iterator();
        while(iter.hasNext()){

            tmpMeal = iter.next();
            tmpTime = tmpMeal.getDateTime().toLocalTime();

            if (tmpTime.isAfter(startTime) && tmpTime.isBefore(endTime)){
                tmpCalories = tmpCalories + tmpMeal.getCalories();
                if (tmpCalories > caloriesPerDay){
                    tmpExcess = true;
                }
                result.add(new UserMeal(tmpMeal.getDateTime(), tmpMeal.getDescription(), tmpMeal.getCalories()));
            }
        }

        //Формируем итоговый список с excess
        Iterator<UserMeal> iterLast = result.iterator();
        while(iterLast.hasNext()){
            tmpMeal = iterLast.next();
            resultWithExcess.add(new UserMealWithExcess(tmpMeal.getDateTime(), tmpMeal.getDescription(), tmpMeal.getCalories(), tmpExcess));
        }

        return resultWithExcess;
    }

    public static List<UserMealWithExcess> filteredByStreams(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {

        Stream<UserMeal> streamMeals = meals.stream();
        boolean excessFlag = false;
        List<UserMeal> tmpMeals = streamMeals.filter(x -> x.getDateTime().toLocalTime().isAfter(startTime) && x.getDateTime().toLocalTime().isBefore(endTime)).collect(Collectors.toList());

        int resultCalories = tmpMeals.stream().collect(Collectors.summingInt(UserMeal::getCalories));
        if (resultCalories>caloriesPerDay){
            excessFlag = true;
        }

        boolean finalExcessFlag = excessFlag;
        List<UserMealWithExcess> result = new ArrayList<UserMealWithExcess>();
        tmpMeals.forEach(x-> result.add(new UserMealWithExcess(x.getDateTime(), x.getDescription(), x.getCalories(), finalExcessFlag)));


        return result;
    }
}

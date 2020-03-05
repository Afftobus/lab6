package ru.mirea;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

  public static void main(String[] args) {
    System.out.println("Hello world!");
    Scanner in = new Scanner(System.in);

    // бесконечный цикл, выход из которого производится только по условию
    // т.к. мы будем прерывать его изнутри структуры switch/case, нужно его отметить меткой
    loop:
    while (true) {
      // выведем меню
      System.out.println("-------------------------------------------------------------------");
      System.out.println("Режим работы");
      System.out.println("1) ввод массива данных");
      System.out.println("2) ввод по одному числу с выводом ответа после каждого");
      System.out.println("3) прогон случайными числами, вводимыми пачкой");
      System.out.println("4) прогон случайными числами, вводимыми по одному");
      System.out.println("0) выход");
      System.out.println("выберите пункт меню: ");

      String input = in.nextLine();
      int num = Integer.parseInt(input);

      switch (num) {
        case 1:
          manualEnterArray(in);
          break;

        case 2:
          manualEnterOneByOne(in);
          break;

        case 3:
          randomlEnterArray(in);
          break;

        case 4:
          randomEnterOneByOne(in);
          break;

        default:
          System.out.println("Нет такого варианта");
          break;

        // выход
        case 0:
          break loop;
      }
    }
    in.close();
    System.out.println("Goodbye, cruel world...");
  }

  private static void manualEnterArray(Scanner in) {
    System.out.println("------- ВВОД ДАННЫХ КУЧЕЙ РУКАМИ ----------------------------------");
    System.out.print("Введите массив чисел, разделённых запятой: ");
    String input = in.next();

    String elements[] = input.split(",");
    List<Integer> data = new ArrayList<>();

    for (String el : elements) {
      data.add(Integer.parseInt(el.trim()));
    }

    LabVariant6 counter = new LabVariant6(data);
    System.out.println(toText(counter.count()));
  }

  private static void manualEnterOneByOne(Scanner in) {
    System.out.println("------- ВВОД ДАННЫХ ПО ОДНОМУ РУКАМИ ------------------------------");
    System.out.println("Вводите числа, нажимайте Enter, для окончания введите пустую строку: ");
    LabVariant6 counter = new LabVariant6();

    while (true) {
      String input = in.nextLine();
      if (input.equals("")) {
        return;
      }
      counter.put(Integer.parseInt(input));

      System.out.println(toText(counter.count()));
    }
  }

  private static void randomlEnterArray(Scanner in) {
    System.out.println("------- РАНДОМНАЯ ГЕНЕРАЦИЯ КУЧИ ДАННЫХ И ОБРАБОТКА ПАЧКОЙ --------");

    System.out.println("Количество рандомных чисел в вычисляемом массиве: ");
    String input = in.nextLine();
    int count = Integer.parseInt(input);

    System.out.println("Максимальное число: ");
    input = in.nextLine();
    int max = Integer.parseInt(input);

    System.out.println("Количество прогонов: ");
    input = in.nextLine();
    int iterationsCount = Integer.parseInt(input);


    List<Integer> data;
    LabVariant6 counter;
    int i;

    while (iterationsCount-- > 0) {
      data = new ArrayList<>();
      i = count;

      while (i-- > 0) {
        data.add((int) (Math.random() * max));
      }

      counter = new LabVariant6(data);
      long start = System.nanoTime();
      ResultType result = counter.count();
      long end = System.nanoTime();
      System.out.println(toText(result));
      System.out.println("Расчитано за " + (end - start) / 1000 + "мкс");
    }
  }

  private static void randomEnterOneByOne(Scanner in) {
    System.out.println("------- РАНДОМНАЯ ГЕНЕРАЦИЯ КУЧИ ДАННЫХ И ОБРАБОТКА ПО ОДНОМУ ---");

    System.out.println("Количество рандомных чисел в вычисляемом массиве: ");
    String input = in.nextLine();
    int count = Integer.parseInt(input);

    System.out.println("Максимальное число: ");
    input = in.nextLine();
    int max = Integer.parseInt(input);

    System.out.println("Количество прогонов: ");
    input = in.nextLine();
    int iterationsCount = Integer.parseInt(input);

    LabVariant6 counter;
    long totalCountTime = 0;
    int i;

    while (iterationsCount-- > 0) {
      i = count;

      counter = new LabVariant6();

      while (i-- > 0) {
        counter.put((int) (Math.random() * max));
        long start = System.nanoTime();
        counter.count();
        long end = System.nanoTime();
        totalCountTime += (end - start);
      }

      ResultType result = counter.count();
      System.out.println(toText(result));
      System.out.println("Расчитано за " + (totalCountTime) / 1000 + "мкс");
    }

  }

  private static String toText(ResultType resultType) {
    switch (resultType) {
      case EQUAL:
        return "Монохуйственно";
      case NO_DATA_TO_COUNT_RESULT:
        return "Хуйзнает вообще";
      case GREATER_IS_MORE:
        return "Тех что больше среднего - больше, тех, что меньше - меньше";
      case SMALLER_IS_MORE:
        return "Тех что меньше среднего - больше, тех, что больше - меньше";

      default:
        return "";
    }
  }
}

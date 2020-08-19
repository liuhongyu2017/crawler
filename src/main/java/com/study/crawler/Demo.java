package com.study.crawler;

/**
 * @author liuhongyu2017@aliyun.com
 * @version 1.0 2020年6月4日
 */
public class Demo {

  public static void main(String[] args) {
    System.out.print(randomNum(33, 1));
    System.out.print("  ");
    System.out.print(randomNum(33, 1));
    System.out.print("  ");
    System.out.print(randomNum(33, 1));
    System.out.print("  ");
    System.out.print(randomNum(33, 1));
    System.out.print("  ");
    System.out.print(randomNum(33, 1));
    System.out.print("  ");
    System.out.print(randomNum(33, 1));
    System.out.print("   ");
    System.out.print(randomNum(16, 1));
  }

  public static int randomNum(int max, int min) {
    return (int) (Math.random() * (max - min) + min);
  }

}

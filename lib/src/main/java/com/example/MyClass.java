package com.example;

public class MyClass {
    private String str = new String("newString");
    private char[] cr = {'d', 'o', 'o', 'd'};

    public static void main(String[] args) {
        MyClass myClass = new MyClass();
        myClass.str = "wobianle";
        myClass.cr[0] = 'g';
        System.out.printf(myClass.str + " and ");
        System.out.print(myClass.cr);
    }
}

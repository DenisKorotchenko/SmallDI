package com.company;

public class Writer {
    String prefix;
    Writer(String prefix) {
        this.prefix = prefix;
    }

    void write(String s) {
        System.out.println(prefix + s);
    }
}

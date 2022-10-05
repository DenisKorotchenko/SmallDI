package com.company;

import com.company.di.DI;

public class BaseInfo {
    Writer writer = (Writer) DI.init(Writer.class);

    void printInfo() {
        writer.write("This is BaseInfo");
    }
}

package com.company;

import com.company.di.DI;

public class UsingClass {
    private final BaseInfo usedClass = (BaseInfo) DI.init(BaseInfo.class);

    void run() {
        usedClass.printInfo();
    }
}

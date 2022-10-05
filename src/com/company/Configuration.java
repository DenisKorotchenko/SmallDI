package com.company;

import com.company.di.DI;

public class Configuration {

    static void init() {
        DI.registerPrototype(Writer.class, "Prefix. ");
        DI.registerSingleton(Info.class);
    }
}

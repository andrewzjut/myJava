package com.tairanchina.zt.generic.factory;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public interface Factory<T> {
    T create();
}

class MyFactory {
    static List<Factory<? extends Tool>> partFactories = new ArrayList<>();

    static {
        partFactories.add(new AirFilter.Factory());
        partFactories.add(new FuelFilter.Factory());
        partFactories.add(new FanBelt.Factory());
        partFactories.add(new GeneratorBelt.Factory());
    }

    private static Random random = new Random(47);

    public static Tool createRandom() {
        int n = random.nextInt(partFactories.size());
        return partFactories.get(n).create();
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }
}

class Tool{}

class Filter extends Tool {
}

class FuelFilter extends Filter {
    static class Factory implements com.tairanchina.zt.generic.factory.Factory<FuelFilter> {
        @Override
        public FuelFilter create() {
            return new FuelFilter();
        }
    }
}

class AirFilter extends Filter {
    static class Factory implements com.tairanchina.zt.generic.factory.Factory<AirFilter> {
        @Override
        public AirFilter create() {
            return new AirFilter();
        }
    }
}

class Belt extends Tool {
}

class FanBelt extends Belt {
    static class Factory implements com.tairanchina.zt.generic.factory.Factory<FanBelt> {

        @Override
        public FanBelt create() {
            return new FanBelt();
        }
    }
}

class GeneratorBelt extends Belt {
    static class Factory implements com.tairanchina.zt.generic.factory.Factory<GeneratorBelt> {
        @Override
        public GeneratorBelt create() {
            return new GeneratorBelt();
        }
    }
}

class RegisteredFactories {
    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            System.out.println(MyFactory.createRandom());
        }
    }
}
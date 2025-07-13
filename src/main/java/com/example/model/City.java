package com.example.model;

import java.util.Random;

public enum City {
    MOSCOW("Москва"),
    NIZNIY_NOVGOROD("Нижний Новгород"),
    SAINT_PETERSBURG("Санкт-Петербург"),
    VLADIMIR("Владимир"),
    KAZAN("Казань"),
    KIROV("Киров"),
    UFA("Уфа"),
    KOSTROMA("Кострома");

    private final String description;

    City(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public static City generateRandomCity() {
        City[] values = City.values();
        return values[new Random().nextInt(values.length)];
    }
}

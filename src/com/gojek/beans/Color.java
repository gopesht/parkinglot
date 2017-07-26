package com.gojek.beans;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by a1dmiuxe(gopesh.tulsyan) on 25/07/17.
 */

public class Color {
    String name;
    Set<Car> cars = new HashSet<>();

    public Color(String name){
        this.name = name;
    }
    public String getColorName() {
        return name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Car> getCars() {
        return cars;
    }

    public void setCars(Set<Car> cars) {
        this.cars = cars;
    }

    public void setColor(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Color color = (Color) o;

        return name.equals(color.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}

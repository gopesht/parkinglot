package com.gojek.beans;

/**
 * Created by a1dmiuxe(gopesh.tulsyan) on 25/07/17.
 */

public class Color {
    String name;

    public String getColorName() {
        return name;
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

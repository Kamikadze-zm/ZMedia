package ru.kamikadze_zm.zmedia.model.entity.util;

import java.util.Comparator;

public interface Genre {

    public String getGenre();

    public static Comparator<Genre> getComparator() {
        return (Genre o1, Genre o2) -> o1.getGenre().compareTo(o2.getGenre());
    }
}

package model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Movie {

    private int id;
    private String title;
    private String description;
    private int year;
    private List<Genre> genres;

    public Movie(String title, String description, int year) {
        this.title = title;
        this.description = description;
        this.year = year;
    }
}

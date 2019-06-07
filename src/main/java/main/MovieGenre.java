package main;

import commands.MainCommands;
import meneger.GenreManager;
import meneger.MovieManager;
import model.Genre;
import model.Movie;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MovieGenre implements MainCommands {
    static Scanner scanner = new Scanner(System.in);
    static GenreManager genreManager = new GenreManager();
    static MovieManager movieManager = new MovieManager();

    public static void main(String[] args) {

        boolean isRun = true;
        while (isRun) {
            printCommands();
            int command;
            try {
                command = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                command = -1;
            }
            switch (command) {
                case EXIT:
                    isRun = false;
                    break;
                case ALLMOVIES:
                    printAllMovies();
                    break;
                case ALLGENRES:
                    printAllGenres();
                    break;
                case MOVIESBYGENRE:
                    moviesByGenre();
                    break;
                case MOVIESBYYEAR:
                    moviesByYear();
                    break;
                case ADDGENRE:
                    addGenre();
                    break;
                case ADDMOVIE:
                    addMovie();
                    break;
                default:
                    System.out.println("Invalid command! Please try again");
            }
        }
    }

    private static void printAllMovies() {
        List<Movie> allMovies = movieManager.getAllMovies();
        for (Movie allMovie : allMovies) {
            System.out.println(allMovie.getId() + " | " + allMovie.getTitle() + " | " + allMovie.getDescription() + " | " + allMovie.getYear());

        }
    }

    private static void printAllGenres() {
        List<Genre> allGenre = genreManager.getAllGenre();
        for (Genre genre : allGenre) {
            System.out.println(genre.getId() + " | " + genre.getName());
        }
    }

    private static void moviesByGenre() {
        System.out.println("Please choose and enter movie's genre");
        printAllGenres();
        String moviesGenre = scanner.nextLine();

        List<Movie> allMovies = movieManager.getMoviesByGenre(moviesGenre);
        for (Movie m : allMovies) {
            System.out.println("Title: " + m.getTitle() + "|  Description: " + m.getDescription() + "|  Year: " + m.getYear());
        }
    }

    private static void moviesByYear() {
        System.out.println("Please select movie's presented year");
        movieManager.printAllYearsOfMovies();
        String moviesYear = scanner.nextLine();
        List<Movie> MovieByYear = movieManager.printMoviesByYear(Integer.parseInt(moviesYear));
        for (Movie movie : MovieByYear) {
            System.out.println(movie);
        }
    }

    private static void addGenre() {
        System.out.println("Please enter Genre name");
        String newGenre = scanner.nextLine();
        Genre genre = new Genre(newGenre);
        genreManager.addGenre(genre);
    }

    private static void addMovie() {
        System.out.println("Please choose movie's genre");
        printAllGenres();
        String moviesGenre = scanner.nextLine();
        String[] genreData = moviesGenre.split(",");
        List<Genre> genres = genreManager.getAllGenre();
        List<Genre> newGenreList = new ArrayList<Genre>();
        for (Genre g : genres) {
            for (int i = 0; i < genreData.length; i++) {
                if (g.getName().equals(genreData[i])) {
                    newGenreList.add(g);
                }
            }
        }
        System.out.println("Please enter Movies title,description,year");
        String moveArray = scanner.nextLine();
        String moveData[] = moveArray.split(",");
        Movie movie = new Movie();
        movie.setTitle(moveData[0]);
        movie.setDescription(moveData[1]);
        movie.setYear(Integer.parseInt(moveData[2]));
        movie.setGenres(newGenreList);
        movieManager.addMovie(movie);

        movieManager.addIntoRelationshipTable(movie, newGenreList);
        System.out.println("Execute this statement " + movie);
    }

    private static void printCommands() {
        System.out.println("Please input " + EXIT + " for exit");
        System.out.println("Please input " + ALLMOVIES + " for print all movies");
        System.out.println("Please input " + ALLGENRES + " for print all genres");
        System.out.println("Please input " + MOVIESBYGENRE + " for print movies by genre");
        System.out.println("Please input " + MOVIESBYYEAR + " for print movies by year");
        System.out.println("Please input " + ADDGENRE + " for add genre");
        System.out.println("Please input " + ADDMOVIE + " for add movie");
    }
}

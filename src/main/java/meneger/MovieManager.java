package meneger;

import db.DBConnectionProvider;
import model.Genre;
import model.Movie;

import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class MovieManager {

    private Connection connection;

    public MovieManager() {
        connection = DBConnectionProvider.getInstance().getConnection();
    }

    public void addMovie(Movie movie) {
        String query = "INSERT INTO movie(title, description, year) VALUES (?,?,?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, movie.getTitle());
            preparedStatement.setString(2, movie.getDescription());
            preparedStatement.setInt(3, movie.getYear());
            preparedStatement.executeUpdate();
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                movie.setId(generatedKeys.getInt(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addIntoRelationshipTable(Movie movie, List<Genre> genre) {
        String query = "INSERT INTO movies_genre(movie_id, genre_id) VALUES (?,?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            for (Genre genreList : genre) {
                int genreId = genreList.getId();
                preparedStatement.setInt(1, movie.getId());
                preparedStatement.setInt(2, genreId);
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Movie> getAllMovies() {
        String query = "SELECT * FROM movie";

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            List<Movie> movies = new LinkedList<Movie>();

            while (resultSet.next()) {
                Movie movie = new Movie();
                movie.setId(resultSet.getInt(1));
                movie.setTitle(resultSet.getString(2));
                movie.setDescription(resultSet.getString(3));
                movie.setYear(resultSet.getInt(4));
                movies.add(movie);
            }
            return movies;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Movie> getMoviesByGenre(String moviesGenre) {
        String query = "SELECT `movie`.`title`, `movie`.`description`, `movie`.`year` \n" +
                "FROM `movie`,`genre`,`movies_genre` " +
                "WHERE`movie`.`id` = `movies_genre`.`movie_id`\n" +
                "AND `genre`.`genreId` = `movies_genre`.`genre_id`\n" +
                "AND `genre`.`genreName` = '" + moviesGenre + "'";
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            List<Movie> movies = new LinkedList<Movie>();

            while (resultSet.next()) {
                Movie movie = new Movie();
                movie.setTitle(resultSet.getString(1));
                movie.setDescription(resultSet.getString(2));
                movie.setYear(resultSet.getInt(3));

                movies.add(movie);
            }
            return movies;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Movie> printMoviesByYear(int year) {
        String query = "SELECT * FROM `movie` WHERE `year` = " + year;
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            List<Movie> movies = new ArrayList<Movie>();

            while (resultSet.next()) {
                Movie movie = new Movie();
                movie.setTitle(resultSet.getString(1));
                movie.setDescription(resultSet.getString(2));
                movie.setYear(resultSet.getInt(3));
                movies.add(movie);
            }
            return movies;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void printAllYearsOfMovies() {
        String query = "SELECT `year` FROM `movie`";
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                Movie movie = new Movie();
                movie.setYear(resultSet.getInt(1));
                System.out.println(movie);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

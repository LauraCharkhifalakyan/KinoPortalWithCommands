package meneger;

import db.DBConnectionProvider;
import model.Genre;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GenreManager {
    private Connection connection;

    public GenreManager() {

        connection = DBConnectionProvider.getInstance().getConnection();
    }

    public  void addGenre(Genre genre){
        String query =  "INSERT INTO genre(`genreName`) VALUES (?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1,genre.getName());
            preparedStatement.executeUpdate();
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if(generatedKeys.next()){
                genre.setId(generatedKeys.getInt(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public List<Genre> getAllGenre(){
        String query = "SELECT * FROM genre";

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            List<Genre> genres = new ArrayList<Genre>();

            while (resultSet.next()){
                Genre genre = new Genre();
                genre.setId(resultSet.getInt(1));
                genre.setName(resultSet.getString(2));
                genres.add(genre);
            }
            return genres;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

}

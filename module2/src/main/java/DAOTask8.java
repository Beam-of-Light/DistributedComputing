import java.sql.*;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;

public class DAOTask8 {
    static final String JDBC_DRIVER = "org.postgresql.Driver";
    static final String DB_URL = "jdbc:postgresql://localhost:5432/module2";

    static final String USER = "admin";
    static final String PASS = "admin";

    Connection conn = null;

    public DAOTask8() {
        try {
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Weather> getAllWeatherByRegionId(int regionId) {
        List<Weather> weatherList = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = conn.prepareStatement("SELECT *  FROM weather WHERE regionId= ?");
            preparedStatement.setInt(1, regionId);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {

                int id = rs.getInt("id");
                Date date = rs.getDate("date");
                float temperature = rs.getFloat("temperature");
                float precipitation = rs.getFloat("precipitation");

                weatherList.add(new Weather(id, regionId, date, temperature, precipitation));
            }
            return weatherList;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return weatherList;
    }

    public List<Weather> getAllWeatherByTemperature(float temperature) {
        List<Weather> souvenirList = new ArrayList<>();
        try {
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery("SELECT *  FROM weather WHERE temperature = "
                    + "'" + temperature + "'");
            while (rs.next()) {

                int id = rs.getInt("id");
                int regionId = rs.getInt("regionId");
                Date date = rs.getDate("date");
                float precipitation = rs.getFloat("precipitation");

                souvenirList.add(new Weather(id, regionId, date, temperature, precipitation));
            }
            return souvenirList;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return souvenirList;
    }

    public boolean deleteWeatherById(int id) {
        try {
            PreparedStatement preparedStatement = conn.prepareStatement("DELETE FROM weather WHERE id= ?");
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}

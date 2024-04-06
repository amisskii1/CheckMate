package com.misskii.javatodolistapp.DAO;

import com.misskii.javatodolistapp.Util.DBUtil;
import com.misskii.javatodolistapp.Util.PersonNotExistsException;
import org.postgresql.util.PSQLException;

import java.sql.*;

public class LicenseDAO {
    private final Connection connection = DBUtil.getConnection();
    private final PersonDAO personDAO = new PersonDAO();
    public void save(String email, String licenseValue) throws SQLException {
        PreparedStatement preparedStatement =
                connection.prepareStatement("INSERT INTO licenses (user_email,license_value, user_id) VALUES (?,?, ?)");
        preparedStatement.setString(1, email);
        preparedStatement.setString(2, licenseValue);
        try {
            preparedStatement.setInt(3, personDAO.getPersonIdByEmail(email));
        } catch (SQLException e){
            throw new PersonNotExistsException();
        }
        preparedStatement.executeUpdate();
    }

    public void update(String email, String licenseValue) {
        try {
            PreparedStatement preparedStatement =
                    connection.prepareStatement("UPDATE licenses SET license_value=? where user_email=?");
            preparedStatement.setString(1, licenseValue);
            preparedStatement.setString(2, email);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

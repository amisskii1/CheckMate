package com.misskii.javatodolistapp.DAO;

import com.misskii.javatodolistapp.Util.DBUtil;

import java.sql.*;

public class LicenseDAO {
    private final Connection connection = DBUtil.getConnection();
    private final PersonDAO personDAO = new PersonDAO();
    public void save(String email, String licenseValue){
        try {
            PreparedStatement preparedStatement =
                    connection.prepareStatement("INSERT INTO licenses (license_value, user_id) VALUES (?, ?)", Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, licenseValue);
            preparedStatement.setInt(2, personDAO.getPersonIdByEmail(email));
            preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}

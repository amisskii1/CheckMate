package com.misskii.javatodolistapp.DAO;

import com.misskii.javatodolistapp.Util.DBUtil;
import com.misskii.javatodolistapp.Util.PersonNotExistsException;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.chrono.ChronoLocalDateTime;

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

    public String getLicenseValueByUserID(int userId) {
        String licenseValue = "";
        try {
            PreparedStatement preparedStatement =
                    connection.prepareStatement("SELECT license_value FROM licenses WHERE user_id=?");
            preparedStatement.setInt(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                licenseValue = resultSet.getString("license_value");
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return licenseValue;
    }

    public void updateLicenseStatus(String licenseStatus, LocalDateTime expireDate , int id) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE licenses SET license_status=?, expire_date=? WHERE user_id=?");
            preparedStatement.setString(1, licenseStatus);
            preparedStatement.setTimestamp(2, Timestamp.valueOf(expireDate));
            preparedStatement.setInt(3, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public String getLicenseStatus(int id) {
        String status = "invalid";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT license_status FROM licenses WHERE user_id=?");
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                status = resultSet.getString("license_status");
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return status;
    }

    public void setLicenseStatusToFalse(int id) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE licenses SET license_status='false' WHERE user_id=?");
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public ChronoLocalDateTime<?> getExpireDate(int id) {
        LocalDateTime expireDate = null;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT expire_date FROM licenses WHERE user_id=?");
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                expireDate = resultSet.getTimestamp("expire_date").toLocalDateTime();
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return expireDate;
    }

    public void updateLicenseStatus(String licenseStatus, int id) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE licenses SET license_status=? WHERE user_id=?");
            preparedStatement.setString(1, licenseStatus);
            preparedStatement.setInt(2, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }
}

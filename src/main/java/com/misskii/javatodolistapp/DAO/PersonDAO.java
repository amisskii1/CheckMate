package com.misskii.javatodolistapp.DAO;

import com.misskii.javatodolistapp.Models.Person;
import com.misskii.javatodolistapp.Models.Task;
import com.misskii.javatodolistapp.Util.DBUtil;
import com.misskii.javatodolistapp.Util.PersonNotExistsException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PersonDAO {
    private final Connection connection = DBUtil.getConnection();

    public int getPersonIdByEmail(String email) throws SQLException, PersonNotExistsException {
        int id = 0;
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT user_id FROM users WHERE user_email=?");
        preparedStatement.setString(1, email);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()){
            id = resultSet.getInt("user_id");
        }else {
            throw new PersonNotExistsException();
        }
        return id;
    }

    public void createNewPerson(Person person){
        try {
            PreparedStatement preparedStatement =
                    connection.prepareStatement("INSERT INTO users (user_name, user_email, user_password) VALUES (?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, person.getName());
            preparedStatement.setString(2, person.getEmail());
            preparedStatement.setString(3, person.getPassword());
            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if(resultSet.next()){
                int id = resultSet.getInt(1);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public List<Person> loginUser(){
        List<Person> people = new ArrayList<>();

        try {
            Statement statement = connection.createStatement();
            String SQL = "SELECT * FROM users";
            ResultSet resultSet = statement.executeQuery(SQL);
            while(resultSet.next()){
                Person person = new Person();
                person.setId(resultSet.getInt("user_id"));
                person.setName(resultSet.getString("user_name"));
                person.setEmail(resultSet.getString("user_email"));
                person.setPassword(resultSet.getString("user_password"));
                people.add(person);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return people;
    }
}

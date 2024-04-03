module com.misskii.javatodolistapp {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires org.postgresql.jdbc;
    requires com.fasterxml.jackson.databind;
    requires org.apache.httpcomponents.httpclient;
    requires org.apache.httpcomponents.httpcore;
    requires spring.web;


    opens com.misskii.javatodolistapp to javafx.fxml;
    opens com.misskii.javatodolistapp.Models to javafx.base;
    exports com.misskii.javatodolistapp;
    exports com.misskii.javatodolistapp.Controllers;
    opens com.misskii.javatodolistapp.Controllers to javafx.fxml;
}
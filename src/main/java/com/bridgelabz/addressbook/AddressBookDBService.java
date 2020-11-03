package com.bridgelabz.addressbook;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AddressBookDBService {

    /*Method to setup Connection with database*/
    private Connection getConnection() throws SQLException {
        String jdbcURL = "jdbc:mysql://localhost:3306/addressbook";
        String userName = "root";
        String password = "G@ur@v@123";
        Connection con;
        System.out.println("Connecting to database: " + jdbcURL);
        con = DriverManager.getConnection(jdbcURL, userName, password);
        System.out.println("Connection Successful: " + con);
        return con;
    }
    /*Method to read Contact from database*/
    public List<Contact> readData() throws AddressBookException {
        String sql = "select * from user_details join contact on user_details.user_id = contact.user_id " +
                "join location on user_details.user_id = location.user_id\n" +
                "join user_contact_type_link on user_details.user_id = user_contact_type_link.user_id\n" +
                " join contact_type on user_contact_type_link.type_id = contact_type.type_id;";
        return this.getAddressBookUsingDB(sql);
    }

    private List<Contact> getAddressBookUsingDB(String sql) throws AddressBookException {
        List<Contact> ContactList = null;
        try (Connection connection = this.getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            ContactList = this.getEmployeepayrollData(resultSet);
        } catch (SQLException e) {
            throw new AddressBookException(e.getMessage(), AddressBookException.ExceptionType.SQL_EXCEPTION);
        }
        return ContactList;
    }
    private List<Contact> getEmployeepayrollData(ResultSet resultSet) throws AddressBookException {
        List<Contact> ContactList = new ArrayList<>();
        try {
            while (resultSet.next()) {
                int user_id = resultSet.getInt("user_id");
                int type_id = resultSet.getInt("type_id");
                String first_name = resultSet.getString("first_name");
                String last_name = resultSet.getString("last_name");
                String address = resultSet.getString("address");
                String city = resultSet.getString("city");
                String state = resultSet.getString("state");
                String zip_code = resultSet.getString("zip");
                String phone_number = resultSet.getString("phone");
                String email = resultSet.getString("email");
                String contact_type = resultSet.getString("type_of_contact");
                ContactList.add(new Contact(first_name, last_name, address, city, state, zip_code, phone_number, email, user_id, type_id, contact_type));
            }
        } catch (SQLException e) {
            throw new AddressBookException(e.getMessage(), AddressBookException.ExceptionType.SQL_EXCEPTION);
        }
        return ContactList;
    }
}
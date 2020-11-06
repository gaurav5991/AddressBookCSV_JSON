package com.bridgelabz.addressbook;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddressBookDBService {
    private PreparedStatement contactDataStatement;

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
                LocalDate startDate = resultSet.getDate("date_added").toLocalDate();
                ContactList.add(new Contact(first_name, last_name, address, city, state, zip_code, phone_number, email, user_id, type_id, contact_type, startDate));
            }
        } catch (SQLException e) {
            throw new AddressBookException(e.getMessage(), AddressBookException.ExceptionType.SQL_EXCEPTION);
        }
        return ContactList;
    }

    /*method to update contact details*/
    public int updateContactData(String first_name, String lastName) throws AddressBookException {
        return this.updateContactDataUsingPreparedStatement(first_name, lastName);
    }

    /*method to update contact details using prepared statement*/
    private int updateContactDataUsingPreparedStatement(String first_name, String lastName) throws AddressBookException {
        try (Connection connection = this.getConnection()) {
            String sql = "Update user_details set last_name = ? where first_name = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, lastName);
            preparedStatement.setString(2, first_name);
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new AddressBookException(e.getMessage(), AddressBookException.ExceptionType.SQL_EXCEPTION);
        }
    }

    /*Method to generate result for contact data*/
    public List<Contact> getContactData(String first_name) throws AddressBookException {
        List<Contact> contactList;
        if (this.contactDataStatement == null)
            this.prepareStatementForContactData();
        try {
            contactDataStatement.setString(1, first_name);
            ResultSet resultSet = contactDataStatement.executeQuery();
            contactList = this.getEmployeepayrollData(resultSet);
        } catch (SQLException e) {
            throw new AddressBookException(e.getMessage(), AddressBookException.ExceptionType.SQL_EXCEPTION);
        }
        return contactList;
    }

    /*Method to generate prepared statement for contact data*/
    private void prepareStatementForContactData() throws AddressBookException {
        try {
            Connection connection = this.getConnection();
            String sql = "select * from user_details join contact on user_details.user_id = contact.user_id " +
                    "join location on user_details.user_id = location.user_id\n" +
                    "join user_contact_type_link on user_details.user_id = user_contact_type_link.user_id\n" +
                    " join contact_type on user_contact_type_link.type_id = contact_type.type_id where user_details.first_name = ?;";
            contactDataStatement = connection.prepareStatement(sql);
        } catch (SQLException e) {
            throw new AddressBookException(e.getMessage(), AddressBookException.ExceptionType.SQL_EXCEPTION);
        }
    }

    /*Method to get number of contacts added for given date range*/
    public List<Contact> readContactDataForDateRange(LocalDate startDate, LocalDate endDate) throws AddressBookException {
        String sql = String.format("select * from user_details join contact on user_details.user_id = contact.user_id " +
                "join location on location.user_id = user_details.user_id\n" +
                "join user_contact_type_link on user_contact_type_link.user_id = user_details.user_id\n" +
                " join contact_type on contact_type.type_id = user_contact_type_link.type_id where user_details.date_added between '%s' and '%s'; ", Date.valueOf(startDate), Date.valueOf(endDate));
        return this.getAddressBookUsingDB(sql);
    }

    /*Method to read Contact by State*/
    public Map<String, String> readContactByState() {
        String sql = "select user_details.first_name as first_name,location.state as state from user_details " +
                "inner join location on user_details.user_id = location.user_id";
        return getAggregateByParameter("first_name", "state", sql);
    }

    private Map<String, String> getAggregateByParameter(String first_name, String option, String sql) {
        Map<String, String> CountMap = new HashMap<>();
        try (Connection connection = this.getConnection();) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                String getResult1 = resultSet.getString(option);
                String getResult2 = resultSet.getString(first_name);
                CountMap.put(getResult1, getResult2);
            }
        } catch (SQLException e) {
            e.getMessage();
        }
        return CountMap;
    }

    /*Method to read Contact by City*/
    public Map<String, String> readContactByCity() {
        String sql = "select user_details.first_name as first_name,location.city as city from user_details " +
                "inner join location on user_details.user_id = location.user_id";
        return getAggregateByParameter("first_name", "city", sql);
    }

    /*Method to add Contacts*/
    public Contact addContact(String firstName, String lastName, String address, String city,
                              String state, String zip, String phone, String email, LocalDate date,
                              int user_id, int type_id, String type_of_contact) throws AddressBookException {
        int contactId = -1;
        Contact contact = null;
        Connection connection = null;
        try {
            connection = this.getConnection();
            connection.setAutoCommit(false);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try (Statement statement = connection.createStatement();) {
            String sql = String.format("insert into user_details(user_id,first_name,last_name,date_added)" +
                    "values(%d,'%s','%s','%s'); ", user_id, firstName, lastName, Date.valueOf(date));
            int rowAffected = statement.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);
            if (rowAffected == 1) {
                ResultSet resultSet = statement.getGeneratedKeys();
                if (resultSet.next()) {
                    contactId = resultSet.getInt("user_id");
                }
            }
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException e1) {
                throw new AddressBookException(e1.getMessage(), AddressBookException.ExceptionType.UNABLE_TO_ROLLBACK);
            }
            throw new AddressBookException(e.getMessage(), AddressBookException.ExceptionType.SQL_EXCEPTION);
        }

        try (Statement statement = connection.createStatement()) {
            String sql = String.format("insert into location (user_id,address,city,state,zip) " + "VALUES (%d,'%s','%s','%s','%s')",
                    user_id, address, city, state, zip);
            int rowAffected = statement.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);
            if (rowAffected == 1) {
                ResultSet resultSet = statement.getGeneratedKeys();
                if (resultSet.next()) {
                    contactId = resultSet.getInt("user_id");
                }
            }
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException exception) {
                exception.printStackTrace();
            }
            throw new AddressBookException(AddressBookException.ExceptionType.SQL_EXCEPTION);
        }
        try (Statement statement = connection.createStatement()) {
            String sql = String.format("insert into contact (user_id,phone,email) " + "VALUES (%d,'%s','%s')",
                    user_id, phone, email);
            int rowAffected = statement.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);
            if (rowAffected == 1) {
                ResultSet resultSet = statement.getGeneratedKeys();
                if (resultSet.next()) {
                    contactId = resultSet.getInt("user_id");
                }
            }
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException exception) {
                exception.printStackTrace();
            }
            throw new AddressBookException(AddressBookException.ExceptionType.SQL_EXCEPTION);
        }

        try (Statement statement = connection.createStatement()) {
            String sql = String.format("insert into contact_type(type_id,type_of_contact) " + "VALUES (%d,'%s')",
                    type_id, type_of_contact);
            int rowAffected = statement.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);
            if (rowAffected == 1) {
                ResultSet resultSet = statement.getGeneratedKeys();
                if (resultSet.next()) {
                    contactId = resultSet.getInt("type_id");
                }
            }
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException exception) {
                exception.printStackTrace();
            }
            throw new AddressBookException(AddressBookException.ExceptionType.SQL_EXCEPTION);
        }
        try (Statement statement = connection.createStatement()) {
            String sql = String.format("insert into user_contact_type_link (user_id,type_id) " + "VALUES (%d,%d)",
                    user_id, type_id);
            int rowAffected = statement.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);
            if (rowAffected == 1) {
                contact = new Contact(firstName, lastName, address, city, state, zip, phone, email, user_id, type_id, type_of_contact, date);
            }
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException exception) {
                exception.printStackTrace();
            }
            throw new AddressBookException(AddressBookException.ExceptionType.SQL_EXCEPTION);
        }
        try {
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                connection.rollback();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return contact;
    }

    /*Method to add Multiple Contacts*/
    public void addMultipleContactsToDB(List<Contact> contactDetailsDataList) {
        Map<Integer, Boolean> contactAdditionStatus = new HashMap<>();
        contactDetailsDataList.forEach(contactEntry ->
                {
                    Runnable task = () -> {
                        contactAdditionStatus.put(contactEntry.hashCode(), false);
                        System.out.println("Contact Entry Being Added: "+Thread.currentThread().getName());
                        try {
                            this.addContact(contactEntry.getFirst_name(), contactEntry.getFirst_name(), contactEntry.getAddress(), contactEntry.getCity(),
                                    contactEntry.getState(), contactEntry.getZip_code(), contactEntry.getPhone_number(), contactEntry.getEmail(), contactEntry.getStartDate(),
                            contactEntry.getUser_id(), contactEntry.getType_id(), contactEntry.getContact_type());
                        } catch (AddressBookException e) {
                            System.out.println("Unable to add contact entry to DB");
                        }
                        contactAdditionStatus.put(contactEntry.hashCode(), true);
                        System.out.println("Contact Entry Added: " + Thread.currentThread().getName());
                    };
                    Thread thread = new Thread(task, contactEntry.getFirst_name());
                    thread.start();
                }
        );
        while(contactAdditionStatus.containsValue(false)) {
            try {
                Thread.sleep(10);
            } catch(InterruptedException e) {
                System.out.println("Unable to sleep");
            }
        }
    }
}

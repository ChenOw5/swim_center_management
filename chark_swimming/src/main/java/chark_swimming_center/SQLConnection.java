package chark_swimming_center;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class SQLConnection {
    static String DatabaseName = "chark";
    static String url = "jdbc:mysql://localhost:3306/" + DatabaseName;
    static String username = "root";
    static String password = "";

    public static boolean isConnected() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(url, username, password);
            connection.close();
            return true;
        } catch (Exception e) {
            System.out.println("Error: " + e);
            return false;
        }
    }

    public static void signup(String user_email, String user_username, String user_password) {
        String query = "INSERT INTO user_data (user_email, user_name, user_password, user_privilege) VALUES (?, ?, ?, ?)";
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(url, username, password);
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, user_email);
            statement.setString(2, user_username);
            statement.setString(3, user_password);
            statement.setString(4, "user");
            int rowsAffected = statement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("User " + user_username + " signed up successfully!");
            } else {
                System.out.println("Signup failed.");
            }

            statement.close();
            connection.close();
        } catch (Exception e) {
            System.out.println("Error In Sign Up: " + e);
        }
    }

    public static boolean EmailExist(String user_email) {
        boolean exists = false;
        String query = "SELECT * FROM user_data WHERE user_email = ?";
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(url, username, password);
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, user_email);
            ResultSet resultSet = statement.executeQuery();
            exists = resultSet.next();
            resultSet.close();
            statement.close();
            connection.close();
        } catch (Exception e) {
            System.out.println("Error In Checking Email: " + e);
        }
        return exists;
    }

    public static boolean UsernameExist(String usernameCheck) {
        boolean exists = false;
        String query = "SELECT * FROM user_data WHERE user_name = ?";
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(url, username, password);
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, usernameCheck);
            ResultSet resultSet = statement.executeQuery();
            exists = resultSet.next();
            resultSet.close();
            statement.close();
            connection.close();
        } catch (Exception e) {
            System.out.println("Error In Checking Username: " + e);
        }
        return exists;
    }

    public static boolean matchPassword(String user_email, String user_password) {
        boolean match = false;
        String true_password = "";
        String query = "SELECT user_password FROM user_data WHERE user_email = ?";
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(url, username, password);
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, user_email);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                true_password = resultSet.getString("user_password");
            }
            resultSet.close();
            statement.close();
            connection.close();
        } catch (Exception e) {
            System.out.println("Error In Checking Password: " + e);
        }
        return user_password.equals(true_password);
    }

    public static String getUsername(String user_email) {
        String res = "";
        String query = "SELECT user_name FROM user_data WHERE user_email = ?";
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(url, username, password);
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, user_email);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                res = resultSet.getString("user_name");
            }
            resultSet.close();
            statement.close();
            connection.close();
        } catch (Exception e) {
            System.out.println("Error In Getting Username: " + e);
        }
        return res;
    }

    public static String getPrivilege(String user_email) {
        String res = "";
        String query = "SELECT user_privilege FROM user_data WHERE user_email = ?";
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(url, username, password);
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, user_email);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                res = resultSet.getString("user_privilege");
            }
            resultSet.close();
            statement.close();
            connection.close();
        } catch (Exception e) {
            System.out.println("Error In Getting Privilege: " + e);
        }
        return res;
    }

    public static void updateUsername(String user_email, String new_username) {
        String query = "UPDATE user_data SET user_name = ? WHERE user_email = ?";
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(url, username, password);
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, new_username);
            statement.setString(2, user_email);
            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Username Updated Successfully");
            } else {
                System.out.println("Username Failed to Update");
            }
            statement.close();
            connection.close();
        } catch (Exception e) {
            System.out.println("Error In Updating Username: " + e);
        }
    }

    public static void updatePassword(String user_email, String new_password) {
        String query = "UPDATE user_data SET user_password = ? WHERE user_email = ?";
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(url, username, password);
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, new_password);
            statement.setString(2, user_email);
            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Password Updated Successfully");
            } else {
                System.out.println("Password Failed to Update");
            }
            statement.close();
            connection.close();
        } catch (Exception e) {
            System.out.println("Error In Updating Password: " + e);
        }
    }

    public static void updatePrivilege(String user_email, String privilege) {
        String query = "UPDATE user_data SET user_privilege = ? WHERE user_email = ?";
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(url, username, password);
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, privilege.toLowerCase());
            statement.setString(2, user_email);
            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Privilege Updated Successfully");
            } else {
                System.out.println("Privilege Failed to Update");
            }
            statement.close();
            connection.close();
        } catch (Exception e) {
            System.out.println("Error In Updating Privilege: " + e);
        }
    }

    public static int deleteUser(String user_email) {
        String query = "DELETE FROM user_data WHERE user_email = ?;";
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(url, username, password);
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, user_email);
            int rowsAffected = statement.executeUpdate();
            statement.close();
            connection.close();
            if (rowsAffected > 0) {
                System.out.println("User Deletion is Successful");
                return 1;
            } else {
                System.out.println("User Deletion Failed");
                return 0;
            }
        } catch (Exception e) {
            System.out.println("Error In Deleting User: " + e);
            return -1;
        }
    }

    public static ObservableList<User> getAllUsers() {
        ObservableList<User> userList = FXCollections.observableArrayList();
        String query = "SELECT * FROM user_data;";
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(url, username, password);
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                User user = new User(
                        resultSet.getString("user_email"),
                        resultSet.getString("user_name"),
                        resultSet.getString("user_password"),
                        resultSet.getString("user_privilege")
                );
                userList.add(user);
            }
            resultSet.close();
            statement.close();
            connection.close();
        } catch (Exception e) {
            System.out.println("Error In Retrieving All Sessions Booked: " + e);
        }
        return userList;
    }

    public static int createPayment(double amount, String paymentMethod) {
        String query = "INSERT INTO payment_data (amount, payment_method) VALUES (?, ?)";
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(url, username, password);
            PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setDouble(1, amount);
            preparedStatement.setString(2, paymentMethod);
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    int insertedId = generatedKeys.getInt(1);
                    System.out.println("Payment is Successful. Inserted ID: " + insertedId);
                    return insertedId;
                }
            }
            preparedStatement.close();
            connection.close();
        } catch (Exception e) {
            System.out.println("Error In Making Payment: " + e);
        }
        return -1;
    }

    public static int BookSession(String user_email, LocalDate date, int start_time, int end_time, int pax, int payment_id) {
        String query = "INSERT INTO session_data(user_email, session_date, start_time, end_time, session_pax, payment_id, is_deleted) VALUES (?, ?, ?, ?, ?, ?,0)";
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(url, username, password);
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, user_email);
            statement.setDate(2, Date.valueOf(date));
            statement.setInt(3, start_time);
            statement.setInt(4, end_time);
            statement.setInt(5, pax);
            statement.setInt(6, payment_id);
            int rowsAffected = statement.executeUpdate();
            statement.close();
            connection.close();
            if (rowsAffected > 0) {
                System.out.println("Booking is Successful");
                return 1;
            } else {
                System.out.println("Booking failed.");
                return 0;
            }
        } catch (Exception e) {
            System.out.println("Error In Making Booking: " + e);
            return -1;
        }
    }

    public static ObservableList<Session> getBookedSessions(String user_email) {
        ObservableList<Session> sessionList = FXCollections.observableArrayList();
        String query = "SELECT session_id, user_email ,session_date, start_time, end_time, session_pax, payment_data.amount, payment_data.payment_method, is_deleted " +
                "FROM session_data JOIN payment_data ON session_data.payment_id = payment_data.payment_id " +
                "WHERE session_data.user_email = ? AND session_data.is_deleted = 0 ORDER BY session_date ASC";
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(url, username, password);
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, user_email);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Session session = new Session(
                        resultSet.getInt("session_id"),
                        resultSet.getString("user_email"),
                        resultSet.getDate("session_date"),
                        resultSet.getString("start_time"),
                        resultSet.getString("end_time"),
                        resultSet.getInt("session_pax"),
                        resultSet.getDouble("amount"),
                        resultSet.getString("payment_method"),
                        resultSet.getInt("is_deleted")
                );
                sessionList.add(session);
            }
            resultSet.close();
            statement.close();
            connection.close();
        } catch (Exception e) {
            System.out.println("Error In Retrieving Sessions Booked: " + e);
        }
        return sessionList;
    }

    public static ObservableList<Session> getBookedSessions_admin() {
        ObservableList<Session> sessionList = FXCollections.observableArrayList();
        String query = "SELECT session_id, user_email ,session_date, start_time, end_time, session_pax, payment_data.amount, payment_data.payment_method, is_deleted " +
                "FROM session_data JOIN payment_data ON session_data.payment_id = payment_data.payment_id ";
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(url, username, password);
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Session session = new Session(
                        resultSet.getInt("session_id"),
                        resultSet.getString("user_email"),
                        resultSet.getDate("session_date"),
                        resultSet.getString("start_time"),
                        resultSet.getString("end_time"),
                        resultSet.getInt("session_pax"),
                        resultSet.getDouble("amount"),
                        resultSet.getString("payment_method"),
                        resultSet.getInt("is_deleted")
                );
                sessionList.add(session);
            }
            resultSet.close();
            statement.close();
            connection.close();
        } catch (Exception e) {
            System.out.println("Error In Retrieving All Sessions Booked: " + e);
        }
        return sessionList;
    }

    public static int updateSession(int session_ID, LocalDate new_date, int new_startHour, int new_endHour) {
        String query = "UPDATE session_data SET session_date= ?,start_time= ?,end_time= ? WHERE session_id = ?;";
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(url, username, password);
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setDate(1, Date.valueOf(new_date));
            statement.setInt(2, new_startHour);
            statement.setInt(3, new_endHour);
            statement.setInt(4, session_ID);
            int rowsAffected = statement.executeUpdate();
            statement.close();
            connection.close();
            if (rowsAffected > 0) {
                System.out.println("Session Update is Successful");
                return 1;
            } else {
                System.out.println("Session Update Failed");
                return 0;
            }
        } catch (Exception e) {
            System.out.println("Error In Updating Session: " + e);
            return -1;
        }
    }

    public static int updateSession_admin(int session_ID, LocalDate new_date, int new_startHour, int new_endHour, int pax, double amount, String payment_method, int is_deleted) {
        String sessionQuery = "UPDATE session_data SET session_date = ?, start_time = ?, end_time = ?, session_pax = ?,is_deleted = ? WHERE session_id = ?";
        String paymentQuery = "UPDATE payment_data SET amount = ?, payment_method = ? WHERE payment_id = (SELECT payment_id FROM session_data WHERE session_id = ?)";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(url, username, password);

            PreparedStatement sessionStmt = connection.prepareStatement(sessionQuery);
            sessionStmt.setDate(1, Date.valueOf(new_date));
            sessionStmt.setInt(2, new_startHour);
            sessionStmt.setInt(3, new_endHour);
            sessionStmt.setInt(4, pax);
            sessionStmt.setInt(5, is_deleted);
            sessionStmt.setInt(6, session_ID);
            int sessionRows = sessionStmt.executeUpdate();
            sessionStmt.close();

            PreparedStatement paymentStmt = connection.prepareStatement(paymentQuery);
            paymentStmt.setDouble(1, amount);
            paymentStmt.setString(2, payment_method);
            paymentStmt.setInt(3, session_ID);
            int paymentRows = paymentStmt.executeUpdate();
            paymentStmt.close();

            connection.close();

            if (sessionRows > 0 && paymentRows > 0) {
                System.out.println("Session and Payment Update are Successful");
                return 1;
            } else {
                System.out.println("Session or Payment Update Failed");
                return 0;
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error In Updating Session and Payment: " + e);
            return -1;
        }
    }


    public static boolean softDeleteSession(int session_ID) {
        String query = "UPDATE session_data SET is_deleted = ? WHERE session_id = ?;";
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(url, username, password);
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, 1);
            statement.setInt(2, session_ID);
            int rowsAffected = statement.executeUpdate();
            statement.close();
            connection.close();
            if (rowsAffected > 0) {
                System.out.println("Session Soft Deletion is Successful");
                return true;
            } else {
                System.out.println("Session Soft Deletion Failed");
                return false;
            }
        } catch (Exception e) {
            System.out.println("Error In Deleting Session: " + e);
            return false;
        }
    }

    public static boolean hardDeleteSession(int session_ID) {
        String getPaymentIdQuery = "SELECT payment_id FROM session_data WHERE session_id = ?";
        String deletePaymentQuery = "DELETE FROM payment_data WHERE payment_id = ?";
        String deleteSessionQuery = "DELETE FROM session_data WHERE session_id = ?";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(url, username, password);

            PreparedStatement getPaymentStmt = connection.prepareStatement(getPaymentIdQuery);
            getPaymentStmt.setInt(1, session_ID);
            ResultSet resultSet = getPaymentStmt.executeQuery();

            int paymentId = -1;
            if (resultSet.next()) {
                paymentId = resultSet.getInt("payment_id");
            }
            resultSet.close();
            getPaymentStmt.close();

            PreparedStatement deleteSessionStmt = connection.prepareStatement(deleteSessionQuery);
            deleteSessionStmt.setInt(1, session_ID);
            int sessionDeleted = deleteSessionStmt.executeUpdate();
            deleteSessionStmt.close();

            if (paymentId != -1) {
                PreparedStatement deletePaymentStmt = connection.prepareStatement(deletePaymentQuery);
                deletePaymentStmt.setInt(1, paymentId);
                deletePaymentStmt.executeUpdate();
                deletePaymentStmt.close();
            }

            connection.close();

            if (sessionDeleted > 0) {
                System.out.println("Session and Payment Deletion Successful");
                return true;
            } else {
                System.out.println("Session Deletion Failed");
                return false;
            }
        } catch (Exception e) {
            System.out.println("Error In Deleting Session and Payment: " + e);
            return false;
        }
    }

    public static List<Term> getTerms() {
        List<Term> terms = new ArrayList<>();
        String query = "SELECT * FROM term_data";
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(url, username, password);
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Term term = new Term(
                        resultSet.getInt("term_id"),
                        resultSet.getString("term")
                );
                terms.add(term);
            }
            resultSet.close();
            statement.close();
            connection.close();
        } catch (Exception e) {
            System.out.println("Error In Retrieving Trainers: " + e);
        }
        return terms;
    }

    public static boolean addTerm(String termText) {
        String query = "INSERT INTO term_data (term) VALUES (?)";
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(url, username, password);
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, termText);
            int rowsAffected = statement.executeUpdate();
            statement.close();
            connection.close();
            return rowsAffected > 0;
        } catch (Exception e) {
            System.out.println("Error adding term: " + e);
            return false;
        }
    }

    public static boolean updateTerm(int termId, String newText) {
        String query = "UPDATE term_data SET term = ? WHERE term_id = ?";
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(url, username, password);
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, newText);
            statement.setInt(2, termId);
            int rowsAffected = statement.executeUpdate();
            statement.close();
            connection.close();
            return rowsAffected > 0;
        } catch (Exception e) {
            System.out.println("Error updating term: " + e);
            return false;
        }
    }

    public static boolean deleteTerm(int termId) {
        String query = "DELETE FROM term_data WHERE term_id = ?";
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(url, username, password);
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, termId);
            int rowsAffected = statement.executeUpdate();
            statement.close();
            connection.close();
            return rowsAffected > 0;
        } catch (Exception e) {
            System.out.println("Error deleting term: " + e);
            return false;
        }
    }
}

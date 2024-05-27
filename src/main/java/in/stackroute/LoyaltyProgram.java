package in.stackroute;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Use streams API to implement the following methods.
 */
public class LoyaltyProgram {

    private final Map<Integer, Customer> customers;
    private final List<Transaction> transactions;
    private final List<Reward> rewards;

    /**
     * Registers a new customer. The customer is assigned a unique ID and that
     * unique ID is used as the key in the customers map.
     * Constraints:
     * Name should not be null or empty.
     * Email should not be null or empty and should be in the format
     * <name>@<domain>.<tld>.
     * Phone should not be null or empty and should be a 10-digit number.
     * Customer can only be registered once.
     *
     * @param name  Name of the customer
     * @param email Email of the customer
     * @param phone Phone number of the customer
     */

    static Connection connection;

    static {
        try {
            connection = DbConnection.getInstance();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public LoyaltyProgram() throws SQLException {
        customers = new HashMap<>();
        transactions = new ArrayList<>();
        rewards = new ArrayList<>();
    }


    public void registerCustomer(String name, String email, String phone) throws SQLException {
        if (name == null || name.isEmpty()) {
            System.out.println("Provide proper details of customer");
        }
        if (!isValidEmail(email)) {
            System.out.println("Invalid email format. Please enter a valid email address.");
            return;
        }


        if (!isValidPhone(phone)) {
            System.out.println("Invalid phone number format. Please enter a valid phone number.");
            return;
        }
        Customer c1 = new Customer(name, email, phone);

       if(!customers.containsValue(c1))
       {
           customers.put(c1.getCustomerId(), c1);
       }

        try {
            String query = "INSERT INTO customers (first_name, email, phone,join_date) VALUES (?, ?, ?,CURRENT_DATE())";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, email);
            preparedStatement.setString(3, phone);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());;
        }

    }

    private boolean isValidEmail(String email) {
        // Regular expression for validating email
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    private boolean isValidPhone(String phone) {
        // Regular expression for validating phone number
        String phoneRegex = "^\\d{10}$";
        Pattern pattern = Pattern.compile(phoneRegex);
        Matcher matcher = pattern.matcher(phone);
        return matcher.matches();
    }

    /**
     * Add a transaction for a customer. The transaction is added to the
     * transactions list.
     * Constraints:
     * Customer should be registered before adding a transaction.
     * Once a transaction is added, the customer's points should be updated.
     *
     * @param customerId ID of the customer
     * @param amount     Amount of the transaction
     */
    public void addTransaction(int customerId, double amount) throws SQLException {


        if (!isCustomerRegistered(customerId)) {
            System.out.println("Customer is not registered. Please register the customer first.");
            return;
        }

        Transaction t1 = new Transaction(customerId, amount);
        transactions.add(t1);

       try {
           String query = "INSERT INTO transactions (customer_id, amount,points_earned) VALUES (?, ?,?)";
           PreparedStatement preparedStatement = connection.prepareStatement(query);
           preparedStatement.setInt(1, customerId);
           preparedStatement.setDouble(2, amount);
           preparedStatement.setDouble(3, t1.getPointsEarned());
           preparedStatement.executeUpdate();

       } catch (Exception e) {
           throw new RuntimeException(e);
       }

        updateCustomerPoints(customerId, amount);

    }


    private void updateCustomerPoints(int customerId, double amount) throws SQLException {

        int currentPoints = getCustomerPoints(customerId);


        int pointsEarned = (int) (amount / 10);


        try
        {
            String updateQuery = "UPDATE customers SET loyalty_points = ? WHERE customer_id = ?";
            try (PreparedStatement updateStatement = connection.prepareStatement(updateQuery)) {
                updateStatement.setInt(1, currentPoints + pointsEarned);
                updateStatement.setInt(2, customerId);
                updateStatement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    private boolean isCustomerRegistered(int customerId) throws SQLException {

        ResultSet resultSet;
        try
        {
            String query = "SELECT * FROM customers WHERE customer_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, customerId);
           resultSet = preparedStatement.executeQuery();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }


        if (resultSet.next()) {
            int res = resultSet.getInt("customer_id");
            System.out.println(res);
        }

        return true;
    }


    /**
     * Add a reward to the rewards list.
     *
     * @param rewardId       ID of the reward
     * @param description    Description of the reward
     * @param pointsRequired Points required to redeem the reward
     */
    public void addReward(int rewardId, String description, int pointsRequired) throws SQLException {

        Reward r1 = new Reward(rewardId, description, pointsRequired);

        rewards.add(r1);

       try
       {
           String query = "INSERT INTO rewards (reward_id, description, points_required) VALUES (?, ?, ?)";
           PreparedStatement preparedStatement = connection.prepareStatement(query);
           preparedStatement.setInt(1, rewardId);
           preparedStatement.setString(2, description);
           preparedStatement.setInt(3, pointsRequired);
           preparedStatement.executeUpdate();
       } catch (SQLException e) {
           throw new RuntimeException(e);
       }

    }

    /**
     * Redeem a reward for a customer.
     * Constraints:
     * Customer should be registered before redeeming a reward.
     * Reward should be present in the rewards list.
     * Customer should have enough points to redeem the reward.
     *
     * @param customerId ID of the customer already registered
     * @param rewardId   ID of the reward to redeem
     */
    public void redeemReward(int customerId, int rewardId) throws SQLException {


        int customerPoints = getCustomerPoints(customerId);
        int rewardPointsRequired = getRewardPointsRequired(rewardId);

        if (customerPoints < rewardPointsRequired) {
            System.out.println("Customer does not have enough points to redeem this reward.");
            return;
        }


        updateCustomerPoints(customerId, customerPoints - rewardPointsRequired);


        issueRewardToCustomer(customerId, rewardId);
        insertRedemptionRecord(customerId, rewardId, rewardPointsRequired);
        System.out.println("Points redeemed Successfully");

    }

    private void insertRedemptionRecord(int customerId, int rewardId, int points) throws SQLException {
        String query = "INSERT INTO redemptions (customer_id, reward_id, redemption_date,points_deducted) VALUES (?, ?, CURRENT_DATE(),?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, customerId);
            preparedStatement.setInt(2, rewardId);
            preparedStatement.setInt(3, points);
            preparedStatement.executeUpdate();
        }
    }

    private int getCustomerPoints(int customerId) throws SQLException {

        int points = 0;

        String query = "SELECT loyalty_points FROM customers WHERE customer_id = ?";
       try
       {
           PreparedStatement preparedStatement = connection.prepareStatement(query);
           preparedStatement.setInt(1, customerId);
           ResultSet resultSet = preparedStatement.executeQuery();
           if (resultSet.next()) {
               points = resultSet.getInt("loyalty_points");
           }
       } catch (SQLException e) {
           throw new RuntimeException(e);
       }

        return points;
    }

    private int getRewardPointsRequired(int rewardId) throws SQLException {

        int pointsRequired = 0;

        String query = "SELECT points_required FROM rewards WHERE reward_id = ?";
        try
        {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, rewardId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                pointsRequired = resultSet.getInt("points_required");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return pointsRequired;
    }

    private void updateCustomerPoints(int customerId, int points) throws SQLException {
        // Update customer points in the database

       try{
           String query = "UPDATE customers SET loyalty_points = ? WHERE customer_id = ?";
           PreparedStatement preparedStatement = connection.prepareStatement(query);
           preparedStatement.setInt(1, points);
           preparedStatement.setInt(2, customerId);
           preparedStatement.executeUpdate();
       }catch(SQLException e)
       {
           throw new RuntimeException(e);
       }

    }

    private void issueRewardToCustomer(int customerId, int rewardId) {

        System.out.println("Reward with ID " + rewardId + " redeemed successfully by customer with ID " + customerId);
    }

    /**
     * View the customer's information.
     * Constraints:
     * Customer should be registered before viewing the information.
     * Print the customer information in a tabular format.
     */
    public void viewCustomerInfo(int customerId) throws SQLException {

        if (!isCustomerRegistered(customerId)) {
            System.out.println("Customer is not registered. Please register the customer first.");
            return;
        }

        String query = "SELECT * FROM customers WHERE customer_id = ?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, customerId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                System.out.println("Customer Information:");
                System.out.println("ID: " + resultSet.getInt("customer_id"));
                System.out.println("Name: " + resultSet.getString("first_name"));
                System.out.println("Email: " + resultSet.getString("email"));
                System.out.println("Phone: " + resultSet.getString("phone"));
                System.out.println("Points Balance: " + resultSet.getInt("loyalty_points"));

            } else {
                System.out.println("Customer not found.");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

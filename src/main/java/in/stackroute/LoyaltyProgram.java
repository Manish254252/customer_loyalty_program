package in.stackroute;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Use streams API to implement the following methods.
 */
public class LoyaltyProgram {

  private Map<Integer, Customer> customers;
  private List<Transaction> transactions ;
  private List<Reward> rewards;

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

  public LoyaltyProgram() {
    customers = new HashMap<>();
    transactions = new ArrayList<>();
    rewards = new ArrayList<>();
  }


  public void registerCustomer(String name, String email, String phone) throws SQLException {
    Customer c1 = new Customer(name,email,phone);

    // TODO: Implement this method

    customers.put(Customer.idCounter,c1);

      String query = "INSERT INTO customers (first_name, email, phone) VALUES (?, ?, ?)";
      PreparedStatement preparedStatement = connection.prepareStatement(query);
      preparedStatement.setString(1, name);
      preparedStatement.setString(2, email);
      preparedStatement.setString(3, phone);
      preparedStatement.executeUpdate();

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
    // TODO: Implement this method

    if (!isCustomerRegistered(customerId)) {
      System.out.println("Customer is not registered. Please register the customer first.");
      return;
    }

    Transaction t1 = new Transaction(customerId,amount);
    transactions.add(t1);

    String query = "INSERT INTO transactions (customer_id, amount,points_earned) VALUES (?, ?,?)";
    PreparedStatement preparedStatement = connection.prepareStatement(query);
    preparedStatement.setInt(1, customerId);
    preparedStatement.setDouble(2, amount);
    preparedStatement.setDouble(3, t1.getPointsEarned());
    preparedStatement.executeUpdate();

    updateCustomerPoints(customerId, amount);

  }


  private void updateCustomerPoints(int customerId, double amount) throws SQLException {
    // Retrieve current points
    int currentPoints = getCustomerPoints(customerId);

    // Calculate points earned based on transaction amount (e.g., 1 point for every $10 spent)
    int pointsEarned = (int) (amount / 10);

    // Update customer points in the database
    String updateQuery = "UPDATE customers SET loyalty_points = ? WHERE customer_id = ?";
    try (PreparedStatement updateStatement = connection.prepareStatement(updateQuery)) {
      updateStatement.setInt(1, currentPoints + pointsEarned);
      updateStatement.setInt(2, customerId);
      updateStatement.executeUpdate();
    }
  }


  private boolean isCustomerRegistered(int customerId) throws SQLException {
    // Check if the customer is registered (you need to implement this method)
    String query = "SELECT * FROM customers WHERE customer_id = ?";
    PreparedStatement preparedStatement = connection.prepareStatement(query);
    preparedStatement.setInt(1, customerId);
    ResultSet resultSet = preparedStatement.executeQuery();


    if(resultSet.next())
    {
     int  res=resultSet.getInt("customer_id");
      System.out.println(res);}

    return true;
  }


  /**
   * Add a reward to the rewards list.
   * 
   * @param rewardId          ID of the reward
   * @param description       Description of the reward
   * @param pointsRequired    Points required to redeem the reward
   */
  public void addReward(int rewardId, String description, int pointsRequired) throws SQLException {

    Reward r1 = new Reward(rewardId,description,pointsRequired);
    // TODO: Implement this method
    rewards.add(r1);

    String query = "INSERT INTO rewards (reward_id, description, points_required) VALUES (?, ?, ?)";
    PreparedStatement preparedStatement = connection.prepareStatement(query);
    preparedStatement.setInt(1, rewardId);
    preparedStatement.setString(2, description);
    preparedStatement.setInt(3, pointsRequired);
    preparedStatement.executeUpdate();

  }

  /**
   * Redeem a reward for a customer.
   * Constraints:
   * Customer should be registered before redeeming a reward.
   * Reward should be present in the rewards list.
   * Customer should have enough points to redeem the reward.
   * 
   * @param customerId    ID of the customer already registered
   * @param rewardId      ID of the reward to redeem
   */
  public void redeemReward(int customerId, int rewardId) throws SQLException {
    // TODO Implement this method

    int customerPoints = getCustomerPoints(customerId);
    int rewardPointsRequired = getRewardPointsRequired(rewardId);

    if (customerPoints < rewardPointsRequired) {
      System.out.println("Customer does not have enough points to redeem this reward.");
      return;
    }

    // Deduct points from the customer's balance
    updateCustomerPoints(customerId, customerPoints - rewardPointsRequired);

    // Issue the reward to the customer
    issueRewardToCustomer(customerId, rewardId);

  }

  private int getCustomerPoints(int customerId) throws SQLException {
    // Retrieve customer points from the database
    int points = 0;

      String query = "SELECT loyalty_points FROM customers WHERE customer_id = ?";
      PreparedStatement preparedStatement = connection.prepareStatement(query);
      preparedStatement.setInt(1, customerId);
      ResultSet resultSet = preparedStatement.executeQuery();
      if (resultSet.next()) {
        points = resultSet.getInt("loyalty_points");
      }

    return points;
  }

  private int getRewardPointsRequired(int rewardId) throws SQLException {
    // Retrieve reward points required from the database
    int pointsRequired = 0;

      String query = "SELECT points_required FROM rewards WHERE customer_id = ?";
      PreparedStatement preparedStatement = connection.prepareStatement(query);
      preparedStatement.setInt(1, rewardId);
      ResultSet resultSet = preparedStatement.executeQuery();
      if (resultSet.next()) {
        pointsRequired = resultSet.getInt("points_required");
      }

    return pointsRequired;
  }

  private void updateCustomerPoints(int customerId, int points) throws SQLException {
    // Update customer points in the database

      String query = "UPDATE customers SET points = ? WHERE customer_id = ?";
      PreparedStatement preparedStatement = connection.prepareStatement(query);
      preparedStatement.setInt(1, points);
      preparedStatement.setInt(2, customerId);
      preparedStatement.executeUpdate();

  }

  private void issueRewardToCustomer(int customerId, int rewardId) {
    // Implement logic to issue the reward to the customer (e.g., update customer's reward history)
    System.out.println("Reward with ID " + rewardId + " redeemed successfully by customer with ID " + customerId);
  }

  /**
   * View the customer's information.
   * Constraints:
   * Customer should be registered before viewing the information.
   * Print the customer information in a tabular format.
   */
  public void viewCustomerInfo(int customerId) throws SQLException {
//    customers.entrySet().stream().forEach(System.out::println);

    String query = "SELECT * FROM customers WHERE customer_id = ?";
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
      // Add logic to display transaction history if needed
    } else {
      System.out.println("Customer not found.");
  }
}}

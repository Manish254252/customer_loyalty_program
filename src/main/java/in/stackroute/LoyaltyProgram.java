package in.stackroute;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Use streams API to implement the following methods.
 */
public class LoyaltyProgram {

  private Map<Integer, Customer> customers = new HashMap<>();
  private List<Transaction> transactions = new ArrayList<>();
  private List<Reward> rewards = new ArrayList<>();

  /**
   * Registers a new customer. The customer is assigned a unique ID and that
   * unique ID is used as the key in the customers map.
   * 
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
  public void registerCustomer(String name, String email, String phone) {
    // TODO: Implement this method
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
  public void addTransaction(int customerId, double amount) {
    // TODO: Implement this method
  }

  /**
   * Add a reward to the rewards list.
   * 
   * @param rewardId          ID of the reward
   * @param description       Description of the reward
   * @param pointsRequired    Points required to redeem the reward
   */
  public void addReward(int rewardId, String description, int pointsRequired) {
    // TODO: Implement this method
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
  public void redeemReward(int customerId, int rewardId) {
    // TODO Implement this method
  }

  /**
   * View the customer's information.
   * Constraints:
   * Customer should be registered before viewing the information.
   * Print the customer information in a tabular format.
   * 
   * @param customerId
   */
  public void viewCustomerInfo(int customerId) {

  }
}

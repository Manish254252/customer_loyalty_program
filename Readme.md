# Case Study: Customer Loyalty and Rewards Program in Java

## Objective:

Develop a Java application for managing a customer loyalty and rewards program for a retail store. The application will allow customers to register, make purchases, earn loyalty points, and redeem rewards.

### Requirements:

**Customer Registration:**

Customers can register by providing their name, email, and phone number.
Each customer is assigned a unique ID.

**Earning Points:**

Customers earn points based on the amount spent in each transaction (e.g., 1 point for every $10 spent).

**Reward Redemption:**

Customers can redeem points for rewards.
The program maintains a list of available rewards and their point values.

**View Customer Information:**

Ability to view customer details, points balance, and transaction history.

### Design:

Classes
- `Customer`
- `Transaction`
- `Reward`
- `LoyaltyProgram`

### Explanation

`Customer Class`: Represents a customer with attributes like customerId, name, email, phone, and points.

`Transaction Class`: Represents a transaction with attributes like transactionId, customerId, amount, and pointsEarned.

`Reward Class`: Represents a reward with attributes like rewardId, description, and pointsRequired.

`LoyaltyProgram Class`: Manages the registration of customers, addition of transactions, rewards, and redemption of rewards. It also 
provides a method to view customer information.

`Main Class`: Provides a menu-driven console application to interact with the loyalty program.

### Further Improvements:

This program covers the basic functionality of a customer loyalty and rewards system, and can be expanded with additional features such as persistent storage (e.g., using a database), GUI interface, and more sophisticated point calculation and reward redemption logic. A sample `db.sql` file is provided to create the necessary tables for storing customer, transaction, and reward data.
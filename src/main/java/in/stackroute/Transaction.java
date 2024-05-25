package in.stackroute;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Transaction {
  private static int transactionCounter = 1;
  private int transactionId;
  private int customerId;
  private double amount;
  private int pointsEarned;

  public Transaction(int customerId, double amount) {
      this.transactionId = transactionCounter++;
      this.customerId = customerId;
      this.amount = amount;
      this.pointsEarned = (int) (amount / 10);  // 1 point for every $10
  }

  
}


package in.stackroute;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Customer {
  // Static counter to generate unique customer IDs
  private static int idCounter = 1;
  private int customerId;
  private String name;
  private String email;
  private String phone;
  private int points;

  public Customer(String name, String email, String phone) {
    this.customerId = idCounter++;
    this.name = name;
    this.email = email;
    this.phone = phone;
    this.points = 0;
  }
}

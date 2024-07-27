package in.stackroute;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class LoyaltyProgramTest {

     private  LoyaltyProgram loyaltyProgram;
 private   List<Transaction> abc;

    @BeforeEach
    public void setUp() throws SQLException {
        loyaltyProgram = mock(LoyaltyProgram.class);
        abc = mock();
    }






    @Test
    public void testRegisterCustomer() throws SQLException {
        // Mock the behavior of registerCustomer method
        doNothing().when(loyaltyProgram).registerCustomer(anyString(), anyString(), anyString());

        // Call the registerCustomer method with sample data
        loyaltyProgram.registerCustomer("John Doe", "john@example.com", "1234567890");

        // Verify that the registerCustomer method is called with the correct parameters
        verify(loyaltyProgram).registerCustomer("John Doe", "john@example.com", "1234567890");
    }

    @Test
    public void testAddTransaction() throws SQLException {
        doNothing().when(loyaltyProgram).registerCustomer(anyString(), anyString(), anyString());
        loyaltyProgram.registerCustomer("Jane Doe", "jane@example.com", "9876543210");

        // Mock the behavior of addTransaction method to actually add a transaction
        doAnswer(invocation -> {
            int customerId = invocation.getArgument(0);
            double amount = invocation.getArgument(1);
            int pointsEarned = (int) (amount / 10);
            Transaction t1 = new Transaction(customerId, amount);
            t1.setPointsEarned(pointsEarned);
            loyaltyProgram.getTransactions().add(t1);



            System.out.println("Added transaction: " + abc);
            System.out.println("Transaction list size: " + abc.size());

            return null; // Method is void, so return null
        }).when(loyaltyProgram).addTransaction(anyInt(), anyDouble());

        // Call the addTransaction method with sample data
        loyaltyProgram.addTransaction(1, 100.0);

        // Verify that the transaction is added correctly
        Optional<Transaction> transaction = abc.stream().findFirst();
        assertEquals(10, transaction.get());
    }


    @Test
    public void testAddReward() throws SQLException {
        loyaltyProgram.addReward(4, "Discount Coupon", 100);
     Optional<Reward> r1 =   loyaltyProgram.getRewards().stream().findFirst();

    }

    @Test
    public void testRedeemReward() throws SQLException, SQLException {
        loyaltyProgram.registerCustomer("Alice", "alice@example.com", "1112223334");
        loyaltyProgram.addTransaction(1, 200.0);
        loyaltyProgram.addReward(1, "Discount Coupon", 100);
        loyaltyProgram.redeemReward(1, 1);
        // Add assertions to verify that the reward is redeemed successfully
    }

    @Test
    public void testViewCustomerInfo() throws SQLException {
        loyaltyProgram.registerCustomer("Bob", "bob@example.com", "5556667778");
        loyaltyProgram.viewCustomerInfo(1);
        // Add assertions to verify that the customer information is displayed correctly
    }
}

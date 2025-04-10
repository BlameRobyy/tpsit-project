import org.junit.Test;
import static org.junit.Assert.*;
import java.util.Scanner;

public class AccTest {

    @Test
    public void testGettersAndSetters() {
        User user = new User("12345", "testUser");
        Acc account = new Acc("testAcc", "password123", user);

        assertEquals("testAcc", account.getUsername());
        assertEquals("password123", account.getPassword());
        assertEquals(user, account.getUser());

        account.setUsername("newUsername");
        account.setPassword("newPassword");
        User newUser = new User("67890", "newUser");
        account.setUser(newUser);

        assertEquals("newUsername", account.getUsername());
        assertEquals("newPassword", account.getPassword());
        assertEquals(newUser, account.getUser());
    }

    @Test
    public void testDeposit() {
        // Creazione di un oggetto Bank e User
        User user = new User("12345", "testUser");
        Bank bank = new Bank();
        BankAccount account = new BankAccount("12345", "testUser");
        bank.createAccountList(account);  // Associa l'account al banco
        account.setPersonalBalance(0.0);

        // Eseguiamo un deposito con una somma valida
        double d = 100.0;
        user.deposit(bank, d);  // Depositiamo 100
        assertEquals(0.0, user.getPersonalWallet(), 0.01);  // Portafoglio dell'utente
        assertEquals(100.0, account.getPersonalBalance(), 0.01);  // Bilancio del conto bancario

    }

    @Test
    public void testWithdraw() {
        // Creazione di un oggetto Bank e User
        User user = new User("12345", "testUser");
        Bank bank = new Bank();
        BankAccount account = new BankAccount("12345", "testUser");
        account.setPersonalBalance(100.0);  // Impostiamo un saldo iniziale di 100
        bank.createAccountList(account);

        // Eseguiamo un prelievo valido
        double w = 50.0;
        user.withdraw(bank, w);  // Preleviamo 50
        assertEquals(50.0, account.getPersonalBalance(), 0.01);  // Il saldo del conto bancario è 50 dopo il prelievo
        assertEquals(150.0, user.getPersonalWallet(), 0.01);  // Il portafoglio dell'utente è 50, non cambia
    }


    @Test
    public void testInvest() {
        // Creazione di un oggetto Bank e User
        User user = new User("12345", "testUser");
        Bank bank = new Bank();
        BankAccount account = new BankAccount("12345", "testUser");
        account.setPersonalBalance(200.0);  // Impostiamo un saldo iniziale di 200
        bank.createAccountList(account);

        System.out.println("\nTesting Investment:");

        // Creiamo un investimento
        Investment investment = new Investment(100.0, 1, 0);  // Investiamo 100 per una durata di 60 mesi con rischio 0
        account.addInvestmentList(investment);

        System.out.println("Investment of 100 added.");
        System.out.println("Account balance after investment: " + account.getPersonalBalance());
        System.out.println("Number of investments: " + account.getInvestmentList().size());

        assertEquals(100.0, account.getPersonalBalance(), 0.01);  // Il saldo del conto è ora 100
        assertEquals(1, account.getInvestmentList().size());  // L'investimento è stato aggiunto

        // Proviamo a fare un altro investimento che non è possibile (saldo non sufficiente)
        Investment badInvestment = new Investment(500.0, 1, 0);  // Tentiamo di investire 500
        account.addInvestmentList(badInvestment);
        System.out.println("Attempted investment of 500 (Should fail).");
        System.out.println("Number of investments after failed attempt: " + account.getInvestmentList().size());

        assertEquals(1, account.getInvestmentList().size());  // L'investimento non è stato aggiunto
    }
}

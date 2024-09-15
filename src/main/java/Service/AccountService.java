package Service;

import Model.Account;
import DAO.AccountDAO;

// Service class that acts as a layer between the controller and the DAO for account-related operations
public class AccountService {
    // Data Access Object for interacting with the database
    private AccountDAO accountDAO;

    // Default constructor that initializes the AccountDAO
    public AccountService(){
        accountDAO = new AccountDAO();
    }

    // Constructor that allows injecting an existing AccountDAO
    public AccountService(AccountDAO accountDAO){
        this.accountDAO = accountDAO;
    }

    // Method to add a new account
    // If username is blank or password is less than 4 characters, return null
    public Account addAccount(Account account){
        if (account.username == "") return null;
        if (account.password.length() < 4) return null;
        return accountDAO.insertAccount(account);
    }

    // Method to retrieve an account based on username and password
    public Account getAccountByUserPass(Account account) {
        return accountDAO.selectAccount(account);
    }
}

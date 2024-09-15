package Service;

import Model.Account;
import DAO.AccountDAO;

public class AccountService {
    private AccountDAO accountDAO;

    public AccountService(){
        accountDAO = new AccountDAO();
    }

    public AccountService(AccountDAO accountDAO){
        this.accountDAO = accountDAO;
    }

    public Account addAccount(Account account){
        if (account.username == "") return null;
        if (account.password.length() < 4) return null;
        return accountDAO.insertAccount(account);
    }

    public Account getAccountByUserPass(Account account) {
        return accountDAO.selectAccount(account);
    }
}

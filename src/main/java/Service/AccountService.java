package Service;

import Model.Account;
import DAO.AccountDAO;

public class AccountService {
    private AccountDAO accountDAO;
    
    public AccountService() {
        accountDAO = new AccountDAO();
    }

    public AccountService(AccountDAO accountDAO) {
        this.accountDAO = accountDAO;
    }

    public Account addAccount(Account account) {
        if(account.getUsername() != "" && account.getPassword().length() >= 4 && accountDAO.getAccountByName(account.username) == null) {
            return accountDAO.insertAccount(account);
        }
        return null;
    }

    public Account verifyAccount(Account account) {
        Account foundAccount = accountDAO.getAccountByName(account.getUsername());
        if(foundAccount != null && foundAccount.getUsername().equals(account.getUsername()) && foundAccount.getPassword().equals(account.getPassword())) {
            return foundAccount;
        }
        return null;
    }

}

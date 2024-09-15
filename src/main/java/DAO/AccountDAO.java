package DAO;

import Model.Account;
import Util.ConnectionUtil;
import java.sql.*;

public class AccountDAO {
    public Account insertAccount(Account account){
        Connection connection = ConnectionUtil.getConnection();
        try {
            String sql = "INSERT INTO Account (username, password) VALUES (?,?)";
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, account.getUsername());
            ps.setString(2, account.getPassword());
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if(rs.next()){
                int accountID = rs.getInt("account_id");
                System.out.println(accountID);
                return new Account(accountID, account.getUsername(), account.getPassword());
            }
        } catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    public Account selectAccount(Account account){
        Connection connection = ConnectionUtil.getConnection();
        try {
            String sql = "SELECT account_id FROM Account WHERE username = ? AND password = ?";
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, account.getUsername());
            ps.setString(2, account.getPassword());
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                int accountID = rs.getInt("account_id");
                return new Account(accountID, account.getUsername(), account.getPassword());
            }
        } catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }
}

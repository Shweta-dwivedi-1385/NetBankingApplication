import java.sql.*;

public class BankService {

	public boolean validateAccountHolder(int accNo, String enteredName) throws SQLException {
		try (Connection con = DBConnection.getConnection();
			 PreparedStatement ps = con.prepareStatement("SELECT name FROM accounts WHERE acc_no=?")) {
			ps.setInt(1, accNo);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				String actualName = rs.getString("name");
				return actualName.equalsIgnoreCase(enteredName);
			}
		}
		return false;
	}

    public boolean accountExists(int accNo) throws SQLException {
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement("SELECT COUNT(*) FROM accounts WHERE acc_no=?")) {
            ps.setInt(1, accNo);
            ResultSet rs = ps.executeQuery();
            rs.next();
            return rs.getInt(1) > 0;
        }
    }

    public double checkBalance(int accNo) throws SQLException {
        if (!accountExists(accNo)) 
			return -1;

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement("SELECT balance FROM accounts WHERE acc_no=?")) {
            ps.setInt(1, accNo);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) 
				return rs.getDouble("balance");
        }
        return -1;
    }

    public void deposit(int accNo, double amt) throws SQLException {
        if (!accountExists(accNo)) 
			return;

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement("UPDATE accounts SET balance = balance + ? WHERE acc_no=?")) {
            ps.setDouble(1, amt);
            ps.setInt(2, accNo);
            ps.executeUpdate();
        }
    }

    public boolean withdraw(int accNo, double amt) throws SQLException {
        double bal = checkBalance(accNo);
        if (bal == -1 || bal < amt) 
			return false;

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement("UPDATE accounts SET balance = balance - ? WHERE acc_no=?")) {
            ps.setDouble(1, amt);
            ps.setInt(2, accNo);
            ps.executeUpdate();
            return true;
        }
    }
}
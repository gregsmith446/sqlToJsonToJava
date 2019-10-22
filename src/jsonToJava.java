import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class jsonToJava {

	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
		Class.forName("com.mysql.cj.jdbc.Driver");
		
		Connection conn = null;
		
		// first parameter is the location of the specified database
		conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1/Business", "root", "SQLpassword1!");
	
		// object of statement class will help us to execute queries

		Statement st = conn.createStatement();
		
		ResultSet rs = st.executeQuery("select * from CustomerInfo where Location ='Asia' and PurchasedDate='2019-10-21' LIMIT 1;");

		// while there is another line in the resultSet
		// pull out all the records and print them (this loop replaces repetitive code)
		while (rs.next())
		{
			CustomerDetails c = new CustomerDetails();
			
			c.setCourseName(rs.getString(1));
			c.setPurchaseDate(rs.getString(2));
			c.setAmount(rs.getInt(3));
			c.setLocation(rs.getString(4));	
			
			System.out.println(c.getCourseName());
			System.out.println(c.getPurchaseDate());
			System.out.println(c.getAmount());
			System.out.println(c.getLocation());
		}		
		conn.close();
	}
}

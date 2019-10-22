import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class jsonToJava {

	public static void main(String[] args) throws ClassNotFoundException, SQLException, JsonGenerationException, JsonMappingException, IOException {
		// TODO Auto-generated method stub
		Class.forName("com.mysql.cj.jdbc.Driver");
		Connection conn = null;
		CustomerDetails c = new CustomerDetails();
		conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1/Business", "root", "SQLpassword1!");

		Statement st = conn.createStatement();
		ResultSet rs = st.executeQuery("select * from CustomerInfo where Location ='Asia' and PurchasedDate='2019-10-21' LIMIT 1;");
		while (rs.next())
		{			
			c.setCourseName(rs.getString(1));
			c.setPurchaseDate(rs.getString(2));
			c.setAmount(rs.getInt(3));
			c.setLocation(rs.getString(4));	
			
			System.out.println(c.getCourseName());
			System.out.println(c.getPurchaseDate());
			System.out.println(c.getAmount());
			System.out.println(c.getLocation());	
		}		
		// writeValue() arguments (where the json file to write in is located, the java object to write from)
			ObjectMapper o = new ObjectMapper();
			o.writeValue(new File("C:\\Users\\gregs\\eclipse-workspace\\JsonJava\\customerInfo.json"), c);
		
		conn.close();
	}
}

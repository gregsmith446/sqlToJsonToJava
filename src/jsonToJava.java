import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class jsonToJava {

	public static void main(String[] args) throws ClassNotFoundException, SQLException, JsonGenerationException, JsonMappingException, IOException {
		// TODO Auto-generated method stub
		Class.forName("com.mysql.cj.jdbc.Driver");
		Connection conn = null;
		ArrayList<CustomerDetails> a = new ArrayList<CustomerDetails>();
		conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1/Business", "root", "SQLpassword1!");

		Statement st = conn.createStatement();
		ResultSet rs = st.executeQuery("select * from CustomerInfo where Location ='Asia' and PurchasedDate='2019-10-21';");
		while (rs.next())
		{			
			CustomerDetails c = new CustomerDetails();
			
			// 3 different json files = 3 different java objects
			c.setCourseName(rs.getString(1));
			c.setPurchaseDate(rs.getString(2));
			c.setAmount(rs.getInt(3));
			c.setLocation(rs.getString(4));	
			a.add(c);
		}		
		
		// create JSON files with jackson API maven dependency
		for(int i = 0; i < a.size(); i++)
		{
			ObjectMapper o = new ObjectMapper();
			o.writeValue(new File("C:\\Users\\gregs\\eclipse-workspace\\JsonJava\\customerInfo"+i+".json"), a.get(i));
		}
		
		conn.close();
	}
}

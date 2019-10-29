import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import org.apache.commons.text.StringEscapeUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

public class oneSingleJson {
	public static void main(String[] args) throws ClassNotFoundException, SQLException, JsonGenerationException, JsonMappingException, IOException {
		
		Class.forName("com.mysql.cj.jdbc.Driver");
		Connection conn = null;
		conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1/Business", "root", "SQLpassword1!");

		ArrayList<CustomerDetails> a = new ArrayList<CustomerDetails>();
		JSONArray js = new JSONArray();
		
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
		
		// create a JSON array of DB data via looping
		for(int i = 0; i < a.size(); i++)
		{
			ObjectMapper o = new ObjectMapper();
			o.writeValue(new File("C:\\Users\\gregs\\eclipse-workspace\\JsonJava\\customerInfo"+i+".json"), a.get(i));
			Gson g = new Gson();
			// create JSON string from java object with GSON
			String jsonString = g.toJson(a.get(i));
			// add each string to JSON array
			js.add(jsonString);
		}
		
		// use JSON simple maven dependency to create one JSON file from JSON string (of all database records)
		JSONObject jo = new JSONObject();
		// (name of the JSON array to create, "the array to put in the JSON array")
		jo.put("transactionRecords", js);
		String unescapeString = StringEscapeUtils.unescapeJava(jo.toJSONString());
		// Wherever the "{ before the JSON occurs, replace it with just {
		String string1 = unescapeString.replace("\"{", "{");
		String finalString = string1.replace("}\"", "}");
		
		try (FileWriter file = new FileWriter("C:\\Users\\gregs\\eclipse-workspace\\JsonJava\\SingleJson.json"))
		{
			file.write(finalString);
		}
	
		
		
		conn.close();
	}
	
}

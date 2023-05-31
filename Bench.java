import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class Bench{

	private static String[] read(String path){
		List<String> lines = new ArrayList<>();
		try{
			FileReader fr = new FileReader(path);
			BufferedReader br = new BufferedReader(fr);
			String line;
			while((line = br.readLine()) != null){
				if (line.trim().equals("")) continue;
				lines.add(line);
			}
		}catch(IOException e){
			System.out.println("File is missing or not readable!");
		}
		return lines.toArray(new String[0]);
	}
	
	private static long[] query(String[] queries){
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		long[] executionTime = new long[queries.length];
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://abenobashi.xyz:7707/University?" + "user=py&password=password123!");
			stmt = conn.createStatement();
			
			int index = 0;
			for(String query : queries){
				if(query == null) break;
				if(query.contains("INSERT") || query.contains("UPDATE") || query.contains("DELETE") || query.contains("REPLACE")) {
					long start = System.currentTimeMillis();
					rs = stmt.executeQuery(query);
					conn.commit();
					executionTime[index] = System.currentTimeMillis() - start;
				}else{
					long start = System.currentTimeMillis();
					rs = stmt.executeQuery(query);
					executionTime[index] = System.currentTimeMillis() - start;
				}
				index++;
			}
			conn.close();

			/**
			
			Testing output code, not necessary to measure performance as this is all Java time, not SQL.

			while(rs.next()){
				System.out.println(rs.getString("firstName"));
			}

			**/

		} catch (SQLException ex) {
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
		} catch (ClassNotFoundException classexception){
			System.out.println("Did not find the driver");
		}
		return executionTime;
	}

	public static void main(String[] args){
		long[] times = Bench.query(Bench.read("QueryList.sql"));
		for (long time: times) System.out.println(time);
		return;
	}
}

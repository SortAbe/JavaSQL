import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.ResultSet;

public class Bench{

	private static String[] ReadFile(){
		String[] lns = new String[100];
		try{
			FileReader fr = new FileReader("QueryList.sql");
			BufferedReader br = new BufferedReader(fr);
			String ln;
			int i = 0;
			while((ln = br.readLine()) != null){
				//System.out.println(ln);
				lns[i] = ln;
				i++;
			}
		}catch(IOException e){
			System.out.println("QueryList.sql file is missing or not readable!");
		}
		return lns;
	}
	
	private static long QueryStatic(String[] queries){
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		long execTime = 0;
		try {
			conn = DriverManager.getConnection("jdbc:mysql://abenobashi.xyz:7707/University?" + "user=py&password=password123!");
			stmt = conn.createStatement();
			
			for(String query : queries){
				if(query == null) break;
				long start = System.currentTimeMillis();
				rs = stmt.executeQuery(query);
				long singleTime = System.currentTimeMillis() - start;
				execTime += singleTime;
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
		}
		return execTime;
	}

	public static void main(String[] args){
		System.out.println(String.valueOf(Bench.QueryStatic(Bench.ReadFile())));
		return;
	}
}

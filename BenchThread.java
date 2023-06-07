//Abrahan Diaz
//Version 0.5
//All the things she said
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

public class BenchThread implements Runnable{

	public String[] FemaleNames;
	public String[] MaleNames;
	public String[] LastNames;
	public String[] cities;
	public String[] states;
	public String[] courses;
	public String[] departments;
	public String[] college;

	public static String[] ReadTable(String query){
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		String[] stringResults = new String[5000];
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://abenobashi.xyz:7707/University?" + "user=py&password=password123!");
			stmt = conn.createStatement();
			rs = stmt.executeQuery(query);
			int index = 0;
			while(rs.next()){
				stringResults[index] = rs.getString("name");
				index++;
			}
			conn.close();
		} catch (SQLException ex) {
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
		} catch (ClassNotFoundException classexception){
			System.out.println("Did not find the driver");
		}
		return stringResults;
	}

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

	@Override
	public void run(){

		this.FemaleNames = BenchThread.ReadTable("SELECT name FROM femaleNames;");
		this.MaleNames = BenchThread.ReadTable("SELECT name FROM maleNames;");
		this.LastNames = BenchThread.ReadTable("SELECT name FROM lastNames;");
		this.cities = BenchThread.ReadTable("SELECT DISTINCT city FROM sAddress;");
		this.states = BenchThread.ReadTable("SELECT DISTINCT state FROM sAddress;");
		this.courses = BenchThread.ReadTable("SELECT title FROM course;");
		this.departments = BenchThread.ReadTable("SELECT dept_name FROM departments;");
		this.college = BenchThread.ReadTable("SELECT DISTINCT college FROM departments;");

		try {
			Thread.sleep(10_000);
		} catch(InterruptedException ex) {
			System.out.println("Thread is kill!");
		}

		long[] times = BenchThread.query(BenchThread.read("QueryList.sql"));
		for (long time: times) {
			try{
				System.out.println(time);
				Thread.sleep(200);
			}catch(InterruptedException e){
				System.out.println("Thread is kill!");
			}
		}
	}

}

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
import java.util.Random;

public class BenchThread implements Runnable{

	private static String url = "jdbc:mysql://abenobashi.xyz:7707/University?user=py&password=password123!";

	public String[] FemaleNames;
	public String[] MaleNames;
	public String[] LastNames;
	public String[] cities;
	public String[] states;
	public String[] courses;
	public String[] departments;
	public String[] college;

	public BenchThread(){
		if(this.FemaleNames != null) this.FemaleNames = BenchThread.ReadTable("SELECT name FROM femaleNames;", "name");
		if(this.MaleNames != null) this.MaleNames = BenchThread.ReadTable("SELECT name FROM maleNames;", "name");
		if(this.LastNames != null) this.LastNames = BenchThread.ReadTable("SELECT name FROM lastNames;", "name");
		if(this.cities != null) this.cities = BenchThread.ReadTable("SELECT DISTINCT city FROM sAddress;", "city");
		if(this.states != null) this.states = BenchThread.ReadTable("SELECT DISTINCT state FROM sAddress;", "state");
		if(this.courses != null) this.courses = BenchThread.ReadTable("SELECT title FROM course;", "title");
		if(this.departments != null) this.departments = BenchThread.ReadTable("SELECT dept_name FROM departments;", "dept_name");
		if(this.college != null) this.college = BenchThread.ReadTable("SELECT DISTINCT college FROM departments;", "college");
		BenchThread.query(BenchThread.read("WakeUp.sql"));
	}

	public static void sleep(long time){
		try {
			Thread.sleep(time);
		} catch(InterruptedException ex) {
			System.out.println("Thread is kill!");
		}
	}

	public static String[] ReadTable(String query, String column){
		String[] stringResults = new String[5000];
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection conn = DriverManager.getConnection(url);
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			int index = 0;
			while(rs.next()){
				stringResults[index] = rs.getString(column);
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
			String query = "";
			while((line = br.readLine()) != null){
				if (line.trim().equals("") || (line.charAt(0) == '-' && line.charAt(1) == '-')) continue;
				if(!line.contains(";")) query += line; 
				else{
					query += line; 
					lines.add(query);
					query = "";
				}
			}
		}catch(IOException e){
			System.out.println("File is missing or not readable!");
		}
		return lines.toArray(new String[0]);
	}
	
	private static long[] query(String[] queries){
		long[] executionTime = new long[queries.length];
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection conn = DriverManager.getConnection(url);
			Statement stmt = conn.createStatement();
			ResultSet rs = null;
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
		Random random = new Random();
		long standOff = (long) random.nextDouble() * 1000l;
		sleep(standOff);

		long[] times = BenchThread.query(BenchThread.read("QueryList.sql"));
		for (long time: times) {
			System.out.println(time);
			sleep(200);
		}
	}
}

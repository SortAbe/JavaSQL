//Abrahan Diaz
//Version 0.5
//All the things she said
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BenchThread implements Runnable{

	private static String url = "jdbc:mysql://abenobashi.xyz:7707/University?user=py&password=password123!";

	public static String[] FemaleNames;
	public static String[] MaleNames;
	public static String[] LastNames;
	public static String[] cities;
	public static String[] states;
	public static String[] courses;
	public static String[] departments;
	public static String[] college;
	public static String femk = "#FEM#", malk = "#MAL#", lask = "#LAS#", citk = "#CIT#", stak = "#STA#", cork = "#COR#", depk = "#DEP#", colk = "#COL#";

	public BenchThread(){
		FemaleNames = BenchThread.ReadTable("SELECT name FROM femaleNames;", "name");
		MaleNames = BenchThread.ReadTable("SELECT name FROM maleNames;", "name");
		LastNames = BenchThread.ReadTable("SELECT name FROM lastNames;", "name");
		cities = BenchThread.ReadTable("SELECT DISTINCT city FROM sAddress;", "city");
		states = BenchThread.ReadTable("SELECT DISTINCT state FROM sAddress;", "state");
		courses = BenchThread.ReadTable("SELECT title FROM course;", "title");
		departments = BenchThread.ReadTable("SELECT dept_name FROM departments;", "dept_name");
		college = BenchThread.ReadTable("SELECT DISTINCT college FROM departments;", "college");
		BenchThread.query(BenchThread.read("WakeUp.sql"));
	}

	public static void sleep(long time){
		try {
			Thread.sleep(time);
		} catch(InterruptedException ex) {
			System.out.println("Thread is kill!");
		}
	}

	public static String generate(String key){
		Random random = new Random();
		String out = "";
		switch(key){
			case "#FEM#": 
				out = FemaleNames[random.nextInt(0, FemaleNames.length)];
				break;
			case "#MAL#": 
				out = MaleNames[random.nextInt(0, MaleNames.length)];
				break;
			case "#LAS#": 
				out = LastNames[random.nextInt(0, LastNames.length)];
				break;
			case "#CIT#": 
				out = cities[random.nextInt(0, cities.length)];
				break;
			case "#STA#": 
				out = states[random.nextInt(0, states.length)];
				break;
			case "#COR#": 
				out = courses[random.nextInt(0, courses.length)];
				break;
			case "#DEP#": 
				out = departments[random.nextInt(0, departments.length)];
				break;
			case "#COL#": 
				out = college[random.nextInt(0, college.length)];
				break;
		}
		return out;
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
				line = line.replaceAll( femk, generate( femk));
				line = line.replaceAll( malk, generate( malk));
				line = line.replaceAll( lask, generate( lask));
				line = line.replaceAll( citk, generate( citk));
				line = line.replaceAll( stak, generate( stak));
				line = line.replaceAll( cork, generate( cork));
				line = line.replaceAll( depk, generate( depk));
				line = line.replaceAll( colk, generate( colk));
				if(!line.contains(";")) query += line; 
				else{
					query += line; 
					lines.add(query);
					query = "";
				}
			}
			br.close();
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
		long standOff = (long) (random.nextDouble() * 3000l);
		sleep(standOff);

		long[] times = BenchThread.query(BenchThread.read("QueryList.sql"));
		for (long time: times) {
			System.out.println(time);
			sleep(200);
		}
	}
}

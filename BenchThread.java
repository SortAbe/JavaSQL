//Abrahan Diaz
//Version 0.9
//All the things she said

//TODO
//Need to start staging this week.
//Need to detach databases

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Collections;
import java.util.Random;


public class BenchThread implements Runnable {

	private static String server = System.getenv("server");
	private static String pass = System.getenv("dbpass");
	private static String port = System.getenv("dbport");
	private static String user = System.getenv("dbuser");
	public static String url = "jdbc:mysql://" + server + ":" + port + "/University?user=" + user + "&password=" + pass;

	private static String serverAux = System.getenv("serverAux");
	private static String passAux = System.getenv("dbpassAux");
	private static String portAux = System.getenv("dbportAux");
	private static String userAux = System.getenv("dbuserAux");
	public static String urlAux = "jdbc:mysql://" + serverAux + ":" + portAux
		+ "/University?user=" + userAux + "&password=" + passAux;
	private static String table;

	public static String[] FemaleNames, MaleNames, LastNames;
	public static String[] cities, states;
	public static String[] courses, department, college;

	public static String femk = "#FEM#", malk = "#MAL#", lask = "#LAS#";
	public static String citk = "#CIT#", stak = "#STA#";
	public static String cork = "#COR#", depk = "#DEP#", colk = "#COL#";

	public BenchThread(String BatchID) {
//		table = BatchID;
//		try {
//			Connection conn = DriverManager.getConnection(url);
//			Statement stmt = conn.createStatement();
//			stmt.executeUpdate("CREATE TABLE " + table + "(" +
//				"queryId INT," +
//				"query VARCHAR(400)," +
//				"time INT);");
//			conn.close();
//		} catch (SQLException ex) {
//			System.out.println("Constructor SQLException: " + ex.getMessage());
//			System.out.println("Constructor SQLState: " + ex.getSQLState());
//			System.out.println("Constructor VendorError: " + ex.getErrorCode());
//		}
		FemaleNames = BenchThread.readTable("SELECT name FROM femaleNames;", "name");
		MaleNames = BenchThread.readTable("SELECT name FROM maleNames;", "name");
		LastNames = BenchThread.readTable("SELECT name FROM lastNames;", "name");
		cities = BenchThread.readTable("SELECT DISTINCT city FROM sAddress;", "city");
		states = BenchThread.readTable("SELECT DISTINCT state FROM sAddress;", "state");
		courses = BenchThread.readTable("SELECT title FROM course;", "title");
		department = BenchThread.readTable("SELECT dept_name FROM department;", "dept_name");
		college = BenchThread.readTable("SELECT DISTINCT college FROM department;", "college");
		BenchThread.query(BenchThread.read("WakeUp.sql"));
	}

	public static void sleep(long time) {
		try {
			Thread.sleep(time);
		} catch (InterruptedException ex) {
			System.out.println("Thread is kill!");
		}
	}

	public static String generate(String key) {
		Random random = new Random();
		String out = "";
		switch (key) {
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
				out = department[random.nextInt(0, department.length)];
				break;
			case "#COL#":
				out = college[random.nextInt(0, college.length)];
				break;
		}
		return out;
	}

	public static String[] readTable(String query, String column) {
		List<String> results = new ArrayList<>();
		try {
			Connection conn = DriverManager.getConnection(url);
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()) {
				results.add(rs.getString(column));
			}
			conn.close();
		} catch (SQLException ex) {
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
		}
		return results.toArray(new String[0]);
	}

	public static void writeTable(String[] queries, String db) {
		try {
			Connection conn = DriverManager.getConnection(db);
			conn.setAutoCommit(false);
			Statement stmt = conn.createStatement();
			for (String query : queries) {
				stmt.executeUpdate(query);
			}
			conn.commit();
			conn.close();
		} catch (SQLException ex) {
			System.out.println("writeTable SQLException: " + ex.getMessage());
			System.out.println("writeTable SQLState: " + ex.getSQLState());
			System.out.println("writeTable VendorError: " + ex.getErrorCode());
		}
	}

	private static String[] read(String path) {
		List<String> lines = new ArrayList<>();
		try {
			FileReader fr = new FileReader(path);
			BufferedReader br = new BufferedReader(fr);
			String line;
			String query = "";
			while ((line = br.readLine()) != null) {
				if (line.trim().equals("") || (line.charAt(0) == '-' && line.charAt(1) == '-'))
					continue;
				line = line.replaceAll(femk, generate(femk));
				line = line.replaceAll(malk, generate(malk));
				line = line.replaceAll(lask, generate(lask));
				line = line.replaceAll(citk, generate(citk));
				line = line.replaceAll(stak, generate(stak));
				line = line.replaceAll(cork, generate(cork));
				line = line.replaceAll(depk, generate(depk));
				line = line.replaceAll(colk, generate(colk));
				if (!line.contains(";"))
					query += (line + " ");
				else {
					query += line;
					lines.add(query);
					query = "";
				}
			}
			br.close();
		} catch (IOException e) {
			System.out.println("File is missing or not readable!");
		}
		return lines.toArray(new String[0]);
	}

	private static long[] query(String[] queries) {
		long[] executionTime = new long[queries.length];
		try {
			Connection conn = DriverManager.getConnection(url);
			conn.setAutoCommit(false);
			Statement stmt = conn.createStatement();
			ResultSet rs = null;
			int index = 0;
			for (String query : queries) {
				if (query == null)
					break;
				if (query.contains("INSERT") || query.contains("UPDATE") || query.contains("DELETE")
						|| query.contains("REPLACE")) {
					long start = System.currentTimeMillis();
					stmt.executeUpdate(query);
					conn.commit();
					executionTime[index] = System.currentTimeMillis() - start;
				} else {
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
		}
		return executionTime;
	}

	private static String[] shuffle(String[] queries){
		Collections.shuffle(Arrays.asList(queries));
		return queries;
	}

	private static String[] mix(String[] left, String[] right, int split){
		double splitd = (double) (split / 100d);
		List<String> queries = new ArrayList<>();
		Random random = new Random();
		int index = -1;
		if(random.nextDouble() < splitd) {
			while(index < 0 && index > left.length) index = (int) (random.nextDouble() * left.length);
			queries.add(left[index]);
			index = -1;
		} else {
			while(index < 0 && index > right.length) index = (int) (random.nextDouble() * right.length);
			queries.add(right[(int) (random.nextDouble() * right.length)]);
			index = -1;
		}
		return queries.toArray(new String[queries.size()]);
	}

	public static String[] recipe(){
		String[] output = new String[0];
		try{
			FileReader fr = new FileReader("recipe");
			BufferedReader br = new BufferedReader(fr);
			List<String[]> queries = new ArrayList<>();
			String[] mixed = new String[100];
			String line; 
			boolean mixxed = false;
			while ((line = br.readLine()) != null) {
				mixed = new String[100];
				mixxed = false;
				if (line.charAt(0) == '#') continue;
				String[] files = line.split(",")[0].split(" ");
				for (String file : files) {
					queries.add(BenchThread.read(file + ".sql"));
				}
				String[] actions = line.split(",")[1].split(" ");
				for (int i = 0; i < actions.length; i++) {
					if (actions[i].equals("shuffle")) {
						for (int j = 0; j < queries.size(); j++) {
							BenchThread.shuffle(queries.get(j));
						}
					}
					if (actions[i].equals("mix")) {
						mixxed = true;
						mixed = BenchThread.mix(queries.get(queries.size() - 2), 
								queries.get(queries.size() - 1), Integer.parseInt(actions[i+1].trim()));
						queries.remove(queries.size() - 2);
						queries.remove(queries.size() - 1);
						queries.add(mixed);
					}
				}
				int multiplier = Integer.parseInt(line.split(",")[2].trim());
				if (mixxed) {
					while (multiplier > 0) {
						queries.add(queries.get(queries.size()-1));
						multiplier--;
					}
				} else {
					while (multiplier > 0) {
						queries.add(queries.get(queries.size()-2));
						queries.add(queries.get(queries.size()-1));
						multiplier--;
					}
				}
			}
			int totalSize = 0;
			for (String[] query : queries) {
				totalSize += query.length;
			}
			output = new String[totalSize];
			int index = 0;
			for (String[] query : queries) {
				for (int i = 0; i < query.length; i++) {
					output[index] = query[i];
					index++;
				}
			}
			br.close();
		} catch(FileNotFoundException e){
			System.out.println("Recepie seems to be missing?");
		} catch(IOException e){
			System.out.println("Error while reading the file recepie");
		}
		return output;
	}

	@Override
	public void run() {
		Random random = new Random();
		long standOff = (long) (random.nextDouble() * 3000l);
		sleep(standOff);
		String[] queries = BenchThread.recipe();
		//String[] queries = BenchThread.read("QueryList.sql");
		long[] times = BenchThread.query(queries);
		String[] results = new String[times.length];
		for (int i = 0; i < times.length; i++) {
			results[i] = "INSERT INTO " + table + " VALUES(" + i + ", \""
				+ queries[i].replaceAll("'[^']+'", "___") + "\", " + times[i] + ");";
		}
		writeTable(results, url);
	}
}

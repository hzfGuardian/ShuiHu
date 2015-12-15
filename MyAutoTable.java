
import java.sql.*;
import java.util.Scanner;

public class MyAutoTable {
	
	private Connection conn = null;
	
	private Statement stat = null;
	
	private String TableName;
	
	MyAutoTable(String name) throws Exception {
		
		TableName = name;
		
		Class.forName("org.sqlite.JDBC");
	    conn = DriverManager.getConnection("jdbc:sqlite:test.db");
	}
	
	//it is an initial step and test step which test the function of sqlite
	void init() throws Exception {
		
		stat = conn.createStatement();
		stat.executeUpdate("drop table if exists " + TableName + ";");
		stat.executeUpdate("create table " + TableName + " (id, name);");
		PreparedStatement prep = conn.prepareStatement("insert into " + TableName + " values (?, ?);");
		
	    prep.setString(1, "1000");
	    prep.setString(2, "politics");
	    prep.addBatch();
	    prep.setString(1, "1001");
	    prep.setString(2, "computers");
	    prep.addBatch();
	    prep.setString(1, "1002");
	    prep.setString(2, "smartypants");
	    prep.addBatch();
		
		conn.setAutoCommit(false);
		prep.executeBatch();
		conn.setAutoCommit(true);
		
		ResultSet rs = stat.executeQuery("select * from " + TableName + ";");
		while (rs.next()) {
		    System.out.println("id = " + rs.getString("id"));
		    System.out.println("name = " + rs.getString("name"));
		}
		
		rs.close();
		conn.close();
	}
	
	
	public static void main(String[] args) throws Exception {
		
		Scanner in = new Scanner(System.in);
		
		String table_name = in.next();
		
		MyAutoTable table = new MyAutoTable(table_name);
		
		table.init();
		
		System.out.println("\n\n/*This is the beginning of the generated program.*/\n");
		
		System.out.println("import java.util.*;\n"
				+"import java.sql.*;\n\n"
				+"public class " + table_name + "\n{");
		
		System.out.println("\tprivate Connection conn = null;\n\t"
				+ "private Statement stat = null;\n\t"
				+ "private String TableName = \"" + table_name + "\";\n\t");
		
		System.out.println("\tvoid insert(String id, String name) {\n\t\t"
			+ "stat = conn.createStatement();\n\t\t"
			+ "PreparedStatement prep = conn.prepareStatement(\"insert into \" + TableName + \" values (?, ?);\");\n\t\t"
			+ "prep.setString(1, \"1000\");\n\t\t"
			+ "prep.setString(2, \"politics\");\n\t\t"
			+ "prep.addBatch();\n\t\t"
			+ "conn.setAutoCommit(false);\n\t\t"
			+ "prep.executeBatch();\n\t\t"
			+ "conn.setAutoCommit(true);\n\t"
			+ "}\n");
		
		
		System.out.println(
				"\tvoid delete(String[] Cond_list) {\n\t\t"
				+ "String statement = \"delete from \" + TableName + \" \";\n\t\t"
				+ "if (Cond_list.length > 0) {\n\t\t\t"
				+ "statement += \" where \";\n\t\t\t"
				+ "statement += Cond_list[0];\n\t\t\t"
				
				+ "for (int i = 1;i < Cond_list.length; i++)\n\t\t\t\t"				
				+ "statement += (\" and \" + Cond_list[i]);\n\t\t"
				+ "}\n\t\t"
				+ "statement += \";\";\n\t\t"
				+ "conn.setAutoCommit(false);\n\t\t"
				+ "stat.executeUpdate(statement);\n\t\t"
				+ "conn.commit();\n\t"
				+ "}\n");
		
		System.out.println(
				"\tvoid select(String[] col, String[] Cond_list) {\n"
				+ "\t\tString sql = \"select \";\n"
				+ "\t\tif (col.length > 0) {\n"
				+ "\t\t\tsql += col[0];\n"
				+ "\t\t\tfor (int i = 1;i < col.length;i++)\n"
				+ "\t\t\tsql += (\",\" + col[i]);\n"
				+ "\t\t}\n"
				+ "\t\telse sql += \"*\";\n"
				+ "\t\tsql += (\" from \" + TableName);\n"
				+ "\t\tif (Cond_list.length > 0) {\n"
				+ "\t\t\tsql += \" where \" + Cond_list[0];\n"
				+ "\t\t\tfor (int i = 1;i < Cond_list.length;i++)\n"
				+ "\t\t\t\tsql += (\" and \" + Cond_list[i]);\n"
				+ "\t\t}\n"
				+ "\t\tsql += \";\";\n"
				+ "\t\tResultSet rs = stat.executeQuery(sql);\n"
				+ "\t\twhile (rs.next()) {\n"
				+ "\t\t\tSystem.out.println(\"id = \" + rs.getString(\"id\"));\n"
				+ "\t\t\tSystem.out.println(\"name = \" + rs.getString(\"name\"));\n"
				+ "\t\t}\n"
				+ "\t}\n");
		
		System.out.println(
				"\tvoid update(String col, String value, String[] Cond_list) {\n"
				+ "\t\tString sql = \"update \" + TableName + \" set \" + col + \" = \" + value;\n"
				+ "\t\tif (Cond_list.length > 0) {\n"
				+ "\t\t\tsql += \" where \" + Cond_list[0];\n"
				+ "\t\t\tfor (int i = 1;i < Cond_list.length;i++)\n"
				+ "\t\t\t\tsql += (\" and \" + Cond_list[i]);\n"
				+ "\t\t}\n"
				+ "\t\tsql += \";\";\n"
				+ "\t\tconn.setAutoCommit(false);\n"
				+ "\t\tstat.executeUpdate(sql);\n"
				+ "\t\tconn.commit();\n"
				+ "\t}\n");
		
		System.out.println("}\n");
		
		in.close();
		
	}

}




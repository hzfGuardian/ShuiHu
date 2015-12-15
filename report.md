#数据库操作

3130100677 黄卓斐

##1. 目的

* 编写Java程序，产生自动将数据库中一张表转换成类操作的Java程序。包括基本的数据库查询、显示与增、删、改操作。
* 输入指定数据库的表名，显示或产生Java程序文件。 
* SQLite的数据库界面如下：

![](2.png)

* Eclipse配置数据库jdbc sqlite驱动，见`sqlitejdbc-v033-nested.jar`文件。

##2. 代码实现

（1）程序中声明一个总类`MyAutoTable`，程序用SQLite数据库实现。首先用`Class.forName("org.sqlite.JDBC");`加载sqlite的数据库驱动，然后再用`DriverManager.getConnection("jdbc:sqlite:test.db");`连接当前目录下的sqlite数据库，其本质是一个独立的数据库文件`test.db`。
	
	private Connection conn = null;
	
	private Statement stat = null;
	
	private String TableName;
	
	MyAutoTable(String name) throws Exception {
		
		TableName = name;
		
		Class.forName("org.sqlite.JDBC");
	    conn = DriverManager.getConnection("jdbc:sqlite:test.db");
	}

（2）数据库操作

* 首先在数据库中创建表，根据程序中用户输入的`TableName`，执行`Statement`类的`executeUpdate`方法，执行create table语句。
* insert语句需要调用`PreparedStatement`类的`prepareStatement`方法，运用占位符的技巧，先将insert语句除去value的部分输入，如下：

		stat = conn.createStatement();
		stat.executeUpdate("drop table if exists " + TableName + ";");
		stat.executeUpdate("create table " + TableName + " (id, name);");
		PreparedStatement prep = conn.prepareStatement("insert into " + TableName + " values (?, ?);");

* 然后用`setString`方法将占位符填充，最后`addBatch`方法标识插入结束即可。

		prep.setString(1, "1000");
	    prep.setString(2, "politics");
	    prep.addBatch();

* 最后将操作commit：

		conn.setAutoCommit(false);
		prep.executeBatch();
		conn.setAutoCommit(true);

* 查询操作要用`ResultSet`的对象接收结果，执行`executeQuery`方法，输入查询语句即可。

		ResultSet rs = stat.executeQuery("select * from " + TableName + ";");
		while (rs.next()) {
		    System.out.println("id = " + rs.getString("id"));
		    System.out.println("name = " + rs.getString("name"));
		}

* 执行init函数后程序输出结果如下：

	![](1.png)
	


##3. 生成的目标程序card.java

按照如下程序，对card表生成如下card类的代码，其中提供insert，update，select和delete四种操作，分别用上述方法执行数据库操作即可。

	/*This is the beginning of the generated program.*/
	
	import java.util.*;
	import java.sql.*;
	
	public class card
	{
		private Connection conn = null;
		private Statement stat = null;
		private String TableName = "card";
		
		void insert(String id, String name) {
			stat = conn.createStatement();
			PreparedStatement prep = conn.prepareStatement("insert into " + TableName + " values (?, ?);");
			prep.setString(1, "1000");
			prep.setString(2, "politics");
			prep.addBatch();
			conn.setAutoCommit(false);
			prep.executeBatch();
			conn.setAutoCommit(true);
		}
	
		void delete(String[] Cond_list) {
			String statement = "delete from " + TableName + " ";
			if (Cond_list.length > 0) {
				statement += " where ";
				statement += Cond_list[0];
				for (int i = 1;i < Cond_list.length; i++)
					statement += (" and " + Cond_list[i]);
			}
			statement += ";";
			conn.setAutoCommit(false);
			stat.executeUpdate(statement);
			conn.commit();
		}
	
		void select(String[] col, String[] Cond_list) {
			String sql = "select ";
			if (col.length > 0) {
				sql += col[0];
				for (int i = 1;i < col.length;i++)
				sql += ("," + col[i]);
			}
			else sql += "*";
			sql += (" from " + TableName);
			if (Cond_list.length > 0) {
				sql += " where " + Cond_list[0];
				for (int i = 1;i < Cond_list.length;i++)
					sql += (" and " + Cond_list[i]);
			}
			sql += ";";
			ResultSet rs = stat.executeQuery(sql);
			while (rs.next()) {
				System.out.println("id = " + rs.getString("id"));
				System.out.println("name = " + rs.getString("name"));
			}
		}
	
		void update(String col, String value, String[] Cond_list) {
			String sql = "update " + TableName + " set " + col + " = " + value;
			if (Cond_list.length > 0) {
				sql += " where " + Cond_list[0];
				for (int i = 1;i < Cond_list.length;i++)
					sql += (" and " + Cond_list[i]);
			}
			sql += ";";
			conn.setAutoCommit(false);
			stat.executeUpdate(sql);
			conn.commit();
		}
	
	}
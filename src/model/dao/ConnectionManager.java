/*
 * WebApp_05_sp03_UseBean
 * model.dao.ConnectionManager.java
 */
package model.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * コネクションマネージャ
 */
public class ConnectionManager {

	/**
	 * データベースURL
	 */
	private final static String URL = "jdbc:mysql://localhost:3306/oshikatsu?useSSL=false";

	/**
	 * ユーザ
	 */
	private final static String USER = "admin";

	/**
	 * パスワード
	 */
	private final static String PASSWORD = "admin";

	/**
	 * データベースへの接続を取得して返します。
	 *
	 * @return コネクション
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public static Connection getConnection() throws SQLException, ClassNotFoundException {

		// JDBCドライバの読み込み
		Class.forName("com.mysql.jdbc.Driver");
		return DriverManager.getConnection(URL, USER, PASSWORD);

	}
}
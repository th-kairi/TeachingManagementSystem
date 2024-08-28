package dao;

import java.sql.Connection;

import javax.naming.InitialContext;
import javax.sql.DataSource;

/**
 * DAOクラス...データベースに接続をするためのクラス
 *
 * @author admin
 * @version 1.0
 */
public class DAO {
//	クラス変数　全体で1つだけ、共有データとして保持
	static DataSource ds;

	/**
	 * DBに接続をするメソッド
	 *
	 * @return DtaSource
	 * @throws Exception
	 */
	public Connection getConnection() throws Exception{
//		最初の1回だけ実行したい
		if(ds == null){
			// データベースに接続
			InitialContext ic = new InitialContext();
			ds = (DataSource) ic.lookup("java:comp/env/jdbc/TMSDB");
		}

		return ds.getConnection();
	}
}

package net.gamebacon.videopoker;

import javax.swing.plaf.nimbus.State;
import java.io.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

class Save {
	Saves s;
	private final String path = "";
	private final File file = new File(path);
	private final DataBase dataBase = new DataBase();

	public int getDBMoney() throws SQLException {

	    dataBase.connect("localhost", "3306", "test123", "baconwilliam", "qazwsxedc");
	    if(dataBase.isConnected()) {
			Statement statement = dataBase.getConnection().createStatement();
			ResultSet resultSet = statement.executeQuery("select * from videopoker");
			if(resultSet.next())
				return resultSet.getInt("money");
		}
	    return -1;

	}

	public void saveMoneyToDB(int money) throws SQLException {
	    if(dataBase.isConnected()) {
			Statement statement = dataBase.getConnection().createStatement();
			statement.executeUpdate(String.format("UPDATE videopoker SET money = %d WHERE id = 1", money));
		}
	}

	public Save() {
		s = new Saves();


	}

	int load() {
		try {
		ObjectInputStream OIS = new ObjectInputStream(new FileInputStream(file));
		 s = (Saves) OIS.readObject();
		 OIS.close();
		} catch (Exception ex) {s.bal = 100;}
		return s.bal;
	}
	void save(int bal_) {
		s.bal = bal_;
		try {
			ObjectOutputStream OOS = new ObjectOutputStream(new FileOutputStream(file));
			OOS.writeObject(s);
			OOS.close();
		} catch (Exception ex) {}
	}
}
class Saves implements Serializable {
	int bal;
}

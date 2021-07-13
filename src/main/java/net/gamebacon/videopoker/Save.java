package net.gamebacon.videopoker;

import java.io.File;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.Serializable;

class Save {
	Saves s;
	public Save() {
		s = new Saves();
	}

	int load() {
		try {
		ObjectInputStream OIS = new ObjectInputStream(new FileInputStream(new File("saves.ser")));
		 s = (Saves) OIS.readObject();
		 OIS.close();
		} catch (Exception ex) {s.bal = 100;}
		return s.bal;
	}
	void save(int bal_) {
		s.bal = bal_;
		try {
			ObjectOutputStream OOS = new ObjectOutputStream(new FileOutputStream(new File("saves.ser")));
			OOS.writeObject(s);
			OOS.close();
		} catch (Exception ex) {}
	}
}
class Saves implements Serializable {
	int bal;
}

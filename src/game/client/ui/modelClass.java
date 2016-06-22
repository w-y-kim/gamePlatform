package game.client.ui;

import javafx.beans.property.SimpleStringProperty;

public class modelClass {
	private final SimpleStringProperty hostID;
	private final SimpleStringProperty roomTitle;
	
	public modelClass(String hostID, String roomTitle){
		this.hostID = new SimpleStringProperty(hostID) ;
		this.roomTitle = new SimpleStringProperty(roomTitle) ;
	}

	public String getHostID() {
		return hostID.get();
	}

	public String getRoomTitle() {
		return roomTitle.get();
	}
	
}

package com.g7.wanbao.object;

import com.g7.wanbao.local.Data;

public class Item {
	
	private static Data data;
	private int id;
	
	public Item(int _id, Data _data) {
		this.id = _id;
		Item.data = _data;
	}
	
	public int getID() {
		return id;
	}

	public String getName() {
		return data.getItemNamefromItemID(id);
	}

	public String getImgUrl() {
		return data.getImgUrlfromItemID(id);
	}

}

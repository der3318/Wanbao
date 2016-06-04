package com.g7.wanbao.object;

public class LocalOrder {
	
	private String name;
	private int price;
	private int amount;
	private String time;
	
	public LocalOrder(String _name, int _price, int _amount, String _time) {
		this.name = _name;
		this.price = _price;
		this.amount = _amount;
		this.time = _time;
	}

	public String getName() {
		return name;
	}

	public void setName(String _name) {
		this.name = _name;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int _price) {
		this.price = _price;
	}
	
	public int getAmount() {
		return this.amount;
	}
	
	public String getTime() {
		return this.time;
	}
	
	public int getTotalPrice() {
		return this.price * this.amount;
	}
	
}

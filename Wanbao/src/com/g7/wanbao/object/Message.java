package com.g7.wanbao.object;

public class Message {
	
	private String data;
	private String user;
	private String time;
	
	public Message(String _data, String _user, String _time) {
		this.data = _data;
		this.setUser(_user);
		this.setTime(_time);
	}

	public String getData() {
		return data;
	}

	public void setData(String _data) {
		this.data = _data;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

}

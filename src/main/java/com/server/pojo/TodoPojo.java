package com.server.pojo;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class TodoPojo{
	private Integer todoid;
	private String todo;
	public Integer getTodoid() {
		return todoid;
	}
	public void setTodoid(Integer todoid) {
		this.todoid = todoid;
	}
	public String getTodo() {
		return todo;
	}
	public void setTodo(String todo) {
		this.todo = todo;
	}
	
	
}

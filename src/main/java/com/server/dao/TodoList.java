package com.server.dao;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name="todolist")
@XmlRootElement
public class TodoList{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="todoid")
	private Integer todoid;
	
	@Column(name = "todo")
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

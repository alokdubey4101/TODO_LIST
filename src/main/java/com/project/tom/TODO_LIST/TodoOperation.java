package com.project.tom.TODO_LIST;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Predicate;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Root;
import javax.websocket.server.PathParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import com.server.dao.TodoList;
import com.server.pojo.AckPojo;
import com.server.pojo.TodoPojo;

@Path("/event")
public class TodoOperation {
	
	SessionFactory sf=new Configuration().configure().buildSessionFactory();
	
	@POST
	@Path("/add")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addValue(TodoPojo todoPojo) throws JSONException {
		
		AckPojo json=new AckPojo();
		
		try {
			Session session=sf.openSession();
			TodoList todo=new TodoList();
			todo.setTodo(todoPojo.getTodo());
			Transaction trans=session.beginTransaction();
			session.save(todo);
			trans.commit();
			session.close();
			json.setStatus("OK");
			json.setMessage("Added Successfully");
		}
		catch(Exception e) {
			json.setStatus("FAILED");
			json.setMessage("Server not Responding");
			e.printStackTrace();
		}
		return Response.ok(json).build();
	}
	
	
	@GET
	@Path("/getAll")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAll() {
		List<TodoPojo> todoPojoList=new ArrayList();  
		try {
			Session session=sf.openSession();
			CriteriaBuilder criteriabuilder=session.getCriteriaBuilder();
			CriteriaQuery<TodoList> criteriaQuery=criteriabuilder.createQuery(TodoList.class);
			Root<TodoList> root=criteriaQuery.from(TodoList.class);
			CriteriaQuery<TodoList> criteriaQuery1=criteriaQuery.select(root);
			TypedQuery<TodoList> tq=session.createQuery(criteriaQuery1);
			List<TodoList> todoList=tq.getResultList();
			Iterator<TodoList> iterator=todoList.iterator();
			while(iterator.hasNext()) {
				TodoList todo=iterator.next();
				TodoPojo todoPojo=new TodoPojo();
				todoPojo.setTodoid(todo.getTodoid());
				todoPojo.setTodo(todo.getTodo());
				todoPojoList.add(todoPojo);
			}
			session.close();
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return Response.ok(todoPojoList).build();
	}
	
	
	
	@GET
	@Path("/delete/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updateTodo(@PathParam("id") Integer id) throws JSONException {
		AckPojo json=new AckPojo();
		try {
			Session session=sf.openSession();
			CriteriaBuilder cb=session.getCriteriaBuilder();
			CriteriaQuery<TodoList> criteriaQuery=cb.createQuery(TodoList.class);
			Root<TodoList> root=criteriaQuery.from(TodoList.class);
			Predicate pr=(Predicate) session.getCriteriaBuilder().equal(root.get("todoid"), id);
			CriteriaQuery<TodoList> cq=criteriaQuery.select(root);
			TypedQuery<TodoList> tq=session.createQuery(cq);
			List<TodoList> list=tq.getResultList();
			if(!list.isEmpty()) {
				TodoList to=list.get(0);
				Transaction trans=session.beginTransaction();
				session.delete(to);
				trans.commit();
			}
			session.close();
			json.setStatus("OK");
			json.setMessage("Successfully Deleted");
		}
		catch(Exception e) {
			e.printStackTrace();
			json.setStatus("FAILED");
			json.setMessage("Server not Responding");
		}
		return Response.ok(json).build();
	}
	
}

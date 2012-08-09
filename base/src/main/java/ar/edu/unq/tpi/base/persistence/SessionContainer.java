package ar.edu.unq.tpi.base.persistence;

import org.hibernate.Session;

import ar.edu.unq.tpi.base.unitofwork.UnitOfWork;

public class SessionContainer{
	 
	 private Session session;
	 private UnitOfWork unitOfWork;
	 
	public Session getSession() {
		return session;
	}
	public void setSession(Session session) {
		this.session = session;
	}
	public UnitOfWork getUnitOfWork() {
		return unitOfWork;
	}
	public void setUnitOfWork(UnitOfWork unitOfWork) {
		this.unitOfWork = unitOfWork;
	}
	 
}

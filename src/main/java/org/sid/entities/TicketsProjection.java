package org.sid.entities;


import org.springframework.data.rest.core.config.Projection;

@Projection(name="ticketProj", types =Ticket.class)
public interface TicketsProjection {

	public Long getId();
	public  String getNameClient();
	public  double getPrice();
	
	public  Integer getCodePayement();
	public  boolean getReserve();
	
	public  Place getPlace();
	
}

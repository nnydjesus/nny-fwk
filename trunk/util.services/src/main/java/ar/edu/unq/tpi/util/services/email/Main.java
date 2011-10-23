package ar.edu.unq.tpi.util.services.email;

import ar.edu.unq.tpi.util.services.services.ServiceLocator;

public class Main {

	public static void main(String args[]) {
		ServiceLocator.locate(MailService.class).send("prueba", "nnydjesus@gmail.com", "Prueba");
	}
}
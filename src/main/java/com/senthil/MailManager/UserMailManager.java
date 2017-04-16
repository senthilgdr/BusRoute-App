/*package com.senthil.MailManager;

import com.senthil.model.User;

 3 methods in mailmanager
public class UserMailManager {
	
	public static void sendNewRegistrationEmail(User user) throws Exception {

		String subject = "Reg: Welcome to BusRouteApp";

		StringBuilder message = new StringBuilder();

		message.append("Dear" + user.getName() + ",\n");
		message.append("Successfully you are registered to BusRouteApp Website.");
		message.append("\n Please click the activation link below:");
		String activationLink ="http://localhost:8080/busrouteapp-web/activateAccount?emailId=" + user.getEmail();
		message.append("\n" + activationLink + "\n");
		message.append("\n\nRegards ");
		message.append("\nBusRoute Support Team");

		MailUtil.sendMail(user.getEmail(), subject, message.toString());
		System.out.println("Mail sent!");

	}	
	
	public static void sendPassword(User user) throws Exception {

		String subject = "Reg:Your Forgot Password";

		StringBuilder message = new StringBuilder();

		message.append("Dear " + user.getName() + "...\n");
		message.append("Your password here " + user.getPassword() + "\n"); 
																
		message.append("Regards \n");
		message.append("BusRoute Support Team");

		MailUtil.sendMail(user.getEmail(), subject, message.toString());

	}
	
	public static void changePassword(User user, String newPassword) throws Exception {

		String subject = "Reg:Your New Password";
		StringBuilder message = new StringBuilder();

		message.append("Dear " + user.getName() + "...\n");
		message.append("Your new password here (" + newPassword + ")\n"); // send password																			// password
		message.append("Regards \n");
		message.append("BusRoute Support Team");

		MailUtil.sendMail(user.getEmail(), subject, message.toString());
		
	}

}
*/
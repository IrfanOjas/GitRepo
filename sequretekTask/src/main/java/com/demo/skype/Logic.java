package com.demo.skype;

import com.samczsun.skype4j.Skype;
import com.samczsun.skype4j.SkypeBuilder;
import com.samczsun.skype4j.Visibility;
import com.samczsun.skype4j.exceptions.ConnectionException;
import com.samczsun.skype4j.exceptions.InvalidCredentialsException;
import com.samczsun.skype4j.exceptions.NotParticipatingException;

public class Logic {

	public Logic() {

		Skype skype = new SkypeBuilder(BotInfo.USERNAME, BotInfo.PASSWORD).withAllResources().build();

		try {
			skype.login();
		} catch (NotParticipatingException | InvalidCredentialsException | ConnectionException e) {
			e.printStackTrace();
		}
		skype.getEventDispatcher().registerListener(new UserChat());
		try {
			skype.subscribe();
		} catch (ConnectionException e) {
			e.printStackTrace();
		}
		try {
			skype.setVisibility(Visibility.ONLINE);
		} catch (ConnectionException e) {
			e.printStackTrace();
		}
		System.out.println("Skype logged in.");
	}
	

	public static void main(String[] args) {
		new Logic();
	}
}

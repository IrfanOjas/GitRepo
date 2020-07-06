package com.demo.skype;

import com.samczsun.skype4j.chat.Chat;
import com.samczsun.skype4j.exceptions.ConnectionException;

public class Message {

	public static void sendMessage(Chat chat, String msg) {
		try {
			chat.sendMessage(msg);
		} catch (ConnectionException e) {
			e.printStackTrace();
		}
	}
}

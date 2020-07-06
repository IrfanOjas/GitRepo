package com.demo.skype;

import com.samczsun.skype4j.events.EventHandler;
import com.samczsun.skype4j.events.Listener;
import com.samczsun.skype4j.events.chat.message.MessageReceivedEvent;

public class UserChat implements Listener {

	@EventHandler
	public void onChat(MessageReceivedEvent event) {
		String message = event.getMessage().getContent().toString().toLowerCase();
		if (message.startsWith(BotInfo.trigger)) {
			if (message.contains("")) {
				String cmd = message.split(BotInfo.trigger)[1].split("")[0];
				String arg = message.split("")[1];
				if (cmd.equals("resolve")) {
					Message.sendMessage(event.getChat(), "You are resolving:" + arg);
				} else {
					Message.sendMessage(event.getChat(), "Invali Command.");
				}
			} else {
				String cmd = message.split(BotInfo.trigger)[1];
				if (cmd.equals("help")) {
					Message.sendMessage(event.getChat(), "Command \n Help = Show Command.");
				} else {
					Message.sendMessage(event.getChat(), "Invali Command.");
				}
			}
		}
	}

}

package com.vinnnm.websocket.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vinnnm.websocket.model.Item;
import com.vinnnm.websocket.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import org.springframework.web.socket.CloseStatus;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

public class ItemWebSocketHandler extends TextWebSocketHandler {

	@Autowired
	private ItemService itemService;

	private final List<WebSocketSession> sessions = new CopyOnWriteArrayList<>();
	private final ObjectMapper objectMapper = new ObjectMapper();

	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		sessions.add(session);
		sendItemList(session);
	}

	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
		Map<String, Object> payload = objectMapper.readValue(message.getPayload(), Map.class);
		String action = (String) payload.get("action");
		String itemName = (String) payload.get("name");

		switch (action) {
		case "create":
			itemService.createItem(new Item(itemName));
			break;
		case "update":
			Long itemId = ((Number) payload.get("id")).longValue();
			itemService.createItem(new Item(itemId, itemName));
			break;
		case "delete":
			Long deleteItemId = ((Number) payload.get("id")).longValue();
			itemService.deleteItem(deleteItemId);
			break;
		default:
			break;
		}
		broadcastItemList();
	}

	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		sessions.remove(session);
	}

	private void broadcastItemList() throws IOException {
		List<Item> items = itemService.getAllItems();
		String itemListMessage = objectMapper.writeValueAsString(items);
		for (WebSocketSession session : sessions) {
			if (session.isOpen()) {
				session.sendMessage(new TextMessage(itemListMessage));
			}
		}
	}

	private void sendItemList(WebSocketSession session) throws IOException {
		List<Item> items = itemService.getAllItems();
		String itemListMessage = objectMapper.writeValueAsString(items);
		session.sendMessage(new TextMessage(itemListMessage));
	}
}

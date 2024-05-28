package com.vinnnm.websocket.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.vinnnm.websocket.model.Item;

@Service
public interface ItemService {

	void createItem(Item item);

	void deleteItem(long id);

	List<Item> getAllItems();

}

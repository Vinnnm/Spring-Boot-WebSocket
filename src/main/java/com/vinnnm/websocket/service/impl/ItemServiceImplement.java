package com.vinnnm.websocket.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vinnnm.websocket.model.Item;
import com.vinnnm.websocket.repository.ItemRepository;
import com.vinnnm.websocket.service.ItemService;

@Service
public class ItemServiceImplement implements ItemService {
	
	@Autowired
	private ItemRepository itemRepository;

	@Override
	public void createItem(Item item) {
		itemRepository.save(item);
	}

	@Override
	public void deleteItem(long id) {
		itemRepository.deleteById(id);
		
	}

	@Override
	public List<Item> getAllItems() {
		return itemRepository.findAll();
	}

}

package com.example.ordermanagementrestapi.service.impl;

import com.example.ordermanagementrestapi.dto.ItemDTO;
import com.example.ordermanagementrestapi.dto.request.RequestItemSaveDTO;
import com.example.ordermanagementrestapi.entity.Item;
import com.example.ordermanagementrestapi.repo.ItemRepo;
import com.example.ordermanagementrestapi.service.ItemService;
import com.example.ordermanagementrestapi.util.mappers.ItemMapper;
import com.example.ordermanagementrestapi.exception.NotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ItemServiceIMPL implements ItemService {
    @Autowired
    private ItemRepo itemRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ItemMapper itemMapper;

    @Override
    public void addCustomer(RequestItemSaveDTO requestItemSaveDTO) {
        Item item = itemMapper.requestDtoToEntity(requestItemSaveDTO);
        item.setActiveState(false);
        if (!itemRepo.existsById(item.getItemID())) {
            itemRepo.save(item);
        }
    }

    @Override
    public List<ItemDTO> getItemByName(String itemName) {
        List<Item> items = itemRepo.findAllByItemName(itemName);
        List<ItemDTO> itemDTOS = itemMapper.requestEntityListToDtoList(items);
        return itemDTOS;
    }

    @Override
    public List<ItemDTO> getAllItems() {
        List<Item> items = itemRepo.findAllByActiveStateIs(false);
        if (items.size() > 0) {
            List<ItemDTO> itemDTOS = itemMapper.requestEntityListToDtoList(items);
            return itemDTOS;
        } else {
            throw new NotFoundException("No data found");
        }
    }

    @Override
    public String updateItemByName(ItemDTO itemDTO) {
        Item existingItem = itemRepo.findByItemName(itemDTO.getItemName());
        if (existingItem != null) {
            existingItem.setBalanceQty(itemDTO.getBalanceQty());
            existingItem.setSupplierPrice(itemDTO.getSupplierPrice());
            existingItem.setSellerPrice(itemDTO.getSellerPrice());
            itemRepo.save(existingItem);
            return "Updated";
        } else {
            return null;
        }
    }

    @Override
    public String deactivateItemByName(String itemName) {
        try {
            Item item = itemRepo.findByItemName(itemName);
            if (item == null) {
                return "Item Not Found";
            }
            item.setActiveState(false);
            itemRepo.save(item);
            return "Item Deactivated";
        } catch (Exception e) {
            return "Deactivation Failed";
        }
    }

    @Override
    public String activateItemByName(String itemName) {
        Item item = itemRepo.findByItemName(itemName);
        if (item != null) {
            item.setActiveState(true);
            itemRepo.save(item);
            return "Item Activated";
        } else {
            return "Item Not Found";
        }
    }

    public List<String> getAllItemNames() {
        List<Item> items = itemRepo.findAll();
        return items.stream()
                .map(Item::getItemName)
                .collect(Collectors.toList());
    }
}


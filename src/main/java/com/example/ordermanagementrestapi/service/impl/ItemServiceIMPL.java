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
        if(!itemRepo.existsById(item.getItemID())){
            itemRepo.save(item);
            System.out.println("Saved");
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

        if(items.size()>0) {
            List<ItemDTO> itemDTOS = itemMapper.requestEntityListToDtoList(items);
            return itemDTOS;
        }else {
            throw new NotFoundException("No data found");
        }
    }




}

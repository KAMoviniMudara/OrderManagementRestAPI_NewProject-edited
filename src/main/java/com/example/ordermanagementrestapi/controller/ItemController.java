package com.example.ordermanagementrestapi.controller;

import com.example.ordermanagementrestapi.dto.ItemDTO;
import com.example.ordermanagementrestapi.dto.request.RequestItemSaveDTO;
import com.example.ordermanagementrestapi.service.ItemService;
import com.example.ordermanagementrestapi.util.StandardResponse;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/item")
@CrossOrigin
public class ItemController {

    @Autowired
    private ItemService itemService;

    @PostMapping(path="/save")
    public String SaveItem(@RequestBody RequestItemSaveDTO requestItemSaveDTO){

        itemService.addCustomer(requestItemSaveDTO);

        return "Saved";

    }

    @GetMapping(path = "/get-by-name",
            params = "name")
    public List<ItemDTO> getItemByName(@RequestParam(value = "name") String itemName){

        List<ItemDTO> itemDTOList = itemService.getItemByName(itemName);
        return itemDTOList;
    }

    @GetMapping(path = "/get-all-item")
    public ResponseEntity<StandardResponse> getAllItems() {

        List<ItemDTO> itemDTOS = itemService.getAllItems();

        return new ResponseEntity<StandardResponse>(
                new StandardResponse(200,"SUCCESS",itemDTOS),HttpStatus.OK
        );
    }


}
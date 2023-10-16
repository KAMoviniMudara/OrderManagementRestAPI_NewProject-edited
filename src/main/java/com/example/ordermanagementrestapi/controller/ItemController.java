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
import java.util.Map;

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



    @PostMapping(path = "/update-by-name")
    public String updateItemByName(@RequestBody ItemDTO itemDTO) {
        String updated = itemService.updateItemByName(itemDTO);
        if (updated != null) {
            return "Updated";
        } else {
            return "Update Failed";
        }
    }

    @PatchMapping(path = "/deactivate-item-by-name")
    public String deactivateItemByName(@RequestBody Map<String, String> requestBody) {
        String itemName = requestBody.get("itemName");

        String deactivateStatus = itemService.deactivateItemByName(itemName);
        return deactivateStatus;
    }

    @PatchMapping(path = "/activate-item-by-name")
    public String activateItemByName(@RequestBody Map<String, String> requestBody) {
        String itemName = requestBody.get("itemName");

        String activateStatus = itemService.activateItemByName(itemName);
        return activateStatus;
    }

    @GetMapping("/names")
    public ResponseEntity<List<String>> getItemNames() {
        List<String> itemNames = itemService.getAllItemNames();
        return ResponseEntity.ok(itemNames);
    }



}
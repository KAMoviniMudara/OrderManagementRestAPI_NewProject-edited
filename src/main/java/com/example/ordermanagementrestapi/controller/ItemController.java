package com.example.ordermanagementrestapi.controller;

import com.example.ordermanagementrestapi.dto.ItemDTO;
import com.example.ordermanagementrestapi.dto.request.RequestItemSaveDTO;
import com.example.ordermanagementrestapi.service.ItemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/v1/item")
@CrossOrigin
public class ItemController {

    private final Logger logger = LoggerFactory.getLogger(ItemController.class);

    @Autowired
    private ItemService itemService;

    @PostMapping(path="/save")
    public String SaveItem(@RequestBody RequestItemSaveDTO requestItemSaveDTO){
        try {
            itemService.addCustomer(requestItemSaveDTO);
            logger.info("Item saved with name: {}", requestItemSaveDTO.getItemName());
            return "Saved";
        } catch (Exception e) {
            logger.error("Error saving item", e);
            return "Error saving item";
        }
    }

    @GetMapping(path = "/get-by-name",
            params = "name")
    public List<ItemDTO> getItemByName(@RequestParam(value = "name") String itemName){
        try {
            List<ItemDTO> itemDTOList = itemService.getItemByName(itemName);
            logger.info("Retrieved items by name: {}", itemName);
            return itemDTOList;
        } catch (Exception e) {
            logger.error("Error retrieving items by name: {}", itemName, e);
            return null;
        }
    }

    @PostMapping(path = "/update-by-name")
    public String updateItemByName(@RequestBody ItemDTO itemDTO) {
        try {
            String updated = itemService.updateItemByName(itemDTO);
            if (updated != null) {
                logger.info("Item updated by name: {}", itemDTO.getItemName());
                return "Updated";
            } else {
                logger.error("Failed to update item by name: {}", itemDTO.getItemName());
                return "Update Failed";
            }
        } catch (Exception e) {
            logger.error("Error updating item by name", e);
            return "Error updating item by name";
        }
    }

    @PatchMapping(path = "/deactivate-item-by-name")
    public String deactivateItemByName(@RequestBody Map<String, String> requestBody) {
        try {
            String itemName = requestBody.get("itemName");
            String deactivateStatus = itemService.deactivateItemByName(itemName);
            logger.info("Deactivated item by name: {}", itemName);
            return deactivateStatus;
        } catch (Exception e) {
            logger.error("Error deactivating item by name", e);
            return "Error deactivating item";
        }
    }

    @PatchMapping(path = "/activate-item-by-name")
    public String activateItemByName(@RequestBody Map<String, String> requestBody) {
        try {
            String itemName = requestBody.get("itemName");
            String activateStatus = itemService.activateItemByName(itemName);
            logger.info("Activated item by name: {}", itemName);
            return activateStatus;
        } catch (Exception e) {
            logger.error("Error activating item by name", e);
            return "Error activating item";
        }
    }

    @GetMapping("/names")
    public ResponseEntity<List<String>> getItemNames() {
        try {
            List<String> itemNames = itemService.getAllItemNames();
            logger.info("Retrieved all item names");
            return ResponseEntity.ok(itemNames);
        } catch (Exception e) {
            logger.error("Error retrieving item names", e);
            return null;
        }
    }
}

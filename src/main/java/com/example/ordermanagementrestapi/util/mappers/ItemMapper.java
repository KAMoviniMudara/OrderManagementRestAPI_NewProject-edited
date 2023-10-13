package com.example.ordermanagementrestapi.util.mappers;

import com.example.ordermanagementrestapi.dto.ItemDTO;
import com.example.ordermanagementrestapi.dto.request.RequestItemSaveDTO;
import com.example.ordermanagementrestapi.entity.Item;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ItemMapper {
    @Mapping(target = "itemID", ignore = true) // Ignore itemID, it's generated by the database
    @Mapping(target = "itemName", source = "requestItemSaveDTO.itemName")
    @Mapping(target = "measuringUnitType", source = "requestItemSaveDTO.measuringUnitType")
    @Mapping(target = "balanceQty", source = "requestItemSaveDTO.balanceQty")
    @Mapping(target = "supplierPrice", source = "requestItemSaveDTO.supplierPrice")
    @Mapping(target = "sellerPrice", source = "requestItemSaveDTO.sellerPrice")
    @Mapping(target = "activeState", constant = "true") // Set activeState to true by default

    @Mapping(target = "orderDetails", ignore = true) // Ignore orderDetails for now
    Item requestDtoToEntity(RequestItemSaveDTO requestItemSaveDTO);

    List<ItemDTO> requestEntityListToDtoList(List<Item> items);

    List<ItemDTO> retuestPagetoList(List<Item> items);
}
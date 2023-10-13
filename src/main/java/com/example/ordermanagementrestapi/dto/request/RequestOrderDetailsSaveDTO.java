package com.example.ordermanagementrestapi.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RequestOrderDetailsSaveDTO {

    private String itemName;
    private double qty;
    private Double amount;
    private int orders;
    private int items;
}

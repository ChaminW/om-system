package com.sysco.app;


import com.sysco.app.model.Item;

public class Main {
    public static void main(String[] args) {
        Item item = new Item();
        item.setId("item_1");
        item.setName("Salmon");
        item.setDescription("Delmage");
        item.setPricePerItem(12.32);
        item.setTotalQuantity(1.0);
        item.setType("Canned Food");
        System.out.println(item);

    }
}

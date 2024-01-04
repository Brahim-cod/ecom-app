package com.ecom.commandservice.model;

import lombok.*;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@ToString @Builder
public class Product {
    private Long id;
    private String title;
    private double price;
    private int quantity;
}

package com.fpoly.backend.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PaymentDTO {
    String name;
    Integer quantity;
    Integer price;
}

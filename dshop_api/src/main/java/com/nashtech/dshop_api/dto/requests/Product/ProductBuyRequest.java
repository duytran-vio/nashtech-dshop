package com.nashtech.dshop_api.dto.requests.Product;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Setter
@Getter
public class ProductBuyRequest {
    public Long id;
    public Long quantity;
}

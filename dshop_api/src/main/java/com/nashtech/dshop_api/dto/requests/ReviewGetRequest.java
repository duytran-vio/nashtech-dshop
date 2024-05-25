package com.nashtech.dshop_api.dto.requests;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class ReviewGetRequest {
    private Long productId;
    private Long userId;
    private Long rating;
}

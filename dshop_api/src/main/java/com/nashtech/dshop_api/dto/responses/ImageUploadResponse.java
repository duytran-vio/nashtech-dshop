package com.nashtech.dshop_api.dto.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ImageUploadResponse {
    private Long id;
    private String fileName;
    private String url;
    private Long size;
}

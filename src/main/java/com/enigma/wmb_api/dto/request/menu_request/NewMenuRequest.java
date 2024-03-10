package com.enigma.wmb_api.dto.request.menu_request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NewMenuRequest {
    @NotBlank(message = "Menu is Required")
    private String menuName;

    @NotNull(message = "Price is Required")
    @Min(value = 0)
    private Long price;

    MultipartFile image;
}

package net.suyudi.retail_uma.dto.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MenuRequest {

    private Integer menuId;
    
    @NotBlank(message = "Link is required")
    @Size(min = 2, max = 200, message = "Link must be minimum 2 characters")
    private String link;

    @NotBlank(message = "Icon is required")
    @Size(min = 4, max = 200, message = "Link must be minimum 4 characters")
    private String icon;

    @NotBlank(message = "Label is required")
    @Size(min = 2, max = 20, message = "Link must be minimum 2 characters")
    private String label;

    @NotBlank(message = "Menu Type is required")
    @Size(min = 2, max = 10, message = "Link must be minimum 2 characters")
    private String type;

}
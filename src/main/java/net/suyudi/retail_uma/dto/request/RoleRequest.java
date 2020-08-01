package net.suyudi.retail_uma.dto.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RoleRequest {

    @NotBlank(message = "Role Name is required")
    @Size(min = 2, max = 12, message = "Role Name must be between 2 and 12 characters")
    public String name;

    public String description;
    
    public Integer level;
    
}
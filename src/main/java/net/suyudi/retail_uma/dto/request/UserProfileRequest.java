package net.suyudi.retail_uma.dto.request;

import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserProfileRequest {

    @Size(min = 10, max = 90, message = "Email must be between 8 and 90 characters")
    private String email;

    @Size(min = 2, message = "Address must be minimum 2 characters")
    private String address;

}

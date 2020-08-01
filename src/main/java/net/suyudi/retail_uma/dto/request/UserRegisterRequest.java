package net.suyudi.retail_uma.dto.request;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRegisterRequest {

    @NotBlank(message = "First Name is required")
    @Size(min = 2, max = 20, message = "First Name must be between 2 and 20 characters")
    private String firstName;

    @Size(min = 2, max = 20, message = "Last Name must be between 2 and 20 characters")
    private String lastName;

    @NotBlank(message = "Username is required")
    @Size(min = 4, max = 20, message = "Username must be between 4 and 20 characters")
    private String username;

    @NotBlank(message = "Password is required")
    @Size(min = 6, max = 15, message = "Password must be between 6 and 15 characters")
    private String password;

    @NotBlank(message = "Mobile is required")
    @Size(max = 13, message = "Mobile should not be greater than 13 and with 08xx")
    private String mobile;

}

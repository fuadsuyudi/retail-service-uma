package net.suyudi.retail_uma.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
public class LoginRequest {

    @NotBlank(message = "Username is required")
    @Size(min = 0, max = 20, message = "Username cannot be too many")
    private String username;

    @NotBlank(message = "Password is required")
    @Size(min = 0, max = 15, message = "Password cannot be too many")
    private String password;

    @JsonIgnore
    public HashMap<String, String> getMap() {
        HashMap<String, String> maps = new HashMap<>();

        maps.put("username", this.username);
        maps.put("password", this.password);

        return maps;
    }

}

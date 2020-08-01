package net.suyudi.retail_uma.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Embeddable
public class RoleMenuKey implements Serializable {

    @Column(name = "menu_id")
	private int menuId;
	
	@Column(name="role_id")
    private int roleId;
    
}
package net.suyudi.retail_uma.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import org.hibernate.annotations.Where;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Where(clause = "is_delete = 0")
@Table(name = "tbl_menu", schema = "uma")
public class MenuMap implements Serializable {

    @Id
    @Column(name = "id")
    private Integer id;
    
    @Column(name = "menu_id")
    private Integer menuId;

	@OneToMany(mappedBy = "menuId")
	private List<Menu> children = new ArrayList<Menu>();

    @Column(name = "link")
    private String link;

    @Column(name = "icon")
    private String icon;

    @Column(name = "label")
    private String label;

    @Column(name = "type")
    private String type;

    @Column(name = "level")
    private Integer level;

    @Column(name = "is_delete")
    private Integer isDelete;

}
package com.chequer.axboot.core.domain.program.menu;

import com.chequer.axboot.core.annotations.Comment;
import com.chequer.axboot.core.domain.BaseJpaModel;
import com.chequer.axboot.core.domain.program.Program;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Setter
@Getter
@DynamicInsert
@DynamicUpdate
@Entity
@EqualsAndHashCode(callSuper = true)
@Table(name = "MENU_M")
@Comment(value = "메뉴")
@ToString
public class Menu extends BaseJpaModel<Long> implements Cloneable {

    @Id
    @Column(name = "MENU_ID", precision = 20, nullable = false)
    @Comment(value = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long menuId;

    @Column(name = "MENU_GRP_CD", length = 100)
    @Comment(value = "메뉴 그룹코드")
    private String menuGrpCd;

    @Column(name = "MENU_NM", length = 100)
    @Comment(value = "메뉴명")
    private String menuNm;

    @Column(name = "PARENT_ID", precision = 19)
    @Comment(value = "부모 ID")
    private Long parentId;

    @Column(name = "LEVEL", precision = 10)
    @Comment(value = "레벨")
    private Integer level;

    @Column(name = "SORT", precision = 10)
    @Comment(value = "정렬")
    private Integer sort;

    @Column(name = "PROG_CD", length = 50)
    @Comment(value = "프로그램 코드")
    private String progCd;

    @Transient
    private boolean open = false;

    @Transient
    private List<Menu> children = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "PROG_CD", referencedColumnName = "PROG_CD", insertable = false, updatable = false)
    private Program program;

    @Override
    public Long getId() {
        return menuId;
    }

    @JsonProperty("name")
    public String label() {
        return menuNm;
    }

    @JsonProperty("id")
    public Long id() {
        return menuId;
    }

    @JsonProperty("open")
    public boolean isOpen() {
        return open;
    }

    public void addChildren(Menu menu) {
        children.add(menu);
    }

    public Menu clone() {
        try {
            Menu menu = (Menu) super.clone();
            menu.setChildren(new ArrayList<>());
            return menu;
        } catch (Exception e) {
            // ignore
        }
        return null;
    }
}

package com.orhon.smartcampus.modules.core.graphql.orm.entity;


import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "systemctl_modules")
public class Module {

    //模块所属学校列表
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "systemctl_modules_school_relations",
            joinColumns = { @JoinColumn(name = "module_id")},
            inverseJoinColumns = { @JoinColumn(name = "school_id")}
    )
    List<School> schools = new ArrayList<>();

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "module_name")
    private String moduleName;

    @Column(name = "category")
    private String category;

    @Override
    public String toString() {
        return "Module{" +
                "schools=" + schools +
                ", id=" + id +
                ", moduleName='" + moduleName + '\'' +
                ", category='" + category + '\'' +
                '}';
    }
}

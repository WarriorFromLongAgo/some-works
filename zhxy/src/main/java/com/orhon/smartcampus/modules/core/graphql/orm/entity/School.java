package com.orhon.smartcampus.modules.core.graphql.orm.entity;


import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "base_schools")
public class School {

//    //学校模块
//    @ManyToMany
//    @JoinTable(
//            name = "systemctl_modules_school_relations",
//            joinColumns = { @JoinColumn(name = "school_id")},
//            inverseJoinColumns = { @JoinColumn(name = "module_id")}
//    )
//    List<Module> modules = new ArrayList<>();

    @Id
    private Long id;


    @Column(name = "school_name")
    private String school_name;


    //gqllang 转换方法。。
//    public GQLLang getSchoolNameWithGQLLang() {
//        GQLLang lang = new GQLLang(this.getSchoolName());
//        return lang;
//    }



//    @Override
//    public String toString() {
//        return "School{" +
//                "modules=" + modules +
//                ", id=" + id +
//                ", schoolName='" + schoolName + '\'' +
//                '}';
//    }
}

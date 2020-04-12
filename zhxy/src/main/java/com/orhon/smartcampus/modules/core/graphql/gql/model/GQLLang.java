package com.orhon.smartcampus.modules.core.graphql.gql.model;


import com.alibaba.fastjson.JSON;
import com.google.common.base.Optional;
import lombok.Data;

import java.util.Map;

@Data
public class GQLLang {

    private String zh;
    private String en;
    private String mn;


    public GQLLang(String jsonStr){
        Map<String,String> jLang = (Map) JSON.parse(jsonStr);
        Optional<String> m_ZH = Optional.fromNullable(jLang.get("zh"));
        Optional<String> m_EN = Optional.fromNullable(jLang.get("en"));
        Optional<String> m_MN = Optional.fromNullable(jLang.get("mn"));
        this.zh = m_ZH.or("");
        this.en = m_EN.or("");
        this.mn = m_MN.or("");
    }


}

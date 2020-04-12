package com.orhonit.modules.generator.vo;

import com.orhonit.modules.generator.entity.ZgInformationEntity;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
@Data
public class ZgInformationVO implements Serializable {
    private static final long serialVersionUID = 1L;

    private String date;

    private List<ZgInformationEntity> list;

}

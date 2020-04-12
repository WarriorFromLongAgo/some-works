package com.orhonit.modules.generator.vo;

import com.orhonit.modules.generator.entity.DocumentContentEntity;
import com.orhonit.modules.generator.entity.DocumentEntity;
import com.orhonit.modules.generator.entity.DocumentFileEntity;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class DocumentDataVO implements Serializable {

    private static final long serialVersionUID = 1L;

    private DocumentEntity documentEntity;

    private List<DocumentContentEntity> docList;

    private List<DocumentFileEntity> fileList;
}

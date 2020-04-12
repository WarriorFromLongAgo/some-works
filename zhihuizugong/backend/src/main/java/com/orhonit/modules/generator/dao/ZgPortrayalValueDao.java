package com.orhonit.modules.generator.dao;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ZgPortrayalValueDao {

    Integer findAnswer(Long userId);

    Integer findWorkIdea(Long userId);

    Integer findShare(Long userId);

    Integer findOrgLive(Long userId);

    Integer findHelp(Long userId);

    Integer findVolunteer(Long userId);

    Integer findDonate(Long userId);

    Integer findResumption(Long userId);

    Integer findLightspot(Long userId);

    Integer findFeature(Long userId);

    Integer findStudy(Long userId);

    Integer findThinkNote(Long userId);
}

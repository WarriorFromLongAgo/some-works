package com.orhon.smartcampus.modules.user.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.orhon.smartcampus.modules.user.entity.Token;
import com.orhon.smartcampus.framework.mapper.BaseMapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Orhon
 */
@Mapper
public interface TokenMapper extends BaseMapper<Token> {

}

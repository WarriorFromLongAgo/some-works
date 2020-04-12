package com.orhon.smartcampus.modules.user.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.orhon.smartcampus.modules.user.entity.Users;
import com.orhon.smartcampus.framework.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Orhon
 */
@Mapper
public interface UsersMapper extends BaseMapper<Users> {

    HashMap getByID(@Param("id") Long id);

    /**
     * 按照page和limit获取所有用户
     * @param page
     * @param limit
     * @return
     */
    //List<Map<String,Object>> getUsers(@Param("page") Integer page , @Param("limit") Integer limit);

    List<Map<String,Object>> gqlUsersQuery(@Param("page") Integer page ,
                          @Param("limit") Integer limit,
                          @Param("withSchool") Boolean withSchool
    );
}

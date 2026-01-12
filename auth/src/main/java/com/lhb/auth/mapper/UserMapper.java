package com.lhb.auth.mapper;

import com.lhb.auth.pojo.DO.UserDO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {

    UserDO findByUsername(String username);
}

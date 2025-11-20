package com.example.provy.Role;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
@Mapper
public interface RoleMapper {
    @Select("SELECT id FROM role WHERE name = #{name}")
    Long getRoleIdByName(@Param("name") String name);
}

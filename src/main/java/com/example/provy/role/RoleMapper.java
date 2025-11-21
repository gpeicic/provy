package com.example.provy.role;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface RoleMapper {
    @Select("SELECT id FROM role WHERE name = #{name}")
    Long getRoleIdByName(@Param("name") String name);

    @Select("SELECT r.name FROM role r " +
            "INNER JOIN user_roles ur ON r.id = ur.role_id " +
            "WHERE ur.user_id = #{userId}")
    List<String> getRolesByUserId(@Param("userId") Long userId);
}

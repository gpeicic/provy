package com.example.provy.User;

import org.apache.ibatis.annotations.*;

@Mapper
public interface UserMapper {
    @Select("SELECT * FROM \"user\" WHERE id = #{id}")
    User getUserById(Long id);
    @Insert("INSERT INTO \"user\"(email,password,ime,prezime) VALUES(#{email},#{password},#{ime},#{prezime})")
    @Options(useGeneratedKeys = true,keyProperty = "id")
    void registerUser(User user);
    @Insert("INSERT INTO user_roles(user_id,role_id) VALUES(#{userId},#{roleId})")
    void insertUserRole(@Param("userId") Long userId,@Param("roleId") Long roleId);
    @Delete("DELETE FROM \"user\" WHERE id = #{id}")
    void deleteUserById(Long id);
}

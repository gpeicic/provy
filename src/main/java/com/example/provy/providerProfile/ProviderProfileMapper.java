package com.example.provy.providerProfile;

import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ProviderProfileMapper {
    @Insert("INSERT INTO provider_profile(user_id,business_name,address,phone,description) VALUES" +
            "(#{userId},#{businessName},#{address},#{phone},#{description})")
    @Options(useGeneratedKeys = true,keyProperty = "id")
    void registerProviderProfile(ProviderProfile providerProfile);
    @Select("SELECT * FROM provider_profile")
    @ResultMap("providerProfileMap")
    List<ProviderProfile> getAllProviders();

    @Select("SELECT COUNT(*) FROM provider_profile WHERE business_name = #{businessName}")
    int getCountByBusinessName(@Param("businessName") String businessName);

    @Select("SELECT id, user_id, business_name, address, phone, description, status " +
            "FROM provider_profile " +
            "WHERE id = #{id}")
    @Results(
            id = "providerProfileMap",
            value = {
            @Result(property = "id", column = "id"),
            @Result(property = "userId", column = "user_id"),
            @Result(property = "businessName", column = "business_name"),
            @Result(property = "address", column = "address"),
            @Result(property = "phone", column = "phone"),
            @Result(property = "description", column = "description"),
            @Result(property = "status", column = "status")
    })
    ProviderProfile getByProviderId(@Param("id") Long id);

    @Select("SELECT * FROM provider_profile WHERE user_id = #{userId}")
    ProviderProfile getByUserId(@Param("userId")Long id);
    @Delete("DELETE FROM provider_profile WHERE id = #{id}")
    int deleteProviderProfileById(@Param("id")Long id);
}

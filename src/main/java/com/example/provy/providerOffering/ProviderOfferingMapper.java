package com.example.provy.providerOffering;

import org.apache.ibatis.annotations.*;

@Mapper
public interface ProviderOfferingMapper {

    @Results(id = "ProviderOfferingResult", value = {
            @Result(property = "id", column = "id"),
            @Result(property = "providerProfileId", column = "provider_profile_id"),
            @Result(property = "name", column = "name"),
            @Result(property = "description", column = "description"),
            @Result(property = "price", column = "price"),
            @Result(property = "durationInMinutes", column = "duration_in_minutes")
    })
    @Select("SELECT * FROM provider_offering WHERE id = #{id}")
    ProviderOffering getById(@Param("id") Long id);

    @Select("SELECT COUNT(*) FROM provider_offering WHERE provider_id =#{providerId} AND name = #{name}")
    int getCountByProviderIdAndName(@Param("providerId") Long providerId, @Param("name") String name);

    // Reuse result map here if želiš
    @Select("SELECT * FROM provider_offering WHERE provider_profile_id = #{id}")
    @ResultMap("ProviderOfferingResult")
    ProviderOffering getByProviderProfileId(@Param("id") Long id);

    @Insert("INSERT INTO provider_offering(provider_profile_id,name,description,price,duration_in_minutes) VALUES " +
            "(#{providerProfileId},#{name},#{description},#{price},#{durationInMinutes})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void registerProviderOffering(ProviderOffering providerOffering);

    @Delete("DELETE FROM provider_offering WHERE id = #{id}")
    int deleteProviderOffering(@Param("id") Long id);
}

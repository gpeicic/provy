package com.example.provy.ProviderOffering;

import org.apache.ibatis.annotations.*;

@Mapper
public interface ProviderOfferingMapper {
    @Select("SELECT * FROM provider_offering WHERE id = #{id}")
    @Result(property = "providerProfileId", column = "provider_profile_id")
    ProviderOffering getById(@Param("id") Long id);
    @Select("SELECT * FROM provider_offering WHERE provider_profile_id = #{id}")
    ProviderOffering getByProviderProfileId(@Param("id") Long id);
    @Insert("INSERT INTO provider_offering(provider_profile_id,name,description,price,durationinminutes) VALUES " +
            "(#{providerProfileId},#{name},#{description},#{price},#{durationInMinutes})")

    @Options(useGeneratedKeys = true, keyProperty = "id")
    void registerProviderOffering(ProviderOffering providerOffering);
    @Delete("DELETE FROM provider_offering WHERE id = #{id}")
    void deleteProviderOffering(@Param("id") Long id);
}

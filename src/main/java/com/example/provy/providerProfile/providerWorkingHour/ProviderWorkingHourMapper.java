package com.example.provy.providerProfile.providerWorkingHour;

import org.apache.ibatis.annotations.*;

import java.util.List;
@Mapper
public interface ProviderWorkingHourMapper {
    @Select("SELECT * FROM provider_working_hour WHERE provider_profile_id = #{id}")
    @Results({
            @Result(property = "id",column = "id"),
            @Result(property = "providerProfileId", column = "provider_profile_id"),
            @Result(property = "dayOfWeek",column = "day_of_week"),
            @Result(property = "startTime", column = "start_time"),
            @Result(property = "endTime",column = "end_time")
    })
    List<ProviderWorkingHour> getByProviderProfileId(@Param("id") Long id);

    @Insert("INSERT INTO provider_working_hour(provider_profile_id,day_of_week,start_time,end_time) VALUES " +
            "(#{providerProfileId},#{dayOfWeek},#{startTime},#{endTime})")
    @Options(useGeneratedKeys = true,keyProperty = "id")
    void addProviderWorkingHour(ProviderWorkingHour providerWorkingHour);

    @Delete("DELETE FROM provider_working_hour WHERE id = #{id}")
    void deleteProviderWorkingHour(@Param("id") Long id);
}

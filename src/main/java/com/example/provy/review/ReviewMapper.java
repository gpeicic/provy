package com.example.provy.review;

import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ReviewMapper {
    @Select("SELECT * FROM review WHERE provider_profile_id = #{providerProfileId}")
    List<Review> getReviewsByProviderId(@Param("providerProfileId") Long providerProfileId);

    @Insert("INSERT INTO review (provider_profile_id, user_id, rating, comment, created_at) " +
            "VALUES (#{providerProfileId}, #{userId}, #{rating}, #{comment}, #{createdAt})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insertReview(Review review);

    @Delete("DELETE FROM review WHERE id = #{id}")
    int deleteById(@Param("id") Long id);

    @Select("SELECT COUNT(*) FROM review WHERE provider_profile_id = #{providerProfileId} AND user_id = #{userId}")
    int getCountByProviderAndUser(@Param("providerProfileId") Long providerProfileId, @Param("userId") Long userId);

    @Select("SELECT id, provider_profile_id, user_id, rating, comment, created_at " +
            "FROM review " +
            "WHERE id = #{id}")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "providerProfileId", column = "provider_profile_id"),
            @Result(property = "userId", column = "user_id"),
            @Result(property = "rating", column = "rating"),
            @Result(property = "comment", column = "comment"),
            @Result(property = "createdAt", column = "created_at")
    })
    Review getById(@Param("id") Long id);
}

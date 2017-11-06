package com.tikdik.framework.sharding.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * Created by rdadmin on 17-11-3.
 * only for demo
 */
@Repository
public interface ShardingMapper {

    @Insert("INSERT INTO sharding (name) VALUES (#{name})")
    int add(@Param("name") String name);
}

package com.tikdik.framework.sharding.service;

import com.tikdik.framework.sharding.dao.ShardingMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by rdadmin on 17-11-6.
 */
@Service
public class ShardingService {

    @Autowired
    private ShardingMapper shardingMapper;

    public void add(String name) {
        shardingMapper.add(name);
    }
}

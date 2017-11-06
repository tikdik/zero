package com.tikdik.framework.core.service;

import com.tikdik.framework.core.dao.ZeroMapper;
import com.tikdik.framework.core.model.ZeroDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by rdadmin on 17-11-6.
 */
@Service
public class ZeroService {
    @Autowired
    private ZeroMapper zeroMapper;

    public List<ZeroDO> listAll() {
        return zeroMapper.listAll();
    }
}

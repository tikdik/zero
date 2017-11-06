package com.tikdik.framework.core.dao;

import com.tikdik.framework.core.model.ZeroDO;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by rdadmin on 17-11-3.
 * only for demo
 */
@Repository
public interface ZeroMapper {

    @Select("SELECT * FROM zero")
    List<ZeroDO> listAll();
}

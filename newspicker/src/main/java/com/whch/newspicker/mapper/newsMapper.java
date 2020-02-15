package com.whch.newspicker.mapper;

import com.whch.newspicker.entity.news;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface newsMapper {
    int deleteByPrimaryKey(String url);

    int insert(news record);

    int insertSelective(news record);

    news selectByPrimaryKey(String url);

    int updateByPrimaryKeySelective(news record);

    int updateByPrimaryKeyWithBLOBs(news record);

    int updateByPrimaryKey(news record);
}
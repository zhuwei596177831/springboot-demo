package com.zhuweiwei.springbootlearning0405.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zhuweiwei.springbootlearning0405.bean.Test;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

/**
 * @author zww
 * @date 2020-04-12 18:19
 * @description
 **/
public interface TestMapper extends BaseMapper<Test> {

    @Select("select * from test")
    List<Test> getTestList();

    List<Test> getXmlList();

    List<Test> getPageList(Integer pageNum, Integer pageSize, @Param("test") Test test, Long id);

    @Select("select * from test")
    List<Test> getForRowBounds(Test test, RowBounds rowBounds);

}

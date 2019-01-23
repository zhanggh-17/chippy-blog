package com.loser.common.base.service;

import com.loser.common.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * Created by K2 Date: 2016/10/26 Version 1.0.
 */
public class BaseMysqlService<M extends Mapper<T>, T> {

    @Autowired
    protected M mapper;

    public void setMapper(M mapper) {
        this.mapper = mapper;
    }

    public T selectOne(T entity) {
        return mapper.selectOne(entity);
    }

    public T selectById(String id) {
        return mapper.selectByPrimaryKey(id);
    }

    public List<T> selectList(T entity) {
        return mapper.select(entity);
    }

    public List<T> selectListAll() {
        return mapper.selectAll();
    }

    public int selectCount(T entity) {
        return new Integer(mapper.selectCount(entity));
    }

    public int insert(T entity) {
        EntityUtils.setCreatAndUpdatInfo(entity);
        return mapper.insert(entity);
    }

    public void insertData(T entity) {
        mapper.insert(entity);
    }

    public Integer insertSelective(T entity) {
        EntityUtils.setCreatAndUpdatInfo(entity);
        return mapper.insertSelective(entity);
    }

    public int delete(T entity) {
        return mapper.delete(entity);
    }

    public int deleteById(String id) {
        return mapper.deleteByPrimaryKey(id);
    }

    public int updateById(T entity) {
        EntityUtils.setUpdatedInfo(entity);
        return mapper.updateByPrimaryKey(entity);
    }

    public int updateSelectiveById(T entity) {
        EntityUtils.setUpdatedInfo(entity);
        return mapper.updateByPrimaryKeySelective(entity);
    }

    public List<T> selectByExample(Object example) {
        return mapper.selectByExample(example);
    }

    public int selectCountByExample(Object example) {
        return mapper.selectCountByExample(example);
    }
}

package com.whut.dao.impl;

import com.whut.dao.ZhUserMapper;
import com.whut.spider.entity.ZhUser;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

/**
 * Created by fangjin on 2017/7/4.
 */
public class ZHUserDaoImpl extends SqlSessionDaoSupport implements ZhUserMapper {



    public SqlSessionFactory sqlSessionFactory;

    @Resource
    public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
        this.sqlSessionFactory = sqlSessionFactory;
    }

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return 0;
    }

    @Override
    public int insert(ZhUser user) {
        this.getSqlSession().insert("insert", user);
        return 0;
    }

    @Override
    public int insertSelective(ZhUser record) {
        return 0;
    }

    @Override
    public ZhUser selectByPrimaryKey(Integer id) {
        return null;
    }

    @Override
    public int updateByPrimaryKeySelective(ZhUser record) {
        return 0;
    }

    @Override
    public int updateByPrimaryKey(ZhUser record) {
        return 0;
    }
}

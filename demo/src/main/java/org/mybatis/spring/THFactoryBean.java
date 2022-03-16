package org.mybatis.spring;

import java.lang.reflect.Method;

import com.example.userMapper.UserMapper;
import com.spring.Autowired;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.cglib.proxy.InvocationHandler;
import org.springframework.cglib.proxy.Proxy;

public class THFactoryBean implements FactoryBean {
    private Class clazz;
    private SqlSession sqlSession;

    public THFactoryBean(Class clazz) {
        this.clazz = clazz;
    }

    @Autowired
    public void setSqlSession(SqlSessionFactory sqlSessionFactory) {
        sqlSessionFactory.getConfiguration().addMapper(clazz);
        this.sqlSession = sqlSessionFactory.openSession();
    }

    @Override
    public Object getObject() throws Exception {
        // mybatis object
        return sqlSession.getMapper(clazz);
        /*
         * return Proxy.newProxyInstance(THFactoryBean.class.getClassLoader(), new
         * Class[]{clazz}, new InvocationHandler() {
         * 
         * @Override
         * public Object invoke(Object arg0, Method arg1, Object[] arg2) throws
         * Throwable {
         * // TODO Auto-generated method stub
         * return null;
         * }
         * 
         * });
         */
    }

    @Override
    public Class getObjectType() {
        // TODO Auto-generated method stub
        return clazz;
    }

    @Override
    public boolean isSingleton() {
        // TODO Auto-generated method stub
        return true;
    }

}

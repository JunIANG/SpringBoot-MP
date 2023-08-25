package com.jay;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jay.dao.UserDao;
import com.jay.pojo.User;
import com.jay.pojo.UserQuery;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Map;

@SpringBootTest
class SpringBootMpApplicationTests {

    @Autowired
    private UserDao userDao;

    @Test
    void contextLoads() {
    }

    @Test
    void testGetAll() {

        List<User> userList = userDao.selectList(null);
        System.out.println(userList);

    }

    //分页查询
    @Test
    void testGetByPage() {

        IPage iPage = new Page(2, 3);
        userDao.selectPage(iPage, null);
        System.out.println("当前页码：" + iPage.getCurrent());
        System.out.println("每页数据总量：" + iPage.getSize());
        System.out.println("总页数：" + iPage.getPages());
        System.out.println("数据总量：" + iPage.getTotal());
        System.out.println("当前页数据：" + iPage.getRecords());

    }

    //条件查询
    @Test
    void testGet() {

        UserQuery userQuery = new UserQuery();

        userQuery.setId(2L);
        userQuery.setId2(5L);

        LambdaQueryWrapper<User> lqw = new LambdaQueryWrapper<>();
        //先判断第一个参数是否为null，如果为true连接当前条件
        lqw.gt(null != userQuery.getId(), User::getId, userQuery.getId());  //大于
        lqw.lt(null != userQuery.getId2(), User::getId, userQuery.getId2());  //小于

        List<User> userList = userDao.selectList(lqw);
        System.out.println(userList);

    }

    //查询投影
    @Test
    void testGet2() {

//        LambdaQueryWrapper<User> lqw = new LambdaQueryWrapper<>();
//        lqw.select(User::getId, User::getUsername);
        QueryWrapper<User> qw = new QueryWrapper<>();
        //qw.select("id", "username");
        qw.select("count(*) as count , password");
        qw.groupBy("password");  // 分组查询
//        List<User> userList = userDao.selectList(lqw);
//        List<User> userList = userDao.selectList(qw);
        List<Map<String, Object>> userList = userDao.selectMaps(qw);
        System.out.println(userList);

    }

    //查询单条数据  登录操作
    @Test
    void testGetOne() {

        LambdaQueryWrapper<User> lqw = new LambdaQueryWrapper<>();
        lqw.eq(User::getUsername, "zhangsan").eq(User::getPassword, "123");  //等同于=
        User user = userDao.selectOne(lqw);
        System.out.println(user);

    }

    //范围查询
    @Test
    void testGetBetween() {

        LambdaQueryWrapper<User> lqw = new LambdaQueryWrapper<>();
        // gt:>
        // lt:<
        // ge:>=
        // le:<=
        // between:<=?<=
        lqw.between(User::getId,2,5);  //必须前小后大
        List<User> userList = userDao.selectList(lqw);
        System.out.println(userList);

    }

    //模糊查询
    @Test
    void testGetLike() {

        LambdaQueryWrapper<User> lqw = new LambdaQueryWrapper<>();
        lqw.like(User::getUsername,"li");  // %li%
        //lqw.likeRight(User::getUsername,"li");  // li% Right的%在右
        //lqw.likeLeft(User::getUsername,"li");  // %li Left的%在左
        List<User> userList = userDao.selectList(lqw);
        System.out.println(userList);

    }

    //逻辑删除
    @Test
    void testDelete() {

        userDao.deleteById(5L);

    }

}

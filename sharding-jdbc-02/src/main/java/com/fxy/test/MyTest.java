package com.fxy.test;

import com.fxy.model.TUserOrder;
import com.fxy.util.SnowflakeUtil;
import com.alibaba.fastjson.JSONObject;
import com.fxy.ShardingApplication;
import com.fxy.bean.TOrder;
import com.fxy.bean.T_order;
import com.fxy.dao.CommonMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = ShardingApplication.class)
public class MyTest {

    @Autowired
    private CommonMapper commonMapper;

    /*
    * 分库分表测试
    * */
    @Test
    public void testAddTUserOrder() {
        TUserOrder order = new TUserOrder();
        order.setUser_id(2);
        order.setOrder_id(148);
        commonMapper.addTUserOrder(order);

        order.setOrder_id(149);
        commonMapper.addTUserOrder(order);

    }




    @Test
    public void test111() {
        TOrder order = new TOrder();
        order.setOrder_id(147);
        order.setOrder_content("jack147");
        commonMapper.addTOrderAndId(order);
    }


    /*
    * 分库测试
    * */
    @Test
    public void test2() {
        T_order order = new T_order();
        order.setOrderName("Jack");
        order.setOrderType("NZ");
//        order.setCreateTime(new Timestamp(System.currentTimeMillis()));
        order.setCreateTime(new Timestamp(new Date("2020/11/21").getTime()));
        commonMapper.addTOrder(order);
    }

    /*
    * 分库查询
    * 分片键查询
    * 非分片键查询
    * */
    @Test
    public void test3() {
        T_order order = new T_order();
//        order.setOrderId(473200384354025473L);
        order.setCreateTime(new Timestamp(new Date("2020/06/21").getTime()));
        List<T_order> t_orders = commonMapper.queryT_order(order);
        System.out.println(JSONObject.toJSONString(t_orders));
    }

    /*
    * 读写分离，插入
    * */
    @Test
    public void test4() {
        T_order order = new T_order();
        order.setOrderId(SnowflakeUtil.nextId());
        order.setOrderName("Jack");
        order.setOrderType("NZ");
//        order.setCreateTime(new Timestamp(System.currentTimeMillis()));
        order.setCreateTime(new Timestamp(new Date("2020/08/21").getTime()));
        commonMapper.addTOrderhasOrderId(order);
    }

    /*
     * 读写分离，查询
     * */
    @Test
    public void test5() {
        T_order order = new T_order();
        order.setOrderId(147l);
        System.out.println(JSONObject.toJSONString(commonMapper.queryT_orderById(order)));
    }
}

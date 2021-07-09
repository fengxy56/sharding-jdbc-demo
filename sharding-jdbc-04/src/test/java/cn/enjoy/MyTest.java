package cn.enjoy;

import cn.enjoy.bean.TOrder;
import cn.enjoy.bean.T_order;
import cn.enjoy.dao.CommonMapper;
import cn.enjoy.util.SnowflakeUtil;
import com.alibaba.fastjson.JSONObject;
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
    * 分表测试
    * */
    @Test
    public void test1() {
        TOrder order = new TOrder();
        order.setOrder_id(145);
        order.setOrder_content("jack146");
        commonMapper.addT_Order(order);
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
        order.setOrderId(718201344863768576L);
        System.out.println(JSONObject.toJSONString(commonMapper.queryT_orderById(order)));
    }
}

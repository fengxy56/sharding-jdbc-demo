package com.fxy.dao;


import com.fxy.bean.*;
import com.fxy.bean.*;
import com.fxy.model.TUserOrder;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface CommonMapper {

    @Insert("insert into t_user_order(user_id,order_id) values(#{user_id},#{order_id})")
    int addTUserOrder(TUserOrder order);
























    @Insert("insert into tb_user(user_id,user_name) values(#{user_id},#{user_name})")
    int addUser(Tb_user user);

    @Insert("insert into t_order(orderName,orderType,createTime) values(#{orderName},#{orderType},#{createTime})")
    int addTOrder(T_order order);

    @Insert("insert into t_order(orderId,orderName,orderType,createTime) values(#{orderId},#{orderName},#{orderType},#{createTime})")
    int addTOrderhasOrderId(T_order order);

    @Select("select * from t_order where orderId=#{orderId}")
    List<T_order> queryT_orderById(T_order order);

    @Select("select * from t_order where createTime=#{createTime}")
    List<T_order> queryT_order(T_order order);

    @Insert("insert into t_order(order_content) values(#{order_content})")
    int addT_Order(TOrder order);

    @Insert("insert into t_order(order_id,order_content) values(#{order_id},#{order_content})")
    int addTOrderAndId(TOrder order);

    @Insert({"<script>",
//            "/*!mycat:catlet=io.mycat.route.sequence.BatchInsertSequence */",
            "insert into t_order(order_content) values",
            "<foreach collection='list' item='item' index='index' separator=','>",
            "(#{item.order_content})",
            "</foreach>",
            "</script>"})
    void addT_OrderByBatch(List<TOrder> orders);

    @Select("select * from t_order")
    List<TOrder> queryTOrderTest(TOrder order);

    @Insert("insert into t_order_gd_hash(orderId,orderName,createTime) values(#{orderId},#{orderName},now())")
    int addTt_order_gd_hash(T_order order);

    @Insert("insert into t_order_range(orderId,orderName,createTime) values(#{orderId},#{orderName},now())")
    int addTt_order_range(T_order order);

    @Insert("insert into t_order_murmur_hash(orderId,orderName,createTime) values(#{orderId},#{orderName},now())")
    int addTt_order_murmur_hash(T_order order);

    @Insert("insert into t_order_time_day(orderId,orderName,createTime) values(#{orderId},#{orderName},#{createTime})")
    int addTt_order_time_day(T_order order);

    @Insert("insert into t_order_sharing_by_intfile(orderId,orderName,createTime,province) values(#{orderId},#{orderName},now(),#{province})")
    int addT_order_sharing_by_intfile(T_order_sharing_by_intfile order);

    @Select({"<script>", "select * from t_order_range",
            "<trim prefix='where' prefixOverrides='AND|OR'>",
            "<if test='orderId != null'>",
            "and orderId = #{orderId}",
            "</if>",
            "<if test='orderName != null'>",
            "and orderName = #{orderName}",
            "</if>",
            "</trim>",
            "</script>"})
    List<T_order> queryTOrder(T_order order);

    //    @Insert("insert into t_user${seq}(user_id,user_name) values(#{user_id},#{user_name})")
    @Insert("insert into t_user (user_id,user_name) values(#{user_id},#{user_name})")
    int addT_user(Tb_user user);

    //    @Select("select * from t_user${seq} where user_id=#{user_id}")
//    @Select("select * from t_user where user_id=#{user_id}")
    @Select({"<script>", "select * from t_user",
            "<trim prefix='where' prefixOverrides='AND|OR'>",
            "<if test='user_id != null'>",
            "and user_id = #{user_id}",
            "</if>",
            "<if test='user_name != null'>",
            "and user_name = #{user_name}",
            "</if>",
            "</trim>",
            "</script>"})
    List<Tb_user> queryUser(Tb_user user);

    @Insert("insert into t_order_type (orderType,orderTypeName) values(#{orderType},#{orderTypeName})")
    int addTOrderType(TOrderType tOrderType);

    @Select("select * from t_order_type where orderType = #{orderType}")
    List<TOrderType> queryTOrderType(TOrderType tOrderType);

    @Insert("insert into t_order_detail (orderDetailId,orderId,orderDetailName,createTime) values(#{orderDetailId},#{orderId},#{orderDetailName},now())")
    int addTOrderDetail(TOrderDetail tOrderDetail);
}

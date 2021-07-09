package com.fxy.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fxy.bean.HkPush;
import com.fxy.bean.TOrder;
import com.fxy.dao.HkPushMapper;
import com.fxy.dao.TOrderMapper;
import org.springframework.stereotype.Service;

/**
 * @ClassName TOrderServiceImpl
 * @Description 这里描述
 * @Author admin
 * @Date 2021/7/4 20:15
 */
@Service
public class TOrderServiceImpl extends ServiceImpl<TOrderMapper, TOrder> implements TOrderService{
}

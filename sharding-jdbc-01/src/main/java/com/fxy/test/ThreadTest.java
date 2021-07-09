package com.fxy.test;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fxy.ShardingApplication;
import com.fxy.bean.HkPush;
import com.fxy.bean.HkPushSplitAlgorithm;
import com.fxy.bean.HongkunPushinfo;
import com.fxy.dao.HongkunPushinfoMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @ClassName ThreadTest
 * @Description 这里描述
 * @Author admin
 * @Date 2021/7/4 18:16
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = ShardingApplication.class)
@Slf4j
public class ThreadTest {

    @Resource
    private HongkunPushinfoMapper hongkunPushinfoMapper;


}

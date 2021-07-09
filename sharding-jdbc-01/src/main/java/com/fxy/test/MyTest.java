package com.fxy.test;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fxy.bean.*;
import com.fxy.dao.HkPushMapper;
import com.fxy.dao.HkPushSplitAlgorithmMapper;
import com.fxy.dao.HongkunOutsupportinfoMapper;
import com.fxy.dao.HongkunPushinfoMapper;
import com.fxy.service.HkPushService;
import com.fxy.service.HkPushSplitAlgorithmService;
import com.fxy.service.TOrderService;
import com.fxy.util.SnowflakeUtil;
import com.fxy.ShardingApplication;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.platform.commons.util.StringUtils;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = ShardingApplication.class)
@Slf4j
public class MyTest {

    @Autowired
    private HongkunPushinfoMapper hongkunPushinfoMapper;

    @Autowired
    private HongkunOutsupportinfoMapper hongkunOutsupportinfoMapper;

    @Autowired
    private HkPushMapper hkPushMapper;

    @Autowired
    private HkPushService hkPushService;

    @Autowired
    private HkPushSplitAlgorithmMapper hkPushSplitAlgorithmMapper;

    @Autowired
    private HkPushSplitAlgorithmService hkPushSplitAlgorithmService;

    @Resource
    private TOrderService tOrderService;

    /*
     * 分表测试
     * */
    @Test
    public void testHongkunOutsupportinfo() {
        Page<HongkunOutsupportinfo> page = new Page<>(1, 10);
        QueryWrapper<HongkunOutsupportinfo> queryWrapper = new QueryWrapper<>();
        //page.setAsc("SHOW_ORDER");
        IPage<HongkunOutsupportinfo> pageList = hongkunOutsupportinfoMapper.selectPage(page,queryWrapper);
        List<HongkunOutsupportinfo> list = pageList.getRecords();
        log.info("长度:"+list.size());
    }





    /*
    * 数据迁移 测试
    * */
    @Test
    public void testHongkunPushinfo() {



        long start = System.currentTimeMillis();


        //总数
        int totalCount =  hongkunPushinfoMapper.selectCount(null);
        log.info("totalCount:"+totalCount);

        int offset = 0;//开始条数
        //每次循环的个数，从配置文件获取
        int everyCount = 100;
        int n = 0;//循环次数
        if (totalCount % everyCount == 0) {
            n = (int) totalCount / everyCount;
        } else {
            n = (int) totalCount / everyCount + 1;
        }

        int successCount = 0;
        int failCount = 0;
        int errorCount = 0;
        int tgCount = 0;

        for(int i=0;i < n; i++){
            offset = everyCount * i;//开始条数=每次条数*循环次数
            log.info("totalCount:"+totalCount+",n:"+n+",i:"+i+",开始");

            Page<HongkunPushinfo> page = new Page<>(i, everyCount);
            QueryWrapper<HongkunPushinfo> queryWrapper = new QueryWrapper<>();
            //page.setAsc("SHOW_ORDER");
            IPage<HongkunPushinfo> pageList = hongkunPushinfoMapper.selectPage(page,queryWrapper);
            List<HongkunPushinfo> list = pageList.getRecords();
            log.info("长度:"+list.size());
//        log.info("集合数据:"+list);

            if(list != null && list.size() >0){

                 for(int j=0;j<list.size();j++){
                     HkPush hxPush = this.getHkPush(list.get(j));
                     try{

                         String appRequestId = hxPush.getAppId()+"_"+hxPush.getRequestId();
                         HkPush hxPushDb = hkPushService.getById(appRequestId);
                         if( hxPushDb ==null){
                             boolean flag = hkPushService.saveOrUpdate(hxPush);
                             if(flag){
                                 successCount ++;
                             }else{
                                 failCount ++;
                             }
                         }else{
                             tgCount ++ ;
                         }

                         HkPushSplitAlgorithm hkPushSplitAlgorithmDb = hkPushSplitAlgorithmMapper.selectById(appRequestId);
                         if(hkPushSplitAlgorithmDb == null){
                             HkPushSplitAlgorithm hkPushSplitAlgorithm = new HkPushSplitAlgorithm();
                             hkPushSplitAlgorithm.setId(UUID.randomUUID().toString());
                             hkPushSplitAlgorithm.setAppRequestId(appRequestId);
                             hkPushSplitAlgorithm.setUserId(hxPush.getUserId());
                             hkPushSplitAlgorithmMapper.insert(hkPushSplitAlgorithm);
                         }


                     }catch (Exception e){
                         errorCount ++ ;
                         log.info("新增异常hxPush："+hxPush.toString());
                         log.info("新增异常："+e);
                     }
                 }

            }
            log.info("totalCount:"+totalCount+",n:"+n+",i:"+i+",结束");
        }


        long end = System.currentTimeMillis();
        log.info("用时:"+(end-start)/1000+"s");
        log.info("成功successCount:"+successCount+",失败failCount:"+failCount
                +",异常errorCount:"+errorCount+",tgCount:"+tgCount);

    }


    public HkPush getHkPush(HongkunPushinfo hongkunPushinfo){
        HkPush hxPush = new HkPush();
        hxPush.setId(hongkunPushinfo.getAppId()+"_"+hongkunPushinfo.getRequestId());
        hxPush.setUserId(hongkunPushinfo.getUserId());
        hxPush.setAppId(hongkunPushinfo.getAppId());
        hxPush.setRequestId(hongkunPushinfo.getRequestId());
        hxPush.setHxMsgid(hongkunPushinfo.getHxMsgid());
        hxPush.setProcessCode(hongkunPushinfo.getProcessCode());
        hxPush.setMsgTitle(hongkunPushinfo.getMsgTitle());
        hxPush.setReceiveTime(hongkunPushinfo.getReceiveTime());
        hxPush.setAuthorUser(hongkunPushinfo.getAuthorUser());
        hxPush.setClickStatus(hongkunPushinfo.getClickStatus());
        hxPush.setFormInfoContent(hongkunPushinfo.getFormInfoContent());
        hxPush.setIfAuthor(hongkunPushinfo.getIfAuthor());


        if( hongkunPushinfo.getIfTodo() !=null ){
            hxPush.setIfTodo(hongkunPushinfo.getIfTodo());
        }

        if(StringUtils.isNotBlank(hongkunPushinfo.getIsCc())){
            hxPush.setIsCc(hongkunPushinfo.getIsCc());
        }
        if(StringUtils.isNotBlank(hongkunPushinfo.getPtUrl())){
            hxPush.setPtUrl(hongkunPushinfo.getPtUrl());
        }
       return hxPush;
    }




    /*
     * 分表测试
     * */
    @Test
    public void testGetHkPush() {

        LambdaQueryWrapper<HkPushSplitAlgorithm> algorithmLambdaQueryWrapper = Wrappers.lambdaQuery();
        algorithmLambdaQueryWrapper.eq(HkPushSplitAlgorithm::getAppRequestId,"80009_HPS_ABS_AWE_001-102602-46-v_linyx-2-538");

        List<HkPushSplitAlgorithm> hkPushSplitAlgorithmList = hkPushSplitAlgorithmMapper.selectList(algorithmLambdaQueryWrapper);
        log.info("查询hkPushSplitAlgorithmList长度:"+hkPushSplitAlgorithmList.size());
        log.info("查询hkPushSplitAlgorithmList数据:"+hkPushSplitAlgorithmList);

        if(hkPushSplitAlgorithmList !=null && hkPushSplitAlgorithmList.size() >0){
            LambdaQueryWrapper<HkPush> hkPushQueryWrapper = Wrappers.lambdaQuery();
            hkPushQueryWrapper.eq(HkPush::getUserId,hkPushSplitAlgorithmList.get(0).getUserId());

            List<HkPush> hkPushList = hkPushMapper.selectList(hkPushQueryWrapper);
            log.info("查询HkPush长度:"+hkPushList.size());
            hkPushList.forEach( hkPush ->{
                log.info("查询HkPush数据:"+hkPush.getUserId()+":"+hkPush.getAppId()+":"+hkPush.getRequestId());
            });
//            log.info("查询HkPush数据:"+hkPushList);
        }

    }




    /*
     * 分表测试
     * */
    @Test
    public void testgetHkPush() {
        LambdaQueryWrapper<HkPush> hkPushQueryWrapper = Wrappers.lambdaQuery();
        hkPushQueryWrapper.eq(HkPush::getIfTodo,0);

        List<HkPush> hkPushList = hkPushMapper.selectList(hkPushQueryWrapper);
        log.info("查询HkPush长度:"+hkPushList.size());
        hkPushList.forEach( hkPush ->{
            log.info("查询HkPush数据:"+hkPush.getUserId()+":"+hkPush.getAppId()+":"+hkPush.getRequestId());
        });

    }


    @Test
    public void CountDownLatchTest() throws InterruptedException {
        long start = System.currentTimeMillis();
        //总数
        int totalCount =  hongkunPushinfoMapper.selectCount(null);
        log.info("totalCount:"+totalCount);


//        int totalCount = 66000;
        int threadSize = 5000;//每5000条数据开启一个线程
        int remainder = totalCount % threadSize;
        int threadNum  = 0;//线程数
        if(remainder == 0){
            threadNum  = totalCount/threadSize;
        } else {
            threadNum  = totalCount/threadSize + 1;
        }

        log.info("totalCount:"+totalCount+",threadNum:"+threadNum);

        ExecutorService eService = Executors.newFixedThreadPool(threadNum );//创建一个线程池

        List<Callable<String>> cList = new ArrayList<>();
        Callable<String> task = null;
        List<String> sList = null;

        for(int i=0;i<threadNum;i++){

            final int thread = i;
            log.info("thread第:"+thread+"线程,totalCount:" + totalCount);

            task = new Callable<String>() {

                @Override
                public String call() throws Exception {
                    int everyCount = 100;
                    int n = threadSize / everyCount;//循环次数

                    for (int k = 0; k <= n; k++) {

                        int offent = thread * n + k;

                        Page<HongkunPushinfo> page = new Page<>(offent, everyCount);
                        QueryWrapper<HongkunPushinfo> queryWrapper = new QueryWrapper<>();
                        //page.setAsc("SHOW_ORDER");
                        IPage<HongkunPushinfo> pageList = hongkunPushinfoMapper.selectPage(page, queryWrapper);
                        List<HongkunPushinfo> list = pageList.getRecords();

                        log.info("thread第:"+thread+"线程,totalCount:" + totalCount + ",n:" + n + ",循环到offent:" + offent + ",开始");


                        if (list != null && list.size() > 0) {

                            for (int j = 0; j < list.size(); j++) {
                                HkPush hxPush = new MyTest().getHkPush(list.get(j));
                                try {

                                    String appRequestId = hxPush.getAppId() + "_" + hxPush.getRequestId();
                                    HkPush hxPushDb = hkPushService.getById(appRequestId);
                                    if (hxPushDb == null) {
                                        boolean flag = hkPushService.saveOrUpdate(hxPush);
                                        if (flag) {
                                            // successCount ++;
                                        } else {
                                            //failCount ++;
                                        }
                                    } else {
                                        //tgCount ++ ;
                                    }

                                    HkPushSplitAlgorithm hkPushSplitAlgorithmDb = hkPushSplitAlgorithmMapper.selectById(appRequestId);
                                    if (hkPushSplitAlgorithmDb == null) {
                                        HkPushSplitAlgorithm hkPushSplitAlgorithm = new HkPushSplitAlgorithm();
                                        hkPushSplitAlgorithm.setId(UUID.randomUUID().toString());
                                        hkPushSplitAlgorithm.setAppRequestId(appRequestId);
                                        hkPushSplitAlgorithm.setUserId(hxPush.getUserId());
                                        hkPushSplitAlgorithmMapper.insert(hkPushSplitAlgorithm);
                                    }


                                } catch (Exception e) {
                                    // errorCount ++ ;
                                    log.info("新增异常hxPush：" + hxPush.toString());
                                    log.info("新增异常：" + e);
                                }
                            }

                        }


                        //log.info("thread第:"+thread+"线程,totalCount:" + totalCount + ",n:" + n + ",循环到offent:" + offent + ",结束");

                    }


                    return "";
                }
            };
            cList.add(task);
        }
        List<Future<String>> results = eService.invokeAll(cList);
        for(Future<String> str:results){
            try {
                log.info("返回结果");
                log.info(str.get());
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
        eService.shutdown();

        long end = System.currentTimeMillis();
        log.info("用时:"+(end-start)/1000+"s");


    }



    @Test
    public void CountDownLatchSaveBatchTest() throws InterruptedException {
        long start = System.currentTimeMillis();
        //总数
        int totalCount =  hongkunPushinfoMapper.selectCount(null);
        log.info("totalCount:"+totalCount);


//        int totalCount = 66000;
        int threadSize = 5000;//每5000条数据开启一个线程
        int remainder = totalCount % threadSize;
        int threadNum  = 0;//线程数
        if(remainder == 0){
            threadNum  = totalCount/threadSize;
        } else {
            threadNum  = totalCount/threadSize + 1;
        }

        log.info("totalCount:"+totalCount+",threadNum:"+threadNum);

        ExecutorService eService = Executors.newFixedThreadPool(threadNum );//创建一个线程池

        List<Callable<String>> cList = new ArrayList<>();
        Callable<String> task = null;
        List<String> sList = null;

        for(int i=0;i<threadNum;i++){

            final int thread = i;
            log.info("thread第:"+thread+"线程,totalCount:" + totalCount);

            task = new Callable<String>() {

                @Override
                public String call() throws Exception {
                    int everyCount = 100;
                    int n = threadSize / everyCount;//循环次数

                    for (int k = 0; k <= n; k++) {

                        int offent = thread * n + k;

                        Page<HongkunPushinfo> page = new Page<>(offent, everyCount);
                        QueryWrapper<HongkunPushinfo> queryWrapper = new QueryWrapper<>();
                        //page.setAsc("SHOW_ORDER");
                        IPage<HongkunPushinfo> pageList = hongkunPushinfoMapper.selectPage(page, queryWrapper);
                        List<HongkunPushinfo> list = pageList.getRecords();

                        log.info("thread第:"+thread+"线程,totalCount:" + totalCount + ",n:" + n + ",循环到offent:" + offent + ",开始");


                        if (list != null && list.size() > 0) {

                            for (int j = 0; j < list.size(); j++) {

                                try {

                                    List<HkPush> hxList = new ArrayList<>();
                                    HkPush hxPush = new MyTest().getHkPush(list.get(j));
                                    hxList.add(hxPush);
                                    hkPushService.saveOrUpdateBatch(hxList);

                                    List<HkPushSplitAlgorithm> algorithmList = new ArrayList<>();
                                    String appRequestId = hxPush.getAppId() + "_" + hxPush.getRequestId();
                                    HkPushSplitAlgorithm hkPushSplitAlgorithm = new HkPushSplitAlgorithm();
                                    hkPushSplitAlgorithm.setId(UUID.randomUUID().toString());
                                    hkPushSplitAlgorithm.setAppRequestId(appRequestId);
                                    hkPushSplitAlgorithm.setUserId(hxPush.getUserId());

                                    algorithmList.add(hkPushSplitAlgorithm);
                                    hkPushSplitAlgorithmService.saveOrUpdateBatch(algorithmList);


                                } catch (Exception e) {
                                    log.info("新增异常：" + e);
                                }
                            }

                        }


                        //log.info("thread第:"+thread+"线程,totalCount:" + totalCount + ",n:" + n + ",循环到offent:" + offent + ",结束");

                    }


                    return "";
                }
            };
            cList.add(task);
        }
        List<Future<String>> results = eService.invokeAll(cList);
        for(Future<String> str:results){
            try {
                log.info("返回结果");
                log.info(str.get());
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
        eService.shutdown();

        long end = System.currentTimeMillis();
        log.info("用时:"+(end-start)/1000+"s");


    }


    @Test
    public void getCount() throws InterruptedException {
        long start = System.currentTimeMillis();
        //总数
        int totalCount =  hongkunPushinfoMapper.selectCount(null);
        //总数
        int totalCount2 =  hkPushMapper.selectCount(null);
        //总数
        int totalCount3 =  hkPushSplitAlgorithmMapper.selectCount(null);

        log.info("totalCount:"+totalCount);
        log.info("totalCount2:"+totalCount2);
        log.info("totalCount3:"+totalCount3);
    }


    @Test
    public void saveBatch() throws InterruptedException {
//        long start = System.currentTimeMillis();
//        //总数
//        int totalCount =  hongkunPushinfoMapper.selectCount(null);
//        //总数
//        int totalCount2 =  hkPushService.saveBatch(null);

        List<TOrder> list = new ArrayList<>();

        TOrder tOrder = new TOrder();
        tOrder.setId(1);
        tOrder.setName("name11");
        list.add(tOrder);

        TOrder tOrder2 = new TOrder();
        tOrder2.setId(2);
        tOrder2.setName("name22");
        list.add(tOrder2);
        boolean flag = tOrderService.saveOrUpdateBatch(list);
        log.info("flag:"+flag);

    }


    /*
     * 分表测试
     * */
    @Test
    public void testgetHkPush2() {
        LambdaQueryWrapper<HkPush> hkPushQueryWrapper = Wrappers.lambdaQuery();
        hkPushQueryWrapper.eq(HkPush::getRequestId,"007A0593A886CC98929370DC3B344947");
        hkPushQueryWrapper.eq(HkPush::getUserId,"yubing");
        List<HkPush> hkPushList = hkPushMapper.selectList(hkPushQueryWrapper);
        log.info("查询HkPush长度:"+hkPushList.size());
//        hkPushList.forEach( hkPush ->{
//            log.info("查询HkPush数据:"+hkPush.getUserId()+":"+hkPush.getAppId()+":"+hkPush.getRequestId());
//        });


    }


}

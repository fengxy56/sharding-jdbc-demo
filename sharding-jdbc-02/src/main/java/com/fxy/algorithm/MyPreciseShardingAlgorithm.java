package com.fxy.algorithm;

import lombok.extern.slf4j.Slf4j;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingValue;

import javax.validation.constraints.Size;
import java.util.Collection;

@Slf4j
public class MyPreciseShardingAlgorithm implements PreciseShardingAlgorithm<Integer> {
    @Override
    public String doSharding(Collection<String> availableTargetNames, PreciseShardingValue<Integer> shardingValue) {
        for (String tableName : availableTargetNames) {

            try{

                log.info("tableName:"+tableName+",shardingValue:"+shardingValue+",value:"+shardingValue.getValue());

                if (tableName.endsWith( shardingValue.getValue() % 3 + 1 + "")) {
                    return tableName;
                }
            }catch (Exception e){
                log.error("异常e:"+e);
            }

        }
        throw new IllegalArgumentException();
    }
}

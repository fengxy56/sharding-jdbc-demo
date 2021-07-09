package com.fxy.algorithm;

import lombok.extern.slf4j.Slf4j;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingValue;

import java.util.Collection;

@Slf4j
public class HxPushShardingAlgorithm implements PreciseShardingAlgorithm<String> {
    @Override
    public String doSharding(Collection<String> availableTargetNames, PreciseShardingValue<String> shardingValue) {
        for (String tableName : availableTargetNames) {

            try{

                int endTable = Math.abs(shardingValue.getValue().hashCode())% 5 + 1;

                log.info("HxPushShardingAlgorithm规则,tableName:"+tableName+",shardingValue:"+shardingValue+",value:"+endTable);

                if (tableName.endsWith(endTable + "")) {
                    return tableName;
                }
            }catch (Exception e){
                log.error("异常e:"+e);
            }

        }
        throw new IllegalArgumentException();
    }
}

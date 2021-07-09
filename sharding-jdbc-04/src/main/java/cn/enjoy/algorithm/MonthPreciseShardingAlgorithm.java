package cn.enjoy.algorithm;

import lombok.extern.slf4j.Slf4j;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingValue;

import java.util.Collection;
import java.util.Date;

@Slf4j
public class MonthPreciseShardingAlgorithm implements PreciseShardingAlgorithm<Date> {
    @Override
    public String doSharding(Collection<String> collection, PreciseShardingValue<Date> preciseShardingValue) {
        String year = String.format("%tY", preciseShardingValue.getValue());
        String mon = String.format("%tm", preciseShardingValue.getValue());
        String db = "ds" + (Integer.valueOf(mon) % 2);
        log.info("分片节点为：" + db);
        //  ds0,ds1
        return db;
    }
}

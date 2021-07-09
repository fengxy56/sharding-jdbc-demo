package cn.enjoy.bean;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TOrderItem {

    public long order_item_id;

    public int order_id;

    public String order_content;
}

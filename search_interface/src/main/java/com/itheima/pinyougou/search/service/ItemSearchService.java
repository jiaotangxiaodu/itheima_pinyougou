package com.itheima.pinyougou.search.service;

import java.util.List;
import java.util.Map;

public interface ItemSearchService {

    public Map<String,Object> search(Map<Object,Object> searchMap);
    /**
     * 导入数据
     * @param list
     */
    public void importList(List list);
    /**
     * 删除数据
     */
    public void deleteByGoodsIds(List goodsIdList);


}

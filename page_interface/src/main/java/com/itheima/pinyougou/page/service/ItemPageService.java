package com.itheima.pinyougou.page.service;

public interface ItemPageService {

    /**
     * 生成商品详情页
     * @param goodsId
     * @return
     */
    boolean genItemHtml(Long goodsId);
    /**
     * 删除商品详细页
     * @return
     */
    public boolean deleteItemHtml(Long[] goodsIds);


}

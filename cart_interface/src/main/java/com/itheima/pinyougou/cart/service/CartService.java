package com.itheima.pinyougou.cart.service;

import com.itheima.pinyougou.pojogroup.Cart;

import java.util.List;

public interface CartService {
    /**
     * 添加商品到购物车
     */

    public List<Cart> addGoodsToCartList(List<Cart> cartList, Long itemId, Integer num);

    /**
     * 从redis中查询购物车
     *
     * @param username
     * @return
     */
    public List<Cart> findCartListFromRedis(String username);

    /**
     * 将购物车保存到redis
     *
     * @param username
     * @param cartList
     */
    public void saveCartListToRedis(String username, List<Cart> cartList);

    /**
     * 合并购物车
     *
     * @param cartList1
     * @param cartList2
     * @return
     */
    public List<Cart> mergeCartList(List<Cart> cartList1, List<Cart> cartList2);


}

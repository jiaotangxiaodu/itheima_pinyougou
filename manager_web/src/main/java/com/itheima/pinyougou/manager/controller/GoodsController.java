package com.itheima.pinyougou.manager.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.pinyougou.entity.PageResult;
import com.itheima.pinyougou.entity.Result;
import com.itheima.pinyougou.page.service.ItemPageService;
import com.itheima.pinyougou.pojo.TbGoods;
import com.itheima.pinyougou.pojo.TbItem;
import com.itheima.pinyougou.pojogroup.Goods;
import com.itheima.pinyougou.search.service.ItemSearchService;
import com.itheima.pinyougou.sellergoods.service.GoodsService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

/**
 * controller
 *
 * @author Administrator
 */
@RestController
@RequestMapping("/goods")
public class GoodsController {

    @Reference
    private ItemSearchService itemSearchService;

    @Reference
    private GoodsService goodsService;

    @Reference(timeout = 40000)
    private ItemPageService itemPageService;

    /**
     * 返回全部列表
     *
     * @return
     */
    @RequestMapping("/findAll")
    public List<TbGoods> findAll() {
        return goodsService.findAll();
    }


    /**
     * 返回全部列表
     *
     * @return
     */
    @RequestMapping("/findPage")
    public PageResult findPage(int page, int rows) {
        return goodsService.findPage(page, rows);
    }

    /**
     * 增加
     *
     * @param goods
     * @return
     */
    @RequestMapping("/add")
    public Result add(@RequestBody Goods goods) {
        try {
            goodsService.add(goods);
            return new Result(true, "增加成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "增加失败");
        }
    }

    /**
     * 修改
     *
     * @param goods
     * @return
     */
    @RequestMapping("/update")
    public Result update(@RequestBody TbGoods goods) {
        try {
            goodsService.update(goods);
            return new Result(true, "修改成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "修改失败");
        }
    }

    /**
     * 获取实体
     *
     * @param id
     * @return
     */
    @RequestMapping("/findOne")
    public Goods findOne(Long id) {
        return goodsService.findOne(id);
    }




    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    @RequestMapping("/delete")
    public Result delete(Long[] ids) {
        try {
            goodsService.delete(ids);
            itemSearchService.deleteByGoodsIds(Arrays.asList(ids));
            return new Result(true, "删除成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "删除失败");
        }
    }

    /**
     * 查询+分页
     *
     * @param page
     * @param rows
     * @return
     */
    @RequestMapping("/search")
    public PageResult search(@RequestBody TbGoods goods, int page, int rows) {
        return goodsService.findPage(goods, page, rows);
    }


    /**
     * 生成FreeMarker静态页面
     * @param goodsId
     */
    @RequestMapping("genHtml")
    public void genHtml(Long goodsId){
        itemPageService.genItemHtml(goodsId);
    }


    /**
     * 更新状态
     *
     * @param ids
     * @param status
     */
    @RequestMapping("/updateStatus")
    public Result updateStatus(Long[] ids, String status) {
        try {
            goodsService.updateStatus(ids, status);
            if(status.equals("1")){//审核通过
                List<TbItem> itemList = goodsService.findItemListByGoodsIdandStatus(ids, status);
                //调用搜索接口实现数据批量导入
                if(itemList.size()>0){
                    itemSearchService.importList(itemList);
                }else{
                    System.out.println("没有明细数据");
                }

                //静态页生成
                for(Long goodsId:ids){
                    itemPageService.genItemHtml(goodsId);
                }

            }

            return new Result(true, "成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "失败");
        }
    }

}

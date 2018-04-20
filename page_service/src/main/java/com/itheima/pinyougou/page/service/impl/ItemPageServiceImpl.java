package com.itheima.pinyougou.page.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.pinyougou.mapper.TbGoodsDescMapper;
import com.itheima.pinyougou.mapper.TbGoodsMapper;
import com.itheima.pinyougou.mapper.TbItemCatMapper;
import com.itheima.pinyougou.mapper.TbItemMapper;
import com.itheima.pinyougou.page.service.ItemPageService;
import com.itheima.pinyougou.pojo.TbGoods;
import com.itheima.pinyougou.pojo.TbGoodsDesc;
import com.itheima.pinyougou.pojo.TbItem;
import com.itheima.pinyougou.pojo.TbItemExample;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import java.io.FileWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ItemPageServiceImpl implements ItemPageService {

    @Autowired
    private FreeMarkerConfigurer freeMarkerConfigurer;

    @Value("${pagedir}")
    private String pagedir;

    @Autowired
    private TbGoodsMapper tbGoodsMapper;

    @Autowired
    private TbGoodsDescMapper tbGoodsDescMapper;

    @Autowired
    private TbItemCatMapper tbItemCatMapper;

    @Autowired
    private TbItemMapper itemMapper;

    @Override
    public boolean genItemHtml(Long goodsId) {

        try {

            Configuration configuration = freeMarkerConfigurer.getConfiguration();
            Template template = configuration.getTemplate("item.ftl");
            Map<String, Object> model = new HashMap<>();
            //1.加载商品表数据
            TbGoods tbGoods = tbGoodsMapper.selectByPrimaryKey(goodsId);
            model.put("goods",tbGoods);
            //2.加载商品扩展表数据
            TbGoodsDesc tbGoodsDesc = tbGoodsDescMapper.selectByPrimaryKey(goodsId);
            model.put("goodsDesc",tbGoodsDesc);
            //3.商品分类
            Long category1Id = tbGoods.getCategory1Id();
            Long category2Id = tbGoods.getCategory2Id();
            Long category3Id = tbGoods.getCategory3Id();

            String itemCat1 = tbItemCatMapper.selectByPrimaryKey(category1Id).getName();
            String itemCat2 = tbItemCatMapper.selectByPrimaryKey(category2Id).getName();
            String itemCat3 = tbItemCatMapper.selectByPrimaryKey(category3Id).getName();

            model.put("itemCat1",itemCat1);
            model.put("itemCat2",itemCat2);
            model.put("itemCat3",itemCat3);

            //4.SKU列表
            TbItemExample example=new TbItemExample();
            TbItemExample.Criteria criteria = example.createCriteria();
            criteria.andStatusEqualTo("1");//状态为有效
            criteria.andGoodsIdEqualTo(goodsId);//指定SPU ID
            example.setOrderByClause("is_default desc");//按照状态降序，保证第一个为默认
            List<TbItem> itemList = itemMapper.selectByExample(example);
            model.put("itemList", itemList);


            Writer out = new FileWriter(pagedir+goodsId+".html");

            template.process(model,out);
            out.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }



    }
}

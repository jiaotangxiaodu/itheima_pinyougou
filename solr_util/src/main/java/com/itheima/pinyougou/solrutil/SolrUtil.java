package com.itheima.pinyougou.solrutil;

import com.alibaba.fastjson.JSON;
import com.itheima.pinyougou.mapper.TbItemMapper;
import com.itheima.pinyougou.pojo.TbItem;
import com.itheima.pinyougou.pojo.TbItemExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class SolrUtil {

    @Autowired
    private SolrTemplate solrTemplate;

    @Autowired
    private TbItemMapper tbItemMapper;

    /**
     * 导入商品数据
     */
    public void importItemData(){
        TbItemExample example = new TbItemExample();
        TbItemExample.Criteria criteria = example.createCriteria();
        criteria.andStatusEqualTo("1");
        List<TbItem> tbItems = tbItemMapper.selectByExample(example);
        for (TbItem tbItem : tbItems) {
            String spec = tbItem.getSpec();
            Map map = JSON.parseObject(spec);
            tbItem.setSpecMap(map);
        }


        solrTemplate.saveBeans(tbItems);
        solrTemplate.commit();
    }

}

package com.itheima.pinyougou.search.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.pinyougou.pojo.TbItem;
import com.itheima.pinyougou.search.service.ItemSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.core.query.Criteria;
import org.springframework.data.solr.core.query.Query;
import org.springframework.data.solr.core.query.SimpleQuery;
import org.springframework.data.solr.core.query.result.ScoredPage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service(timeout = 3000)
public class ItemSearchServiceImpl implements ItemSearchService {

    @Autowired
    private SolrTemplate solrTemplate;

    @Override
    public Map<String, Object> search(Map<Object, Object> searchMap) {
        Query query = new SimpleQuery();
        Object keywords = searchMap.get("keywords");
        Criteria itemKeywords = new Criteria("item_keywords").is(keywords);
        query.addCriteria(itemKeywords);

        ScoredPage<TbItem> tbItems = solrTemplate.queryForPage(query, TbItem.class);
        List<TbItem> content = tbItems.getContent();
        HashMap<String, Object> map = new HashMap<>();
        map.put("rows",content);
        return map;
    }
}

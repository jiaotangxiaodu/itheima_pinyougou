package com.itheima.pinyougou.sellergoods.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.pinyougou.entity.PageResult;
import com.itheima.pinyougou.mapper.TbItemCatMapper;
import com.itheima.pinyougou.pojo.TbItemCat;
import com.itheima.pinyougou.pojo.TbItemCatExample;
import com.itheima.pinyougou.pojo.TbItemCatExample.Criteria;
import com.itheima.pinyougou.sellergoods.service.ItemCatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 服务实现层
 *
 * @author Administrator
 */
@Service(timeout = 30000)
public class ItemCatServiceImpl implements ItemCatService {

    @Autowired
    private TbItemCatMapper itemCatMapper;
    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 查询全部
     */
    @Override
    public List<TbItemCat> findAll() {
        return itemCatMapper.selectByExample(null);
    }

    /**
     * 按分页查询
     */
    @Override
    public PageResult findPage(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        Page<TbItemCat> page = (Page<TbItemCat>) itemCatMapper.selectByExample(null);
        return new PageResult(page.getTotal(), page.getResult());
    }

    /**
     * 增加
     */
    @Override
    public void add(TbItemCat itemCat) {
        itemCatMapper.insert(itemCat);
    }


    /**
     * 修改
     */
    @Override
    public void update(TbItemCat itemCat) {
        itemCatMapper.updateByPrimaryKey(itemCat);
    }

    /**
     * 根据ID获取实体
     *
     * @param id
     * @return
     */
    @Override
    public TbItemCat findOne(Long id) {
        return itemCatMapper.selectByPrimaryKey(id);
    }

    /**
     * 批量删除
     */
    @Override
    public void delete(Long[] ids) {
        for (Long id : ids) {

            TbItemCatExample example = new TbItemCatExample();
            Criteria criteria = example.createCriteria();
            criteria.andParentIdEqualTo(id);
            List<TbItemCat> children = itemCatMapper.selectByExample(example);
            if(children == null || children.size() == 0){
                itemCatMapper.deleteByPrimaryKey(id);
            }

        }
    }


    @Override
    public PageResult findPage(TbItemCat itemCat, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);

        TbItemCatExample example = new TbItemCatExample();
        Criteria criteria = example.createCriteria();

        if (itemCat != null) {
            if (itemCat.getName() != null && itemCat.getName().length() > 0) {
                criteria.andNameLike("%" + itemCat.getName() + "%");
            }

        }

        Page<TbItemCat> page = (Page<TbItemCat>) itemCatMapper.selectByExample(example);
        return new PageResult(page.getTotal(), page.getResult());
    }

    @Override
    public List<TbItemCat> findByParentId(Long parentId) {
        TbItemCatExample example = new TbItemCatExample();
        Criteria criteria = example.createCriteria();
        criteria.andParentIdEqualTo(parentId);
        //每次执行查询的时候，一次性读取缓存进行存储 (因为每次增删改都要执行此方法)
        List<TbItemCat> list = findAll();
        Map<String,Long> itemCatMap = new HashMap<>();
        for(TbItemCat itemCat:list){
//            redisTemplate.boundHashOps("itemCat").put(itemCat.getName(), itemCat.getTypeId());
            itemCatMap.put(itemCat.getName(),itemCat.getTypeId());

        }
        redisTemplate.boundHashOps("itemCat").putAll(itemCatMap);
        List<TbItemCat> tbItemCats = itemCatMapper.selectByExample(example);
        return tbItemCats;
    }

    @Override
    public List<Map<String, Object>> getCategroyOptionsByParentId(Long parentId) {
        return itemCatMapper.getCategroyOptionsByParentId(parentId);
    }

}

package com.itheima.pinyougou.content.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.pinyougou.cache.annotation.ClearCache;
import com.itheima.pinyougou.cache.annotation.UseCache;
import com.itheima.pinyougou.cache.aspect.RedisCacheAspect;
import com.itheima.pinyougou.content.service.ContentService;
import com.itheima.pinyougou.entity.PageResult;
import com.itheima.pinyougou.mapper.TbContentMapper;
import com.itheima.pinyougou.pojo.TbContent;
import com.itheima.pinyougou.pojo.TbContentExample;
import com.itheima.pinyougou.pojo.TbContentExample.Criteria;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * 服务实现层
 *
 * @author Administrator
 */
@Service
public class ContentServiceImpl implements ContentService {

    @Autowired
    private TbContentMapper contentMapper;

    /**
     * 查询全部
     */
    @Override
    public List<TbContent> findAll() {
        return contentMapper.selectByExample(null);
    }

    /**
     * 按分页查询
     */
    @Override
    public PageResult findPage(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        Page<TbContent> page = (Page<TbContent>) contentMapper.selectByExample(null);
        return new PageResult(page.getTotal(), page.getResult());
    }

    /**
     * 增加
     */
    @ClearCache
    @Override
    public void add(TbContent content) {
        contentMapper.insert(content);
    }


    /**
     * 修改
     */
    @ClearCache
    @Override
    public void update(TbContent content) {
        contentMapper.updateByPrimaryKey(content);
    }

    /**
     * 根据ID获取实体
     *
     * @param id
     * @return
     */
    @Override
    public TbContent findOne(Long id) {
        return contentMapper.selectByPrimaryKey(id);
    }

    /**
     * 批量删除
     */
    @ClearCache
    @Override
    public void delete(Long[] ids) {
        for (Long id : ids) {
            contentMapper.deleteByPrimaryKey(id);
        }
    }


    @Override
    public PageResult findPage(TbContent content, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);

        TbContentExample example = new TbContentExample();
        Criteria criteria = example.createCriteria();

        if (content != null) {
            if (content.getTitle() != null && content.getTitle().length() > 0) {
                criteria.andTitleLike("%" + content.getTitle() + "%");
            }
            if (content.getUrl() != null && content.getUrl().length() > 0) {
                criteria.andUrlLike("%" + content.getUrl() + "%");
            }
            if (content.getPic() != null && content.getPic().length() > 0) {
                criteria.andPicLike("%" + content.getPic() + "%");
            }
            if (content.getStatus() != null && content.getStatus().length() > 0) {
                criteria.andStatusLike("%" + content.getStatus() + "%");
            }

        }

        Page<TbContent> page = (Page<TbContent>) contentMapper.selectByExample(example);
        return new PageResult(page.getTotal(), page.getResult());
    }


    @UseCache
    @Override
    public List<TbContent> findByCategoryId(Long categoryId) {

        Class<RedisCacheAspect> redisCacheAspectClass = RedisCacheAspect.class;

        TbContentExample example = new TbContentExample();
        Criteria criteria = example.createCriteria();
        criteria.andCategoryIdEqualTo(categoryId);
        criteria.andStatusEqualTo("1");
        return contentMapper.selectByExample(example);
    }

}

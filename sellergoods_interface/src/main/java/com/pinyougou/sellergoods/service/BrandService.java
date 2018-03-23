package com.pinyougou.sellergoods.service;

import com.pinyougou.entries.PageResult;
import com.pinyougou.pojo.TbBrand;

import java.util.List;

public interface BrandService {
    List<TbBrand> findAll();
    PageResult<TbBrand> findByPage(int pageNum,int pageSize);
    void add(TbBrand brand);

    TbBrand findById(Long id);

    void update(TbBrand brand);
}

package com.pinyougou.sellergoods.service;

import com.pinyougou.entries.PageResult;
import com.pinyougou.pojo.TbBrand;

import java.util.List;

public interface BrandService {
    TbBrand findById(Long id);

    List<TbBrand> findAll();

    PageResult<TbBrand> findByPage(int pageNum, int pageSize);

    PageResult<TbBrand> searchByPage(int pageNum, int pageSize, TbBrand searchBrand);

    void add(TbBrand brand);


    void update(TbBrand brand);

    void delete(Long[] ids);
}

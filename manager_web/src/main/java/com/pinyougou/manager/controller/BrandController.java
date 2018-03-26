package com.pinyougou.manager.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.pinyougou.entries.PageResult;
import com.pinyougou.entries.ResultInfo;
import com.pinyougou.pojo.TbBrand;
import com.pinyougou.sellergoods.service.BrandService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("brand")
public class BrandController {

    @Reference
    private BrandService brandService;

    @RequestMapping("findAll")
    public List<TbBrand> findAll() {
        return brandService.findAll();
    }


    @RequestMapping("findByPage")
    public PageResult<TbBrand> findByPage(@RequestParam("pageNum") int pageNum, @RequestParam("pageSize") int pageSize) {
        return brandService.findByPage(pageNum,pageSize);
    }

    @RequestMapping("search")
    public PageResult<TbBrand> search(@RequestParam("pageNum") int pageNum, @RequestParam("pageSize") int pageSize , @RequestBody TbBrand searchBrand) {
        return brandService.searchByPage(pageNum,pageSize,searchBrand);
    }

    @RequestMapping("findById")
    public TbBrand findById(@RequestParam("id") Long id){
        return brandService.findById(id);
    }
    @RequestMapping("add")
    public ResultInfo add(@RequestBody TbBrand brand){

        try {
            brandService.add(brand);
            return new ResultInfo(true,"添加成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new ResultInfo(false,"添加失败");
        }
    }



    @RequestMapping("update")
    public ResultInfo update(@RequestBody TbBrand brand){
        try {
            brandService.update(brand);
            return new ResultInfo(true,"更新成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new ResultInfo(false,"更新失败");
        }
    }

    @RequestMapping("delete")
    public ResultInfo delete(@RequestParam("ids") Long[] ids){
        try {
            brandService.delete(ids);
            return new ResultInfo(true,"删除成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new ResultInfo(false,"删除失败");
        }
    }



}

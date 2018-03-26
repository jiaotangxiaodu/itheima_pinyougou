package com.itheima.pinyougou.manager.controller;
import java.util.List;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.pinyougou.pojo.TbFreightTemplate;
import com.itheima.pinyougou.sellergoods.service.FreightTemplateService;

import com.itheima.pinyougou.entity.PageResult;
import com.itheima.pinyougou.entity.Result;
/**
 * controller
 * @author Administrator
 *
 */
@RestController
@RequestMapping("/freightTemplate")
public class FreightTemplateController {

	@Reference
	private FreightTemplateService freightTemplateService;
	
	/**
	 * 返回全部列表
	 * @return
	 */
	@RequestMapping("/findAll")
	public List<TbFreightTemplate> findAll(){			
		return freightTemplateService.findAll();
	}
	
	
	/**
	 * 返回全部列表
	 * @return
	 */
	@RequestMapping("/findPage")
	public PageResult  findPage(int page,int rows){			
		return freightTemplateService.findPage(page, rows);
	}
	
	/**
	 * 增加
	 * @param freightTemplate
	 * @return
	 */
	@RequestMapping("/add")
	public Result add(@RequestBody TbFreightTemplate freightTemplate){
		try {
			freightTemplateService.add(freightTemplate);
			return new Result(true, "增加成功");
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(false, "增加失败");
		}
	}
	
	/**
	 * 修改
	 * @param freightTemplate
	 * @return
	 */
	@RequestMapping("/update")
	public Result update(@RequestBody TbFreightTemplate freightTemplate){
		try {
			freightTemplateService.update(freightTemplate);
			return new Result(true, "修改成功");
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(false, "修改失败");
		}
	}	
	
	/**
	 * 获取实体
	 * @param id
	 * @return
	 */
	@RequestMapping("/findOne")
	public TbFreightTemplate findOne(Long id){
		return freightTemplateService.findOne(id);		
	}
	
	/**
	 * 批量删除
	 * @param ids
	 * @return
	 */
	@RequestMapping("/delete")
	public Result delete(Long [] ids){
		try {
			freightTemplateService.delete(ids);
			return new Result(true, "删除成功"); 
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(false, "删除失败");
		}
	}
	
		/**
	 * 查询+分页
	 * @param brand
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping("/search")
	public PageResult search(@RequestBody TbFreightTemplate freightTemplate, int page, int rows  ){
		return freightTemplateService.findPage(freightTemplate, page, rows);		
	}
	
}

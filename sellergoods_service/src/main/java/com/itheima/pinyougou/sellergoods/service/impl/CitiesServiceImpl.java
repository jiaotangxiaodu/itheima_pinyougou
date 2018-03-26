package com.itheima.pinyougou.sellergoods.service.impl;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.pinyougou.mapper.TbCitiesMapper;
import com.itheima.pinyougou.pojo.TbCities;
import com.itheima.pinyougou.pojo.TbCitiesExample;
import com.itheima.pinyougou.pojo.TbCitiesExample.Criteria;
import com.itheima.pinyougou.sellergoods.service.CitiesService;

import com.itheima.pinyougou.entity.PageResult;

/**
 * 服务实现层
 * @author Administrator
 *
 */
@Service
public class CitiesServiceImpl implements CitiesService {

	@Autowired
	private TbCitiesMapper citiesMapper;
	
	/**
	 * 查询全部
	 */
	@Override
	public List<TbCities> findAll() {
		return citiesMapper.selectByExample(null);
	}

	/**
	 * 按分页查询
	 */
	@Override
	public PageResult findPage(int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);		
		Page<TbCities> page=   (Page<TbCities>) citiesMapper.selectByExample(null);
		return new PageResult(page.getTotal(), page.getResult());
	}

	/**
	 * 增加
	 */
	@Override
	public void add(TbCities cities) {
		citiesMapper.insert(cities);		
	}

	
	/**
	 * 修改
	 */
	@Override
	public void update(TbCities cities){
		citiesMapper.updateByPrimaryKey(cities);
	}	
	
	/**
	 * 根据ID获取实体
	 * @param id
	 * @return
	 */
	@Override
	public TbCities findOne(Integer id){
		return citiesMapper.selectByPrimaryKey(id);
	}

	/**
	 * 批量删除
	 */
	@Override
	public void delete(Integer[] ids) {
		for(Integer id:ids){
			citiesMapper.deleteByPrimaryKey(id);
		}		
	}
	
	
		@Override
	public PageResult findPage(TbCities cities, int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		
		TbCitiesExample example=new TbCitiesExample();
		Criteria criteria = example.createCriteria();
		
		if(cities!=null){			
						if(cities.getCityid()!=null && cities.getCityid().length()>0){
				criteria.andCityidLike("%"+cities.getCityid()+"%");
			}
			if(cities.getCity()!=null && cities.getCity().length()>0){
				criteria.andCityLike("%"+cities.getCity()+"%");
			}
			if(cities.getProvinceid()!=null && cities.getProvinceid().length()>0){
				criteria.andProvinceidLike("%"+cities.getProvinceid()+"%");
			}
	
		}
		
		Page<TbCities> page= (Page<TbCities>)citiesMapper.selectByExample(example);		
		return new PageResult(page.getTotal(), page.getResult());
	}
	
}

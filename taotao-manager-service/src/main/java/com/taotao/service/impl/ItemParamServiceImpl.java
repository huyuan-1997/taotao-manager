package com.taotao.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.common.pojo.EUDataGridResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.mapper.TbItemParamMapper;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemExample;
import com.taotao.pojo.TbItemParam;
import com.taotao.pojo.TbItemParamExample;
import com.taotao.pojo.TbItemParamExample.Criteria;
import com.taotao.service.ItemParamService;
@Service
public class ItemParamServiceImpl implements ItemParamService {
	@Autowired
	private TbItemParamMapper itemParamMapper;
	//分类模板在数据库中是保存了一个商品分类id的
	/**
	 * 根据分类id查询规格参数模板
	 */
	@Override
	public TaotaoResult getItemParamByCid(Long itemCatId) {
		// TODO Auto-generated method stub
		TbItemParamExample example = new TbItemParamExample();
		Criteria criteria = example.createCriteria();
		criteria.andItemCatIdEqualTo(itemCatId);
		//执行查询
		List<TbItemParam> list = itemParamMapper.selectByExampleWithBLOBs(example);
		//判断是否查到结果
		if(list!=null && list.size()>0){
			//查到了
			return TaotaoResult.ok(list.get(0));
		}
		return TaotaoResult.ok();
	}
	/**
	 * 保存商品规格模板
	 */
	@Override
	public TaotaoResult insertItemParam(TbItemParam itemParam) {
		// TODO Auto-generated method stub
		//补全pojo
		itemParam.setCreated(new Date());
		itemParam.setUpdated(new Date());
		//插入到规格参数模板中
		itemParamMapper.insert(itemParam);
		return TaotaoResult.ok();
	}	
}

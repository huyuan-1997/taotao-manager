package com.taotao.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.common.pojo.EUDataGridResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.IDUtils;
import com.taotao.mapper.TbItemDescMapper;
import com.taotao.mapper.TbItemMapper;
import com.taotao.mapper.TbItemParamItemMapper;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemDesc;
import com.taotao.pojo.TbItemExample;
import com.taotao.pojo.TbItemExample.Criteria;
import com.taotao.pojo.TbItemParamItem;
import com.taotao.service.ItemService;

@Service
public class ItemServiceImpl implements ItemService {
	@Autowired
	private TbItemMapper itemMapper;
	@Autowired
	private TbItemDescMapper itemDescMapper;
	@Autowired
	private TbItemParamItemMapper itemParamItemParamMapper;

	/**
	 * 用来测试工程是否可用
	 */
	public TbItem getItemById(long itemId) {
		// TODO Auto-generated method stub
		// 添加查询条件
		TbItemExample example = new TbItemExample();
		Criteria criteria = example.createCriteria();
		criteria.andIdEqualTo(itemId);
		// 执行查询
		List<TbItem> list = itemMapper.selectByExample(example);
		if (list != null && list.size() > 0) {
			TbItem item = list.get(0);
			return item;
		}
		return null;
	}

	/**
	 * 商品分页查询
	 */
	@Override
	public EUDataGridResult getItemList(Integer page, Integer rows) {
		// TODO Auto-generated method stub
		// 查询商品列表
		TbItemExample example = new TbItemExample();
		// 分页处理，其实就相当于criteria
		PageHelper.startPage(page, rows);
		List<TbItem> list = itemMapper.selectByExample(example);
		// 创建一个返回值
		EUDataGridResult result = new EUDataGridResult();
		result.setRows(list);
		// 取记录总数
		PageInfo<TbItem> pageInfo = new PageInfo<>(list);
		result.setTotal(pageInfo.getTotal());
		return result;
	}

	// 分表原则，需要存储到数据库，单个字段的信息量很大，而且又不经常使用的字段可以分出一个表来
	@Override
	public TaotaoResult createItem(TbItem item, String desc, String itemParam) throws Exception {
		// TODO Auto-generated method stub
		// item补全
		// 生成商品ID
		Long itemId = IDUtils.genItemId();
		item.setId(itemId);
		// 商品状态
		item.setStatus((byte) 1);
		item.setCreated(new Date());
		item.setUpdated(new Date());
		// 插入到数据库
		itemMapper.insert(item);
		// 插入商品描述
		TaotaoResult result = insertItemDesc(itemId, desc);
		if (result.getStatus() != 200) {
			throw new Exception();
		}
		// 插入商品描述正常
		// 添加规格参数
		// 添加规格参数
		result = insertItemParamItem(itemId, itemParam);
		if (result.getStatus() != 200) {
			throw new Exception();
		}

		return TaotaoResult.ok();
	}

	/**
	 * 添加商品规格参数
	 * 
	 * @param itemId
	 * @param itemParam
	 * @return
	 */
	private TaotaoResult insertItemParamItem(Long itemId, String itemParam) {
		// TODO Auto-generated method stub
		// 创建一个pojo
		TbItemParamItem itemParamItem = new TbItemParamItem();
		itemParamItem.setItemId(itemId);
		itemParamItem.setParamData(itemParam);
		itemParamItem.setCreated(new Date());
		itemParamItem.setUpdated(new Date());
		// 插入数据
		itemParamItemParamMapper.insert(itemParamItem);
		return TaotaoResult.ok();
	}

	/**
	 * 添加商品描述
	 * 
	 * @param itemId
	 * @param itemParam
	 * @return
	 */
	private TaotaoResult insertItemDesc(Long itemId, String desc) {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
		TbItemDesc itemDesc = new TbItemDesc();
		itemDesc.setItemId(itemId);
		itemDesc.setItemDesc(desc);
		itemDesc.setCreated(new Date());
		itemDesc.setUpdated(new Date());
		itemDescMapper.insert(itemDesc);
		return TaotaoResult.ok();
	}

}

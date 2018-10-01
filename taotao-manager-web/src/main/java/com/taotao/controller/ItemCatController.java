package com.taotao.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.pojo.TbItemCat;
import com.taotao.service.ItemCatService;

@Controller
@RequestMapping("item/cat")
public class ItemCatController {
	@Autowired
	private ItemCatService itemCatService;
	/**
	 * 商品分类叶目，根据父节点查询子节点
	 */
	@RequestMapping("/list")
	@ResponseBody
	public List categoryList(@RequestParam(value="id",defaultValue="0")Long parentId){
		//默认值是0也就是没有父节点的节点
		List catList = new ArrayList();
		List<TbItemCat> list = itemCatService.getItemCatList(parentId);
		/**
		 * 构建异步tree所需要的数据结构
		 */
		for (TbItemCat tbItemCat : list) {
			Map node = new HashMap<>();
			node.put("id", tbItemCat.getId());
			node.put("text", tbItemCat.getName());
			//如果是父节点的话就设置成关闭状态，如果是叶子节点就是open状态
			node.put("state", tbItemCat.getIsParent()?"closed":"open");
			catList.add(node);
		}
		return catList;
	}
}

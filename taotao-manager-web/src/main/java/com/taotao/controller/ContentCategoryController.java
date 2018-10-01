package com.taotao.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.common.pojo.EUTreeNode;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.service.ContentCategoryService;

/**
 * 内容分类，异步树请求
 * @author 胡园
 *
 */
@Controller
@RequestMapping("/content/category")
public class ContentCategoryController {
	@Autowired
	private ContentCategoryService contentCategoryService;
	/**
	 * 异步树请求
	 */
	@RequestMapping("/list")
	@ResponseBody
	public List<EUTreeNode> getContentCatList(@RequestParam(value = "id", defaultValue = "0") Long parentId){
		List<EUTreeNode> list = contentCategoryService.getCategoryList(parentId);
		return list;
	}
	/*
	 * 增加子节点
	 * 
	 */
	@RequestMapping("/create")
	@ResponseBody
	public TaotaoResult createContentCategory(Long parentId, String name) {
		TaotaoResult result = contentCategoryService.insertContentCategory(parentId, name);
		return result;
	}

}
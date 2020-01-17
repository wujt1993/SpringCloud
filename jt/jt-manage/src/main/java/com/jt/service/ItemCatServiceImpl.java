package com.jt.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jt.mapper.ItemCatMapper;
import com.jt.pojo.ItemCat;
import com.jt.util.ObjectMapperUtil;
import com.jt.vo.EasyUITree;

import redis.clients.jedis.Jedis;

@Service
public class ItemCatServiceImpl implements ItemCatService{
	
	@Autowired
	private ItemCatMapper itemCatMapper;
	
	@Autowired(required=false)
	private Jedis jedis;

	@Override
	public String findItemCatNameById(Long itemCatId) {
		QueryWrapper<ItemCat> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("id", itemCatId);
		return itemCatMapper.selectOne(queryWrapper).getName();
	}

	@Override
	public List<EasyUITree> findItemCatByParentId(Long parentId) {
		List<ItemCat> cartList = findItemCatList(parentId);
		List<EasyUITree> treeList = new ArrayList<>();
		for(ItemCat item : cartList) {
			EasyUITree e = new EasyUITree();
			e.setId(item.getId());
			e.setText(item.getName());
			e.setState(item.getIsParent() ? "closed":"open");
			treeList.add(e);
		}
		return treeList;
	}

	private List<ItemCat> findItemCatList(Long parentId) {
		QueryWrapper<ItemCat> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("parent_id", parentId);
		return itemCatMapper.selectList(queryWrapper);
	}

	@Override
	public List<EasyUITree> findItemCatByCache(Long parentId) {
		String key = "item_cat_" + parentId;
		String res = jedis.get(key);
		List<EasyUITree> treeList = new ArrayList<>();
		if(res == null) {
			treeList = findItemCatByParentId(parentId);
			String json = ObjectMapperUtil.toJSON(treeList);
			jedis.set(key, json);
			System.out.println("查询数据库");
		}else {
			treeList = ObjectMapperUtil.toObject(res, treeList.getClass());
			System.out.println("查询缓存");
		}
		return treeList;
	}
}

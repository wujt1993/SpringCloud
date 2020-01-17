package com.jt.service;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jt.mapper.ItemDescMapper;
import com.jt.mapper.ItemMapper;
import com.jt.pojo.Item;
import com.jt.pojo.ItemDesc;
import com.jt.vo.EasyUIData;

@Service
public class ItemServiceImpl implements ItemService {
	
	@Autowired
	private ItemMapper itemMapper;
	@Autowired
	private ItemDescMapper itemDescMapper;

	@Override
	public EasyUIData findItemByPage(Integer page, Integer rows) {
		Integer total = itemMapper.selectCount(null);
		
		Integer start = (page - 1) * rows;
		List<Item> itemList = itemMapper.findItemByPage(start,rows);
		return new EasyUIData(total,itemList);
	}

	@Transactional//添加事务控制
	@Override
	public void saveItem(Item item, ItemDesc itemDesc) {
		item.setStatus(1)
			.setCreated(new Date())
			.setUpdated(item.getCreated());
		itemMapper.insert(item);
		itemDesc.setItemId(item.getId())
		.setCreated(item.getCreated())
		.setUpdated(item.getCreated());
		itemDescMapper.insert(itemDesc);
	}

	@Override
	public void updateItem(Item item, ItemDesc itemDesc) {
		item.setUpdated(new Date());
		itemMapper.updateById(item);
		
		itemDesc.setItemId(item.getId()).setUpdated(item.getUpdated());
		itemDescMapper.updateById(itemDesc);
	}

	@Override
	public void deleteItem(Long[] ids) {
		List<Long> itemList = Arrays.asList(ids);
		itemMapper.deleteBatchIds(itemList);
		itemDescMapper.deleteBatchIds(itemList);
	}

	@Override
	public void updateStatus(Long[] ids, Integer status) {
		Item item = new Item();
		item.setStatus(status).setUpdated(new Date());
		List<Long> longIds = Arrays.asList(ids);
		UpdateWrapper<Item> updateWrapper = new UpdateWrapper<>();
		updateWrapper.in("id", longIds);
		itemMapper.update(item, updateWrapper);
	}

	@Override
	public ItemDesc findItemDescById(Long itemId) {
		// TODO Auto-generated method stub
		return itemDescMapper.selectById(itemId);
	}

	@Override
	public Item findItemById(Long id) {
		return itemMapper.selectById(id);
	}
	
	
	
	
	
	
}

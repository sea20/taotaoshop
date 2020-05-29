package com.taotao.service.impl;

import com.taotao.common.pojo.EUTreeNode;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.mapper.TbContentCategoryMapper;
import com.taotao.pojo.TbContentCategory;
import com.taotao.pojo.TbContentCategoryExample;
import com.taotao.service.ContentCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 广告内容管理
 */
@Service
public class ContentCategoryServiceImpl implements ContentCategoryService {
    @Autowired
    TbContentCategoryMapper contentCategoryMapper;
    @Override
    public List<EUTreeNode> getCategoryList(long parentId) {
        TbContentCategoryExample example = new TbContentCategoryExample();
        TbContentCategoryExample.Criteria criteria = example.createCriteria();
        criteria.andParentIdEqualTo(parentId);
        List<TbContentCategory> contentList = contentCategoryMapper.selectByExample(example);
        List<EUTreeNode> list = new ArrayList<>();
        EUTreeNode node;
        for (TbContentCategory tbContentCategory : contentList) {
            node = new EUTreeNode();
            node.setId(tbContentCategory.getId());
            node.setText(tbContentCategory.getName());
            node.setState(tbContentCategory.getIsParent()?"closed":"open");
            list.add(node);
        }
        return list;
    }

    @Override
    public TaotaoResult insertContentCategory(long parentId, String name) {
        TbContentCategory contentCategory = new TbContentCategory();
        contentCategory.setName(name);
        contentCategory.setIsParent(false);
        //1正常 2删除
        contentCategory.setStatus(1);
        contentCategory.setUpdated(new Date());
        contentCategory.setSortOrder(1);
        contentCategory.setCreated(new Date());
        contentCategory.setParentId(parentId);
        int id = contentCategoryMapper.insert(contentCategory);
        System.out.println("id"+id);
        System.out.println("id"+contentCategory.getId());
        TbContentCategory parent = contentCategoryMapper.selectByPrimaryKey(parentId);
        //判断是否为父节点
        if(!parent.getIsParent()){
            parent.setIsParent(true);
            //更新父节点
            contentCategoryMapper.updateByPrimaryKey(parent);
        }
        return TaotaoResult.ok(contentCategory);
    }

    @Override
    public TaotaoResult deleteContentCategory(long id) {
        TbContentCategory contentCat = contentCategoryMapper.selectByPrimaryKey(id);
        contentCategoryMapper.deleteByPrimaryKey(id);
        TbContentCategoryExample example = new TbContentCategoryExample();
        TbContentCategoryExample.Criteria criteria = example.createCriteria();
        criteria.andParentIdEqualTo(contentCat.getParentId());
        List<TbContentCategory> list = contentCategoryMapper.selectByExample(example);
        if(list!=null&&list.size()>0){
            //父节点没有子节点了
            TbContentCategory parent = contentCategoryMapper.selectByPrimaryKey(contentCat.getParentId());
            parent.setIsParent(false);
            contentCategoryMapper.updateByPrimaryKey(parent);
        }
        return null;
    }

    /**
     * 重命名
     * @param id
     * @param name
     * @return
     */
    @Override
    public TaotaoResult updateContentCategoryName(Long id, String name) {
        TbContentCategory contentCat = contentCategoryMapper.selectByPrimaryKey(id);
        contentCat.setName(name);
        contentCategoryMapper.updateByPrimaryKey(contentCat);
        return null;
    }
}

package com.taotao.rest.service.impl;

import com.taotao.common.utils.JsonUtils;
import com.taotao.mapper.TbItemCatMapper;
import com.taotao.pojo.TbItemCat;
import com.taotao.pojo.TbItemCatExample;
import com.taotao.rest.dao.JedisClient;
import com.taotao.rest.pojo.CatData;
import com.taotao.rest.pojo.CatNode;
import com.taotao.rest.service.ItemCatService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class ItemCatServiceImpl implements ItemCatService {
    @Autowired
    TbItemCatMapper itemCatMapper;
    @Autowired
    JedisClient jedisClient;
    @Value("${INDEX_Cat_REDIS_KEY}")
    String INDEX_Cat_REDIS_KEY;
    /**
     * 返回分类数据
     * @return
     */
    @Override
    public CatData getItemCatList() {
        CatData data = new CatData();
        data.setData(getCatNodeList(0));
        return data;
    }
    private List getCatNodeList(long parentId){
        //redis查找
        try {
            String res = jedisClient.hget(INDEX_Cat_REDIS_KEY, parentId+"");
            //System.out.println("res"+res);
            if(!StringUtils.isBlank(res)){
                List<CatNode> result = JsonUtils.jsonToList(res, CatNode.class);
                return result;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        TbItemCatExample example = new TbItemCatExample();
        TbItemCatExample.Criteria criteria = example.createCriteria();
        criteria.andParentIdEqualTo(parentId);
        List<TbItemCat> tbItemCats = itemCatMapper.selectByExample(example);
        CatNode node;
        List dataList = new ArrayList();
        int count=0;
        for (TbItemCat tbItemCat : tbItemCats) {
            //如果是父节点
            if(tbItemCat.getIsParent()){
                node = new CatNode();
                if(parentId==0){
                    node.setName("<a href= ' /products/" +tbItemCat . getId()+". html'>"+tbItemCat . getName()+"</a>");
                }else {
                    node.setName(tbItemCat.getName());
                }
                node.setUrl("/category/" + tbItemCat.getId() + ".html");
                //递归
                node.setItem(getCatNodeList(tbItemCat.getId()));
                dataList.add(node);
                count++;
                if(count>=14&&parentId==0)
                    break;
            }else{
                String catItem = "/item/" + tbItemCat.getId() + ".html|" + tbItemCat.getName();
                dataList.add(catItem);
            }
        }
        //redis存储
        try {
            String json = JsonUtils.objectToJson(dataList);
            jedisClient.hset(INDEX_Cat_REDIS_KEY,parentId+"",json);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dataList;
    }
}

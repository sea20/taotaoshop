package com.taotao.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.common.pojo.EUDataGridResult;
import com.taotao.common.pojo.EUTreeNode;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.mapper.TbItemParamMapper;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemExample;
import com.taotao.pojo.TbItemParam;
import com.taotao.pojo.TbItemParamExample;
import com.taotao.service.ItemParamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
@Service
public class ItemParamServiceImpl implements ItemParamService {
    @Autowired
    TbItemParamMapper itemParamMapper;
    /**
     * 查找规格参数模板
     * @param cid
     * @return
     */
    @Override
    public TaotaoResult getItemParamByCid(long cid) {
        TbItemParamExample example = new TbItemParamExample();
        TbItemParamExample.Criteria criteria = example.createCriteria();
        criteria.andItemCatIdEqualTo(cid);
        List<TbItemParam> list = itemParamMapper.selectByExampleWithBLOBs(example);
        if(list!=null&&list.size()>0){
            return TaotaoResult.ok(list.get(0));
        }
        return TaotaoResult.ok() ;
    }

    @Override
    public TaotaoResult insertItemParam(TbItemParam itemParam) {
        itemParam.setCreated(new Date());
        itemParam.setUpdated(new Date());
        itemParamMapper.insert(itemParam);
        return TaotaoResult.ok();
    }

    @Override
    public EUDataGridResult getParamList(int page, int rows) {
        System.out.println("cha xun 前");
        TbItemParamExample example = new TbItemParamExample();
        PageHelper.startPage(page,rows);
        List<TbItemParam> list = itemParamMapper.selectByExampleWithBLOBs(example);
        PageInfo<TbItemParam> pageInfo = new PageInfo<>(list);
        long total = pageInfo.getTotal();
        EUDataGridResult result = new EUDataGridResult();
        result.setRows(list);
        result.setTotal(total);
        return result;
    }
}

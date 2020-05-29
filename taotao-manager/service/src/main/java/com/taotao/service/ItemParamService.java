package com.taotao.service;

import com.taotao.common.pojo.EUDataGridResult;
import com.taotao.common.pojo.EUTreeNode;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.pojo.TbItemParam;

import java.util.List;

public interface ItemParamService {
    public TaotaoResult getItemParamByCid(long cid);
    public TaotaoResult insertItemParam(TbItemParam itemParam);
    public EUDataGridResult getParamList(int page, int rows);
}

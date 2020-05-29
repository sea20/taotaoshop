package com.taotao.service;

import com.taotao.common.pojo.EUDataGridResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.pojo.TbContent;

public interface ContentService {
    public EUDataGridResult getContentList(long categoryId, int page, int rows);
    public TaotaoResult insertContent(TbContent content);
    public TaotaoResult deleteContent(Long ids);
}

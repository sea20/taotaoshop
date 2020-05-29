package com.taotao.search.pojo;

import java.util.List;

public class SearchResult {
    //总记录数
    private Long recordCount;
    //商品列表
    private List<Item> itemList;
    //总页数
    private Integer pageCount;
    //当前页数
    private Integer curPage;

    public Long getRecordCount() {
        return recordCount;
    }

    public void setRecordCount(Long recordCount) {
        this.recordCount = recordCount;
    }

    public List<Item> getItemList() {
        return itemList;
    }

    public void setItemList(List<Item> itemList) {
        this.itemList = itemList;
    }

    public Integer getPageCount() {
        return pageCount;
    }

    public void setPageCount(Integer pageCount) {
        this.pageCount = pageCount;
    }

    public Integer getCurPage() {
        return curPage;
    }

    public void setCurPage(Integer curPage) {
        this.curPage = curPage;
    }
}

package com.qf.entity;

import lombok.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author ken
 * @Time 2018/11/21 10:23
 * @Version 1.0
 */
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PageSolr<T> implements Serializable {

    private Integer page = 1;
    private Integer pageSize = 8;
    private Integer pageCount;//共有多少条记录
    private Integer pageSum;//共有多少页
    private List<T> datas;
    private List<Integer> indexs;//显示的页码范围 首页 上一页 6 7 8 9 10 下一页 尾页

    public void setPage(Integer page){
        this.page = page;
        setIndex();
    }

    private void setIndex(){

        if(getPageSum() == null){
            return ;
        }

        indexs = new ArrayList<>();
        int begin = Math.max(1, getPage() - 2);
        int end = Math.min(getPageSum(), getPage() + 2);
        for (int i = begin; i <= end; i++){
            indexs.add(i);
        }
    }

    public Integer getPage() {
        return page;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getPageCount() {
        return pageCount;
    }

    public void setPageCount(Integer pageCount) {
        this.pageCount = pageCount;
    }

    public Integer getPageSum() {
        return pageSum;
    }

    public void setPageSum(Integer pageSum) {
        this.pageSum = pageSum;
        setIndex();
    }

    public List<T> getDatas() {
        return datas;
    }

    public void setDatas(List<T> datas) {
        this.datas = datas;
    }

    public List<Integer> getIndexs() {
        return indexs;
    }

    public void setIndexs(List<Integer> indexs) {
        this.indexs = indexs;
    }
}

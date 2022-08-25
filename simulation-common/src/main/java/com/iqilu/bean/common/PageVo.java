package com.iqilu.bean.common;

import com.github.pagehelper.Page;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 分页
 *
 * @author zhangyicheng
 * @date 2020/07/28
 */
@Data
public class PageVo<T> implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 当前页
     */
    private int pageNum;

    /**
     * 每页的数量
     */
    private int pageSize;

    /**
     * 总记录数
     */
    private long total;

    /**
     * 总页数
     */
    private int pages;

    /**
     * 结果集
     */
    private List<T> list;

    /**
     * 是否为第一页
     */
    private boolean firstPage = false;

    /**
     * 是否为最后一页
     */
    private boolean lastPage = false;

    public PageVo() {
    }

    /**
     * 包装Page对象
     */
    public PageVo(List<T> list) {
        if (list instanceof Page) {
            Page page = (Page) list;
            this.pageNum = page.getPageNum();
            this.pageSize = page.getPageSize();

            this.pages = page.getPages();
            this.list = page;
            this.total = page.getTotal();
        } else if (list != null) {
            this.pageNum = 1;
            this.pageSize = list.size();

            this.pages = 1;
            this.list = list;
            this.total = list.size();
        }
        if (list != null) {
            judgePageBoundary();
        }
    }

    /**
     * 判定页面边界
     */
    private void judgePageBoundary() {
        firstPage = pageNum == 1;
        lastPage = pageNum == pages;
    }
}


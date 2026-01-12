package com.lhb.product.pojo.DTO;

import lombok.Data;

import java.util.List;

@Data
public class PageResult<T> {

    /**
     * 总记录数
     * -1 表示游标分页（不返回 total）
     */
    private long total;

    private int page;
    private int size;

    private List<T> records;

    public PageResult(long total, int page, int size, List<T> records) {
        this.total = total;
        this.page = page;
        this.size = size;
        this.records = records;
    }
}

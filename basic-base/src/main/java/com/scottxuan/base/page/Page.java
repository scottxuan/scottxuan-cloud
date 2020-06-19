package com.scottxuan.base.page;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author : zhaoxuan
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Page {
    private Integer pageIndex = 1;
    private Integer pageSize = 10;
    private Integer totalPage = 0;
    private Long totalSize = 0L;
}

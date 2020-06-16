package scottxuan.cloud.base.page;

import lombok.Getter;

/**
 * @author : zhaoxuan
 */
@Getter
public class PageParam {
    public static final Integer minPageIndex = 1;
    public static final Integer maxPageSize = 10000;

    private Integer pageIndex;
    private Integer pageSize;
    private String sort;
    private Boolean asc = Boolean.TRUE;

    public PageParam(Integer pageIndex, Integer pageSize) {
        if (pageIndex < minPageIndex) {
            this.pageIndex = minPageIndex;
        } else if (pageSize > maxPageSize) {
            this.pageSize = minPageIndex;
        } else {
            this.pageIndex = pageIndex;
            this.pageSize = pageSize;
        }
    }

    public PageParam(Integer pageIndex, Integer pageSize, String sort) {
        if (pageIndex < minPageIndex) {
            this.pageIndex = minPageIndex;
        } else if (pageSize > maxPageSize) {
            this.pageSize = minPageIndex;
        } else {
            this.pageIndex = pageIndex;
            this.pageSize = pageSize;
        }
        this.sort = sort;
    }

    public PageParam(Integer pageIndex, Integer pageSize, String sort, Boolean asc) {
        if (pageIndex < minPageIndex) {
            this.pageIndex = minPageIndex;
        } else if (pageSize > maxPageSize) {
            this.pageSize = minPageIndex;
        } else {
            this.pageIndex = pageIndex;
            this.pageSize = pageSize;
        }
        this.sort = sort;
        this.asc = asc;
    }
}

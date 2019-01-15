package top.chippy.blog.vo;

import lombok.Data;
import lombok.ToString;

/**
 * @Date: 2019/1/14 17:42
 * @program: chippy-blog
 * @Author: chippy
 * @Description: 通用参数
 */
@ToString
@Data
public class Params {
    // 默认第一页
    private int pageNum = 1;
    // 默认10分个数据
    private int limit = 10;
    private String search;
}

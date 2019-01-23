package top.chippy.blog.vo;

import lombok.Data;
import lombok.ToString;

/**
 * @Date: 2019/1/18 15:30
 * @program: chippy-blog
 * @Author: chippy
 * @Description: 文章查询条件
 */
@ToString
@Data
public class ArticleParams extends Params {

    private String crtUser;
    private String delFlag;
    private String type;

}

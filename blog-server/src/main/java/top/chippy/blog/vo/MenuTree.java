package top.chippy.blog.vo;

import com.loser.common.vo.TreeNode;
import lombok.Data;
import lombok.ToString;

/**
 * @Date: 2019/1/14 16:29
 * @program: chippy-blog
 * @Author: chippy
 * @Description:
 */
@ToString
@Data
public class MenuTree extends TreeNode {
    private String title;
    private String href;
    private int sort;
    private String icon;
    private int type;
    private String parentId;
}

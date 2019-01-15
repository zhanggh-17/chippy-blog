package com.loser.common.vo;

import lombok.Data;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

/**
 * @Date: 2019/1/14 16:30
 * @program: chippy-blog
 * @Author: chippy
 * @Description:
 */
@ToString
@Data
public class TreeNode {
    protected String id;
    protected String parentId;
    protected List<TreeNode> children = new ArrayList<TreeNode>();

    public void add(TreeNode node) {
        children.add(node);
    }
}

package top.chippy.blog.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @Date: 2019/1/4 14:10
 * @program: chippy-blog
 * @Author: chippy
 * @Description:
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Table(name = "base_dictionary")
public class Dictionary {
    /**
     * id
     */
    @Id
    private String id;

    /**
     * 字典编码
     */
    private Integer dicCode;

    /**
     * 英文名称
     */
    private String itemEnglish;

    /**
     * 字典名称
     */
    private String itemName;

    /**
     * 父级id
     */
    private String parentId;

    /**
     * 是否树形展示 1 y 2 n
     */
    private Boolean isPost;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 创建人
     */
    private String crtUser;

    /**
     * 信息更新人
     */
    private String updUser;

    /**
     * 创建时间
     */
    private String crtTime;

    /**
     * 更新时间
     */
    private String updTime;

    /**
     * 是否删除
     */
    private Boolean delFlag;

}

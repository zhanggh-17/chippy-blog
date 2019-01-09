package top.chippy.blog.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * @Date: 2019/1/4 14:09
 * @program: chippy-blog
 * @Author: chippy
 * @Description:
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Table(name = "chippy_article")
public class Article {

    @Id
    private String id;
    private String title;
    private String content;
    private String halfContent;
    private String cover;
    private String author;
    private String type;
    private String count;
    private String crtTime;
    private String updTime;
    private String crtUser;
    private String updUser;
    private int delFlag;

    /**
     * @Description 注入类型名称
     */
    @Transient
    private String typeName;

}

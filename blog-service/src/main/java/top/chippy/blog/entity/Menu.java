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
@Table(name = "chippy_menu")
public class Menu {

    @Id
    private String id;
    private String title;
    private String href;
    private int sort;
    private String parentId;
    private String description;
    private String crtTime;
    private String updTime;
    private String crtUser;
    private String updUser;

}

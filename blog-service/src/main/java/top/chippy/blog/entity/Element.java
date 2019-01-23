package top.chippy.blog.entity;

import lombok.Data;
import lombok.ToString;

import javax.persistence.Id;

/**
 * @Date: 2019/1/18 16:07
 * @program: chippy-blog
 * @Author: chippy
 * @Description:
 */
@ToString
@Data
public class Element {

    @Id
    private String id;
    private String code;
    private String type;
    private String name;
    private String uri;
    private String menuId;
    private String crtTime;
    private String updTime;
    private String crtUser;
    private String updUser;

}

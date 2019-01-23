package top.chippy.blog.entity;

import lombok.Data;
import lombok.ToString;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @Date: 2019/1/18 10:42
 * @program: chippy-blog
 * @Author: chippy
 * @Description:
 */
@Table(name = "base_role")
@ToString
@Data
public class Role {

    @Id
    private String id;
    private String name;
    private String parentId;
    private String crtTime;
    private String updTime;
    private String crtUser;
    private String updUser;


}

package top.chippy.blog.entity;

import lombok.Data;
import lombok.ToString;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @Date: 2019/1/9
 * @Author: chippy
 * @Description: 用户实体类
 */
@ToString
@Data
@Table(name = "chippy_user")
public class User {
    @Id
    private String id;
    private String name;
    private String email;
    private String password;
    private String crtTime;
    private String updTime;
    private String crtUser;
    private String updUser;
    private int delFlag;
}
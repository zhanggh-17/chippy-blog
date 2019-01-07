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
@Table(name = "chippy_contact")
public class Contact {
    @Id
    private String id;
    private String ip;
    private String name;
    private String email;
    private String content;
}

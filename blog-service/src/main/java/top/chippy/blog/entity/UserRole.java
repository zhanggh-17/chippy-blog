package top.chippy.blog.entity;

import lombok.Data;
import lombok.ToString;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @Date: 2019/1/18 11:35
 * @program: chippy-blog
 * @Author: chippy
 * @Description:
 */
@Table(name = "base_user_role")
@ToString
@Data
public class UserRole {

    @Id
    private String id;
    private String userId;
    private String roleId;

    public UserRole(String id, String userId, String roleId) {
        this.id = id;
        this.userId = userId;
        this.roleId = roleId;
    }

    public UserRole() {
    }
}

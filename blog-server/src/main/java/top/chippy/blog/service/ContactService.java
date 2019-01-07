package top.chippy.blog.service;

import com.loser.common.base.service.BaseMysqlService;
import org.springframework.stereotype.Service;
import top.chippy.blog.entity.Contact;
import top.chippy.blog.mapper.ContactMapper;

/**
 * @Date: 2019/1/7
 * @program: xx
 * @Author: chippy
 * @Description:
 */
@Service
public class ContactService extends BaseMysqlService<ContactMapper, Contact> {
}
package top.chippy.blog.controller;

import com.loser.common.base.controller.BaseController;
import com.loser.common.util.ClientUtil;
import com.loser.common.util.Stringer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.chippy.blog.entity.Contact;
import top.chippy.blog.service.ContactService;

/**
 * @Date: 2019/1/7
 * @program: xx
 * @Author: chippy
 * @Description:
 */
@Slf4j
@RestController
@RequestMapping("/contact")
public class ContactController extends BaseController {

    @Autowired
    private ContactService contactService;

    /*
     * @Description 接受消息
     * @param [contact]
     * @return
     */
    @PostMapping("/message")
    public Object message(Contact contact) {
        int flag = 0;
        if (!Stringer.isNullOrEmpty(contact)) {
            if (Stringer.isNullOrEmpty(contact.getName()))
                return error("姓名不能为空");
            if (Stringer.isNullOrEmpty(contact.getEmail()))
                return error("邮箱不能为空");
            if (Stringer.isNullOrEmpty(contact.getContent()))
                return error("内容不能为空");
            String clientIp = ClientUtil.getClientIp(request);
            contact.setIp(clientIp);
            try {
                flag = contactService.insert(contact);
            } catch (Exception e) {
                log.error(e.getMessage());
                return error("提交异常。");
            }
        }
        return success(flag);
    }

}
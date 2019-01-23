package top.chippy.blog.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.loser.common.base.service.BaseMysqlService;
import com.loser.common.util.Stringer;
import org.springframework.stereotype.Service;
import top.chippy.blog.constant.BlogConstant;
import top.chippy.blog.entity.Article;
import top.chippy.blog.entity.Contact;
import top.chippy.blog.mapper.ContactMapper;
import top.chippy.blog.vo.Params;

import java.util.List;

/**
 * @Date: 2019/1/7
 * @program: xx
 * @Author: chippy
 * @Description:
 */
@Service
public class ContactService extends BaseMysqlService<ContactMapper, Contact> {

    public PageInfo<Contact> list(Params params) {
        PageHelper.startPage(params.getPageNum(), params.getLimit());
        List<Contact> contactList = mapper.list(params.getSearch());
        PageInfo<Contact> pageInfo = new PageInfo<Contact>(contactList);
        return pageInfo;
    }
}
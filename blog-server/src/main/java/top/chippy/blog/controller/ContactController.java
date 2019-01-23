package top.chippy.blog.controller;

import cn.hutool.http.HttpUtil;
import com.alibaba.druid.sql.visitor.functions.Concat;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.loser.common.base.controller.BaseController;
import com.loser.common.util.ClientUtil;
import com.loser.common.util.Stringer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.chippy.blog.annotation.IgnoreAuth;
import top.chippy.blog.constant.BlogConstant;
import top.chippy.blog.entity.Contact;
import top.chippy.blog.service.ContactService;
import top.chippy.blog.vo.AddressInfo;
import top.chippy.blog.vo.Params;

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
    @IgnoreAuth
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
            String addressInfo = getAddressInfo(clientIp);
            try {
                contact.setIp(clientIp);
                contact.setAddress(addressInfo);
                contact.setIsRead(-1); // 默认未读
                flag = contactService.insert(contact);
            } catch (Exception e) {
                log.error("接受交流信息失败 >>> " + contact, e);
                log.error(e.getMessage());
            }
        }
        return success(flag);
    }

    @GetMapping("/list")
    public Object list(Params params) {
        PageInfo<Contact> pageInfo = contactService.list(params);
        return success(pageInfo);
    }

    @GetMapping("/{id}")
    public Object single(@PathVariable("id") String id) {
        Contact contact = contactService.selectById(id);
        return success(contact);
    }

    @PostMapping("/{id}")
    public Object state(@PathVariable("id") String id) {
        Contact contact = new Contact();
        contact.setId(id);
        contact.setIsRead(1);
        contactService.updateSelectiveById(contact);
        return success(contact);
    }

    /**
     * @Description 地址信息拼接成 国家+省市县
     * @Author chippy
     * @Datetime 2019/1/23 16:26
     */
    private String getAddressInfo(String clientIp) {
        String country, area, city, county = null;
        AddressInfo address = getAddress(clientIp);
        if (Stringer.isNullOrEmpty(address))
            return null;
        StringBuilder addressStringBuilder = new StringBuilder();
        AddressInfo.AddressResult addressResult = address.getData();

        // 判断返回地址值
        country = Stringer.isNullOrEmpty(addressResult.getCountry()) ? BlogConstant.UNDEFINED_INFO : addressResult.getCountry();
        area = Stringer.isNullOrEmpty(addressResult.getArea()) ? BlogConstant.UNDEFINED_INFO : addressResult.getArea();
        city = Stringer.isNullOrEmpty(addressResult.getCity()) ? BlogConstant.UNDEFINED_INFO : addressResult.getCity();
        county = Stringer.isNullOrEmpty(addressResult.getCounty()) ? BlogConstant.UNDEFINED_INFO : addressResult.getCounty();

        addressStringBuilder.append(country + "-")
                            .append(area + "-")
                            .append(city + "-")
                            .append(county);
        return addressStringBuilder.toString();
    }

    /**
     * @Description 通过ip获取地址
     * @Author chippy
     * @Datetime 2019/1/23 16:14
     */
    private AddressInfo getAddress(String clientIp) {
        String url = BlogConstant.CONVERT_ADDRESS + clientIp;
        String resultAddressJson = HttpUtil.get(url);
        AddressInfo addressInfo = JSONObject.parseObject(resultAddressJson, AddressInfo.class);
        if (addressInfo.getCode() != 0)
            return null;
        return addressInfo;
    }

}
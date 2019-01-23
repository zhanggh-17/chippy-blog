package top.chippy.blog.vo;

import lombok.Data;
import lombok.ToString;

/**
 * @Date: 2019/1/23 16:17
 * @program: chippy-blog
 * @Author: chippy
 * @Description: 淘宝IP转实体
 */
@ToString
@Data
public class AddressInfo {
    private int code;
    private AddressResult data;

    @Data
    public class AddressResult {
        private String ip;
        private String country;
        private String area;
        private String region;
        private String city;
        private String county;
        private String isp;
    }
}

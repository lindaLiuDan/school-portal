package com.info.utils;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.info.xss.SQLFilter;
import org.apache.commons.lang.StringUtils;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 查询参数
 *
 * @author Gaosx
 */
public class Query<T> extends LinkedHashMap<String, Object> {

    //分页参数
    private static long currPage = 1;
    private static long limit = 10;

    public IPage<T> getPage(Map<String, Object> params) {
        return this.getPage(params, null, false);
    }

    public IPage<T> getPage(Map<String, Object> params, String defaultOrderField, boolean isAsc) {

        if (params.get(Constant.PAGE) != null) {
            currPage = Long.parseLong((String) params.get(Constant.PAGE));
        }
        if (params.get(Constant.PAGESIZE) != null) {
            limit = Long.parseLong((String) params.get(Constant.PAGESIZE));
        }

        //分页对象
        Page<T> page = new Page<>(currPage, limit);

        //分页参数
        params.put(Constant.PAGE, page);

        //排序字段
        //防止SQL注入（因为sidx、order是通过拼接SQL实现排序的，会有SQL注入风险）
        String orderField = SQLFilter.sqlInject((String) params.get(Constant.ORDER_FIELD));
        String order = (String) params.get(Constant.ORDER);

        //前端字段排序
        if (StringUtils.isNotEmpty(orderField) && StringUtils.isNotEmpty(order)) {
            if (Constant.ASC.equalsIgnoreCase(order)) {
                return page.setAsc(orderField);
            } else {
                return page.setDesc(orderField);
            }
        }
        //默认排序
        if (isAsc) {
            page.setAsc(defaultOrderField);
        } else {
            page.setDesc(defaultOrderField);
        }

        return page;
    }

    /**
     * 功能描述: 自定义分页的方法
     *
     * @Params: * @param null
     * @Author: Gaosx  By User
     * @Date: 2019/6/8 15:46
     * @Return:
     */
//    public Query(Map<String, Object> params) {
//        this.putAll(params);
//
//        //分页参数
//        if (params.get("page") != null) {
//            currPage = Integer.parseInt((String) params.get("page"));
//        }
//        if (params.get("pageSize") != null) {
//            limit = Integer.parseInt((String) params.get("pageSize"));
//        }
//        this.put("offset", (currPage - 1) * limit);
//        this.put("page", currPage);
//        this.put("limit", limit);
//
//        //防止SQL注入（因为sidx、order是通过拼接SQL实现排序的，会有SQL注入风险）
//        String sidx = SQLFilter.sqlInject((String) params.get("sidx"));
//        String order = SQLFilter.sqlInject((String) params.get("order"));
//        this.put("sidx", sidx);
//        this.put("order", order);
//
//        //mybatis-plus分页
//        this.page = new Page<>(currPage, limit);
//
//    }

}

package com.info.modules.area.service.impl;

import com.info.modules.area.entity.OpenCityEntity;
import com.info.modules.area.dao.IOpenCityDao;
import com.info.modules.area.service.IOpenCityService;
import com.info.utils.ConfigConstant;
import org.apache.commons.lang.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import com.info.utils.PageUtils;
import com.info.utils.Query;

import java.util.List;
import java.util.Map;

/**
 * 开放城市区域信息表
 *
 * @author Gaosx
 * @email
 * @date 2019-06-12 19:31:54
 */
@Service("openCityService")
public class OpenCityServiceImpl extends ServiceImpl<IOpenCityDao, OpenCityEntity> implements IOpenCityService {

    /**
     * 功能描述: 开放城市区域信息表
     *
     * @Params: * @param null
     * @Author: Gaosx 960889426@qq.com By User
     * @Date: 2019/6/12 19:47
     * @Return:
     */
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        String parames = (String) params.get("params");
        String endTime = (String) params.get("endTime");
        String begTime = (String) params.get("begTime");
        IPage<OpenCityEntity> page = this.page(
                new Query<OpenCityEntity>().getPage(params),
                new QueryWrapper<OpenCityEntity>()
                        .select("open_id,city_name,city_no,parent_id")
                        .eq("is_del", ConfigConstant.NOTDEL)
                        .ge(StringUtils.isNotBlank(begTime), "creator_time", begTime)
                        .le(StringUtils.isNotBlank(endTime), "creator_time", endTime)
                        .like(StringUtils.isNotBlank(parames), "parames", parames)
        );
        return new PageUtils(page);
    }

    /**
     * 功能描述: 无分页的查询所有开放城市
     *
     * @Params: * @param null
     * @Author: Gaosx 960889426@qq.com By User
     * @Date: 2019/6/12 19:40
     * @Return:
     */
    @Override
    public List<OpenCityEntity> all(Map<String, Object> params) {
        return this.list(new QueryWrapper<OpenCityEntity>()
                .select("open_id,city_name,city_no,parent_id")
                .eq("is_del", ConfigConstant.NOTDEL)
        );
    }

}

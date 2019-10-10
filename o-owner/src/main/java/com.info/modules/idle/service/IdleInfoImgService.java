package com.info.modules.idle.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.info.modules.idle.entity.IdleInfoImgEntity;
import com.info.utils.PageUtils;

import java.util.List;
import java.util.Map;

/**
 * 闲置信息图片表
 *
 * @author Gaosx
 * @email
 * @date 2019-06-07 17:17:37
 */
public interface IdleInfoImgService extends IService<IdleInfoImgEntity> {

    /**
     * @Author: Gaosx
     * @Description: 闲置信息图片表
     * @Date: 2019-06-07 17:17:37
     */
    PageUtils queryPage(Map<String, Object> params);

    /**
     * 功能描述: 根据idleId查询对应的图片信息集合
     *
     * @Params: * @param null
     * @Author: Gaosx 960889426@qq.com By User
     * @Date: 2019/6/22 20:26
     * @Return:
     */
    List<IdleInfoImgEntity> imgList(Integer idleId);

    /**
     * 功能描述: 删除图片集合
     *
     * @Params: * @param null
     * @Author: Gaosx 960889426@qq.com By User
     * @Date: 2019/6/23 22:13
     * @Return:
     */
    Boolean delImgList(Integer idleId);

    /**
     * 功能描述: 修改图片集合
     *
     * @Params: * @param null
     * @Author: Gaosx 960889426@qq.com By User
     * @Date: 2019/6/23 22:13
     * @Return:
     */
    void updateImgList(List<IdleInfoImgEntity> imgEntityList);


}


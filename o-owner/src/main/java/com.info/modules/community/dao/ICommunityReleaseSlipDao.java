package com.info.modules.community.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.info.modules.community.entity.CommunityReleaseSlipEntity;
import com.info.modules.community.vo.CommunityReleasePassAndApplyVo;
import com.info.utils.PageUtils;

import java.util.List;
import java.util.Map;

/**
 * @author Gaosx
 * @email 960889426@qq.com
 * @date 2019-06-25 18:19:11
 */
public interface ICommunityReleaseSlipDao extends BaseMapper<CommunityReleaseSlipEntity> {

    /**
     * @Description 查询放行单记录或者申请记录
     * @Author LiuDan
     * @Date 2019/7/8 17:08
     * @Param
     * @Return
     * @Exception
     */
    List<CommunityReleasePassAndApplyVo> getPassAndApplyList(Map<String, Object> map);


    /**
     * @Description 查询放行单记录或者申请记录——总页数
     * @Author LiuDan
     * @Date 2019/7/8 17:08
     * @Param
     * @Return
     * @Exception
     */
    Integer getPassAndApplyListTotal(Map<String, Object> map);

}

package com.info.modules.oss.controller;

import com.google.gson.Gson;
import com.info.common.group.AliyunGroup;
import com.info.common.group.QcloudGroup;
import com.info.common.group.QiniuGroup;
import com.info.date.DateUtils;
import com.info.exception.LiftException;
import com.info.modules.oss.cloud.CloudStorageConfig;
import com.info.modules.oss.cloud.OSSFactory;
import com.info.modules.oss.entity.SysOssEntity;
import com.info.modules.oss.service.SysOssService;
import com.info.modules.sys.controller.AbstractController;
import com.info.modules.sys.service.SysConfigService;
import com.info.utils.ConfigConstant;
import com.info.utils.Constant;
import com.info.utils.PageUtils;
import com.info.utils.ResultMessage;
import com.info.validator.Assert;
import com.info.validator.ValidatorUtils;
import com.info.validator.group.AddGroup;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.Date;
import java.util.Map;

/**
 * 文件上传
 *
 * @author Gaosx
 */
@RestController
@RequestMapping("sys/oss")
public class SysOssController extends AbstractController {

    @Autowired
    private SysOssService sysOssService;

    @Autowired
    private SysConfigService sysConfigService;

    private final static String KEY = ConfigConstant.CLOUD_STORAGE_CONFIG_KEY;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("sys:oss:all")
    public ResultMessage list(@RequestParam Map<String, Object> params) {
        PageUtils page = sysOssService.queryPage(params);
        return ResultMessage.ok(page);
    }

    /**
     * 云存储配置信息
     */
    @RequestMapping("/config")
    @RequiresPermissions("sys:oss:all")
    public ResultMessage config() {
        CloudStorageConfig config = sysConfigService.getConfigObject(KEY, CloudStorageConfig.class);
        return ResultMessage.ok(config);
    }

    /**
     * 保存云存储配置信息
     */
    @RequestMapping("/saveConfig")
    @RequiresPermissions("sys:oss:all")
    public ResultMessage saveConfig(@Valid @RequestBody CloudStorageConfig config) {
        //校验类型
        ValidatorUtils.validateEntity(config, ConfigConstant.ERROR, AddGroup.class);
        if (config.getType() == Constant.CloudService.QINIU.getValue()) {
            //校验七牛数据
            ValidatorUtils.validateEntity(config, QiniuGroup.class);
        } else if (config.getType() == Constant.CloudService.ALIYUN.getValue()) {
            //校验阿里云数据
            ValidatorUtils.validateEntity(config, AliyunGroup.class);
        } else if (config.getType() == Constant.CloudService.QCLOUD.getValue()) {
            //校验腾讯云数据
            ValidatorUtils.validateEntity(config, QcloudGroup.class);
        }
        sysConfigService.updateValueByKey(KEY, new Gson().toJson(config));
        return ResultMessage.ok();
    }

    /**
     * 上传文件
     */
    @RequestMapping("/upload")
    @RequiresPermissions("sys:oss:all")
    public ResultMessage upload(@RequestParam("file") MultipartFile file) throws Exception {
        if (file.isEmpty()) {
            return ResultMessage.error(ConfigConstant.ERROR, "上传文件不能为空");
        }
        //上传文件
        String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
        String url = OSSFactory.build().uploadSuffix(file.getBytes(), suffix);
        //保存文件信息
        SysOssEntity ossEntity = new SysOssEntity();
        ossEntity.setUrl(url);
        ossEntity.setCreateDate(DateUtils.now());
        sysOssService.save(ossEntity);
        return ResultMessage.ok(url);
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("sys:oss:all")
    public ResultMessage delete(@RequestBody Long[] ids) {
        Assert.isNull(ids, "文件ID不能为空", ConfigConstant.ERROR);
        sysOssService.removeByIds(Arrays.asList(ids));
        return ResultMessage.ok();
    }

}

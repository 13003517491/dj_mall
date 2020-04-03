package com.dj.mall.admin.web.auth.base;

import com.alibaba.dubbo.config.annotation.Reference;
import com.dj.mall.admin.vo.PermissionCode;
import com.dj.mall.api.auth.base.BaseDataApi;
import com.sun.org.apache.xpath.internal.operations.Mod;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author ZhangJQ
 * @描述 基础数据访问前台
 * @创建人 zhangjq
 * @创建时间 2020/4/1
 */
@Controller
@RequestMapping("/auth/base/")
public class BaseDataPageController {

    @Reference
    private BaseDataApi baseDataApi;

    /**
     * 去展示
     */
    @RequestMapping("toList")
    public String toList(Model model) throws Exception {
        model.addAttribute("baseDataList", baseDataApi.getBaseDataList());
        return "base/base_data_list";
    }

    /**
     * 去修改
     * @param id 数据id
     * @param model 存放数据
     * @return
     * @throws Exception
     */
    @RequestMapping("toUpdate")
    public String toList(Integer id, Model model) throws Exception {
        model.addAttribute("baseData", baseDataApi.getBaseDataById(id));
        return "base/base_data_update";
    }
}

package com.dj.mall.admin.web.auth.resource;

import com.dj.mall.api.auth.resource.ResourceApi;
import jdk.nashorn.internal.ir.annotations.Reference;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/auth/resource/")
public class ResourcePageController {
    @Reference
    private ResourceApi resourceApi;

    /**
     * 去展示
     */
    @RequestMapping("toList")
    public String toList() {
        return "resource/resource_list";
    }

//    /**
//     * 去新增
//     */
//    @RequestMapping("toAdd/{pId}")
//    public String toAdd(@PathVariable Integer pId, Model model) {
//        model.addAttribute("pId", pId);
//        return "resource/resource_add";
//    }
//
//    /**
//     * 去修改页面
//     */
//    @RequestMapping("toUpdate")
//    public String toUpdate(Integer resourceId, Model model) throws Exception {
//        model.addAttribute("resource", resourceApi.getResourceById(resourceId));
//        return "resource/resource_update";
//    }
}

package com.quick.recording.auth.service.controller.admin;

import com.quick.recording.auth.service.model.AdminModel;
import com.quick.recording.auth.service.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Objects;

@Controller
@RequestMapping
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    @GetMapping("/admin")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ModelAndView adminPanel(ModelAndView modelAndView) {
        AdminModel model = new AdminModel();
        modelAndView.addObject("model", model);
        modelAndView.setViewName("admin");
        return modelAndView;
    }

    @PostMapping("/admin")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ModelAndView adminPanel(ModelAndView modelAndView,
                                   @ModelAttribute("model") AdminModel model) {
        if(Objects.nonNull(model.getMainSelect())) {
            switch (model.getMainSelect()) {
                case "security": {
                    if(Objects.nonNull(model.getSecuritySelect())) {
                        switch (model.getSecuritySelect()) {
                            case "role": {
                                model.setRoleList(adminService.searchRole(model.getRoleSearch()));
                                break;
                            }
                            case "permission": {
                                model.setPermissionList(adminService.searchPermission(model.getPermissionSearch()));
                                break;
                            }
                            default:
                                break;
                        }
                    }
                    break;
                }
                default:
                    break;
            }
        }
        modelAndView.setViewName("admin");
        return modelAndView;
    }

    @GetMapping("/admin/{securityItem}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ModelAndView securityPanel(ModelAndView modelAndView,
                                      @RequestParam(required = false) String selectUUID,
                                      @PathVariable String securityItem) {
        switch (securityItem){
            case "role" : {
                modelAndView.setViewName("role");
                return modelAndView;
            }
            case "permission" : {
                modelAndView.setViewName("permission");
                return modelAndView;
            }
            default: {
                modelAndView.setViewName("admin");
                return modelAndView;
            }
        }
    }
}

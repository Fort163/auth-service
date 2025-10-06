package com.quick.recording.auth.service.controller.admin;

import com.quick.recording.auth.service.model.AdminModel;
import com.quick.recording.auth.service.model.PageModel;
import com.quick.recording.auth.service.model.PermissionModel;
import com.quick.recording.auth.service.model.RoleModel;
import com.quick.recording.auth.service.service.AdminService;
import com.quick.recording.gateway.dto.BaseDto;
import com.quick.recording.gateway.dto.auth.PermissionDto;
import com.quick.recording.gateway.dto.auth.RoleDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@Controller
@RequestMapping
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    @GetMapping("/admin")
    @PreAuthorize("hasAuthority('PERMISSION_ADMIN')")
    public ModelAndView adminPanel(ModelAndView modelAndView) {
        AdminModel model = new AdminModel();
        modelAndView.addObject("model", model);
        modelAndView.setViewName("admin");
        return modelAndView;
    }

    @PostMapping("/admin")
    @PreAuthorize("hasAuthority('PERMISSION_ADMIN')")
    public ModelAndView adminPanel(ModelAndView modelAndView,
                                   @ModelAttribute("model") AdminModel model) {
        if(Objects.nonNull(model.getMainSelect())) {
            switch (model.getMainSelect()) {
                case "security": {
                    if(Objects.nonNull(model.getSecuritySelect())) {
                        switch (model.getSecuritySelect()) {
                            case "role": {
                                int page = model.getCurrentPage() > 0 ? model.getCurrentPage() - 1 : 0;
                                Page<RoleDto> pageDto = adminService.searchRole(model.getRoleSearch(),
                                        PageRequest.of(page, model.getPageSize(), Sort.by("uuid")));
                                setPageInfo(model, pageDto);
                                model.setRoleList(pageDto.getContent());
                                break;
                            }
                            case "permission": {
                                int page = model.getCurrentPage() > 0 ? model.getCurrentPage() - 1 : 0;
                                Page<PermissionDto> pageDto = adminService.searchPermission(model.getPermissionSearch(),
                                        PageRequest.of(page, model.getPageSize(), Sort.by("uuid")));
                                setPageInfo(model, pageDto);
                                model.setPermissionList(pageDto.getContent());
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
    @PreAuthorize("hasAuthority('PERMISSION_ADMIN')")
    @SuppressWarnings("unchecked")
    public ModelAndView securityPanel(ModelAndView modelAndView,
                                      @RequestParam(required = false) String selectUUID,
                                      @PathVariable String securityItem) {
        switch (securityItem){
            case "role" : {
                RoleModel model = new RoleModel();
                PermissionDto permissionSearch = new PermissionDto();
                RoleDto roleDto = null;
                if(Objects.nonNull(selectUUID)){
                    model.setRoleUUID(selectUUID);
                    roleDto = adminService.getRoleByUUID(UUID.fromString(selectUUID));
                }
                else {
                    roleDto = new RoleDto();
                    roleDto.setPermissions(new ArrayList<>());
                }
                Page<PermissionDto> pageDto = adminService.searchPermission(permissionSearch,
                        PageRequest.of(0, 10, Sort.by("uuid")));
                model.setBackPanel("@/admin");
                model.setCurrentModel(roleDto);
                model.setSearchPermission(permissionSearch);
                model.setCurrentPermissionUUIDs(roleDto.getPermissions().stream()
                        .map(BaseDto::getUuid).collect(Collectors.toList())
                );
                model.setPermissionList(pageDto.getContent());
                setPageInfo(model, pageDto);
                modelAndView.addObject("model", model);
                modelAndView.setViewName("role");
                return modelAndView;
            }
            case "permission" : {
                PermissionModel model = new PermissionModel();
                PermissionDto dto = new PermissionDto();
                if(Objects.nonNull(selectUUID)){
                    model.setPermissionUUID(selectUUID);
                    dto = adminService.getPermissionByUUID(UUID.fromString(selectUUID));
                }
                else {
                    dto = new PermissionDto();
                }
                model.setCurrentModel(dto);
                modelAndView.addObject("model", model);
                modelAndView.setViewName("permission");
                return modelAndView;
            }
            default: {
                modelAndView.setViewName("admin");
                return modelAndView;
            }
        }
    }

    @PostMapping("/permission")
    @PreAuthorize("hasAuthority('PERMISSION_ADMIN')")
    public ModelAndView permissionPanel(ModelAndView modelAndView,
                                  @ModelAttribute("model") PermissionModel model) {
        model.getCurrentModel().setUuid(UUID.fromString(model.getPermissionUUID()));
        PermissionDto dto = null;
        if(Objects.nonNull(model.getPermissionUUID()) && !model.getPermissionUUID().isBlank()){
            dto = adminService.getPermissionByUUID(UUID.fromString(model.getPermissionUUID()));
        }
        else {
            dto = new PermissionDto();
        }
        dto.setPermission(model.getCurrentModel().getPermission());
        if(dto.getIsActive() != model.getCurrentModel().getIsActive()){
            adminService.changePermissionActive(model.getCurrentModel());
        }
        dto = adminService.persistPermission(dto);
        model.setPermissionUUID(dto.getUuid().toString());
        model.setCurrentModel(dto);
        modelAndView.addObject("model", model);
        modelAndView.setViewName("permission");
        return modelAndView;
    }

    @PostMapping("/role")
    @PreAuthorize("hasAuthority('PERMISSION_ADMIN')")
    public ModelAndView rolePanel(ModelAndView modelAndView,
                                  @ModelAttribute("model") RoleModel model) {
        int page = model.getCurrentPage() > 0 ? model.getCurrentPage() - 1 : 0;
        Page<PermissionDto> pageDto = adminService.searchPermission(model.getSearchPermission(),
                PageRequest.of(page, model.getPageSize(), Sort.by("uuid")));
        model.getCurrentModel().setUuid(UUID.fromString(model.getRoleUUID()));
        RoleDto roleDto = null;
        if(Objects.nonNull(model.getRoleUUID()) && !model.getRoleUUID().isBlank()){
            roleDto = adminService.getRoleByUUID(UUID.fromString(model.getRoleUUID()));
        }
        else {
            roleDto = new RoleDto();
            roleDto.setPermissions(new ArrayList<>());
        }
        roleDto.setName(model.getCurrentModel().getName());
        if(Objects.nonNull(model.getAddPermissionUUID()) && !model.getAddPermissionUUID().isBlank()){
            PermissionDto permission = adminService.getPermissionByUUID(UUID.fromString(model.getAddPermissionUUID()));
            roleDto.getPermissions().add(permission);
        }
        if(Objects.nonNull(model.getDeletePermissionUUID()) && !model.getDeletePermissionUUID().isBlank()){
            PermissionDto permission = adminService.getPermissionByUUID(UUID.fromString(model.getDeletePermissionUUID()));
            roleDto.getPermissions().remove(permission);
        }
        model.setAddPermissionUUID(null);
        model.setDeletePermissionUUID(null);
        if(roleDto.getIsActive() != model.getCurrentModel().getIsActive()){
            adminService.changeRoleActive(model.getCurrentModel());
        }
        roleDto = adminService.persistRole(roleDto);
        model.setRoleUUID(roleDto.getUuid().toString());
        model.setCurrentModel(roleDto);
        model.setCurrentPermissionUUIDs(model.getCurrentModel().getPermissions().stream()
                .map(BaseDto::getUuid).collect(Collectors.toList())
        );
        model.setPermissionList(pageDto.getContent());
        setPageInfo(model, pageDto);
        modelAndView.addObject("model", model);
        modelAndView.setViewName("role");
        return modelAndView;
    }

    private void setPageInfo(PageModel model, Page<?> page){
        model.setBackPage(page.getNumber());
        model.setNextPage(Math.min(page.getNumber() + 2, page.getTotalPages()));
        model.setCurrentPage(page.getNumber() + 1 );
        model.setCounterPage(model.getCurrentPage() + "/" + page.getTotalPages());
    }


}

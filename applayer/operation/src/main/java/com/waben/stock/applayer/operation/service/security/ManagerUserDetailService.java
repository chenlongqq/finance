package com.waben.stock.applayer.operation.service.security;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.waben.stock.applayer.operation.warpper.auth.AccountCredentials;
import com.waben.stock.applayer.operation.warpper.auth.RolePermissionAuthority;
import com.waben.stock.interfaces.dto.manage.RoleDto;
import com.waben.stock.interfaces.dto.manage.StaffDto;
import com.waben.stock.interfaces.pojo.Response;
import com.waben.stock.interfaces.service.manage.StaffInterface;

/**
 * @author Created by yuyidi on 2017/11/15.
 * @desc
 */
@Component
public class ManagerUserDetailService implements UserDetailsService {

    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    @Qualifier("staffInterface")
    private StaffInterface staffService;
//    @Autowired
//    private RoleService roleService;
//    @Autowired
//    private MenuService menuService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Response<StaffDto> response = staffService.fetchByUserName(username);
        if (response.getCode().equals("200")) {
            StaffDto staffDto = response.getResult();
            logger.info("用户信息获取成功:{}", staffDto.getUserName());
            //绑定角色权限
            List<RolePermissionAuthority> authority = new ArrayList<>();
            RoleDto roleDto = staffDto.getRoleDto();
            RolePermissionAuthority rolePermissionAuthority = new RolePermissionAuthority(roleDto.getCode());
            authority.add(rolePermissionAuthority);
            AccountCredentials accountCredentials = new AccountCredentials(staffDto.getUserName(), staffDto
                    .getPassword(), authority);
            //获取员工权限
//            Response<List<MenuDto>> menusResponse = menuService.menusByStaff(staffDto.getId());
//            if (menusResponse.getCode() != "200") {
//                throw new ServiceException(menusResponse.getCode());
//            }
//            List<MenuDto> menus = menusResponse.getResult();
//            accountCredentials.setMenus(menus);
            accountCredentials.setRole(roleDto.getId());
            accountCredentials.setOperator(true);
            accountCredentials.setSecurity(staffDto);
            return accountCredentials;
        }
        throw new UsernameNotFoundException("当前用户找不到");
    }

}

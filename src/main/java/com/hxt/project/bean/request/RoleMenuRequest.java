package com.hxt.project.bean.request;

import lombok.Data;

import java.util.List;

/**
 * ClassName: RoleMenuRequest
 * Package: com.hxt.project.bean
 * Description:
 *
 * @Author hxt
 * @Create 2024/9/11 17:31
 * @Version 1.0
 */
@Data
public class RoleMenuRequest {
    private Integer roleId;
    private List<Long> moduleIds;

}

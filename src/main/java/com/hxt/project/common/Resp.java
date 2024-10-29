package com.hxt.project.common;

import lombok.Builder;
import lombok.Data;

/**
 * ClassName: Resp
 * Package: com.hxt.comment
 * Description:
 *
 * @Author hxt
 * @Create 2024/7/24 18:29
 * @Version 1.0
 */
@Data
@Builder
public class Resp {
    private int code;
    private String msg;
    private Object data;
}


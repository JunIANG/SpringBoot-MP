package com.jay.pojo;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

@Data
@TableName("tb_user")  //映射表名
public class User {

    //@TableId(type = IdType.ASSIGN_ID)  //id生成策略
    private Long id;
    private String username;
    @TableField(value = "pwd", select = false)  //映射字段名，不参与sql查询
    private String password;
    @TableField(exist = false)  //自己加的字段
    private Integer online;
    //逻辑删除字段，标记当前记录是否被删除
    //@TableLogic(value = "0", delval = "1")
    private Integer deleted;

}

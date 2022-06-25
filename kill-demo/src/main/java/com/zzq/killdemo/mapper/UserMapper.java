package com.zzq.killdemo.mapper;

import com.zzq.killdemo.pojo.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;


/**
 * 用户Mapper
 *
 * @author 224
 * @date 2022/06/25
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

}

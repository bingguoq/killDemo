package com.zzq.killdemo.annoation;

import com.zzq.killdemo.utils.ValidatorUtil;
import com.zzq.killdemo.annoation.IsMobile;
import org.springframework.util.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 *  手机校验注解实现类
 *
 * @author 224
 * @date 2022/06/25
 */
public class IsMobileValidator implements ConstraintValidator<IsMobile,String> {

    private boolean required =false;

    @Override
    public void initialize(IsMobile constraintAnnotation) {
        required=constraintAnnotation.required();
    }

    /**
     * 判断手机号是否有效
     *
     * @param value                      价值
     * @param constraintValidatorContext 约束验证器上下文
     * @return boolean
     */
    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        if(required){//校验手机号
            return ValidatorUtil.isMobile(value);
        }else{
            if(StringUtils.isEmpty(value)){
                return true;
            }else{
                return ValidatorUtil.isMobile(value);
            }
        }
    }
}

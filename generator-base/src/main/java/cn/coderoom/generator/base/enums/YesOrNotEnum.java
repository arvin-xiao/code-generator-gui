package cn.coderoom.generator.base.enums;

import lombok.Getter;

/**
 *  是、否的枚举
 * @class YesOrNotEnum
 * @package cn.coderoom.generator.base.enums
 * @author lim
 * @email coderoom.cn@gmail.com
 * @date 2019/12/18 15:43
*/
@Getter
public enum YesOrNotEnum {

    Y(true, "是", 1),

    N(false, "否", 0);

    private Boolean flag;
    private String desc;
    private Integer code;

    YesOrNotEnum(Boolean flag, String desc, Integer code) {
        this.flag = flag;
        this.desc = desc;
        this.code = code;
    }

    public static String valueOf(Integer status) {
        if (status == null) {
            return "";
        } else {
            for (YesOrNotEnum s : YesOrNotEnum.values()) {
                if (s.getCode().equals(status)) {
                    return s.getDesc();
                }
            }
            return "";
        }
    }

}

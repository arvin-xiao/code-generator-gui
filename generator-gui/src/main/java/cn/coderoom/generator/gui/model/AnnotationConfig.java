package cn.coderoom.generator.gui.model;

import lombok.Data;

/**
 * @package：cn.coderoom.mybatis.generator.model
 * @description:
 * @author: Leesire
 * @email:coderoom.cn@gmail.com
 * @createtime: 2019/12/13
 */
@Data
public class AnnotationConfig {

    private String columnName;
    private boolean NotNull;
    private boolean Max;
    private boolean Min;
    private boolean Length;
    private boolean Pattern;

}

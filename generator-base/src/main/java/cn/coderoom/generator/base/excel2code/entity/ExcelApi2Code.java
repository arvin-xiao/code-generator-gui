package cn.coderoom.generator.base.excel2code.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;

/**
 * 根据Excel的api文档生成代码
 * @class ExcelApi2Code
 * @package cn.coderoom.generator.base.excel2code.entity
 * @author lim
 * @email coderoom.cn@gmail.com
 * @date 2020/1/10 11:36 
*/ 
public class ExcelApi2Code {

    /**
     * 字段类型
    */
    @Excel(name = "字段类型")
    private String fieldType;

    /**
     * 接口字段
    */
    @Excel(name = "接口字段")
    private String apiField;

    /**
     * 本地字段
    */
    @Excel(name = "本地字段")
    private String localFild;

    /**
     *  字段描述
    */
    @Excel(name = "字段描述")
    private String description;

    public String getFieldType() {
        return fieldType;
    }

    public void setFieldType(String fieldType) {
        this.fieldType = fieldType;
    }

    public String getApiField() {
        return apiField;
    }

    public void setApiField(String apiField) {
        this.apiField = apiField;
    }

    public String getLocalFild() {
        return localFild;
    }

    public void setLocalFild(String localFild) {
        this.localFild = localFild;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

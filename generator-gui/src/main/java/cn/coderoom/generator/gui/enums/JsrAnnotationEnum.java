package cn.coderoom.generator.gui.enums;


import cn.coderoom.generator.gui.constants.AnnotationConstant;

/**
 * 
 * @class JsrAnnotationEnum
 * @package cn.coderoom.mybatis.generator.enums
 * @author lim
 * @email coderoom.cn@gmail.com
 * @date 2019/12/16 9:47 
*/ 
public enum JsrAnnotationEnum {

    NOTNULL("NotNull","javax.validation.constraints.NotNull","{message:\"message不能为空\"}"),
    LENGTH("Length","org.hibernate.validator.constraints.Length","{max:\"length\",message:\"客户编号最长length位\"}"),
    PATTERN("Pattern","javax.validation.constraints.Pattern",""),
    MIN("Min","javax.validation.constraints.Min",""),
    MAX("Max","javax.validation.constraints.Max","");

    private String name;
    private String packageUrl;
    private String attributejson;

    JsrAnnotationEnum(String name,String packageUrl,String attributejson) {
        this.name = name;
        this.packageUrl = packageUrl;
        this.attributejson = attributejson;
    }

    public static String getPackageUrl(String name){
        String packageUrl = null;
        switch (name) {
            case AnnotationConstant.LENGTH:
                packageUrl = JsrAnnotationEnum.LENGTH.packageUrl;
                break;
            case AnnotationConstant.MAX:
                packageUrl = JsrAnnotationEnum.MIN.packageUrl;
                break;
            case AnnotationConstant.MIN:
                packageUrl = JsrAnnotationEnum.MIN.packageUrl;
                break;
            case AnnotationConstant.PATTERN:
                packageUrl = JsrAnnotationEnum.PATTERN.packageUrl;
                break;
            case AnnotationConstant.NOTNULL:
                packageUrl = JsrAnnotationEnum.NOTNULL.packageUrl;
                break;
            default:
                break;
        }
        return packageUrl;
    }

    public static String getAttributejson(String name){
        String attributejson = null;
        switch (name) {
            case AnnotationConstant.LENGTH:
                attributejson = JsrAnnotationEnum.LENGTH.attributejson;
                break;
            case AnnotationConstant.MAX:
                attributejson = JsrAnnotationEnum.MIN.attributejson;
                break;
            case AnnotationConstant.MIN:
                attributejson = JsrAnnotationEnum.MIN.attributejson;
                break;
            case AnnotationConstant.PATTERN:
                attributejson = JsrAnnotationEnum.PATTERN.attributejson;
                break;
            case AnnotationConstant.NOTNULL:
                attributejson = JsrAnnotationEnum.NOTNULL.attributejson;
                break;
            default:
                break;
        }
        return  attributejson;
    }
    @Override
    public String toString() {
        return "JsrAnnotationEnum{" +
                "name=" + name +
                "attributejson=" + attributejson +
                ", packageUrl='" + packageUrl + '\'' +
                '}';
    }


}

package cn.coderoom.generator.gui.util;

import cn.coderoom.generator.gui.constants.AnnotationAttributeConstant;
import com.alibaba.fastjson.JSONObject;
import org.mybatis.generator.api.IntrospectedColumn;

/**
 * @package：cn.coderoom.mybatis.generator.util
 * @description:
 * @author: Leesire
 * @email:coderoom.cn@gmail.com
 * @createtime: 2019/12/16
 */
public class MybatisGeneratorUtil {

    public static String getAttributeString(JSONObject jsonObject ,IntrospectedColumn introspectedColumn){

        StringBuffer sb = new StringBuffer();
        sb.append("(");

        for(String str:jsonObject.keySet()) {
            sb.append(str + " = ");
            String value = (String)jsonObject.get(str);
            if (value.contains(AnnotationAttributeConstant.LENGTH)) {
                sb.append("\"" + value.replace(AnnotationAttributeConstant.LENGTH , Integer.toString(introspectedColumn.getLength()) ) + "\",");
            }else if(value.contains(AnnotationAttributeConstant.MESSAGE)) {
                sb.append("\"" + value.replace(AnnotationAttributeConstant.MESSAGE, introspectedColumn.getRemarks() ) + "\",");
            }else{
                sb.append("\"" + value + "\"");
            }
        }

        sb.append(")");

        return sb.toString().replace(",)",")");

    }

}

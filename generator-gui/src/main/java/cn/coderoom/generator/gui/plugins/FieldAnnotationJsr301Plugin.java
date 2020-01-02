package cn.coderoom.generator.gui.plugins;

import cn.coderoom.generator.base.utils.StringUtils;
import cn.coderoom.generator.gui.enums.JsrAnnotationEnum;
import cn.coderoom.generator.gui.util.MybatisGeneratorUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.Field;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.TopLevelClass;

import java.util.*;

/** 
 *  model 增加注解
 * @class FieldAnnotationJsr301Plugin 
 * @package cn.coderoom.mybatis.generator.plugins
 * @author lim
 * @email coderoom.cn@gmail.com
 * @date 2019/12/13 14:45 
*/ 
public class FieldAnnotationJsr301Plugin extends PluginAdapter {

    private FullyQualifiedJavaType annotationRepository;

    private HashMap<String, Object> map;

    public FieldAnnotationJsr301Plugin () {

        annotationRepository = new FullyQualifiedJavaType("org.springframework.stereotype.Repository"); //$NON-NLS-1$

    }

    @Override
    public boolean validate(List<String> warnings) {
        return true;
    }

    @Override
    public boolean modelFieldGenerated(Field  field, TopLevelClass topLevelClass, IntrospectedColumn introspectedColumn,
                                       IntrospectedTable introspectedTable, ModelClassType modelClassType){

        String annotationStr = context.getProperty("annotationConfig");
        List<Map> annotationConfigs = (List<Map>) JSONArray.parseArray(annotationStr,Map.class);

        Set<FullyQualifiedJavaType> importedTypes = new HashSet<>();
        FullyQualifiedJavaType fullyQualifiedJavaType = null;

        //TODO 优化
        for (Map annotationConfig:
                annotationConfigs) {
            String camelStr = StringUtils.dbStringToCamelStyle(annotationConfig.get("ColumnName").toString());
            if(StringUtils.upperFirstLatter(field.getName()).equals(camelStr)){

                Iterator<Map.Entry<String, Object>> entries = annotationConfig.entrySet().iterator();
                while(entries.hasNext()){
                    Map.Entry<String, Object> entry = entries.next();
                    String key = entry.getKey();
                    if(!"ColumnName".equals(key)){
                        System.out.println(key+":"+entry.getValue().toString());
                        Boolean value = Boolean.valueOf(entry.getValue().toString());

                        if(value){

                            String jsonStr = JsrAnnotationEnum.getAttributejson(key);
                            JSONObject object = JSONObject.parseObject(jsonStr);
                            if(object!=null){

                                String att = MybatisGeneratorUtil.getAttributeString(object,introspectedColumn);
                                field.addAnnotation("@"+key + att);
                            }

                            String packageUrl = JsrAnnotationEnum.getPackageUrl(key);
                            fullyQualifiedJavaType = new FullyQualifiedJavaType(packageUrl);
                            importedTypes.add(fullyQualifiedJavaType);
                        }

                    }

                }

            }

        }

        topLevelClass.addImportedTypes(importedTypes);

        return true;
    }
}

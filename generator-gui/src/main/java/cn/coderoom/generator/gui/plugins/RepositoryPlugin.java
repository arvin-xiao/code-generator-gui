package cn.coderoom.generator.gui.plugins;

import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.Interface;
import org.mybatis.generator.api.dom.java.TopLevelClass;

import java.util.List;

/** 
 * 
 * @class RepositoryPlugin 
 * @package cn.coderoom.mybatis.generator.plugins
 * @author lim
 * @email coderoom.cn@gmail.com
 * @date 2019/12/13 14:22 
*/ 
public class RepositoryPlugin extends PluginAdapter {

    private FullyQualifiedJavaType annotationRepository;
    private String annotation = "@Repository";

    public RepositoryPlugin () {
        annotationRepository = new FullyQualifiedJavaType("org.springframework.stereotype.Repository"); //$NON-NLS-1$
    }

    @Override
    public boolean validate(List<String> list) {
        return true;
    }

    /**
     *  client: Dao 文件
     * @param interfaze
     * @param topLevelClass
     * @param introspectedTable
     * @author lim
     * @date 2019/12/13 14:42
     * @return boolean
    */
    @Override
    public boolean clientGenerated(Interface interfaze, TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        interfaze.addImportedType(annotationRepository);
        interfaze.addAnnotation(annotation);
        return true;
    }
}

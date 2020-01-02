package cn.coderoom.generator.frontend.engine.base;

import cn.coderoom.generator.base.utils.ToolUtil;
import cn.coderoom.generator.frontend.engine.config.FrontendGeneratorContextConfig;
import cn.hutool.core.bean.BeanUtil;
import com.sun.javafx.PlatformUtil;
import org.beetl.core.Configuration;
import org.beetl.core.GroupTemplate;
import org.beetl.core.Template;
import org.beetl.core.resource.ClasspathResourceLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * 模板生成 引擎 (设计模式——模板方法模式)
 * @class GunsTemplateEngine
 * @package cn.coderoom.generator.frontend.engine.base
 * @author lim
 * @email coderoom.cn@gmail.com
 * @date 2019/12/18 15:23
*/
public abstract class CodeTemplateEngine extends AbstractTemplateEngine {

    private static final Logger logger = LoggerFactory.getLogger(CodeTemplateEngine.class);

    private GroupTemplate defaultGroupTemplate;

    public CodeTemplateEngine() {
        initBeetlEngine();
    }

    /** 
     * 
     * @param 	
     * @author lim
     * @date 2019/12/19 14:21
     * @return void
    */ 
    protected void initBeetlEngine() {
        Properties properties = new Properties();
        properties.put("RESOURCE.root", "");
        properties.put("DELIMITER_STATEMENT_START", "<%");
        properties.put("DELIMITER_STATEMENT_END", "%>");
        properties.put("HTML_TAG_FLAG", "##");
        Configuration cfg = null;
        try {
            cfg = new Configuration(properties);
        } catch (IOException e) {
            e.printStackTrace();
        }
        ClasspathResourceLoader resourceLoader = new ClasspathResourceLoader();
        defaultGroupTemplate = new GroupTemplate(resourceLoader, cfg);
        defaultGroupTemplate.registerFunctionPackage("tool", new ToolUtil());

    }

    /**
     *  Beetl 模板引擎参数
     * @param template
     * @author lim
     * @date 2019/12/23 15:00
     * @return void
    */
    protected void configTemplate(Template template) {
        template.binding("controller", super.controllerConfig);
        template.binding("context", super.contextConfig);
        template.binding("dao", super.daoConfig);
        template.binding("service", super.serviceConfig);
        template.binding("sqls", super.sqlConfig);
        template.binding("table", super.tableInfo);
    }

    /**
     *  根据模板生成代码
     * @param template 模板文件地址
     * @param filePath  生成文件路径
     * @author lim
     * @date 2019/12/19 10:12
     * @return void
    */
    protected void generateFile(String template, String filePath) {
        logger.debug("template文件路径 :{}", template);
        Template pageTemplate = defaultGroupTemplate.getTemplate(template);
        configTemplate(pageTemplate);
        if (PlatformUtil.isWindows()) {
            filePath = filePath.replaceAll("/+|\\\\+", "\\\\");
        } else {
            filePath = filePath.replaceAll("/+|\\\\+", "/");
        }
        logger.debug("文件路径 :{}", filePath);
        File file = new File(filePath);
        File parentFile = file.getParentFile();
        if (!parentFile.exists()) {
            parentFile.mkdirs();
        }
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(file);
            pageTemplate.renderTo(fileOutputStream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                fileOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public void start() {
        //配置之间的相互依赖
        super.initConfig();

        //生成模板
        if (super.contextConfig.getControllerSwitch()) {
            generateController();
        }
        if (super.contextConfig.getIndexPageSwitch()) {
            generatePageHtml();
        }
        if (super.contextConfig.getAddPageSwitch()) {
            generatePageAddHtml();
        }
        if (super.contextConfig.getEditPageSwitch()) {
            generatePageEditHtml();
        }
        if (super.contextConfig.getJsSwitch()) {
            generatePageJs();
        }
        if (super.contextConfig.getInfoJsSwitch()) {
            generatePageInfoJs();
        }
        if (super.contextConfig.getSqlSwitch()) {
            generateSqls();
        }
    }

    public void generatorFrontend(FrontendGeneratorContextConfig frontendGeneratorContextConfig) {
        //配置之间的相互依赖
        super.initConfig();
        //contextConfig.setBizEnName(bizEnName);
        BeanUtil.copyProperties(frontendGeneratorContextConfig,config);

        generatePageHtml();
        generatePageAddHtml();
        generatePageEditHtml();
        generatePageJs();
        generatePageInfoJs();

    }

    protected abstract void generatePageEditHtml();

    protected abstract void generatePageAddHtml();

    protected abstract void generatePageInfoJs();

    protected abstract void generatePageJs();

    protected abstract void generatePageHtml();

    protected abstract void generateController();

    protected abstract void generateSqls();

}

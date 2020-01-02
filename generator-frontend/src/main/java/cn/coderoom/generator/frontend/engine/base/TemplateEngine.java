package cn.coderoom.generator.frontend.engine.base;

import cn.coderoom.generator.base.utils.ToolUtil;
import cn.coderoom.generator.frontend.engine.config.*;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import org.beetl.core.Configuration;
import org.beetl.core.GroupTemplate;
import org.beetl.core.Template;
import org.beetl.core.resource.ClasspathResourceLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Properties;

/**
 * @package：cn.coderoom.generator.frontend.engine.base
 * @description:
 * @author: Leesire
 * @email:coderoom.cn@gmail.com
 * @createtime: 2019/12/23
 */
public class TemplateEngine {

    private static final Logger logger = LoggerFactory.getLogger(TemplateEngine.class);
    /**
     * 全局配置
    */
    protected FrontendGeneratorContextConfig config;
    /**
     * 全局配置
    */
    protected ContextConfig contextConfig;
    /**
     * 表的信息
    */
    protected TableInfo tableInfo;
    protected GroupTemplate defaultGroupTemplate;

    public TemplateEngine() {
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

    public void initConfig() {
        if (this.config == null) {
            this.config = new FrontendGeneratorContextConfig();
        }
        if (this.contextConfig == null) {
            this.contextConfig = new ContextConfig();
        }
        this.contextConfig.init();

    }

    /**
     *  Beetl 模板引擎参数
     * @param template
     * @author lim
     * @date 2019/12/23 15:00
     * @return void
     */
    protected void bindData2Template(Template template) {

        template.binding("context", this.contextConfig);
        template.binding("table", this.tableInfo);

    }

    public FrontendGeneratorContextConfig getConfig() {
        return config;
    }

    public void setConfig(FrontendGeneratorContextConfig config) {
        this.config = config;
    }

    public ContextConfig getContextConfig() {
        return contextConfig;
    }

    public void setContextConfig(ContextConfig contextConfig) {
        this.contextConfig = contextConfig;
    }

    public TableInfo getTableInfo() {
        return tableInfo;
    }

    public void setTableInfo(TableInfo tableInfo) {
        this.tableInfo = tableInfo;
    }
}

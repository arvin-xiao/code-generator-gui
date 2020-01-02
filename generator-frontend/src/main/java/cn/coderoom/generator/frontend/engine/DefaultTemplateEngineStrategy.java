package cn.coderoom.generator.frontend.engine;

import cn.coderoom.generator.frontend.engine.base.TemplateEngine;
import cn.coderoom.generator.frontend.engine.base.TemplateEngineStrategy;
import cn.coderoom.generator.frontend.engine.config.FrontendGeneratorContextConfig;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.sun.javafx.PlatformUtil;
import org.beetl.core.Template;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/** 
 * 
 * @class DefaultTemplateEngineStrategy
 * @package cn.coderoom.generator.frontend.engine
 * @author lim
 * @email coderoom.cn@gmail.com
 * @date 2019/12/23 16:29 
*/ 
public class DefaultTemplateEngineStrategy extends TemplateEngine implements TemplateEngineStrategy{

    private static final Logger logger = LoggerFactory.getLogger(DefaultTemplateEngineStrategy.class);
    private static final String[] files = {"\\{0}.html.btl","\\{0}.html.btl","\\{0}Add.html.btl","\\{0}Edit.html.btl","\\{0}Info.js.btl"};


    public DefaultTemplateEngineStrategy(TableInfo tableInfo, FrontendGeneratorContextConfig config) {
        super.tableInfo =tableInfo;
        super.config = config;
    }

    /**
     * 生成文件
     *
     * @param template
     * @param filePath
     * @return void
     * @author lim
     * @date 2019/12/23 15:40
     */
    @Override
    public void generateFile(String template, String filePath) {
        logger.debug("template文件路径 :{}", template);
        Template pageTemplate = super.defaultGroupTemplate.getTemplate(template);
        bindData2Template(pageTemplate);
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

    @Override
    public void execute() {
        super.initConfig();
        for (String file:
                files) {

            String path = StrUtil.indexedFormat(super.getContextConfig().getProjectPath()+file,super.getConfig().getBizEnName());
            path = path.replace(".btl","");
            generateFile(super.getContextConfig().getTemplatePrefixPath()+file , path);
        }

    }


}

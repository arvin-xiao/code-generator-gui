package cn.coderoom.generator.frontend.engine;

import cn.coderoom.generator.base.utils.FileUtil;
import cn.coderoom.generator.base.utils.ToolUtil;
import cn.coderoom.generator.frontend.engine.base.TemplateEngine;
import cn.coderoom.generator.frontend.engine.base.TemplateEngineStrategy;
import cn.coderoom.generator.frontend.engine.config.FrontendGeneratorContextConfig;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.sun.javafx.PlatformUtil;
import org.beetl.core.Configuration;
import org.beetl.core.GroupTemplate;
import org.beetl.core.Template;
import org.beetl.core.resource.FileResourceLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

/**
 * @package：cn.coderoom.generator.frontend.engine
 * @description:
 * @author: Leesire
 * @email:coderoom.cn@gmail.com
 * @createtime: 2019/12/23
 */
public class CustomerTemplateEngineStrategy extends TemplateEngine implements TemplateEngineStrategy{

    private static final Logger logger = LoggerFactory.getLogger(CustomerTemplateEngineStrategy.class);
    private GroupTemplate customGroupTemplate;

    public CustomerTemplateEngineStrategy(TableInfo tableInfo, FrontendGeneratorContextConfig config) {
        super.tableInfo =tableInfo;
        super.config = config;
    }
    /**
     * 生成文件
     *
     * @param template 模板文件路径
     * @param filePath 生成文件路径
     * @return void
     * @author lim
     * @date 2019/12/23 15:40
     */
    @Override
    public void generateFile(String template, String filePath) {
        String root =  config.getCustomTemplatePath()+File.separator;
        //这是安装eclipse软件的位置，D盘下D:\software\eclipse\template
        FileResourceLoader resourceLoader = new FileResourceLoader(root,"utf-8");
        Properties properties = new Properties();
        properties.put("RESOURCE.root", "");
        properties.put("DELIMITER_STATEMENT_START", "<%");
        properties.put("DELIMITER_STATEMENT_END", "%>");
        properties.put("HTML_TAG_FLAG", "##");
        Configuration cfg = null;
        try {
            cfg = new Configuration(properties); //Configuration.defaultConfiguration();
            customGroupTemplate= new GroupTemplate(resourceLoader, cfg);
            customGroupTemplate.registerFunctionPackage("tool", new ToolUtil());
        }catch (IOException e){
            e.printStackTrace();
        }

        logger.debug("template文件路径 :{}", root+template);
        Template pageTemplate = customGroupTemplate.getTemplate(template);
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
        List<File> files = FileUtil.getFiles(config.getCustomTemplatePath());

        for (File file:
             files) {
            /**
             * 修改文件名
            */
            String path = StrUtil.indexedFormat(file.toPath().toString(), super.getConfig().getBizEnName());
            //String path = file.toPath().toString().replace("{}",super.getConfig().getBizEnName());
            String templateFileName = file.getPath().substring(file.getPath().lastIndexOf("\\")+1);
            String fileName = path.substring(file.getPath().lastIndexOf("\\")+1);
            fileName = fileName.replace(".btl","");
            generateFile(templateFileName, config.getFilePath()+"\\"+fileName );
        }

    }


}

package cn.coderoom.generator.frontend.engine.config;

import cn.coderoom.generator.base.config.AbstractGeneratorContextConfig;

/**
 * 
 * @class FrontendGeneratorContextConfig
 * @package cn.coderoom.generator.base.config
 * @author lim
 * @email coderoom.cn@gmail.com
 * @date 2019/12/20 10:37 
*/ 
public class FrontendGeneratorContextConfig extends AbstractGeneratorContextConfig {

    /**
     * 自定义模板路径
     */
    private String customTemplatePath = "/template/base";

    /**
     * 文件输出路径
     */
    private String filePath= "D:\\generator\\default";


    public String getCustomTemplatePath() {
        return "".equals(customTemplatePath) ? "/template/base" : customTemplatePath;
    }

    public void setCustomTemplatePath(String customTemplatePath) {
        this.customTemplatePath = customTemplatePath;
    }

    public String getFilePath() {
        return "".equals(filePath)? "D:\\generator\\default" : filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
}

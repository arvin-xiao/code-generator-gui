package cn.coderoom.generator.frontend.engine.config;

/**
 *  页面 模板生成的配置
 * @class PageConfig
 * @package cn.coderoom.generator.frontend.engine.config
 * @author lim
 * @email coderoom.cn@gmail.com
 * @date 2019/12/20 14:33
*/
public class PageConfig {

    private FrontendGeneratorContextConfig config;

    private String pagePathTemplate;
    private String pageAddPathTemplate;
    private String pageEditPathTemplate;
    private String pageJsPathTemplate;
    private String pageInfoJsPathTemplate;

    public void init() {
        pagePathTemplate = config.getCustomTemplatePath()+"/{}/{}.html";
        pageAddPathTemplate = config.getCustomTemplatePath() + "/{}/{}_add.html";
        pageEditPathTemplate = config.getCustomTemplatePath() + "/{}/{}_edit.html";
        pageJsPathTemplate = config.getCustomTemplatePath() + "/{}/{}.js";
        pageInfoJsPathTemplate = config.getCustomTemplatePath() + "/{}/{}_info.js";
    }

    public String getPagePathTemplate() {
        return pagePathTemplate;
    }

    public void setPagePathTemplate(String pagePathTemplate) {
        this.pagePathTemplate = pagePathTemplate;
    }

    public String getPageJsPathTemplate() {
        return pageJsPathTemplate;
    }

    public void setPageJsPathTemplate(String pageJsPathTemplate) {
        this.pageJsPathTemplate = pageJsPathTemplate;
    }

    public String getPageAddPathTemplate() {
        return pageAddPathTemplate;
    }

    public void setPageAddPathTemplate(String pageAddPathTemplate) {
        this.pageAddPathTemplate = pageAddPathTemplate;
    }

    public String getPageEditPathTemplate() {
        return pageEditPathTemplate;
    }

    public void setPageEditPathTemplate(String pageEditPathTemplate) {
        this.pageEditPathTemplate = pageEditPathTemplate;
    }

    public String getPageInfoJsPathTemplate() {
        return pageInfoJsPathTemplate;
    }

    public void setPageInfoJsPathTemplate(String pageInfoJsPathTemplate) {
        this.pageInfoJsPathTemplate = pageInfoJsPathTemplate;
    }

    public FrontendGeneratorContextConfig getConfig() {
        return config;
    }

    public void setConfig(FrontendGeneratorContextConfig config) {
        this.config = config;
    }

}

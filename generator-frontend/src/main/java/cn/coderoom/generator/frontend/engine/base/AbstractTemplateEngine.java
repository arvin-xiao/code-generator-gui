package cn.coderoom.generator.frontend.engine.base;

import cn.coderoom.generator.frontend.engine.config.*;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;

/** 
 * 模板生成父类
 * @class AbstractTemplateEngine
 * @package cn.coderoom.generator.frontend.engine.base
 * @author lim
 * @email coderoom.cn@gmail.com
 * @date 2019/12/19 10:17 
*/ 
public class AbstractTemplateEngine {

    protected FrontendGeneratorContextConfig config;                //全局配置
    protected ContextConfig contextConfig;                //全局配置
    protected ControllerConfig controllerConfig;          //控制器的配置
    protected PageConfig pageConfig;                      //页面的控制器
    protected DaoConfig daoConfig;                        //Dao配置
    protected ServiceConfig serviceConfig;                //Service配置
    protected SqlConfig sqlConfig;                        //sql配置
    protected TableInfo tableInfo;                        //表的信息

    public void initConfig() {
        if (this.config == null) {
            this.config = new FrontendGeneratorContextConfig();
        }
        if (this.contextConfig == null) {
            this.contextConfig = new ContextConfig();
        }
        if (this.controllerConfig == null) {
            this.controllerConfig = new ControllerConfig();
        }
        if (this.pageConfig == null) {
            this.pageConfig = new PageConfig();
        }
        if (this.daoConfig == null) {
            this.daoConfig = new DaoConfig();
        }
        if (this.serviceConfig == null) {
            this.serviceConfig = new ServiceConfig();
        }
        if (this.sqlConfig == null) {
            this.sqlConfig = new SqlConfig();
        }
        this.contextConfig.init();

        this.controllerConfig.setContextConfig(this.contextConfig);
        this.controllerConfig.init();

        this.serviceConfig.setContextConfig(this.contextConfig);
        this.serviceConfig.init();

        this.daoConfig.setContextConfig(this.contextConfig);
        this.daoConfig.init();

        this.pageConfig.setConfig(config);
        this.pageConfig.init();

        this.sqlConfig.setContextConfig(this.contextConfig);
        this.sqlConfig.init();
    }

    public FrontendGeneratorContextConfig getConfig() {
        return config;
    }

    public void setConfig(FrontendGeneratorContextConfig config) {
        this.config = config;
    }

    public PageConfig getPageConfig() {
        return pageConfig;
    }

    public void setPageConfig(PageConfig pageConfig) {
        this.pageConfig = pageConfig;
    }

    public ContextConfig getContextConfig() {
        return contextConfig;
    }

    public void setContextConfig(ContextConfig contextConfig) {
        this.contextConfig = contextConfig;
    }

    public ControllerConfig getControllerConfig() {
        return controllerConfig;
    }

    public void setControllerConfig(ControllerConfig controllerConfig) {
        this.controllerConfig = controllerConfig;
    }

    public DaoConfig getDaoConfig() {
        return daoConfig;
    }

    public void setDaoConfig(DaoConfig daoConfig) {
        this.daoConfig = daoConfig;
    }

    public ServiceConfig getServiceConfig() {
        return serviceConfig;
    }

    public void setServiceConfig(ServiceConfig serviceConfig) {
        this.serviceConfig = serviceConfig;
    }

    public SqlConfig getSqlConfig() {
        return sqlConfig;
    }

    public void setSqlConfig(SqlConfig sqlConfig) {
        this.sqlConfig = sqlConfig;
    }

    public TableInfo getTableInfo() {
        return tableInfo;
    }

    public void setTableInfo(TableInfo tableInfo) {
        this.tableInfo = tableInfo;
    }
}


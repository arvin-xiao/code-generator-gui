package cn.coderoom.generator.frontend.executor;

import cn.coderoom.generator.base.db.entity.DatabaseConfig;
import cn.coderoom.generator.base.db.entity.DbTypeEnum;
import cn.coderoom.generator.base.db.utils.DbUtil;
import cn.coderoom.generator.base.utils.StringUtils;
import cn.coderoom.generator.frontend.engine.base.TemplateEngineStrategyContext;
import cn.coderoom.generator.frontend.engine.config.FrontendGeneratorContextConfig;
import cn.hutool.core.io.FileUtil;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.DbType;

import java.io.File;
import java.util.List;

/**
 * @package：cn.coderoom.generator.frontend.executor
 * @description:
 * @author: Leesire
 * @email:coderoom.cn@gmail.com
 * @createtime: 2019/12/19
 */
public class CodeGenerator {

    private DatabaseConfig selectedDatabaseConfig;
    private FrontendGeneratorContextConfig config;

    /**
     * mybatis-plus代码生成器配置
     */
    GlobalConfig globalConfig = new GlobalConfig();
    DataSourceConfig dataSourceConfig = new DataSourceConfig();
    StrategyConfig strategyConfig = new StrategyConfig();
    PackageConfig packageConfig = new PackageConfig();
    TableInfo tableInfo = null;

    public void setSelectedDatabaseConfig(DatabaseConfig selectedDatabaseConfig) {
        this.selectedDatabaseConfig = selectedDatabaseConfig;
    }

    public void setConfig(FrontendGeneratorContextConfig config) {
        this.config = config;
    }

    public void init() {

        //packageConfig.setService(generatorConfig.getDaoPackage() + ".modular." + generatorConfig.getDomainObjectName() + ".service");
        //packageConfig.setServiceImpl(generatorConfig.getDaoPackage() + ".modular." + generatorConfig.getDomainObjectName() + ".service.impl");

        dataSourceConfig.setDriverName(DbTypeEnum.valueOf(selectedDatabaseConfig.getDbType().toUpperCase()).getDriverClass());
        dataSourceConfig.setDbType(DbType.valueOf(selectedDatabaseConfig.getDbType().toUpperCase()));
        dataSourceConfig.setUsername(selectedDatabaseConfig.getUsername());
        dataSourceConfig.setPassword(selectedDatabaseConfig.getPassword());
        dataSourceConfig.setSchemaname(selectedDatabaseConfig.getSchema());
        try {

            dataSourceConfig.setUrl(DbUtil.getConnectionUrlWithSchema(selectedDatabaseConfig));
        } catch (ClassNotFoundException e){
            e.printStackTrace();
        }
    }

    /**
     * 删除不必要的代码
     * @param
     * @author lim
     * @date 2019/12/19 15:41
     * @return void
    */
    public void destory() {
        String outputDir = globalConfig.getOutputDir() + "/TTT";
        FileUtil.del(new File(outputDir));
    }

    public void generationFrontendCode(String tableName) {
        init();
        AutoGenerator autoGenerator = new AutoGenerator();
        autoGenerator.setGlobalConfig(globalConfig);
        autoGenerator.setDataSource(dataSourceConfig);
        autoGenerator.setStrategy(strategyConfig);
        autoGenerator.setPackageInfo(packageConfig);
        autoGenerator.execute();
        destory();

        //获取table信息,用于guns代码生成
        List<TableInfo> tableInfoList = autoGenerator.getConfig().getTableInfoList();
        for (TableInfo tableInfo:
        tableInfoList) {
            if (tableName.equals(tableInfo.getName())) {
                this.tableInfo = tableInfo;
            }
        }

        /*CodeTemplateEngine codeTemplateEngine = new SimpleTemplateEngine();
        codeTemplateEngine.setTableInfo(tableInfo);
        config.setBizChName(tableInfo.getComment());
        config.setBizEnName(StringUtils.dbStringToCamelStyle(tableInfo.getName()));
        codeTemplateEngine.generatorFrontend(config);*/

        TemplateEngineStrategyContext templateEngineStrategyContext = new TemplateEngineStrategyContext();
        config.setBizChName(tableInfo.getComment());
        config.setBizEnName(StringUtils.dbStringToCamelStyle(tableInfo.getName()));
        templateEngineStrategyContext.execute(tableInfo,config);

    }

}

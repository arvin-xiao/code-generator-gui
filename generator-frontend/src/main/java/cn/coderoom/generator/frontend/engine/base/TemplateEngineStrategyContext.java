package cn.coderoom.generator.frontend.engine.base;

import cn.coderoom.generator.frontend.engine.CustomerTemplateEngineStrategy;
import cn.coderoom.generator.frontend.engine.DefaultTemplateEngineStrategy;
import cn.coderoom.generator.frontend.engine.config.FrontendGeneratorContextConfig;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;

/**
 * @package：cn.coderoom.generator.frontend.engine.base
 * @description:
 * @author: Leesire
 * @email:coderoom.cn@gmail.com
 * @createtime: 2019/12/23
 */
public class TemplateEngineStrategyContext {

    public void execute(TableInfo tableInfo,FrontendGeneratorContextConfig config){

        if(StrUtil.isEmpty(config.getCustomTemplatePath()) || "/template/base".equals(config.getCustomTemplatePath())){
            TemplateEngineStrategy templateEngineStrategy = new DefaultTemplateEngineStrategy(tableInfo,config);
            templateEngineStrategy.execute();
        }else{
            TemplateEngineStrategy customerTemplateEngineStrategy = new CustomerTemplateEngineStrategy(tableInfo,config);
            customerTemplateEngineStrategy.execute();
        }

    }

}

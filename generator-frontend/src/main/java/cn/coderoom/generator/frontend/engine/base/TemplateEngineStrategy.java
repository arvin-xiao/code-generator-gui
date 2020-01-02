package cn.coderoom.generator.frontend.engine.base;

/** 
 *  模板引擎策略
 * @class TemplateEngineStrategy
 * @package cn.coderoom.generator.frontend.engine.base
 * @author lim
 * @email coderoom.cn@gmail.com
 * @date 2019/12/23 15:33 
*/ 
public interface TemplateEngineStrategy {

    /**
     * 生成文件
     * @param template
     * @param filePath
     * @author lim
     * @date 2019/12/23 15:40
     * @return void
    */
    public void generateFile(String template, String filePath);

    /** 
     * 执行遍历所有模板生成文件
     * @param
     * @author lim
     * @date 2019/12/23 15:51 
     * @return void
    */ 
    public void execute();
}

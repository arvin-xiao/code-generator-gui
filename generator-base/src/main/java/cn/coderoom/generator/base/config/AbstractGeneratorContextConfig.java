package cn.coderoom.generator.base.config;

/** 
 * 
 * @class AbstractGeneratorContextConfig
 * @package cn.coderoom.generator.base.config
 * @author lim
 * @email coderoom.cn@gmail.com
 * @date 2019/12/20 14:20
*/ 
public class AbstractGeneratorContextConfig {

    /**
     * 业务名称
    */
    private String bizChName;
    /**
     * 业务英文名称
     */
    private String bizEnName;
    /**
     * 业务英文名称(大写)
     */
    private String bizEnBigName;

    public String getBizChName() {
        return bizChName;
    }

    public void setBizChName(String bizChName) {
        this.bizChName = bizChName;
    }

    public String getBizEnName() {
        return bizEnName;
    }

    public void setBizEnName(String bizEnName) {
        this.bizEnName = bizEnName;
    }

    public String getBizEnBigName() {
        return bizEnBigName;
    }

    public void setBizEnBigName(String bizEnBigName) {
        this.bizEnBigName = bizEnBigName;
    }
}

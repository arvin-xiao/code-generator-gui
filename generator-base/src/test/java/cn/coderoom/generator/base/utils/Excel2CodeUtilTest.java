package cn.coderoom.generator.base.utils;

import org.junit.Test;

/** 
 * 根据apiExcel文档生成代码
 * @class Excel2CodeUtilTest
 * @package cn.coderoom.generator.base.utils
 * @author lim
 * @email coderoom.cn@gmail.com
 * @date 2020/1/10 11:54 
*/ 
public class Excel2CodeUtilTest {

    @Test
    public void generatorCode() throws Exception {

        Excel2CodeUtil.generatorCode("C:\\Users\\zsn\\Desktop\\2code.xlsx","taxArrivedIncomeInvoiceManage");

    }

    @Test
    public void generatorModelCode() throws Exception {

        Excel2CodeUtil.generatorModelCode("C:\\Users\\zsn\\Desktop\\2code.xlsx","taxArrivedIncomeInvoiceManage");

    }

    @Test
    public void generatorZjmiecOACode() throws Exception {

        Excel2CodeUtil.generatorZjmiecOACode("C:\\Users\\zsn\\Desktop\\2code.xlsx","taxArrivedIncomeInvoiceManage");

    }

}
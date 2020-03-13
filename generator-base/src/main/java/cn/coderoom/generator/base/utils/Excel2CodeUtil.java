package cn.coderoom.generator.base.utils;

import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.coderoom.generator.base.excel2code.entity.ExcelApi2Code;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.List;

/** 
 *  根据Excel生产代码
 * @class Excel2CodeUtil 
 * @package cn.coderoom.generator.base.utils
 * @author lim
 * @email coderoom.cn@gmail.com
 * @date 2020/1/10 14:15 
*/ 
public class Excel2CodeUtil {

    private Logger logger = LoggerFactory.getLogger(getClass());

    public static void generatorCode(String url,String modelName){

        File file = new File(url);
        List<ExcelApi2Code> excelApi2Codes = EasypoiUtil.importExcel(file, ExcelApi2Code.class,new ImportParams());
        for (ExcelApi2Code excelApi2Code:
        excelApi2Codes) {
            System.out.println("/**");
            System.out.println(" * "+excelApi2Code.getDescription().toString());
            System.out.println(" */");
            if(excelApi2Code.getFieldType()==null || excelApi2Code.getFieldType().equals("String")){

                System.out.println("String "+excelApi2Code.getLocalFild().toString()+" = jsonObject.getString(\""+ excelApi2Code.getApiField().toString() +"\")");
            }else{
                System.out.println(excelApi2Code.getFieldType().toString()+" "+excelApi2Code.getLocalFild().toString()+" = jsonObject.getObject(\""+ excelApi2Code.getApiField().toString() +"\","+excelApi2Code.getFieldType()+".class)");

            }
        }
        for (ExcelApi2Code excelApi2Code:
                excelApi2Codes) {
            System.out.println(modelName+".set"+StringUtils.upperFirstLatter(excelApi2Code.getLocalFild()).toString()+"("+excelApi2Code.getLocalFild()+");");
        }

    }

    public static void generatorModelCode(String url,String modelName){

        File file = new File(url);
        List<ExcelApi2Code> excelApi2Codes = EasypoiUtil.importExcel(file, ExcelApi2Code.class,new ImportParams());
        for (ExcelApi2Code excelApi2Code:
                excelApi2Codes) {
            System.out.println("/**");
            System.out.println(" * "+excelApi2Code.getDescription().toString());
            System.out.println(" */");
            if(excelApi2Code.getFieldType()==null || excelApi2Code.getFieldType().equals("String")){

                System.out.println("private "+ "String "+excelApi2Code.getLocalFild().toString()+";");
            }else{
                System.out.println("private "+ excelApi2Code.getFieldType().toString()+" "+excelApi2Code.getLocalFild().toString()+";");

            }
        }

    }

    public static void generatorZjmiecOACode(String url,String modelName){

        File file = new File(url);
        List<ExcelApi2Code> excelApi2Codes = EasypoiUtil.importExcel(file, ExcelApi2Code.class,new ImportParams());

        System.out.println("WorkflowRequestTableField[] wrtf = new WorkflowRequestTableField[" + excelApi2Codes.size() +"]");
        int i = 0;
        for (ExcelApi2Code excelApi2Code:
                excelApi2Codes) {

            System.out.println("/**");
            System.out.println(" * "+excelApi2Code.getDescription().toString());
            System.out.println(" */");
            System.out.println("wrti["+i+"] = new WorkflowRequestTableField();");
            System.out.println("wrti["+i+"].setFieldName(\""+excelApi2Code.getLocalFild()+"\");");
            System.out.println("wrti["+i+"].setFieldValue("+excelApi2Code.getLocalFild()+");");
            System.out.println("wrti["+i+"].setView(true);//字段是否可见");
            System.out.println("wrti["+i+"].setEdit(true);//字段是否可编辑");
            System.out.println(" ");
            i++;
        }

    }

}

package cn.coderoom.generator.base.utils;

import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.afterturn.easypoi.excel.imports.ExcelImportService;
import cn.afterturn.easypoi.exception.excel.ExcelImportException;
import com.baomidou.mybatisplus.toolkit.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;

/** 
 * 
 * @class EasypoiUtil
 * @package cn.coderoom.generator.base.utils
 * @author lim
 * @email coderoom.cn@gmail.com
 * @date 2020/1/10 11:38
*/ 
public class EasypoiUtil {

    private static final Logger logger = LoggerFactory.getLogger(EasypoiUtil.class);


    /**
     * Excel 导入 数据源本地文件,不返回校验结果 导入 字 段类型 Integer,Long,Double,Date,String,Boolean
     * @param file
     * @param pojoClass
     * @param params
     * @author coderoom.cn@gmail.com
     * @date 2020/1/10 11:22
     * @return java.util.List<T>
    */
    public static <T> List<T> importExcel(File file, Class<?> pojoClass, ImportParams params) {
        FileInputStream in = null;
        try {
            in = new FileInputStream(file);
            return new ExcelImportService().importExcelByIs(in, pojoClass, params, false).getList();
        } catch (ExcelImportException e) {
            throw new ExcelImportException(e.getType(), e);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new ExcelImportException(e.getMessage(), e);
        } finally {
            IOUtils.closeQuietly(in);
        }
    }

    /**
     * Excel 导入 数据源IO流,不返回校验结果 导入 字段类型 Integer,Long,Double,Date,String,Boolean
     * @param inputstream
     * @param pojoClass
     * @param params
     * @author coderoom.cn@gmail.com
     * @date 2020/1/10 11:22
     * @return java.util.List<T>
    */
    public static <T> List<T> importExcel(InputStream inputstream, Class<?> pojoClass,
                                          ImportParams params) throws Exception {
        return new ExcelImportService().importExcelByIs(inputstream, pojoClass, params, false).getList();
    }


}

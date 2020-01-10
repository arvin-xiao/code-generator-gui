package cn.coderoom.generator.base.utils;

import org.junit.Test;

import java.io.File;
import java.util.List;
/**
 * @package：cn.coderoom.generator.base.utils
 * @description:
 * @author: Leesire
 * @email:coderoom.cn@gmail.com
 * @createtime: 2019/12/23
 */
public class FileUtilTest {
    @Test
    public void getFiles() throws Exception {
        List<File> files= FileUtil.getFiles("D:\\11111\\template\\base\\NsomsBbCompany");
        for (File file:
                files) {
            System.out.println(">>>>>>>>>>>>>>"+file.toPath());
        }
    }

}
package cn.coderoom.generator.base.utils;

import cn.hutool.core.util.StrUtil;
import org.junit.Test;

import java.io.File;

/**
 * @package：cn.coderoom.generator.base.utils
 * @description:
 * @author: Leesire
 * @email:coderoom.cn@gmail.com
 * @createtime: 2019/12/23
 */
public class StrUtilTest {
    @Test
    public void getFiles() throws Exception {
        File file = new File("D:\\11111\\template\\{0}.html.btl");

        System.out.println(">>>>>>"+ StrUtil.format(file.toPath().toString(),"123"));
        System.out.println(">>>>>>"+ StrUtil.indexedFormat(file.toPath().toString(),"123"));
        System.out.println(">>>>>>"+file.getPath().substring(file.getPath().lastIndexOf("\\")+1));

    }

}
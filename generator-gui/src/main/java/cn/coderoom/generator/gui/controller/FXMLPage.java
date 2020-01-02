package cn.coderoom.generator.gui.controller;

/** 
 *  FXML User Interface enum
 * @class FXMLPage 
 * @package cn.coderoom.mybatis.generator.controller
 * @author lim
 * @email coderoom.cn@gmail.com
 * @date 2019/12/17 9:35 
*/ 
public enum FXMLPage {

    NEW_CONNECTION("fxml/newConnection.fxml"),
    SELECT_ANNOTATION_TABLE_COLUMN("fxml/selectTableColumnAnnotation.fxml"),
    SELECT_TABLE_COLUMN("fxml/selectTableColumn.fxml"),
    GENERATOR_CONFIG("fxml/generatorConfigs.fxml"),
    ;

    private String fxml;

    FXMLPage(String fxml) {
        this.fxml = fxml;
    }

    public String getFxml() {
        return this.fxml;
    }


}

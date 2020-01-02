package cn.coderoom.generator.gui.model;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * @package：cn.coderoom.mybatis.generator.model
 * @description:
 * @author: Leesire
 * @email:coderoom.cn@gmail.com
 * @createtime: 2019/12/13
 */
public class UIAnnotationTableColumnVo {

    private StringProperty columnName = new SimpleStringProperty();
    private BooleanProperty notNull = new SimpleBooleanProperty(true); // Default set to true
    private BooleanProperty max = new SimpleBooleanProperty(true); // Default set to true
    private BooleanProperty min = new SimpleBooleanProperty(true); // Default set to true
    private BooleanProperty length = new SimpleBooleanProperty(true); // Default set to true

    public boolean isLength() {
        return length.get();
    }

    public BooleanProperty lengthProperty() {
        return length;
    }

    public void setLength(boolean length) {
        this.length.set(length);
    }

    public String getColumnName() {
        return columnName.get();
    }

    public StringProperty columnNameProperty() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName.set(columnName);
    }

    public boolean isNotNull() {
        return notNull.get();
    }

    public BooleanProperty notNullProperty() {
        return notNull;
    }

    public void setNotNull(boolean notNull) {
        this.notNull.set(notNull);
    }

    public boolean isMax() {
        return max.get();
    }

    public BooleanProperty maxProperty() {
        return max;
    }

    public void setMax(boolean max) {
        this.max.set(max);
    }

    public boolean isMin() {
        return min.get();
    }

    public BooleanProperty minProperty() {
        return min;
    }

    public void setMin(boolean min) {
        this.min.set(min);
    }
}

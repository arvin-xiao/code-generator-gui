package cn.coderoom.generator.gui.controller;

import cn.coderoom.generator.gui.model.AnnotationConfig;
import cn.coderoom.generator.gui.model.UIAnnotationTableColumnVo;
import cn.coderoom.generator.gui.util.ReflectUtil;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import org.mybatis.generator.config.ColumnOverride;
import org.mybatis.generator.config.IgnoredColumn;

import java.net.URL;
import java.util.*;

/** 
 *  注解选择
 * @class SelectTableColumnAnnotationController
 * @package cn.coderoom.mybatis.generator.controller
 * @author lim
 * @email coderoom.cn@gmail.com
 * @date 2019/12/13 15:46 
*/ 
public class SelectTableColumnAnnotationController extends BaseFXController {

    @FXML
    private TableView<UIAnnotationTableColumnVo> columnListView;
    @FXML
    private TableColumn<UIAnnotationTableColumnVo, String> columnNameColumn;
    @FXML
    private TableColumn<UIAnnotationTableColumnVo, Boolean> notNull;
    @FXML
    private TableColumn<UIAnnotationTableColumnVo, Boolean> max;
    @FXML
    private TableColumn<UIAnnotationTableColumnVo, Boolean> min;


    private MainUIController mainUIController;

    private String tableName;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // cellvaluefactory
        columnNameColumn.setCellValueFactory(new PropertyValueFactory<>("columnName"));
        notNull.setCellValueFactory(new PropertyValueFactory<>("notNull"));
        max.setCellValueFactory(new PropertyValueFactory<>("max"));
        min.setCellValueFactory(new PropertyValueFactory<>("min"));

        notNull.setCellFactory(CheckBoxTableCell.forTableColumn(notNull));
        max.setCellFactory(CheckBoxTableCell.forTableColumn(max));
        min.setCellFactory(CheckBoxTableCell.forTableColumn(min));

        TableColumn firstNameCol = new TableColumn("First Name");
        columnListView.getColumns().add(firstNameCol);
    }

    @FXML
    public void ok() {
        ObservableList<UIAnnotationTableColumnVo> items = columnListView.getItems();
        List<Object> annotationConfigs = new ArrayList<Object>();
        if (items != null && items.size() > 0) {
            List<IgnoredColumn> ignoredColumns = new ArrayList<>();
            List<ColumnOverride> columnOverrides = new ArrayList<>();
            AnnotationConfig config = null;
            for (UIAnnotationTableColumnVo item:
                items) {
                config = new AnnotationConfig();
                config.setColumnName(item.getColumnName());
                config.setNotNull(item.isNotNull());
                config.setMax(item.isMax());
                config.setMin(item.isMin());
                config.setLength(item.isLength());

                Map<String, Object> addProperties = new HashMap() {{
                    put("hello", false);
                    put("abc", false);
                }};
                Object annotationConfig = ReflectUtil.getTarget(config, addProperties);

                annotationConfigs.add(annotationConfig);
            }

            mainUIController.setIgnoredColumns(ignoredColumns);
            mainUIController.setColumnOverrides(columnOverrides);
            mainUIController.setAnnotationConfigs(annotationConfigs);
        }
        getDialogStage().close();
    }

    @FXML
    public void cancel() {
        getDialogStage().close();
    }

    public void setColumnList(ObservableList<UIAnnotationTableColumnVo> columns) {
        columnListView.setItems(columns);
    }

    public void setMainUIController(MainUIController mainUIController) {
        this.mainUIController = mainUIController;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }


}

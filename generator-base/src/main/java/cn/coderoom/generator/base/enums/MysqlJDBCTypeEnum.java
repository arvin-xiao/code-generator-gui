package cn.coderoom.generator.base.enums;

import lombok.Getter;

@Getter
public enum MysqlJDBCTypeEnum {

    Varchar("varchar", "String"),
    IntEn("int","int"),
    Tinyint("tinyint","int"),
    Bigint("bigint","int"),
    Timestamp("timestamp","Timestamp"),
    Date("Date","Date"),
    DateTime("Date","DateTime");


    private String key;
    private String value;

    MysqlJDBCTypeEnum(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public static String valueOf(String keys,String value) {

        if (keys == "") {
            return "";
        } else {
            for (MysqlJDBCTypeEnum s : MysqlJDBCTypeEnum.values()) {
                if (s.getKey().equals(keys)) {
                    return s.getValue();
                }
            }
            return "";
        }

    }

}

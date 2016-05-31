package ru.progmatik.java.pregis.connectiondb.reference;

/**
 * Класс, описывает объект справочника полученного или готового для записи в БД ГРАДа.
 */
public class ReferenceItemDataSet {

    private Integer id;
    private String name;
    private String code;
    private String uiid;
    private String groupName;
    private Integer codeParent;

    public ReferenceItemDataSet() {

    }

    public ReferenceItemDataSet(String name, String code, String uiid) {
        this.name = name;
        this.code = code;
        this.uiid = uiid;
    }

    public ReferenceItemDataSet(Integer id, String name, String code, String uiid, String groupName, Integer codeParent) {
        this(name, code, uiid);
        this.id = id;
        this.groupName = groupName;
        this.codeParent = codeParent;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getUiid() {
        return uiid;
    }

    public void setUiid(String uiid) {
        this.uiid = uiid;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public Integer getCodeParent() {
        return codeParent;
    }

    public void setCodeParent(Integer codeParent) {
        this.codeParent = codeParent;
    }
}

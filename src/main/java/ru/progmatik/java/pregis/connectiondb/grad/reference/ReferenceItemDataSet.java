package ru.progmatik.java.pregis.connectiondb.grad.reference;

/**
 * Класс, описывает объект справочника полученного или готового для записи в БД ГРАДа.
 */
public class ReferenceItemDataSet {

    private Integer id;
    private String name;
    private String code;
    private String guid;
    private String groupName;
    private Integer codeParent;

    public ReferenceItemDataSet() {

    }

    public ReferenceItemDataSet(String name, String code, String guid) {
        this.name = name;
        this.code = code;
        this.guid = guid;
    }

    public ReferenceItemDataSet(Integer id, String name, String code, String guid, String groupName, Integer codeParent) {
        this(name, code, guid);
        this.id = id;
        this.groupName = groupName;
        this.codeParent = codeParent;
    }

    public ReferenceItemDataSet(String name, String code, String guid, String groupName, Integer codeParent) {
        this(name, code, guid);
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

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
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

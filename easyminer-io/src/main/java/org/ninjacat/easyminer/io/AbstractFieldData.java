package org.ninjacat.easyminer.io;

public abstract class AbstractFieldData implements FieldData {

    protected String fieldName;

    protected boolean isTarget = false;

    public AbstractFieldData(String fieldName) {
        super();
        this.fieldName = fieldName;
    }

    public String getFieldName() {
        return fieldName;
    }

    public boolean isTarget() {
        return isTarget;
    }

    public void setTarget(boolean isTarget) {
        this.isTarget = isTarget;
    }

    abstract public int getRecordCount();

    abstract public Object getRecord(int i);

    abstract public void addRecord(Object value);

}

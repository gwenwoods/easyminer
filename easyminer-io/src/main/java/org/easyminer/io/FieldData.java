package org.easyminer.io;

public interface FieldData {

    public String getFieldName();

    public int getRecordCount();

    public Object getRecord(int i);

    public void addRecord(Object value);

    public boolean isTarget();

    public void setTarget(boolean isTarget);
}

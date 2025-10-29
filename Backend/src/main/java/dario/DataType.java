package dario;

public enum DataType {
    READ(0x01),
    WRITE_HIGH(0x02),
    WRITE_LOW(0x03);

    private final byte data;
    
    DataType(int data) {
        this.data = (byte) data;
    }

    public byte getData() {
        return data;
    }
}

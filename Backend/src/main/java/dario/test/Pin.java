package dario.test;

public enum Pin {

    PIN_0(0),
    PIN_1(1),
    PIN_2(2),
    PIN_3(3),
    PIN_4(4),
    PIN_5(5),
    PIN_6(6),
    PIN_7(7),
    PIN_8(8),
    PIN_9(9),
    PIN_10(10),
    PIN_11(11),
    PIN_12(12),
    PIN_13(13),
    PIN_A0(14),
    PIN_A1(15),
    PIN_A2(16),
    PIN_A3(17),
    PIN_A4(18),
    PIN_A5(19),
    ;

    private int value;
    Pin(int value) {
        this.value = value;
    }
    public int getValue() {
        return value;
    }

}

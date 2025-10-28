class PinConfiguration {
  private:
    int pins[];

  public:
    PinConfiguration(int c_pins[]) {
      pins = c_pins;
    }
    int[] pins() {
      return pins;
    }
}
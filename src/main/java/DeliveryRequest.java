class DeliveryRequest {

    public String superMarketId;
    public String code;
    public int quantity;

    public DeliveryRequest(final String superMarketId, final String code, final int quantity) {
        this.superMarketId = superMarketId;
        this.code = code;
        this.quantity = quantity;
    }

    public String getCode() {
        return code;
    }
}

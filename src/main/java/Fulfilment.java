import java.util.List;
import java.util.Map;

class Fulfilment {
    public static DispatchRequest[] forDelivery(final List<DeliveryRequest> deliveryRequests) {
        final Map<String, Integer> countedItems = new Clerk().rationaliseDeliveryRequests(deliveryRequests);

        final String[] consignment = new PalletStacker().consignmentFor(countedItems);

        return new Dispatcher().organiseDispatchesFor(consignment);
    }
}

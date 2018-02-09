import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

class Clerk {

    private final Collector<DeliveryRequest, ?, Map<String, List<DeliveryRequest>>> groupByItemCode = Collectors.groupingBy(DeliveryRequest::getCode);

    Map<String, Integer> rationaliseDeliveryRequests(List<DeliveryRequest> deliveryRequests) {
        final HashMap<String, Integer> countedItems = new HashMap<>();
        final Map<String, List<DeliveryRequest>> requestsByCode = deliveryRequests.stream().collect(groupByItemCode);

        requestsByCode.keySet().forEach(code -> countedItems.put(code, 0));

        requestsByCode.forEach((code, requests) -> {
            Integer current = countedItems.get(code);
            final int newTotal = current + requests.stream().mapToInt(r -> r.quantity).sum();
            countedItems.put(code, newTotal);
        });

        return countedItems;
    }
}

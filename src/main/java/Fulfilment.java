
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;



class Fulfilment {

    static class Clerk {

        private final Collector<DeliveryRequest, ?, Map<String, List<DeliveryRequest>>> groupByItemCode = Collectors.groupingBy(DeliveryRequest::getCode);

        Map<String,Integer> rationaliseDeliveryRequests(List<DeliveryRequest> deliveryRequests) {
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

    static class PalletStacker {
        private static final Map<String, Integer> unitsPerPallet = new HashMap<>();
        static {
            unitsPerPallet.put("A", 6);
            unitsPerPallet.put("B", 10);
        }

        String[] consignmentFor(Map<String, Integer> requestedItems) {
            final ArrayList<String> consignment = new ArrayList<>();

            requestedItems.forEach((code, quantity) -> {
                final Integer unitsPerPalletForThisCode = unitsPerPallet.get(code);
                final int numberOfPallets = countAtLeastOnePallet(quantity, unitsPerPalletForThisCode);
                addPalletsToConsignment(consignment, code, numberOfPallets);
            });

            return consignment.toArray(new String[]{});
        }
    }

    static class Dispatcher {
        private static String possibleVehicle = "Modified Transit";
        private final String arbitraryDestination = "anything";

        DispatchRequest organiseDispatchesFor(String[] consignment) {
            return new DispatchRequest(possibleVehicle, consignment, arbitraryDestination);
        }
    }

    public static DispatchRequest forDelivery(final List<DeliveryRequest> deliveryRequests) {

        final Map<String, Integer> countedItems = new Clerk().rationaliseDeliveryRequests(deliveryRequests);

        final String[] consignment = new PalletStacker().consignmentFor(countedItems);

       return new Dispatcher().organiseDispatchesFor(consignment);
   }

    private static void addPalletsToConsignment(final ArrayList<String> consignment, final String code, final int numberOfPallets) {
        for (int i = 0; i < numberOfPallets; i++) {
            consignment.add(code);
        }
    }

    private static int countAtLeastOnePallet(final int totalUnits, final Integer unitsPerPallet) {
        return (int) Math.ceil(totalUnits / unitsPerPallet.doubleValue());
    }
}

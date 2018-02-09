
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;



class Fulfilment {

    static class Clerk {
        public Map<String,Integer> rationaliseDeliveryRequests(List<DeliveryRequest> deliveryRequests) {
            final HashMap<String, Integer> countedItems = new HashMap<>();
            final Map<String, List<DeliveryRequest>> requestsByCode =
                    deliveryRequests.stream()
                            .collect(Collectors.groupingBy(DeliveryRequest::getCode));
            requestsByCode.forEach((code, requests) -> {
                countedItems.putIfAbsent(code, 0);
                Integer current = countedItems.get(code);
                countedItems.put(code, current + requests.stream().mapToInt(r -> r.quantity).sum());
            });
            return countedItems;
        }
    }

    private static final Map<String, Integer> unitsPerPallet = new HashMap<>();
    static {
        unitsPerPallet.put("A", 6);
        unitsPerPallet.put("B", 10);
    }


    /*
    group requests by code
    so we can count units by code
    as long as there is at least 1 unit
    add at least 1 pallet
    _and_ add enough pallets based on how many of this code will fit on a pallet
     */
    public static DispatchRequest forDelivery(final List<DeliveryRequest> deliveryRequests) {
        final ArrayList<String> consignment = new ArrayList<>();

        final Map<String, Integer> countedItems = new Clerk().rationaliseDeliveryRequests(deliveryRequests);
        countedItems.forEach((code, quantity) -> {
            final Integer unitsPerPalletForThisCode = Fulfilment.unitsPerPallet.get(code);
            final int numberOfPallets = countAtLeastOnePallet(quantity, unitsPerPalletForThisCode);
            addPalletsToConsignment(consignment, code, numberOfPallets);
        });

       return new DispatchRequest("Modified Transit", consignment.toArray(new String[]{}), deliveryRequests.get(0).superMarketId);
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

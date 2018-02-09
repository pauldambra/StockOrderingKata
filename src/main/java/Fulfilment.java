
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

class Fulfilment {

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

        final Map<String, List<DeliveryRequest>> requestsByCode =
               deliveryRequests.stream()
                       .collect(Collectors.groupingBy(DeliveryRequest::getCode));

        requestsByCode.forEach((code, requestsForCode) -> {
            final int totalUnitsForThisCode = requestsForCode.stream().mapToInt(r -> r.quantity).sum();
            if (totalUnitsForThisCode > 0) {
                final Integer unitsPerPalletForThisCode = Fulfilment.unitsPerPallet.get(code);
                final int numberOfPallets = countAtLeastOnePallet(totalUnitsForThisCode, unitsPerPalletForThisCode);
                addPalletsToConsignment(consignment, code, numberOfPallets);
            }
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

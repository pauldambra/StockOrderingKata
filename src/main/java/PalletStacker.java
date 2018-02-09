import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

class PalletStacker {
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

    private static void addPalletsToConsignment(final ArrayList<String> consignment, final String code, final int numberOfPallets) {
        for (int i = 0; i < numberOfPallets; i++) {
            consignment.add(code);
        }
    }

    private static int countAtLeastOnePallet(final int totalUnits, final Integer unitsPerPallet) {
        return (int) Math.ceil(totalUnits / unitsPerPallet.doubleValue());
    }
}

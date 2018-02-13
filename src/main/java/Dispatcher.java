import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.google.common.collect.ObjectArrays.concat;

class Dispatcher {

    private static final List<String> refrigeratedItems = ImmutableList.of("A", "B");


    private final String arbitraryDestination = "anything";

    DispatchRequest[] organiseDispatchesFor(String[] consignment) {

        final List<String> desiredPallets = Arrays.asList(consignment);
        final List<String> refrigeratedPallets = desiredPallets.stream().filter(refrigeratedItems::contains).collect(Collectors.toList());
        final List<String> nonRefrigeratedPallets = desiredPallets.stream().filter(i -> !refrigeratedItems.contains(i)).collect(Collectors.toList());

        final DispatchRequest[] refrigeratedDispatches = gatherDispatchRequests(refrigeratedPallets, Vehicles.MODIFIED_TRANSIT);
        final DispatchRequest[] nonRefrigeratedDispatches = gatherDispatchRequests(nonRefrigeratedPallets, Vehicles.TRANSIT);

        return concat(refrigeratedDispatches, nonRefrigeratedDispatches, DispatchRequest.class);
    }

    private DispatchRequest[] gatherDispatchRequests(final List<String> desiredPallets, final Vehicles vehicle) {
        return Lists.partition(desiredPallets, vehicle.capacity)
                .stream()
                .map(c -> new DispatchRequest(vehicle.name, c.toArray(new String[]{}), arbitraryDestination))
                .toArray(DispatchRequest[]::new);
    }
}

enum Vehicles {
    TRANSIT(4, "Transit"),
    MODIFIED_TRANSIT(3, "Modified Transit");

    public final int capacity;
    public final String name;

    Vehicles(final int capacity, final String name) {

        this.capacity = capacity;
        this.name = name;
    }
}
import com.google.common.collect.Lists;

import java.util.Arrays;

class Dispatcher {
    private final String arbitraryDestination = "anything";

    DispatchRequest[] organiseDispatchesFor(String[] consignment) {

        return Lists.partition(Arrays.asList(consignment), ModifiedTransit.capacity())
                .stream()
                .map(c -> new DispatchRequest(ModifiedTransit.name(), c.toArray(new String[]{}), arbitraryDestination))
                .toArray(DispatchRequest[]::new);
    }
}

class ModifiedTransit {

    public static int capacity() {
        return 3;
    }

    public static String name() {
        return "Modified Transit";
    }
}
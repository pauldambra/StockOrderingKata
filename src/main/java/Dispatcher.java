class Dispatcher {
    private final String arbitraryDestination = "anything";

    DispatchRequest organiseDispatchesFor(String[] consignment) {
        String vehicle = "Modified Transit";
        if (consignment.length > 3) {
            vehicle = "Lorry";
        }
        return new DispatchRequest(vehicle, consignment, arbitraryDestination);
    }
}

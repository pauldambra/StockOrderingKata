class Dispatcher {
    private static String possibleVehicle = "Modified Transit";
    private final String arbitraryDestination = "anything";

    DispatchRequest organiseDispatchesFor(String[] consignment) {
        return new DispatchRequest(possibleVehicle, consignment, arbitraryDestination);
    }
}

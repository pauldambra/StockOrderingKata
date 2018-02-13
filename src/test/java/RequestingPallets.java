import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;

public class RequestingPallets {

    @Test
    public void onlyOnePalletIsRequestedIfQuantityWillFit() {

        DeliveryRequest singleA = new DeliveryRequest("anything", "A", 1);

        final DispatchRequest expectedDispatch =
                new DispatchRequest("Modified Transit", new String[]{"A"}, "anything");

        final DispatchRequest[] actualDispatch = Fulfilment.forDelivery(singletonList(singleA));

        assertThat(actualDispatch).containsAll(Collections.singletonList(expectedDispatch));
    }

    @Test
    public void onlyJustExceedingTheUnitsPerPalletRequestsTwoPallets() {

        DeliveryRequest deliveryRequest = new DeliveryRequest("anything", "A", 7);

        final DispatchRequest expectedDispatch =
                new DispatchRequest("Modified Transit", new String[]{"A", "A"}, "anything");

        final DispatchRequest[] actualDispatch = Fulfilment.forDelivery(singletonList(deliveryRequest));

        assertThat(actualDispatch).containsAll(Collections.singletonList(expectedDispatch));
    }

    @Test
    public void exceedingTheCapacityOfTwoPalletsRequestsAThird() {
        DeliveryRequest deliveryRequest = new DeliveryRequest("anything", "A", 13);

        final DispatchRequest expectedDispatch =
                new DispatchRequest("Modified Transit", new String[]{"A", "A", "A"}, "anything");

        final DispatchRequest[] actualDispatch = Fulfilment.forDelivery(singletonList(deliveryRequest));

        assertThat(actualDispatch).containsAll(Collections.singletonList(expectedDispatch));
    }

    @Test
    public void fulfilmentHandlesMultipleDeliveryRequests() {
        DeliveryRequest firstRequest = new DeliveryRequest("anything", "A", 6);
        DeliveryRequest secondRequest = new DeliveryRequest("anything", "A", 7);

        final DispatchRequest expectedDispatch =
                new DispatchRequest("Modified Transit", new String[]{"A", "A", "A"}, "anything");

        final DispatchRequest[] actualDispatch = Fulfilment.forDelivery(Arrays.asList(firstRequest, secondRequest));

        assertThat(actualDispatch).containsAll(Collections.singletonList(expectedDispatch));
    }

    @Test
    public void requestingMixedUnitTypesForConsignment() {

        DeliveryRequest singleA = new DeliveryRequest("anything", "A", 1);
        DeliveryRequest singleB = new DeliveryRequest("anything", "B", 1);

        final DispatchRequest expectedDispatch =
                new DispatchRequest("Modified Transit", new String[]{"A", "B"}, "anything");

        final DispatchRequest[] actualDispatch = Fulfilment.forDelivery(Arrays.asList(singleA, singleB));

        assertThat(actualDispatch).containsAll(Collections.singletonList(expectedDispatch));

    }

    @Test
    public void requestingMultipleUnitsOfSkuBTypesCanAddMultiplePalletsForConsignment() {

        DeliveryRequest singleB = new DeliveryRequest("anything", "B", 21);

        final DispatchRequest expectedDispatch =
                new DispatchRequest("Modified Transit", new String[]{"B", "B", "B"}, "anything");

        final DispatchRequest[] actualDispatch = Fulfilment.forDelivery(Collections.singletonList(singleB));

        assertThat(actualDispatch).containsAll(Collections.singletonList(expectedDispatch));

    }

    @Test
    public void requestingMixedUnitTypesCanAddMultiplePalletsForConsignment() {

        DeliveryRequest singleA = new DeliveryRequest("anything", "A", 1);
        DeliveryRequest singleB = new DeliveryRequest("anything", "B", 20);

        final DispatchRequest expectedDispatch =
                new DispatchRequest("Modified Transit", new String[]{"A", "B", "B"}, "anything");

        final DispatchRequest[] actualDispatch = Fulfilment.forDelivery(Arrays.asList(singleA, singleB));

        assertThat(actualDispatch).containsAll(Collections.singletonList(expectedDispatch));

    }

    @Test
    public void vehiclesFillUpAndSoMoreThanThreeRefrigeratedPalletsRequireMultipleDispatches() {

        DeliveryRequest singleA = new DeliveryRequest("anything", "A", 1);
        DeliveryRequest singleB = new DeliveryRequest("anything", "B", 21);

        final List<DispatchRequest> expectedDispatches = Arrays.asList(
            new DispatchRequest("Modified Transit", new String[]{"A", "B", "B"}, "anything"),
            new DispatchRequest("Modified Transit", new String[]{"B"}, "anything")
        );

        final DispatchRequest[] actualDispatch = Fulfilment.forDelivery(Arrays.asList(singleA, singleB));

        assertThat(actualDispatch).containsAll(expectedDispatches);
    }

//    @Test
//    public void () {
//        final int thirtyOnePalletsOfBs = 301;
//        DeliveryRequest bs = new DeliveryRequest("anything", "B", thirtyOnePalletsOfBs);
//
//        final String[] expectedConsignmentForFirstLorry =
//                IntStream
//                    .range(0, 30)
//                    .mapToObj(i -> "B")
//                    .toArray(String[]::new);
//
//        final DispatchRequest expectedDispatchOfFullLorry =
//                new DispatchRequest("Lorry", expectedConsignmentForFirstLorry, "anything");
//        final DispatchRequest expectedDispatchOfSinglePallet =
//                new DispatchRequest("Modified Transit", new String[] {"B"}, "anything");
//
//        final DispatchRequest[] actualDispatch = Fulfilment.forDelivery(Collections.singletonList(bs));
//
//        assertThat(actualDispatch)
//                .containsAll(Arrays.asList(expectedDispatchOfFullLorry, expectedDispatchOfSinglePallet));
//    }
}

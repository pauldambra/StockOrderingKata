import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;

import static java.util.Collections.singletonList;
import static org.junit.Assert.assertEquals;

public class RequestingPallets {

    @Test
    public void onlyOnePalletIsRequestedIfQuantityWillFit() {

        DeliveryRequest singleA = new DeliveryRequest("anything", "A", 1);

        final DispatchRequest expectedDispatch =
                new DispatchRequest("Modified Transit", new String[]{"A"}, "anything");

        final DispatchRequest actualDispatch = Fulfilment.forDelivery(singletonList(singleA));

        assertEquals(expectedDispatch, actualDispatch);
    }

    @Test
    public void onlyJustExceedingTheUnitsPerPalletRequestsTwoPallets() {

        DeliveryRequest deliveryRequest = new DeliveryRequest("anything", "A", 7);

        final DispatchRequest expectedDispatch =
                new DispatchRequest("Modified Transit", new String[]{"A", "A"}, "anything");

        final DispatchRequest actualDispatch = Fulfilment.forDelivery(singletonList(deliveryRequest));

        assertEquals(expectedDispatch, actualDispatch);
    }

    @Test
    public void exceedingTheCapacityOfTwoPalletsRequestsAThird() {
        DeliveryRequest deliveryRequest = new DeliveryRequest("anything", "A", 13);

        final DispatchRequest expectedDispatch =
                new DispatchRequest("Modified Transit", new String[]{"A", "A", "A"}, "anything");

        final DispatchRequest actualDispatch = Fulfilment.forDelivery(singletonList(deliveryRequest));

        assertEquals(expectedDispatch, actualDispatch);
    }

    @Test
    public void fulfilmentHandlesMultipleDeliveryRequests() {
        DeliveryRequest firstRequest = new DeliveryRequest("anything", "A", 6);
        DeliveryRequest secondRequest = new DeliveryRequest("anything", "A", 7);

        final DispatchRequest expectedDispatch =
                new DispatchRequest("Modified Transit", new String[]{"A", "A", "A"}, "anything");

        final DispatchRequest actualDispatch = Fulfilment.forDelivery(Arrays.asList(firstRequest, secondRequest));

        assertEquals(expectedDispatch, actualDispatch);
    }

    @Test
    public void requestingMixedUnitTypesForConsignment() {

        DeliveryRequest singleA = new DeliveryRequest("anything", "A", 1);
        DeliveryRequest singleB = new DeliveryRequest("anything", "B", 1);

        final DispatchRequest expectedDispatch =
                new DispatchRequest("Modified Transit", new String[]{"A", "B"}, "anything");

        final DispatchRequest actualDispatch = Fulfilment.forDelivery(Arrays.asList(singleA, singleB));

        assertEquals(expectedDispatch, actualDispatch);

    }

    @Test
    public void requestingMultipleUnitsOfSkuBTypesCanAddMultiplePalletsForConsignment() {

        DeliveryRequest singleB = new DeliveryRequest("anything", "B", 21);

        final DispatchRequest expectedDispatch =
                new DispatchRequest("Modified Transit", new String[]{"B", "B", "B"}, "anything");

        final DispatchRequest actualDispatch = Fulfilment.forDelivery(Collections.singletonList(singleB));

        assertEquals(expectedDispatch, actualDispatch);

    }

    @Test
    public void requestingMixedUnitTypesCanAddMultiplePalletsForConsignment() {

        DeliveryRequest singleA = new DeliveryRequest("anything", "A", 1);
        DeliveryRequest singleB = new DeliveryRequest("anything", "B", 20);

        final DispatchRequest expectedDispatch =
                new DispatchRequest("Modified Transit", new String[]{"A", "B", "B"}, "anything");

        final DispatchRequest actualDispatch = Fulfilment.forDelivery(Arrays.asList(singleA, singleB));

        assertEquals(expectedDispatch, actualDispatch);

    }

    @Test
    public void moreThanThreeRefrigeratedPalletsMustTravelByLorry() {

        DeliveryRequest singleA = new DeliveryRequest("anything", "A", 1);
        DeliveryRequest singleB = new DeliveryRequest("anything", "B", 21);

        final DispatchRequest expectedDispatch =
                new DispatchRequest("Lorry", new String[]{"A", "B", "B", "B"}, "anything");

        final DispatchRequest actualDispatch = Fulfilment.forDelivery(Arrays.asList(singleA, singleB));

        assertEquals(expectedDispatch, actualDispatch);

    }
}

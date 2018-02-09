import java.util.Arrays;
import java.util.Objects;

class DispatchRequest {
    public DispatchRequest(final String lorryType, final String[] consignment, final String destination) {
        this.lorryType = lorryType;
        this.consignment = consignment;
        this.destination = destination;
    }

    public String lorryType;
    public String[] consignment;
    public String destination;

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final DispatchRequest that = (DispatchRequest) o;
        return Objects.equals(lorryType, that.lorryType) &&
                Arrays.equals(consignment, that.consignment) &&
                Objects.equals(destination, that.destination);
    }

    @Override
    public int hashCode() {

        int result = Objects.hash(lorryType, destination);
        result = 31 * result + Arrays.hashCode(consignment);
        return result;
    }

    @Override
    public String toString() {
        return "DispatchRequest{" +
                "lorryType='" + lorryType + '\'' +
                ", consignment=" + Arrays.toString(consignment) +
                ", destination='" + destination + '\'' +
                '}';
    }
}

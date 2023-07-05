package io.codelex.flightplanner.flights.admin.request;

public class AddFlightRequest {
    private AirportRequest from;
    private AirportRequest to;
    private String carrier;
    private String departureTime;
    private String arrivalTime;

    public AddFlightRequest(AirportRequest from, AirportRequest to, String carrier, String departureTime, String arrivalTime) {
        this.from = from;
        this.to = to;
        this.carrier = carrier;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
    }

    public AirportRequest getFrom() {
        return from;
    }

    public void setFrom(AirportRequest from) {
        this.from = from;
    }

    public AirportRequest getTo() {
        return to;
    }

    public void setTo(AirportRequest to) {
        this.to = to;
    }

    public String getCarrier() {
        return carrier;
    }

    public void setCarrier(String carrier) {
        this.carrier = carrier;
    }

    public String getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(String departureTime) {
        this.departureTime = departureTime;
    }

    public String getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(String arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    @Override
    public String toString() {
        return "AddFlightRequest{" +
                "from=" + from +
                ", to=" + to +
                ", carrier='" + carrier + '\'' +
                ", departureTime='" + departureTime + '\'' +
                ", arrivalTime='" + arrivalTime + '\'' +
                '}';
    }
}

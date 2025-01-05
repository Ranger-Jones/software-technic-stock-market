package com.example.stockcalc.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.List;

public class TickerDetailsResponse {

    @JsonProperty("request_id")
    private String requestId;
    private Results results;
    private String status;

    // Getters and Setters

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public Results getResults() {
        return results;
    }

    public void setResults(Results results) {
        this.results = results;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public static class Results {

        private boolean active;
        private Address address;
        private Branding branding;
        private String cik;
        @JsonProperty("composite_figi")
        private String compositeFigi;

        @JsonProperty("currency_name")
        private String currencyName;
        private String description;

        @JsonProperty("homepage_url")
        private String homepageUrl;

        @JsonProperty("list_date")
        private String listDate;
        private String locale;
        private String market;

        @JsonProperty("market_cap")
        private long marketCap;
        private String name;

        @JsonProperty("phone_number")
        private String phoneNumber;

        @JsonProperty("primary_exchange")
        private String primaryExchange;

        @JsonProperty("round_lot")
        private int roundLot;

        @JsonProperty("share_class_figi")
        private String shareClassFigi;

        @JsonProperty("share_class_shares_outstanding")
        private long shareClassSharesOutstanding;

        @JsonProperty("sic_code")
        private String sicCode;

        @JsonProperty("sic_description")
        private String sicDescription;
        private String ticker;

        @JsonProperty("ticker_root")
        private String tickerRoot;

        @JsonProperty("total_employees")
        private long totalEmployees;
        private String type;

        @JsonProperty("weighted_shares_outstanding")
        private long weightedSharesOutstanding;
        private List<Event> events;

        // Getters and Setters

        // Address Class
        public static class Address {
            private String address1;
            private String city;

            @JsonProperty("postal_code")
            private String postalCode;
            private String state;

            public String getAddress1() {
                return address1;
            }

            public void setAddress1(String address1) {
                this.address1 = address1;
            }

            public String getCity() {
                return city;
            }

            public void setCity(String city) {
                this.city = city;
            }

            public String getPostalCode() {
                return postalCode;
            }

            public void setPostalCode(String postalCode) {
                this.postalCode = postalCode;
            }

            public String getState() {
                return state;
            }

            public void setState(String state) {
                this.state = state;
            }
        }

        // Branding Class
        public static class Branding {
            @JsonProperty("icon_url")
            private String iconUrl;

            @JsonProperty("logo_url")

            private String logoUrl;

            public String getIconUrl() {
                return iconUrl;
            }

            public void setIconUrl(String iconUrl) {
                this.iconUrl = iconUrl;
            }

            public String getLogoUrl() {
                return logoUrl;
            }

            public void setLogoUrl(String logoUrl) {
                this.logoUrl = logoUrl;
            }
        }

        // Event Class
        public static class Event {
            private String date;

            @JsonProperty("ticker_change")
            private TickerChange tickerChange;
            private String type;

            public String getDate() {
                return date;
            }

            public void setDate(String date) {
                this.date = date;
            }

            public TickerChange getTickerChange() {
                return tickerChange;
            }

            public void setTickerChange(TickerChange tickerChange) {
                this.tickerChange = tickerChange;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            // TickerChange Class
            public static class TickerChange {
                private String ticker;

                public String getTicker() {
                    return ticker;
                }

                public void setTicker(String ticker) {
                    this.ticker = ticker;
                }
            }
        }


            public boolean isActive() {
                return active;
            }

            public void setActive(boolean active) {
                this.active = active;
            }

            public Address getAddress() {
                return address;
            }

            public void setAddress(Address address) {
                this.address = address;
            }

            public Branding getBranding() {
                return branding;
            }

            public void setBranding(Branding branding) {
                this.branding = branding;
            }

            public String getCik() {
                return cik;
            }

            public void setCik(String cik) {
                this.cik = cik;
            }

            public String getCompositeFigi() {
                return compositeFigi;
            }

            public void setCompositeFigi(String compositeFigi) {
                this.compositeFigi = compositeFigi;
            }

            public String getCurrencyName() {
                return currencyName;
            }

            public void setCurrencyName(String currencyName) {
                this.currencyName = currencyName;
            }

            public String getDescription() {
                return description;
            }

            public void setDescription(String description) {
                this.description = description;
            }

            public String getHomepageUrl() {
                return homepageUrl;
            }

            public void setHomepageUrl(String homepageUrl) {
                this.homepageUrl = homepageUrl;
            }

            public String getListDate() {
                return listDate;
            }

            public void setListDate(String listDate) {
                this.listDate = listDate;
            }

            public String getLocale() {
                return locale;
            }

            public void setLocale(String locale) {
                this.locale = locale;
            }

            public String getMarket() {
                return market;
            }

            public void setMarket(String market) {
                this.market = market;
            }

            public long getMarketCap() {
                return marketCap;
            }

            public void setMarketCap(long marketCap) {
                this.marketCap = marketCap;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getPhoneNumber() {
                return phoneNumber;
            }

            public void setPhoneNumber(String phoneNumber) {
                this.phoneNumber = phoneNumber;
            }

            public String getPrimaryExchange() {
                return primaryExchange;
            }

            public void setPrimaryExchange(String primaryExchange) {
                this.primaryExchange = primaryExchange;
            }

            public int getRoundLot() {
                return roundLot;
            }

            public void setRoundLot(int roundLot) {
                this.roundLot = roundLot;
            }

            public String getShareClassFigi() {
                return shareClassFigi;
            }

            public void setShareClassFigi(String shareClassFigi) {
                this.shareClassFigi = shareClassFigi;
            }

            public long getShareClassSharesOutstanding() {
                return shareClassSharesOutstanding;
            }

            public void setShareClassSharesOutstanding(long shareClassSharesOutstanding) {
                this.shareClassSharesOutstanding = shareClassSharesOutstanding;
            }

            public String getSicCode() {
                return sicCode;
            }

            public void setSicCode(String sicCode) {
                this.sicCode = sicCode;
            }

            public String getSicDescription() {
                return sicDescription;
            }

            public void setSicDescription(String sicDescription) {
                this.sicDescription = sicDescription;
            }

            public String getTicker() {
                return ticker;
            }

            public void setTicker(String ticker) {
                this.ticker = ticker;
            }

            public String getTickerRoot() {
                return tickerRoot;
            }

            public void setTickerRoot(String tickerRoot) {
                this.tickerRoot = tickerRoot;
            }

            public long getTotalEmployees() {
                return totalEmployees;
            }

            public void setTotalEmployees(long totalEmployees) {
                this.totalEmployees = totalEmployees;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public long getWeightedSharesOutstanding() {
                return weightedSharesOutstanding;
            }

            public void setWeightedSharesOutstanding(long weightedSharesOutstanding) {
                this.weightedSharesOutstanding = weightedSharesOutstanding;
            }

            public List<Event> getEvents() {
                return events;
            }

            public void setEvents(List<Event> events) {
                this.events = events;
            }

    }

    public static TickerDetailsResponse fromJson(String json) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(json, TickerDetailsResponse.class);
    }
}

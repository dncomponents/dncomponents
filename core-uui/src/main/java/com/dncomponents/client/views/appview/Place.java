package com.dncomponents.client.views.appview;

public class Place {

    protected PlaceRegister placeRegister;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        return true;
    }

    public static abstract class PlaceRegister<T extends Place> {

        public static final String DIVIDER = ":";

        public abstract String getHistoryToken();

        public abstract T getPlaceFromToken(String token);

        public abstract String getTokenFromPlace(T place);

        public abstract AbstractActivity getActivity(T place);

        public abstract Class<? extends Place> forPlace();
    }
}

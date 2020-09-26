package com.example.coronaupdateapp;

public class Event {

    /** Title of the earthquake event */
    public final String country;

    /** Time that the earthquake happened (in milliseconds) */
    public final String  cases;

    /** Whether or not a tsunami alert was issued (1 if it was issued, 0 if no alert was issued) */


    /**
     * Constructs a new {@link Event}.
     *
     *
     */
    public Event(String eventTitle,String cases) {
        country = eventTitle;
        this.cases=cases;
    }
}

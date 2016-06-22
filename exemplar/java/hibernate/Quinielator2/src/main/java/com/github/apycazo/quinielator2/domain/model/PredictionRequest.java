
package com.github.apycazo.quinielator2.domain.model;

public class PredictionRequest {
    
    private int year;
    private int day;
    private String predictor;
    private String stringValue;

    public PredictionRequest () {}
    
    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public String getPredictor() {
        return predictor;
    }

    public void setPredictor(String predictor) {
        this.predictor = predictor;
    }
    
    public void setStringValue (String stringValue) {
        this.stringValue = stringValue;
    }
    
    public String getStringValue () {
        return this.stringValue;
    }
    
    @Override
    public String toString () {
        return String.format("Request for year %d, day %02d. Using algorithm %s", year, day, predictor);
    }
    
    
    
}

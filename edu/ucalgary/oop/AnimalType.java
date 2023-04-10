package edu.ucalgary.oop;

public enum AnimalType {
    COYOTE(10, 5, 5, 19, 22),
    FOX(5, 5, 5, 0, 3),
    PORCUPINE(0, 5, 10, 19, 22),
    RACCOON(0, 5, 5, 0, 3),
    BEAVER(0, 5, 5, 8, 11);

    private final int feedingPrepTime;
    private final int feedingTime;
    private final int cleaningTime;
    private final int feedingStartHour;
    private final int feedingEndHour;

    AnimalType(int feedingPrepTime, int feedingTime, int cleaningTime, int feedingStartHour, int feedingEndHour) {
        this.feedingPrepTime = feedingPrepTime;
        this.feedingTime = feedingTime;
        this.cleaningTime = cleaningTime;
        this.feedingStartHour = feedingStartHour;
        this.feedingEndHour = feedingEndHour;
    }

    // Add getters as needed
    public int getFeedingPrepTime() {
        return feedingPrepTime;
    }
    
    public int getFeedingTime() {
        return feedingTime;
    }
    
    public int getCleaningTime() {
        return cleaningTime;
    }
    
    public int getFeedingStartHour() {
        return feedingStartHour;
    }
    
    public int getFeedingEndHour() {
        return feedingEndHour;
    }
}
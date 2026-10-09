package com.kevinsm12.researchmilestone;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "events")
public class EventEntity {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public String eventName;
    public String eventDate;
    public String eventRoute;
    public String allocatedBudget;
    public String description;

    public EventEntity(String eventName, String eventDate, String eventRoute, String allocatedBudget, String description) {
        this.eventName = eventName;
        this.eventDate = eventDate;
        this.eventRoute = eventRoute;
        this.allocatedBudget = allocatedBudget;
        this.description = description;
    }
}

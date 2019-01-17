package me.minu94.demoinfleanrestapi.events;


import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class EventTest {

    @Test
    public void  builder(){
        Event event = Event.builder()
                .name("Inflearn Spring Rest API")
                .description("REST API development with Srping")
                .build();
        assertThat(event).isNotNull();
    }

    @Test
    public void javaBean(){
        //  Given
        String name = "Event";
        String description = "Description 1";

        //  When
        Event event = new Event();
        event.setName(name);
        event.setDescription(description);

        //  Then
        assertThat(event.getName()).isEqualTo(name);
        assertThat(event.getDescription()).isEqualTo(description);
    }

}
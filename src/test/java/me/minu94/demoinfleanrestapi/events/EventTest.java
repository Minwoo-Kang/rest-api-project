package me.minu94.demoinfleanrestapi.events;


import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(JUnitParamsRunner.class)
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


    @Test
    @Parameters(method = "paramsForTestFree")   //You can explicit annotation value using name convention like changing method name as "parametersForTestFree"
    public void testFree(int basePrice,int maxPrice, boolean isFree){
        //Given
        Event event = Event.builder()
                .basePrice(basePrice)
                .maxPrice(maxPrice)
                .build();

        //When
        event.update();

        //Then
        assertThat(event.isFree()).isEqualTo(isFree);
    }

    private Object[] paramsForTestFree(){
        return new Object[]{
                new Object[] {0,0, true},
                new Object[] {100,0,false},
                new Object[] {0,100,false},
                new Object[] {100,200, false}
        };
    }

    @Test
    @Parameters(method = "paramsForTestOffline")
    public void testOffline(String location, boolean isOffline){
        //Given
        Event event = Event.builder()
                .basePrice(0)
                .maxPrice(0)
                .location(location)
                .build();

        //When
        event.update();

        //Then
        assertThat(event.isOffline()).isEqualTo(isOffline);
    }


    private Object[] paramsForTestOffline(){
        return new Object[]{
            new Object[] {"강남 어딘가", true},
            new Object[] {null, false},
            new Object[] {"" , false},
        };
    }

}
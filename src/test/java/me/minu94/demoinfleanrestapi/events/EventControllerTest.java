package me.minu94.demoinfleanrestapi.events;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.regex.Matcher;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class EventControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    public void createEvent_badRequest() throws Exception {
        Event event = Event.builder()
                .id(100)
                .name("Spring")
                .description("REST API Development with Srping")
                .beginEnrollmentDateTime(LocalDateTime.of(2018 , 11, 23, 14 , 21))
                .closeEnrollmentDateTime(LocalDateTime.of(2018, 11, 24, 14 ,21))
                .beginEventDateTime(LocalDateTime.of(2018 , 11, 25, 14, 21))
                .endEventDateTime(LocalDateTime.of(2018,11,26,14,21))
                .basePrice(100)
                .maxPrice(200)
                .limitOfEnrollment(100)
                .location("강남역 D2 스타텁 팩토리")
                .free(true)
                .offline(false)
                .build();


        mockMvc.perform(post("/api/events")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaTypes.HAL_JSON)
                .content(objectMapper.writeValueAsString(event)))
                .andDo(print()) //어떤 요청과 응답이 왔는지 print됨
                .andExpect(status().isBadRequest())
                ;
        //isCreated == 201 response

    }

    @Test
    public void createEvent() throws Exception {
        Event event = Event.builder()
                .id(100)
                .name("Spring")
                .description("REST API Development with Srping")
                .beginEnrollmentDateTime(LocalDateTime.of(2018 , 11, 23, 14 , 21))
                .closeEnrollmentDateTime(LocalDateTime.of(2018, 11, 24, 14 ,21))
                .beginEventDateTime(LocalDateTime.of(2018 , 11, 25, 14, 21))
                .endEventDateTime(LocalDateTime.of(2018,11,26,14,21))
                .basePrice(100)
                .maxPrice(200)
                .limitOfEnrollment(100)
                .location("강남역 D2 스타텁 팩토리")
                .free(true)
                .offline(false)
                .build();


        mockMvc.perform(post("/api/events")
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .accept(MediaTypes.HAL_JSON)
                    .content(objectMapper.writeValueAsString(event)))
                .andDo(print()) //어떤 요청과 응답이 왔는지 print됨
                .andExpect(status().isCreated())
                .andExpect(jsonPath("id").exists())
                .andExpect(jsonPath("id").value(Matchers.not(100)))
                .andExpect(jsonPath("free").value(Matchers.not(true)));
        //isCreated == 201 response

    }
}

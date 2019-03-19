
package me.minu94.demoinfleanrestapi.events;


import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.net.URI;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@Controller
@RequestMapping(value = "api/events", produces = MediaTypes.HAL_JSON_UTF8_VALUE) //produces설정에 의해 모든 응답의 contentType은 HAL_JSON_UTF8_VALUE가 됨
public class EventController {

    private final ModelMapper modelMapper;


    private final EventRepository eventRepository;

    public EventController(ModelMapper modelMapper, EventRepository eventRepository) {
        this.modelMapper = modelMapper;
        this.eventRepository = eventRepository;
    }

    @PostMapping
    public ResponseEntity createEvent(@RequestBody EventDto eventDto){
        Event event = modelMapper.map(eventDto, Event.class);   //입력받은 dto객체를 원래 entity객체로 변경

        Event newEvent = this.eventRepository.save(event);

        URI createUri = linkTo(EventController.class).slash(newEvent.getId()).toUri();

        return ResponseEntity.created(createUri).body(event);
    }
}


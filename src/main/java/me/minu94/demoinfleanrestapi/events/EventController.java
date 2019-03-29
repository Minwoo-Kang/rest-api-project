
package me.minu94.demoinfleanrestapi.events;


import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.net.URI;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@Controller
@RequestMapping(value = "api/events", produces = MediaTypes.HAL_JSON_UTF8_VALUE) //produces설정에 의해 모든 응답의 contentType은 HAL_JSON_UTF8_VALUE가 됨
public class EventController {

    private final ModelMapper modelMapper;


    private final EventRepository eventRepository;

    private final EventValidator eventValidator;

    public EventController(ModelMapper modelMapper, EventRepository eventRepository, EventValidator eventValidator) {
        this.modelMapper = modelMapper;
        this.eventRepository = eventRepository;
        this.eventValidator = eventValidator;
    }

    @PostMapping
    public ResponseEntity createEvent(@RequestBody @Valid EventDto eventDto, Errors errors){
        if (errors.hasErrors()){    //data binding error check
            return ResponseEntity.badRequest().body(errors);
        }

        eventValidator.validate(eventDto, errors);
        if (errors.hasErrors()){    //data value validation
            return ResponseEntity.badRequest().body(errors);
        }

        Event event = modelMapper.map(eventDto, Event.class);   //입력받은 dto객체를 원래 entity객체로 변경
        event.update();
        Event newEvent = this.eventRepository.save(event);


        ControllerLinkBuilder selfLinkBuilder = linkTo(EventController.class).slash(newEvent.getId());
        URI createUri = selfLinkBuilder.toUri();

        //Add _links for HATEOAS
        EventResource eventResource = new EventResource(event);
        eventResource.add(linkTo(EventController.class).withRel("query-events"));
        eventResource.add(linkTo(EventController.class).withRel("update-event"));
        return ResponseEntity.created(createUri).body(eventResource);
    }
}


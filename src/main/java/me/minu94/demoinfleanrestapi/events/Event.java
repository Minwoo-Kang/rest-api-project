package me.minu94.demoinfleanrestapi.events;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Builder @AllArgsConstructor @NoArgsConstructor
@Getter @Setter @EqualsAndHashCode(of = "id")
@Entity
public class Event {

    @Id @GeneratedValue
    private Integer id;//primary key
    private String name;
    private String description;
    private LocalDateTime beginEnrollmentDateTime;
    private LocalDateTime closeEnrollmentDateTime;
    private LocalDateTime beginEventDateTime;
    private LocalDateTime endEventDateTime;
    private String location; // (optional) 이게 없으면 온라인 모임
    private int basePrice; // (optional)
    private int maxPrice; // (optional)
    private int limitOfEnrollment;
    private boolean offline;
    private boolean free;
    @Enumerated(EnumType.STRING) //default는 ordinary지만, string으로 저장하는 이유는 나중에 data가 추가될시 순서가 꼬일 수 있음.
    private EventStatus eventStatus;


    public void update(){
        //Update free
        if (this.basePrice ==0 && this.maxPrice == 0){
            this.free = true;
        }else{
            this.free = false;
        }
        //Update offline
        if (this.location == null || this.location.isEmpty() ){
            this.offline = false;
        }else{
            this.offline = true;
        }
    }

}

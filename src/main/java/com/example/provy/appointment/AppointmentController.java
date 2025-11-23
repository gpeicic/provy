package com.example.provy.appointment;

import com.example.provy.appointment.DTO.AppointmentRequestDTO;
import com.example.provy.appointment.DTO.AppointmentResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/appointments")
public class AppointmentController {
    private final AppointmentService appointmentService;

    public AppointmentController(AppointmentService appointmentService){
        this.appointmentService = appointmentService;
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER','PROVIDER','ADMIN')")
    public ResponseEntity<AppointmentResponseDTO> getById(@PathVariable Long id){
        AppointmentResponseDTO appointment = appointmentService.getById(id);
        return ResponseEntity.ok(appointment);
    }
    @GetMapping("/provider/{id}")
    @PreAuthorize("hasAnyRole('USER','PROVIDER','ADMIN')")
    public ResponseEntity<List<AppointmentResponseDTO>> getAllByProviderId(@PathVariable Long id){
        List<AppointmentResponseDTO> appointments = appointmentService.getAllByProvider(id);
        return ResponseEntity.status(HttpStatus.OK).body(appointments);
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('USER','PROVIDER','ADMIN')")
    public ResponseEntity<Void> bookAppointment(@RequestBody AppointmentRequestDTO appointment){

        appointmentService.bookAppointment(appointment);
      return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER','PROVIDER','ADMIN')")
    public ResponseEntity<Void> deleteAppointmentById(@PathVariable Long id){
        appointmentService.deleteAppointmentById(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}

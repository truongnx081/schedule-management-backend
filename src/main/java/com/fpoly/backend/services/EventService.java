package com.fpoly.backend.services;

import com.fpoly.backend.dto.EventDTO;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public interface EventService {
    List<EventDTO> getAllEvent();
    List<EventDTO> getAllEventByArea(Integer areaId);
    EventDTO create(EventDTO request, MultipartFile file);
    EventDTO update(EventDTO request, MultipartFile file, Integer id);
    void delete(Integer id);
    void importEvent(MultipartFile file);
}

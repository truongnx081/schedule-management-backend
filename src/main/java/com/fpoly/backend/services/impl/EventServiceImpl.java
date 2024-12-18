package com.fpoly.backend.services.impl;

import com.fpoly.backend.dto.CloudinaryResponse;
import com.fpoly.backend.dto.EventDTO;
import com.fpoly.backend.entities.*;
import com.fpoly.backend.exception.AppUnCheckedException;
import com.fpoly.backend.mapper.EventMapper;
import com.fpoly.backend.repository.AreaRepository;
import com.fpoly.backend.repository.EventRepository;
import com.fpoly.backend.services.CloudinaryService;
import com.fpoly.backend.services.EventService;
import com.fpoly.backend.services.IdentifyUserAccessService;
import com.fpoly.backend.until.ExcelUtility;
import com.fpoly.backend.until.FileUpload;
import jakarta.persistence.EntityNotFoundException;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor
@FieldDefaults(makeFinal = true,level = AccessLevel.PRIVATE)
public class EventServiceImpl implements EventService {

    EventRepository eventRepository;
    EventMapper eventMapper;
    AreaRepository areaRepository;
    IdentifyUserAccessService identifyUserAccessService;
    CloudinaryService cloudinaryService;
    private final ExcelUtility excelUtility;

    @Override
    public List<EventDTO> getAllEvent() {
        return eventRepository.findAll().stream().map(eventMapper::toDTO).toList();
    }

    @Override
    public List<EventDTO> getAllEventByArea(Integer areaId) {
        Area area = areaRepository.findById(areaId).orElseThrow(()->
                new RuntimeException("Khu vực này không tồn tại"));
        return eventRepository.findAllByArea(area).stream()
                .map(eventMapper::toDTO).toList();
    }

    @Override
    public EventDTO create(EventDTO request, MultipartFile file) {
        Admin admin = identifyUserAccessService.getAdmin();
        Event event = eventMapper.toEntity(request);

        if (file != null && !file.isEmpty()) {
            FileUpload.assertAllowed(file, FileUpload.IMAGE_PATTERN);
            final String fileName = FileUpload.getFileName(file.getOriginalFilename());
            final CloudinaryResponse response = cloudinaryService.uploadFile(file, fileName);
            event.setImage(response.getPublicId());
        }

        Area area = areaRepository.findById(request.getAreaId()).orElseThrow(()->
                new RuntimeException("Khu vực này không tồn tại"));

        event.setCreatedBy(admin.getCode());
        event.setAdmin(admin);
        event.setArea(area);
        event.setId(null);

        return eventMapper.toDTO(eventRepository.save(event));
    }

    @Override
    public EventDTO update(EventDTO request, MultipartFile file, Integer id) {
        Admin admin = identifyUserAccessService.getAdmin();

        Event event = eventRepository.findById(id).orElseThrow(()->
                new RuntimeException("Sự kiện này không tồn tại"));

        eventMapper.updateEvent(event, request);

        if (file != null && !file.isEmpty()) {
            FileUpload.assertAllowed(file, FileUpload.IMAGE_PATTERN);
            final String fileName = FileUpload.getFileName(file.getOriginalFilename());
            final CloudinaryResponse response = cloudinaryService.uploadFile(file, fileName);
            event.setImage(response.getPublicId());
        }

        Area area = areaRepository.findById(request.getAreaId()).orElseThrow(()->
                new RuntimeException("Khu vực này không tồn tại"));

        event.setUpdatedBy(admin.getCode());
        event.setUpdatedAt(new Date());
        event.setAdmin(admin);
        event.setArea(area);

        return eventMapper.toDTO(eventRepository.save(event));
    }

    @Override
    public void delete(Integer id) {
        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Sinh viên này không tồn tại"));

        if (event.getImage() != null) {
            cloudinaryService.deleteFile(event.getImage());
        }

        eventRepository.delete(event);
    }

    @Override
    public void importEvent(MultipartFile file) {
        try {
            List<Event> eventList = excelUtility.excelToEventList(file.getInputStream());
            eventRepository.saveAll(eventList);
        } catch (IOException ex) {
            throw new RuntimeException("Xuất hiện lỗi trong Excel: " + ex.getMessage());
        }
    }
}

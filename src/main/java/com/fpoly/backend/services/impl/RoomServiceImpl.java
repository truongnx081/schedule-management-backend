package com.fpoly.backend.services.impl;

import com.fpoly.backend.dto.RoomDTO;
import com.fpoly.backend.entities.Admin;
import com.fpoly.backend.entities.Room;
import com.fpoly.backend.mapper.RoomMapper;
import com.fpoly.backend.repository.RoomRepository;
import com.fpoly.backend.services.IdentifyUserAccessService;
import com.fpoly.backend.services.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoomServiceImpl implements RoomService {
    @Autowired
    RoomRepository roomRepository;
    @Autowired
    private IdentifyUserAccessService identifyUserAccessService;
    @Autowired
    private RoomMapper roomMapper;

    @Override
    public Room findById(Integer id) {
        return roomRepository.findById(id).orElse(null);
    }

    @Override
    public List<Map<String,Object>> getAvailableRooms(Integer buildingId, LocalDate date) {
        return roomRepository.findAvailableRooms(buildingId, date);

    }

    @Override
    public List<RoomDTO> getAllRoomByAdminArea() {
        Admin admin = identifyUserAccessService.getAdmin();
        return roomRepository.findAllByBuildingAreaId(admin.getArea().getId())
                .stream()
                .map(roomMapper::toDTO).toList();
    }

    @Override
    public List<Map<String, Object>> findAvailableRoomsByDateAndShift(LocalDate date, Integer shift) {
        List<Integer> usedScheduleRooms = roomRepository.findScheduleUsedRoomsByDateAndShift(date, shift);
        List<Integer> usedExamScheduleRomms = roomRepository.findExamScheduleUsedRoomsByDateAndShift(date, shift);
        List<Integer> usedRetakeScheduleRooms = roomRepository.findRetakeScheduleUsedRoomsByDateAndShift(date, shift);

        List<Integer> allUsedRoom = new ArrayList<Integer>();
        allUsedRoom.addAll(usedScheduleRooms);
        allUsedRoom.addAll(usedExamScheduleRomms);
        allUsedRoom.addAll(usedRetakeScheduleRooms);

        List<Map<String, Object>> rooms = roomRepository.findAllRoomsIdAndName();

        List<Map<String, Object>> availableRooms = rooms.stream().
                                                        filter(room -> !allUsedRoom.contains(room.get("room_id"))).
                                                        collect(Collectors.toList());

        return availableRooms;
    }
}

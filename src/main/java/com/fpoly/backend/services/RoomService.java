package com.fpoly.backend.services;

import com.fpoly.backend.dto.RoomDTO;
import com.fpoly.backend.entities.Room;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Service
public interface RoomService {
    Room findById(Integer id);

    public List<Map<String,Object>> getAvailableRooms(Integer buildingId, LocalDate date);

    List<RoomDTO> getAllRoomByAdminArea();

    List<Map<String, Object>> findAvailableRoomsByDateAndShift (LocalDate date, Integer shift);
}

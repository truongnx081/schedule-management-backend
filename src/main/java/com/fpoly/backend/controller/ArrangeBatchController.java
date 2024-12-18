package com.fpoly.backend.controller;

import com.fpoly.backend.dto.ArrangeBatchDTO;
import com.fpoly.backend.dto.AttendanceDTO;
import com.fpoly.backend.dto.Response;
import com.fpoly.backend.entities.ArrangeBatch;
import com.fpoly.backend.exception.AppUnCheckedException;
import com.fpoly.backend.services.ArrangeBatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/arrangebatchs")
@Validated
public class ArrangeBatchController {
    @Autowired
    private ArrangeBatchService arrangeBatchService;

    @PostMapping("/create")
    public ResponseEntity<Response> create(@RequestBody ArrangeBatchDTO arrangeBatchDTO) {
        try {
            ArrangeBatchDTO batchDTO = arrangeBatchService.createArrangeBatch(arrangeBatchDTO);
            return ResponseEntity.ok(new Response(LocalDateTime.now(), batchDTO, "Arrange batch được tạo thành công !", HttpStatus.OK.value()));
        }
        catch (AppUnCheckedException e) {
            return ResponseEntity.status(e.getStatus()).body(new Response(LocalDateTime.now(), null, e.getMessage(), e.getStatus().value()));
        }
    }

    @PutMapping("/update")
    public ResponseEntity<Response> update(@RequestParam Integer arrangeBatchId,@RequestBody ArrangeBatchDTO arrangeBatchDTO) {
        try {
            ArrangeBatchDTO batchDTO = arrangeBatchService.updateArrangeBatch(arrangeBatchId,arrangeBatchDTO);
            return ResponseEntity.ok(new Response(LocalDateTime.now(), batchDTO, "Arrange batch được cập nhật thành công !", HttpStatus.OK.value()));
        }
        catch (AppUnCheckedException e) {
            return ResponseEntity.status(e.getStatus()).body(new Response(LocalDateTime.now(), null, e.getMessage(), e.getStatus().value()));
        }
    }

    // xoa arrange batch
    @DeleteMapping("/delete")
    public ResponseEntity<Response> delete(@RequestParam Integer clazzId) {
        try {
            arrangeBatchService.deleteArrangeBatch(clazzId);
            return ResponseEntity.ok(new Response(LocalDateTime.now(), null, "Arrange batch đã được xóa thành công!", HttpStatus.OK.value()));
        } catch (AppUnCheckedException e) {
            return ResponseEntity.status(e.getStatus()).body(new Response(LocalDateTime.now(), null, e.getMessage(), e.getStatus().value()));
        }
    }

    @PutMapping("/do-arrange-batch")
    public ResponseEntity<Response> doArrangeBatch(@RequestBody List<ArrangeBatchDTO> arrangeBatchDTOS) {
        try {
            List<ArrangeBatchDTO> doArrangeBatch = arrangeBatchService.doArrangeBatch(arrangeBatchDTOS);
            return ResponseEntity.ok(new Response(LocalDateTime.now(), doArrangeBatch, "Xếp lịch thi thành công thành công", HttpStatus.OK.value()));
        } catch (AppUnCheckedException e) {
            return ResponseEntity.status(e.getStatus()).body(new Response(LocalDateTime.now(), null, e.getMessage(), e.getStatus().value()));
        }
    }
}

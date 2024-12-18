package com.fpoly.backend.controller;

import com.fpoly.backend.dto.BlockDTO;
import com.fpoly.backend.dto.Response;
import com.fpoly.backend.exception.AppUnCheckedException;
import com.fpoly.backend.services.BlockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/blocks")
@Validated
public class BlockController {

    @Autowired
    BlockService blockService;

    @GetMapping
    public ResponseEntity<Response> getAllBlock(){
        try{
            List<BlockDTO> blocks = blockService.getAllBlocks();
            return ResponseEntity.ok(new Response(LocalDateTime.now(),blocks, "Lấy toàn bộ block thành công", HttpStatus.OK.value()));
        }catch (AppUnCheckedException e){
            return ResponseEntity.status(e.getStatus()).body(new Response(LocalDateTime.now(),null,e.getMessage(),e.getStatus().value()));
        }

    }

    @GetMapping("/get-all-blocks-with-default")
    public ResponseEntity<Response> getAllBlocksWithDefalut(){
        try{
            List<Map<String,Object>> blocks = blockService.findAllBlocksWithDefault();
            return ResponseEntity.ok(new Response(LocalDateTime.now(),blocks, "Lấy toàn bộ block thành công", HttpStatus.OK.value()));
        }catch (AppUnCheckedException e){
            return ResponseEntity.status(e.getStatus()).body(new Response(LocalDateTime.now(),null,e.getMessage(),e.getStatus().value()));
        }

    }
}

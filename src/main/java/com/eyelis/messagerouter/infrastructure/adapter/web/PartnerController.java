package com.eyelis.messagerouter.infrastructure.adapter.web;

import com.eyelis.messagerouter.application.usecase.CreatePartnerUseCase;
import com.eyelis.messagerouter.application.usecase.GetPartnerUseCase;
import com.eyelis.messagerouter.application.usecase.ListPartnerUseCase;
import com.eyelis.messagerouter.domain.model.Partner;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api/partners")
@RequiredArgsConstructor
@Slf4j
public class PartnerController {

    private final CreatePartnerUseCase createPartnerUseCase;
    private final GetPartnerUseCase getPartnerUseCase;
    private final ListPartnerUseCase listPartnerUseCase;

    @PostMapping
    public ResponseEntity<Partner> createPartner(@RequestBody
                                                 Partner partner
    ) {
        log.info(STR."Creating partner [\{partner}]");
        Partner savedPartner = createPartnerUseCase.execute(partner);
        URI location = URI.create("/api/partners/" + savedPartner.id());
        return ResponseEntity.created(location).body(savedPartner);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Partner> getPartner(@PathVariable Long id) {
        log.info(STR."Getting partner by id [\{id}] ");
        Optional<Partner> partner = getPartnerUseCase.execute(id);
        return partner.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<Partner>> listPartners() {
        log.info("Listing partners");
        return ResponseEntity.ok(listPartnerUseCase.execute());
    }

}

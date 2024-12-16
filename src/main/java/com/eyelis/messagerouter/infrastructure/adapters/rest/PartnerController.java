package com.eyelis.messagerouter.infrastructure.adapters.rest;

import com.eyelis.messagerouter.application.usecases.CreatePartnerUseCase;
import com.eyelis.messagerouter.application.usecases.DeletePartnerUseCase;
import com.eyelis.messagerouter.application.usecases.GetPartnerUseCase;
import com.eyelis.messagerouter.application.usecases.ListPartnerUseCase;
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
    private final DeletePartnerUseCase deletePartnerUseCase;

    @PostMapping
    public ResponseEntity<Partner> createPartner(@RequestBody
                                                 Partner partner
    ) {
        log.info(STR."Creating partner [\{partner}]");
        Partner savedPartner = createPartnerUseCase.execute(partner);
        URI location = URI.create(STR."/api/partners/\{savedPartner.id()}");
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


    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        log.info(STR."Deleting partner by id [\{id}] ");
        deletePartnerUseCase.execute(id);
    }

}

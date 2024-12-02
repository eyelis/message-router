package com.eyelis.messagerouter.infrastructure.adapter.web;

import com.eyelis.messagerouter.application.usecase.CreatePartnerUseCase;
import com.eyelis.messagerouter.application.usecase.GetPartnerUseCase;
import com.eyelis.messagerouter.application.usecase.ListPartnerUseCase;
import com.eyelis.messagerouter.domain.model.Partner;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api/partners")
@RequiredArgsConstructor
public class PartnerController {

    private final CreatePartnerUseCase createPartnerUseCase;
    private final GetPartnerUseCase getPartnerUseCase;
    private final ListPartnerUseCase listPartnerUseCase;

    @PostMapping
    public ResponseEntity<Partner> createPartner(@RequestBody
                                                 Partner partner
    ) {
        Partner savedPartner = createPartnerUseCase.execute(partner);
        URI location = URI.create("/api/partners/" + savedPartner.id());
        return ResponseEntity.created(location).body(savedPartner);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Partner> getPartner(@PathVariable Long id) {
        Optional<Partner> partner = getPartnerUseCase.execute(id);
        return partner.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<Partner>> listPartners() {
        return ResponseEntity.ok(listPartnerUseCase.execute());
    }

}

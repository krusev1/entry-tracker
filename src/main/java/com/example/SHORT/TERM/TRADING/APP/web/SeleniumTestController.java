package com.example.SHORT.TERM.TRADING.APP.web;

import com.example.SHORT.TERM.TRADING.APP.entity.dto.view.EntryViewDTO;
import com.example.SHORT.TERM.TRADING.APP.service.EntryService;
import com.example.SHORT.TERM.TRADING.APP.service.SeleniumService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Controller
@CrossOrigin("*")
@RequestMapping("/test")
public class SeleniumTestController {

    private final SeleniumService seleniumService;
    private final EntryService entryService;

    public SeleniumTestController(SeleniumService seleniumService, EntryService entryService) {
        this.seleniumService = seleniumService;
        this.entryService = entryService;
    }

//    @GetMapping()
//    public String setSeleniumServiceTest1() throws InterruptedException {
//        seleniumService.openPage("https://jup.ag/swap/CHONKY-USDC", "968987.614935", "40.00");
//        seleniumService.openPage("https://jup.ag/swap/BORK_4jZXkSNgTQKCDb36ECZ6a2aNzcUniGcDeXgTdtM2HxAX-USDC", "31977.16981512", "30.00");
//        seleniumService.openPage("https://jup.ag/swap/APING_94sFWT94hg6qK9VtYwGz8VxbyEMaXf9H2U3HTTbofimy-USDC", "185964532.03146", "30.00");
//        return "Cool";
//    }

    @PostMapping("/{id}")
    public void openSelenium(@PathVariable Long id) throws InterruptedException {

        EntryViewDTO entryViewDTOById = entryService.getEntryViewDTOById(id);

        seleniumService.openPage(entryViewDTOById.getTokenName(), entryViewDTOById.getExchangeUrl(), entryViewDTOById.getTokenAmount().toString(), entryViewDTOById.getTotalEntryAmount().toString());
    }

//    @GetMapping("/getPrices")
//    public List<BigDecimal> getPrices(){
//        Map<String, List<BigDecimal>> currentPrices = seleniumService.getCurrentPrices();
//
//        return currentPrices.values().stream().findFirst().orElse(null);
//    }

}

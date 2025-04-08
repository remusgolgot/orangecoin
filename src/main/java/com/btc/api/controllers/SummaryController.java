package com.btc.api.controllers;

import com.btc.api.model.ResponseModel;
import com.btc.api.model.Summary;
import com.btc.api.services.SummaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.btc.api.messages.Responses.*;

@RestController
@RequestMapping("/api/address")
public class SummaryController extends BaseController {

    @Autowired
    SummaryService summaryService;

    /**
     *
     * @return summary information
     */
    @GetMapping(value = "/summary")
    public ResponseModel<Summary> getSummary() {
        Summary summaryDto = summaryService.getSummary();
        return getResponseModel(summaryDto);
    }

    /**
     *
     * @return summary information
     */
    @PutMapping(value = "/summary")
    public ResponseModel<Summary> updateSummary() {
        summaryService.updateSummary();
        return getResponseModel(UPDATE_SUCCESSFUL);
    }

}

package org.medical.hub.controller;

import org.apache.commons.lang3.StringUtils;
import org.medical.hub.common.Common;
import org.medical.hub.common.Routes;
import org.medical.hub.datatable.*;
import org.medical.hub.dto.EcrfDataTable;
import org.medical.hub.models.Medical2Ecrf1;
import org.medical.hub.repository.SearchCriteria;
import org.medical.hub.repository.SearchOperation;
import org.medical.hub.repository.eCRF1Repository;
import org.medical.hub.repository.eCRFSpecification;
import org.medical.hub.request.ECRFDeleteRequest;
import org.medical.hub.services.ECRFService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class ECRFController {

    private final eCRF1Repository eCRF1Repository;
    private final ECRFService eCRFService;

    @Autowired
    public ECRFController(ECRFService eCRFService, eCRF1Repository eCRF1Repository) {
        this.eCRFService = eCRFService;
        this.eCRF1Repository = eCRF1Repository;
    }

    @GetMapping(value = Routes.ECRF.GET, name = "eCRF-get")
    public String index() {

        return "eCRF/list";
    }

    @PostMapping(value = Routes.ECRF.AJAX_GET, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DataTableResponse<EcrfDataTable>> getECRF(@RequestBody DataTableRequest dataTable) throws ParseException {

        eCRFSpecification specification = new eCRFSpecification();
        String value = dataTable.getSearch().getValue();
        if (StringUtils.isNotBlank(value)) {
            var criteria = new SearchCriteria("patID", value, SearchOperation.LIKE);
            specification.add(criteria);
        }

        DataTableOrder order = dataTable.getOrder().stream().findFirst().orElse(null);

        Pageable sortedByName = PageRequest.of(dataTable.getStart(), dataTable.getLength());
        if (order != null) {
            DataTableColumnSpecs dataTableColumnSpecs = dataTable.getColumns().get(order.getColumn());
            if (dataTableColumnSpecs != null) {
                String data = dataTableColumnSpecs.getData();
                if (data.equals("checkbox") || data.equals("action")) {
                    data = "id";
                }
                Sort.Direction dir = (order.getDir().equals("asc")) ? Sort.Direction.ASC : Sort.Direction.DESC;
                Sort by = Sort.by(new Sort.Order(dir, data));
                int pageNumber = DataTableUtils.getPageNumber(dataTable);
                sortedByName = PageRequest.of(pageNumber, dataTable.getLength(), by);

            }
        }

        Page<Medical2Ecrf1> all = eCRF1Repository.findAll(specification, sortedByName);
        List<EcrfDataTable> dataTablesData = new ArrayList<>();
        for (Medical2Ecrf1 ecrf1 : all.getContent()) {
            EcrfDataTable ecrfDataTable = new EcrfDataTable();
            ecrfDataTable.setId(ecrf1.getId());
            ecrfDataTable.setCheckbox(ecrf1.getSurveyTwoId());
            ecrfDataTable.setAction(ecrf1.getSurveyTwoId());
            ecrfDataTable.setPatID(ecrf1.getPatID());
            ecrfDataTable.setFillingStatus(ecrf1.getFillingStatus());

            String format = new SimpleDateFormat("yyyy-MM-dd HH:mm").format(ecrf1.getCreatedAt());
            ecrfDataTable.setCreatedAt(format);

            dataTablesData.add(ecrfDataTable);
        }
        long totalRecord = eCRF1Repository.count();
        long filteredRecord = eCRF1Repository.count(specification);
        DataTableResponse<EcrfDataTable> response = new DataTableResponse<>();
        response.setData(dataTablesData);
        response.setDraw(dataTable.getDraw());
        response.setRecordsFiltered(filteredRecord);
        response.setRecordsTotal(totalRecord);
        return ResponseEntity.ok(response);
    }

    @GetMapping(value = Routes.ECRF.EDIT, name = "edit-eCRF")
    public String edit(@PathVariable String surveyTwoId) {
        return "eCRF/edit";
    }

    @PostMapping(value = Routes.ECRF.DELETE_SELECTED)
    public String delete(ECRFDeleteRequest request, RedirectAttributes ra) {
        List<String> collect = Arrays.stream(request.getIds().split(",")).distinct().collect(Collectors.toList());
        this.eCRFService.deleteECRFs(collect);
        ra.addFlashAttribute(Common.SUCCESS_MESSAGE, "eCRF deleted successfully.");
        return "redirect:" + Routes.ECRF.GET;
    }

    @PostMapping(value = Routes.ECRF.DELETE)
    public String deleteSingleECRF(@PathVariable String surveyTwoId, RedirectAttributes ra) {

        this.eCRFService.deleteECRF(surveyTwoId);
        ra.addFlashAttribute(Common.SUCCESS_MESSAGE, "eCRF deleted successfully.");
        return "redirect:" + Routes.ECRF.GET;
    }

}
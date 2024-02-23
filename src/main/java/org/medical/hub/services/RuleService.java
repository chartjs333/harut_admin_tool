package org.medical.hub.services;

import org.medical.hub.datatable.DataTableRequest;
import org.medical.hub.datatable.DataTableResponse;
import org.medical.hub.dto.RuleDataTable;
import org.medical.hub.models.CompareBetween;
import org.medical.hub.models.FileType;
import org.medical.hub.models.Rule;
import org.medical.hub.request.CreateRuleRequest;

import java.io.FileNotFoundException;
import java.util.List;

public interface RuleService {

    void save(CreateRuleRequest request);

    void update(Long ruleId, CreateRuleRequest request);

    Rule getRuleById(Long id);

    void delete(Long id);

    List<String> getColumnsFromFileType(FileType fileType);

    DataTableResponse<RuleDataTable> getRules(DataTableRequest dataTable);

    List<Rule> findByFileTypeAndCompareBetween(FileType fileType, CompareBetween compareBetween);;
}

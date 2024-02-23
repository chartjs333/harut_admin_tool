package org.medical.hub.services;

import org.apache.commons.lang3.StringUtils;
import org.medical.hub.datatable.*;
import org.medical.hub.dto.EcrfDataTable;
import org.medical.hub.exception.UserNotFoundException;
import org.medical.hub.exception.eCRFNotFoundException;
import org.medical.hub.exception.eCRFReportNotFoundException;
import org.medical.hub.models.*;
import org.medical.hub.repository.*;
import org.medical.hub.request.AssignECRFRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ECRFServiceImpl implements ECRFService {

    private Logger logger = LoggerFactory.getLogger(ECRFServiceImpl.class);

    private final eCRF1Repository eCRF1REpository;
    private final eCRF2Repository eCRF2Repository;
    private final eCRF3Repository eCRF3Repository;
    private final eCRF4Repository eCRF4Repository;
    private final eCRF5Repository eCRF5Repository;
    private final eCRF6Repository eCRF6Repository;
    private final eCRF7Repository eCRF7Repository;
    private final eCRF8Repository eCRF8Repository;
    private final eCRF9Repository eCRF9Repository;
    private final eCRF10Repository eCRF10Repository;
    private final eCRF20Repository eCRF20Repository;
    private final eCRF30Repository eCRF30Repository;

    private final UserRepository userRepository;

    @Autowired
    public ECRFServiceImpl(eCRF1Repository eCRF1REpository,
                           eCRF2Repository eCRF2Repository,
                           eCRF3Repository eCRF3Repository,
                           eCRF4Repository eCRF4Repository,
                           eCRF5Repository eCRF5Repository,
                           eCRF6Repository eCRF6Repository,
                           eCRF7Repository eCRF7Repository,
                           eCRF8Repository eCRF8Repository,
                           eCRF9Repository eCRF9Repository,
                           eCRF10Repository eCRF10Repository,
                           eCRF20Repository eCRF20Repository,
                           eCRF30Repository eCRF30Repository,
                           UserRepository userRepository) {
        this.eCRF1REpository = eCRF1REpository;
        this.eCRF2Repository = eCRF2Repository;
        this.eCRF3Repository = eCRF3Repository;
        this.eCRF4Repository = eCRF4Repository;
        this.eCRF5Repository = eCRF5Repository;
        this.eCRF6Repository = eCRF6Repository;
        this.eCRF7Repository = eCRF7Repository;
        this.eCRF8Repository = eCRF8Repository;
        this.eCRF9Repository = eCRF9Repository;
        this.eCRF10Repository = eCRF10Repository;
        this.eCRF20Repository = eCRF20Repository;
        this.eCRF30Repository = eCRF30Repository;
        this.userRepository = userRepository;
    }

    @Transactional
    @Override
    public void deleteECRF(String surveyTwoID) {
        logger.info("Deleting the eCRF having survey two id: {}", surveyTwoID);
        Medical2Ecrf1 bySurveyTwoId = eCRF1REpository.findBySurveyTwoId(surveyTwoID);
        if (bySurveyTwoId == null) {
            logger.info("eCRF report not found.");
            throw new eCRFReportNotFoundException();
        }

        eCRF1REpository.delete(bySurveyTwoId);
        this.deleteOtherECRF(surveyTwoID);
        logger.info("eCRF report deleted successfully. Survey two id: {}", surveyTwoID);
    }

    @Transactional
    @Override
    public void deleteECRFs(List<String> surveyTwoID) {
        surveyTwoID.forEach(this::deleteECRF);
    }

    private void deleteOtherECRF(String surveyTwoId){
        Medical2Ecrf2 eCRF2 = this.eCRF2Repository.findBySurveyTwoId(surveyTwoId);
        if (eCRF2 != null) {
            this.eCRF2Repository.delete(eCRF2);
        }

        Medical2Ecrf3 ecrf3 = this.eCRF3Repository.findBySurveyTwoId(surveyTwoId);
        if (ecrf3 != null) {
            this.eCRF3Repository.delete(ecrf3);
        }

        Medical2Ecrf4 ecrf4 = this.eCRF4Repository.findBySurveyTwoId(surveyTwoId);
        if (ecrf4 != null) {
            this.eCRF4Repository.delete(ecrf4);
        }

        Medical2Ecrf5 ecrf5 = this.eCRF5Repository.findBySurveyTwoId(surveyTwoId);
        if (ecrf5 != null) {
            this.eCRF5Repository.delete(ecrf5);
        }

        Medical2Ecrf6 ecrf6 = this.eCRF6Repository.findBySurveyTwoId(surveyTwoId);
        if (ecrf6 != null) {
            this.eCRF6Repository.delete(ecrf6);
        }

        Medical2Ecrf7 ecrf7 = this.eCRF7Repository.findBySurveyTwoId(surveyTwoId);
        if (ecrf7 != null) {
            this.eCRF7Repository.delete(ecrf7);
        }

        Medical2Ecrf8 ecrf8 = this.eCRF8Repository.findBySurveyTwoId(surveyTwoId);
        if (ecrf8 != null) {
            this.eCRF8Repository.delete(ecrf8);
        }

        Medical2Ecrf9 ecrf9 = this.eCRF9Repository.findBySurveyTwoId(surveyTwoId);
        if (ecrf9 != null) {
            this.eCRF9Repository.delete(ecrf9);
        }

        Medical2Ecrf10 ecrf10 = this.eCRF10Repository.findBySurveyTwoId(surveyTwoId);
        if (ecrf10 != null) {
            this.eCRF10Repository.delete(ecrf10);
        }

        Medical2Ecrf20 ecrf20 = this.eCRF20Repository.findBySurveyTwoId(surveyTwoId);
        if (ecrf20 != null) {
            this.eCRF20Repository.delete(ecrf20);
        }

        Calculator ecrf30 = this.eCRF30Repository.findBySurveyTwoId(surveyTwoId);
        if (ecrf30 != null) {
            this.eCRF30Repository.delete(ecrf30);
        }
    }

    @Transactional
    @Override
    public void assignECRF(String surveyTwoId, Long userId) {
        Medical2Ecrf1 bySurveyTwoId = this.eCRF1REpository.findBySurveyTwoId(surveyTwoId);
        if (bySurveyTwoId == null) {
            throw new eCRFNotFoundException();
        }

        Optional<User> byId = userRepository.findById(userId);
        if (byId.isEmpty()) {
            throw new UserNotFoundException();
        }

        User user = byId.get();
        String newSurveyTwoId = createSurveyTwoId(user.getUserId());
        bySurveyTwoId.setSurveyTwoId(newSurveyTwoId);
        bySurveyTwoId.setUser(user);
        this.eCRF1REpository.save(bySurveyTwoId);

        this.updateOtherECRF(surveyTwoId, newSurveyTwoId, user);
    }

    private void updateOtherECRF(String surveyTwoId,
                                 String surveyTwoId1,
                                 User user) {
        Medical2Ecrf2 eCRF2 = this.eCRF2Repository.findBySurveyTwoId(surveyTwoId);
        if (eCRF2 != null) {
            eCRF2.setSurveyTwoId(surveyTwoId1);
            eCRF2.setUser(user);
            this.eCRF2Repository.save(eCRF2);
        }

        Medical2Ecrf3 ecrf3 = this.eCRF3Repository.findBySurveyTwoId(surveyTwoId);
        if (ecrf3 != null) {
            ecrf3.setSurveyTwoId(surveyTwoId1);
            ecrf3.setUser(user);
            this.eCRF3Repository.save(ecrf3);
        }

        Medical2Ecrf4 ecrf4 = this.eCRF4Repository.findBySurveyTwoId(surveyTwoId);
        if (ecrf4 != null) {
            ecrf4.setSurveyTwoId(surveyTwoId1);
            ecrf4.setUser(user);
            this.eCRF4Repository.save(ecrf4);
        }

        Medical2Ecrf5 ecrf5 = this.eCRF5Repository.findBySurveyTwoId(surveyTwoId);
        if (ecrf5 != null) {
            ecrf5.setSurveyTwoId(surveyTwoId1);
            ecrf5.setUser(user);
            this.eCRF5Repository.save(ecrf5);
        }

        Medical2Ecrf6 ecrf6 = this.eCRF6Repository.findBySurveyTwoId(surveyTwoId);
        if (ecrf6 != null) {
            ecrf6.setSurveyTwoId(surveyTwoId1);
            ecrf6.setUser(user);
            this.eCRF6Repository.save(ecrf6);
        }

        Medical2Ecrf7 ecrf7 = this.eCRF7Repository.findBySurveyTwoId(surveyTwoId);
        if (ecrf7 != null) {
            ecrf7.setSurveyTwoId(surveyTwoId1);
            ecrf7.setUser(user);
            this.eCRF7Repository.save(ecrf7);
        }

        Medical2Ecrf8 ecrf8 = this.eCRF8Repository.findBySurveyTwoId(surveyTwoId);
        if (ecrf8 != null) {
            ecrf8.setSurveyTwoId(surveyTwoId1);
            ecrf8.setUser(user);
            this.eCRF8Repository.save(ecrf8);
        }

        Medical2Ecrf9 ecrf9 = this.eCRF9Repository.findBySurveyTwoId(surveyTwoId);
        if (ecrf9 != null) {
            ecrf9.setSurveyTwoId(surveyTwoId1);
            ecrf9.setUser(user);
            this.eCRF9Repository.save(ecrf9);
        }

        Medical2Ecrf10 ecrf10 = this.eCRF10Repository.findBySurveyTwoId(surveyTwoId);
        if (ecrf10 != null) {
            ecrf10.setSurveyTwoId(surveyTwoId1);
            ecrf10.setUser(user);
            this.eCRF10Repository.save(ecrf10);
        }

        Medical2Ecrf20 ecrf20 = this.eCRF20Repository.findBySurveyTwoId(surveyTwoId);
        if (ecrf20 != null) {
            ecrf20.setSurveyTwoId(surveyTwoId1);
            ecrf20.setUser(user);
            this.eCRF20Repository.save(ecrf20);
        }

        Calculator ecrf30 = this.eCRF30Repository.findBySurveyTwoId(surveyTwoId);
        if (ecrf30 != null) {
            ecrf30.setSurveyTwoId(surveyTwoId1);
            ecrf30.setUser(user);
            this.eCRF30Repository.save(ecrf30);
        }
    }

    @Transactional
    @Override
    public void assignECRFByPatIDs(AssignECRFRequest request) {
        Long userId = request.getAssignTo();

        Arrays.stream( request.getSurveyTwoId().split(","))
                .filter(StringUtils::isNotBlank)
                .forEach(pat -> this.assignECRF(pat, userId));

    }

    @Override
    public List<String> getPatId() {
        return eCRF1REpository.getDistinctPatID();
    }

    @Override
    public DataTableResponse getECRFReport(DataTableRequest dataTable) throws ParseException {
        eCRFSpecification specification = new eCRFSpecification();
        String value = dataTable.getSearch().getValue();
        if (StringUtils.isNotBlank(value)) {
            var criteria = new SearchCriteria("patID", value, SearchOperation.LIKE);
            specification.add(criteria);
        }

        for (DataTableColumnSpecs col : dataTable.getColumns()) {
            String searchValue = col.getSearch().getValue();
            if (StringUtils.isNotBlank(searchValue)) {
                if (col.getName().equals("patID")) {
                    specification.setPatIds(Arrays.stream(searchValue.split(",")).collect(Collectors.toList()));
                }else if(col.getName().equals("userWithAddress")){
                    specification.setUserIds(Arrays.stream(searchValue.split(",")).collect(Collectors.toList()));
                }
            }
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

        Page<Medical2Ecrf1> all = eCRF1REpository.findAll(specification, sortedByName);
        List<EcrfDataTable> dataTablesData = mapECRFDataToDataTable(all.getContent());
        long totalRecord = eCRF1REpository.count();
        long filteredRecord = eCRF1REpository.count(specification);
        DataTableResponse<EcrfDataTable> response = new DataTableResponse<>();
        response.setData(dataTablesData);
        response.setDraw(dataTable.getDraw());
        response.setRecordsFiltered(filteredRecord);
        response.setRecordsTotal(totalRecord);

        return response;
    }

    private List<EcrfDataTable> mapECRFDataToDataTable(List<Medical2Ecrf1> eCRFs) throws ParseException {
        List<EcrfDataTable> dataTablesData = new ArrayList<>();
        for (Medical2Ecrf1 eCRF : eCRFs) {
            EcrfDataTable ecrfDataTable = new EcrfDataTable();
            ecrfDataTable.setId(eCRF.getId());
            ecrfDataTable.setCheckbox(eCRF.getSurveyTwoId());
            ecrfDataTable.setAction(eCRF.getSurveyTwoId());
            ecrfDataTable.setPatID(eCRF.getPatID());
            ecrfDataTable.setFillingStatus(eCRF.getFillingStatus());

            String format = new SimpleDateFormat("yyyy-MM-dd HH:mm").format(eCRF.getCreatedAt());
            ecrfDataTable.setCreatedAt(format);

            String fullName = eCRF.getUser().getFirstName() + " " + eCRF.getUser().getSurname();
            String address = eCRF.getUser().getCountry();

            ecrfDataTable.setUserWithAddress(fullName + "(" + address + ")");

            dataTablesData.add(ecrfDataTable);
        }

        return dataTablesData;
    }

    private String createSurveyTwoId(String userId) {
        return userId + "-" + Calendar.getInstance().getTime().getTime();
    }

}

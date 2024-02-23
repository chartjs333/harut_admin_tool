package org.medical.hub.controller;

import org.medical.hub.common.Common;
import org.medical.hub.common.Routes;
import org.medical.hub.dto.ExcelDataDto;
import org.medical.hub.message.ExcelMessages;
import org.medical.hub.models.ExcelFile;
import org.medical.hub.models.User;
import org.medical.hub.request.ExcelDataValidationRequest;
import org.medical.hub.request.ExcelFileActionRequest;
import org.medical.hub.services.ExcelFileService;
import org.medical.hub.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class ExcelFileController {

    private final ExcelFileService fileService;
    private final UserService userService;

    @Autowired
    public ExcelFileController(ExcelFileService fileService,
                               UserService userService) {
        this.fileService = fileService;
        this.userService = userService;
    }

    @GetMapping(value = Routes.ExcelFile.GET, name = "upload-file-get")
    public String create(@RequestParam(defaultValue = "1") Integer page,
                         @RequestParam(defaultValue = "10") Integer size,
                         Model model) {
        List<User> users = this.userService.getUsers();
        Page<ExcelFile> excelFiles = this.fileService.getAll(page - 1, size);
        Integer startIndex = (page - 1) * size + 1;

        model.addAttribute("standardDate", new Date());
        model.addAttribute("excelFiles", excelFiles.getContent());

        model.addAttribute("totalPage", excelFiles.getTotalPages());
        model.addAttribute("startIndex", startIndex);
        model.addAttribute("currentPage", page);
        model.addAttribute("users", users);

        return "excel/list";
    }

    @GetMapping(value = "/upload/excel-file/{id}", name = "edit-excel-file")
    public String editFile(@PathVariable("id") Long id, Model model) throws ClassNotFoundException, InvocationTargetException, IllegalAccessException, NoSuchMethodException, InstantiationException {
        ExcelDataDto byId = fileService.getExcelDataDetails(id);
        model.addAttribute("data", byId.getData());
        model.addAttribute("cols", byId.getColumns());
        model.addAttribute("values", byId.getValues());

        model.addAttribute("id", id);
        return "excel/update";
    }

    @PostMapping(value = "/upload/excel-file/{id}", name = "update-excel-file")
    public ResponseEntity<Map<String, String>> updateFile(@PathVariable("id") Long id,
                                                          @Valid @ModelAttribute("action") ExcelFileActionRequest action) {

        this.fileService.overwrite(id, action);

        Map<String, String> response = new HashMap<>();
        if (action.getButtonAction().equals("2")) {
            response.put("success_message", "File has been overwrite successfully.");
        } else {
            response.put("success_message", "New excel file is created successfully.");
        }
        return ResponseEntity.ok(response);

    }

    @PostMapping(value = Routes.ExcelFile.DELETE, name = "delete-excel-file")
    public String deleteExcelFile(@PathVariable("id") Long id, RedirectAttributes ra) {

        this.fileService.deleteExcelFile(id);
        ra.addFlashAttribute(Common.SUCCESS_MESSAGE, ExcelMessages.SUCCESS_DELETED_EXCEL_FILE);

        return "redirect:" + Routes.ExcelFile.GET;
    }

    @PostMapping(value = Routes.ExcelFile.VALIDATE_DATA)
    public ResponseEntity checkRule(@PathVariable("id") Long id, ExcelDataValidationRequest request) {

        Map<String, String> errorMessage = this.fileService.validateExcelData(id, request);
        String isValid = errorMessage.get("isValid");
        if (!Boolean.valueOf(isValid)) {
            errorMessage.put("backgroundColor", "yellow");
        }

        return ResponseEntity.ok(errorMessage);
    }
}

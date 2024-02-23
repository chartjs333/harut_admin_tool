package org.medical.hub.customer;

import lombok.AllArgsConstructor;
import org.medical.hub.common.Common;
import org.medical.hub.common.Routes;
import org.medical.hub.datatable.DataTableRequest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@AllArgsConstructor
public class CustomerController {

    private static final String ADD_VIEW = "customer/create";
    private static final String EDIT_VIEW = "customer/edit";
    private static final String LIST_VIEW = "customer/index";

    private final CustomerService customerService;

    @GetMapping(value = Routes.Correspondent.GET, name = "get-customers")
    public String index() {
        return LIST_VIEW;
    }

    @PostMapping(value = Routes.Correspondent.AJAX_GET, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getCustomers(@RequestBody DataTableRequest dataTable) {
        return ResponseEntity.ok(customerService.getCustomersForDataTable(dataTable));
    }

    @GetMapping(value = Routes.Correspondent.ADD, name = "add-customer")
    public String addCorrespondent(Model model) {

        model.addAttribute("action", "/customer");
        return ADD_VIEW;
    }

    @PostMapping(value = Routes.Correspondent.GET, name = "save-customer")
    public String saveCorrespondent(@Valid @ModelAttribute("customer") CustomerRequest customerRequest,
                                    BindingResult bindingResult,
                                    RedirectAttributes ra) {
        if (bindingResult.hasErrors()) {
            return ADD_VIEW;
        }

        this.customerService.save(customerRequest);
        ra.addFlashAttribute(Common.SUCCESS_MESSAGE, "Correspondent details saved successfully.");
        return Common.REDIRECT + Routes.Correspondent.GET;
    }

    @GetMapping(value = Routes.Correspondent.EDIT)
    public String edit(@PathVariable("id") Long id, Model model) {

        var customer = customerService.findCustomerById(id);
        CustomerRequest customerRequest = new CustomerRequest();
        customerRequest.setEmail(customer.getEmail());
        customerRequest.setName(customer.getName());
        customerRequest.setPhone(customer.getPhone());
        model.addAttribute("customer", customerRequest);
        model.addAttribute("id", id);
        model.addAttribute("action", "/customer/" + id);
        return EDIT_VIEW;
    }

    @PostMapping(value = Routes.Correspondent.UPDATE, name = "update-customer")
    public String update(@PathVariable("id") Long id,
                         @Valid @ModelAttribute("customer") CustomerRequest customerRequest,
                         BindingResult bindingResult,
                         RedirectAttributes ra) {

        if (bindingResult.hasErrors()) {
            return EDIT_VIEW;
        }

        this.customerService.update(id, customerRequest);
        ra.addFlashAttribute(Common.SUCCESS_MESSAGE, "Correspondent details updated successfully.");
        return Common.REDIRECT;
    }

    @PostMapping(value = Routes.Correspondent.DELETE, name = "delete-customer")
    public String delete(@PathVariable("id") Long id, RedirectAttributes ra) {

        this.customerService.delete(id);
        ra.addFlashAttribute(Common.SUCCESS_MESSAGE, "Correspondent details deleted successfully.");
        return Common.REDIRECT + Routes.Correspondent.GET;
    }

    @ModelAttribute("customer")
    private CustomerRequest customer() {
        return new CustomerRequest();
    }
}

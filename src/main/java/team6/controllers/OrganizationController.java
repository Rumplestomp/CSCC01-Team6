package team6.controllers;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import team6.models.Organization;
import team6.repositories.OrganizationRepository;
import team6.throwables.OrganizationNotFoundException;

@Controller
public class OrganizationController {
    @Autowired
    private OrganizationRepository organizationRepository;

    @GetMapping("/organizations/{id}")
    public String singleIndex(@PathVariable String id, Model model) {
        try {
            Optional<Organization> org = organizationRepository.findById(Long.parseLong(id));
            Organization organization = org.get();
            model.addAttribute("organization", organization);
            return "organization-read-single.html";
        } catch (IllegalArgumentException | EmptyResultDataAccessException err) {
            throw new OrganizationNotFoundException();
        }
    }
    
    @GetMapping("/organizations")
    public String index(Model model) {
        Iterable <Organization> organizations =  organizationRepository.findAll();
        model.addAttribute("organizations",organizations);
        return "organizations-read-list";
    }

    @GetMapping("/organizations/create")
    public String organizationForm(Model model) {
        model.addAttribute("organization", new Organization(null, null, null, null, null, null, null, null));
	    return "organization-create";
    }

    @GetMapping("/organizations/{id}/update")
    public String updateById(Model model, @PathVariable String id) {
	try {
            model.addAttribute("organization", organizationRepository.findById(Long.parseLong(id)).get());
	    return "organization-update";
        } catch (IllegalArgumentException | EmptyResultDataAccessException err) {
            throw new OrganizationNotFoundException();
        }
    }
    
    @PostMapping("/organizations")
    public String create(@ModelAttribute Organization organization) {
        organizationRepository.save(organization);
        return "redirect:/organizations";
    }
    
    @PostMapping("/organizations/{id}")
    public String updatedOrganization(Model model, @ModelAttribute Organization organization, @PathVariable String id) {
    	organizationRepository.save(organization);
    	model.addAttribute("organization", organization);
    	return "redirect:/organizations/{id}";
    }

    @DeleteMapping("/organizations/{id}")
    public String deleteById(@PathVariable String id) {
        try {
            organizationRepository.deleteById(Long.parseLong(id));
            return "redirect:/organizations";
        } catch (IllegalArgumentException | EmptyResultDataAccessException err) {
            throw new OrganizationNotFoundException();
        }
    }
}

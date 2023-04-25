package com.example.patientsmvc.web;
import jakarta.validation.Valid;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.example.patientsmvc.entities.Patient;
import com.example.patientsmvc.repositories.PatientRepository;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.ResponseBody;


import java.util.List;

@Controller
@AllArgsConstructor
public class PatientController {

    private PatientRepository patientRepository;

    @GetMapping(path = "/index")
    public  String patients(Model model,
                            @RequestParam(name = "page" , defaultValue = "0") int page,
                            @RequestParam(name = "size" , defaultValue = "5")  int size,
                            @RequestParam(name = "keyword" , defaultValue = "")  String keyword){
        Page<Patient> pagePatients=patientRepository.findByNomContains(keyword,PageRequest.of(page,size));
        model.addAttribute("listpatients",pagePatients.getContent());
        model.addAttribute("pages",new int[pagePatients.getTotalPages()]);
        model.addAttribute("currentPage",page);
        model.addAttribute("keyword",keyword);
        return "patients";
    }



    @GetMapping(path = "/delete")
    public String delete(Long id,String keyword,int page){
        patientRepository.deleteById(id);
        return "redirect:/index?page="+page+"&keyword="+keyword;
    }



    @GetMapping(path = "/")
    public String home(){
        return "redirect:/index";
    }




    @GetMapping(path = "/patients")
    @ResponseBody
    public List<Patient> listPatient(){
        return patientRepository.findAll();
    }



    @GetMapping(path = "/formpatients")
    public String formpatients(Model model){
        model.addAttribute("patient",new Patient());
        return "formpatients";
    }


    @PostMapping(path="/save")
    public String save(Model model, @Valid Patient patient ,
                       BindingResult bindingResult,
                       @RequestParam(defaultValue="0") int page,
                       @RequestParam(defaultValue="") String keyword ) {
        if (bindingResult.hasErrors()) return "formpatients";
        patientRepository.save(patient);
        return "redirect:/index?page="+page+"&keyword="+keyword;
    }

    @GetMapping(path = "/editpatient")
    public String editpatients(Model model , Long id , String keyword , int page){
        Patient patient=patientRepository.findById(id).orElse(null);
        if (patient==null) throw new RuntimeException("Introuvable");
        model.addAttribute ("patient",patient);
        model.addAttribute ("page",page);
        model.addAttribute ("keyword",keyword);
        return "editpatient";
    }





}

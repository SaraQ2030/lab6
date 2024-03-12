package org.example.employee.Controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import org.example.employee.Api.ApiMessage;
import org.example.employee.Model.Employee;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("api/v1/employee")
public class EmployeeController {
    ArrayList<Employee> employees=new ArrayList<>();

    @GetMapping("/display")
    public ResponseEntity displayEmployee(){
        return ResponseEntity.status(200).body(employees);
    }

    @PostMapping("/add")
    public ResponseEntity addEmployee(@RequestBody @Valid Employee employee, Errors errors){
        if(errors.hasErrors()){
            String message =errors.getFieldError().getDefaultMessage();
            return ResponseEntity.status(400).body(message);
        }
        employees.add(employee);
        return ResponseEntity.status(200).body(new ApiMessage("Employee added"));
    }
    @PutMapping("/update/{id}")
    public ResponseEntity updateEmployee(@PathVariable String id,@RequestBody @Valid Employee employee ,Errors errors){
         if (errors.hasErrors()){
             String message =errors.getFieldError().getDefaultMessage();
             return ResponseEntity.status(400).body(message);
         }
      for (Employee e: employees){
            if (e.getId().equalsIgnoreCase(id) ){
                employees.set(employees.indexOf(e), employee);
                return ResponseEntity.status(200).body("Data updated");
            }
                }
        return ResponseEntity.status(400).body("Not found id ");
}

@DeleteMapping("/delete/{id}")
    public ResponseEntity deleteEmployee(@PathVariable String id){
        for (Employee e:employees){
            if (e.getId().equals(id)){
                employees.remove(e);
                return ResponseEntity.status(200).body("Data deleted");
            }
            }
        return ResponseEntity.status(400).body("the id not found in system");
}


@GetMapping("/search/pos/{pos}")
    public ResponseEntity searchEmployeePosition(@PathVariable String pos){

        ArrayList<Employee> position=new ArrayList<>();
        for(Employee e:employees){
            if (pos.equalsIgnoreCase("supervisor") || pos.equalsIgnoreCase("coordinator")){
            if (e.getPosition().equalsIgnoreCase(pos)){
                position.add(e);}
        }else return ResponseEntity.status(400).body("Enter supervisor OR coordinator only ");
        }
    return ResponseEntity.status(200).body(position);
}

@GetMapping("/search/age/{age}")
    public ResponseEntity searchEmployeeAge(@PathVariable int age){
        ArrayList<Employee> em_Age=new ArrayList<>();
        for (Employee e:employees){
            if (age<= 25 || age>=65) { return ResponseEntity.status(400).body("Enter age between 26 - 64");
            } else if (e.getAge()>=age){
                em_Age.add(e);
            }
        }
        return ResponseEntity.status(200).body(em_Age);
 }
@PutMapping("/annual/{id}")
 public ResponseEntity applayAnnualLeave(@PathVariable String id  ){
        for (Employee e:employees){
            if (e.getId().equals(id)){
                if (!(e.isOnLeave())){
                    if (e.getAnnualLeave()>=1){
                        e.setOnLeave(true);
                        e.setAnnualLeave(e.getAnnualLeave()-1);
                        return ResponseEntity.status(200).body("Done");
                    }
                    return ResponseEntity.status(400).body("Not annual days left");
                }
               return ResponseEntity.status(400).body("The employee on leave");
            }

        }
        return ResponseEntity.status(400).body("Not found id");
 }



 @GetMapping("/search/no")
    public ResponseEntity noAnnualLeaveEmployee(){
       ArrayList<Employee> noLeave=new ArrayList<>();
        for (Employee e:employees){
          if (e.getAnnualLeave()==0){
              noLeave.add(e);
          }
        }
        return ResponseEntity.status(200).body(noLeave);
 }

 @PutMapping("/promote/{id_sup}/pro/{id_em}")
    public ResponseEntity promoteEmployee(@PathVariable String id_sup,@PathVariable String id_em){
        for (Employee e:employees){
            if (e.getId().equals(id_sup) && e.getPosition().equalsIgnoreCase("supervisor")){
                for (Employee em:employees){
                    if (em.getId().equals(id_em)  && em.getAge()>= 30 && !em.isOnLeave() ){
                        em.setPosition("supervisor");
                        return ResponseEntity.status(200).body(" successfully changed ");
                    }
                }
                return ResponseEntity.status(400).body("Not allow to make changes");
                    }

                }
     return ResponseEntity.status(400).body("you are not a supervisor Not allow to make changes");
        }

}

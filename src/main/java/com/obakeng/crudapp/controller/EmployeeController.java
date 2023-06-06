package com.obakeng.crudapp.controller;

import com.obakeng.crudapp.model.Employee;
import com.obakeng.crudapp.repo.EmployeeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController("")
public class EmployeeController {
    @Autowired
    private EmployeeRepo employeeRepo;
    @GetMapping("/getAllEmployees")
    public ResponseEntity<List<Employee>> getAllEmployees(){
        try{
            List<Employee> employeeList = new ArrayList<>();
            employeeRepo.findAll().forEach(employeeList::add);

            if(employeeList.isEmpty()){
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(employeeList, HttpStatus.OK);
        }catch (Exception ex){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/getEmployeeById/{id}")
    public ResponseEntity<Employee> getEmployeeByID(@PathVariable long id){
      Optional<Employee> employeeData = employeeRepo.findById(id);

      if(employeeData.isPresent()){
          return new ResponseEntity<>(employeeData.get(), HttpStatus.OK);
      }
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    @GetMapping("/getEmployeeByEmail/{email}")
    public ResponseEntity<Employee> getEmployeeByEmail(@PathVariable String email){
        Optional<Employee> employeeData = employeeRepo.findByEmail(email);

        if(employeeData.isPresent()){
            return new ResponseEntity<>(employeeData.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/addEmployee")
    public ResponseEntity<Employee> addEmployee(@RequestBody Employee employee){
       Employee employeeObj = employeeRepo.save(employee);

       return new ResponseEntity<>(employeeObj, HttpStatus.OK);
    }
    @PostMapping("/updateEmployeeById/{id}")
    public ResponseEntity<Employee> updateEmployeeById(@PathVariable long id, @RequestBody Employee newEmpdata){
       Optional<Employee> oldEmployeeData = employeeRepo.findById(id);

       if(oldEmployeeData.isPresent()){
         Employee updatedEmployeeData = oldEmployeeData.get();
         updatedEmployeeData.setEmail(newEmpdata.getEmail());
         updatedEmployeeData.setFullName(newEmpdata.getFullName());
         updatedEmployeeData.setPassword(newEmpdata.getPassword());
         updatedEmployeeData.setJobTitle(newEmpdata.getJobTitle());

         Employee employeeObj = employeeRepo.save(updatedEmployeeData);
         return new ResponseEntity<>(employeeObj, HttpStatus.OK);
       }
       return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    @DeleteMapping("/deleteEmployeeById/{id}")
    public ResponseEntity<HttpStatus> deleteEmployeeById(@PathVariable long id){
        employeeRepo.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}

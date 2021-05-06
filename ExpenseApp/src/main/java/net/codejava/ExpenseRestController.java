package net.codejava;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
public class ExpenseRestController {

    @Autowired
    private ExpenseRepository expenseRepository;


    @RequestMapping("/getexpenses")

    public ResponseEntity<List<Expense>> getAllExpenses(){
        //List<Expense> alist=expenseRepository.findAll(Sort.by(Sort.Direction.DESC,"item","amount"));
        List<Expense> alist=expenseRepository.listAllItems();
        Expense aExp=alist.get(0);
        return new ResponseEntity<List<Expense>>(alist,HttpStatus.OK);
    }

    @PostMapping("/saveexpense")
    public Expense saveMe(@RequestBody Expense expense){

        expenseRepository.save(expense);
         List<Expense> e= expenseRepository.findAll().stream().filter(expense1 -> expense.getItem().equals(expense1.getItem())).collect(Collectors.toList());
         return e.get(0);
    }

    @PutMapping("/expenses")
    public ResponseEntity<Expense> updateExpenses(@RequestBody Expense e){

        if(e.getItem()!=null ) {
            List<Expense> e01=expenseRepository.findByItem(e.getItem());
            Expense e0=e01.get(0);
                    e0.setAmount(e.getAmount());
                    e0.setItem(e.getItem());
                    expenseRepository.save(e0);
                    return new ResponseEntity<Expense>(e0,HttpStatus.ACCEPTED);
        }
        return new ResponseEntity<Expense>(HttpStatus.BAD_REQUEST);

    }



}

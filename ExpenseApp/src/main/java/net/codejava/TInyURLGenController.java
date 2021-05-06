package net.codejava;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
public class TInyURLGenController {

    @Autowired
    @Qualifier("generator")
    private  TinyGenerrator tinyGenerrator;
    @Autowired
    private  TInyUrlRepository tInyUrlRepository;

    @RequestMapping("/generatetiny")
    public ResponseEntity<TinyUrl> getTinyUrl(@RequestBody TinyUrl mainUrl){
        System.out.println("Generate mapping");
        if(mainUrl.getMainUrl().length()>0){
      List<TinyUrl> isExists=    tInyUrlRepository.findByMainUrl(mainUrl.getMainUrl().toUpperCase())  ;
      if(isExists.size()==0) {
       String tiny= tinyGenerrator.generate(mainUrl.getMainUrl());

       if(tiny.length()==8){
           tiny=getFullTiny(tiny);
           mainUrl.setTinyUrl(tiny);
           tInyUrlRepository.save(mainUrl);
           System.out.println("Saved sucessfully");
       }
      }else{
           Optional<TinyUrl> existing=isExists.stream().filter(t->mainUrl.getMainUrl().equals(t.getMainUrl())).findFirst();

          return new ResponseEntity<TinyUrl>(existing.get(),HttpStatus.OK);
      }

        }
        return new ResponseEntity<TinyUrl>(mainUrl, HttpStatus.OK);
    }

    private String getFullTiny(String tiny) {
        return "www.by.com/"+tiny;
    }
}

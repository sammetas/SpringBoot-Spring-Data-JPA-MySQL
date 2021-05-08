package net.codejava;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;

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
           Optional<TinyUrl> existing=isExists.stream()
                   .filter(t->mainUrl.getMainUrl().equals(t.getMainUrl())).findFirst();

          return new ResponseEntity<TinyUrl>(existing.get(),HttpStatus.OK);
      }

        }
        return new ResponseEntity<TinyUrl>(mainUrl, HttpStatus.OK);
    }



    @RequestMapping(path="/redirect/{tinyurl1}/{tinyurl2}")
    @ResponseBody
    public ResponseEntity<Object> getRedirect(@PathVariable("tinyurl1") String tinyurl1 ,
                                           @PathVariable("tinyurl2") String tinyurl2) throws MalformedURLException, URISyntaxException {

        String sMainUrl=  "www.nolinkfound.com";
String tinyurl=tinyurl1+"/"+tinyurl2;
        System.out.println("redirect meth"+tinyurl);
        if(null!= tinyurl &&  !"".equals(tinyurl)){

            List<TinyUrl> list= tInyUrlRepository.findMainUrlByTinyUrl(tinyurl);
            if(list.size()>0){
                 Optional<TinyUrl> tinyUrld=list.stream().findFirst();
                sMainUrl=tinyUrld.get().getMainUrl();
                System.out.println("Main url::"+sMainUrl);
            }

            try{
                URL url = new URL("https://"+sMainUrl);
                HttpHeaders httpHeaders = new HttpHeaders();
                httpHeaders.setLocation(url.toURI());
                return new ResponseEntity<>(httpHeaders, HttpStatus.SEE_OTHER);
              //  return new ResponseEntity<URL>(url,HttpStatus.OK);}catch (MalformedURLException e){
                //e.printStackTrace();
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }


        }
        URL url = new URL("https://"+sMainUrl);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(url.toURI());
        return new ResponseEntity<>(httpHeaders,HttpStatus.OK);
    }


    //version 2
    @RequestMapping(path="/redirect/v1/{tinyurl1}/{tinyurl2}")
    @ResponseBody
    public ResponseEntity<Object> getRedirectV1(@PathVariable("tinyurl1") String tinyurl1 ,
                                              @PathVariable("tinyurl2") String tinyurl2) throws MalformedURLException, URISyntaxException {

        String sMainUrl=  "www.nolinkfound.com";
        String tinyurl=tinyurl1+"/"+tinyurl2;
        System.out.println("redirect meth"+tinyurl);
        if(null!= tinyurl &&  !"".equals(tinyurl)){

      //     TinyUrl tinyRep= tInyUrlRepository.getByTinyUrl(tinyurl);
            List<TinyUrl> tinyRep= tInyUrlRepository.findByTinyUrl(tinyurl);


                sMainUrl=tinyRep.get(0).getMainUrl();
                System.out.println("Main url::"+sMainUrl);


            try{
                URL url = new URL("https://"+sMainUrl);
                HttpHeaders httpHeaders = new HttpHeaders();
                httpHeaders.setLocation(url.toURI());
                return new ResponseEntity<>(httpHeaders, HttpStatus.SEE_OTHER);
                //  return new ResponseEntity<URL>(url,HttpStatus.OK);}catch (MalformedURLException e){
                //e.printStackTrace();
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }


        }
        URL url = new URL("https://"+sMainUrl);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(url.toURI());
        return new ResponseEntity<>(httpHeaders,HttpStatus.OK);
    }
    private String getFullTiny(String tiny) {
        return "www.by.com/"+tiny;
    }
}

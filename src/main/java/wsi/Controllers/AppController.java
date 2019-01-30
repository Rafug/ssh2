package wsi.Controllers;

import com.sun.org.apache.xpath.internal.SourceTree;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import wsi.model.EngineStatus;
import wsi.model.Freqofrev;
import wsi.model.GenericResponse;

import wsi.model.Temperatura;
import wsi.repo.EngineInterface;


import java.util.*;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;

@Data
@AllArgsConstructor
@NoArgsConstructor
@RestController
@CrossOrigin
@Slf4j
public class AppController {
    List<Integer> bla;
    List<Temperatura> temp;
    List<Freqofrev> nowe=new ArrayList<>();
    @RequestMapping(value = "/status")
    public String showStatus() {
        return "App running OK";
    }


    @RequestMapping(value = "/current_time", method = GET)
    public String currentTime() {
        return (new Date()).toString();
    }

    @RequestMapping(value = "/id", method = RequestMethod.GET)
    public Temperatura getWeaterInRoom(
             String id
    ) {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

        Temperatura temperatura = null;


        log.info("Getting weater info for [{}]", id);
        String url;
        url = "http://192.168.0.242";
        log.info("url=[{}]", url);  //http://10.10.26.127/update
        Temperatura[] response = restTemplate.getForObject(url, Temperatura[].class);

       // temperatura = restTemplate.getForObject(url, Temperatura.class);
return temperatura;
    }

    public static void main(String[] args) {
        int k=5000;
        Scanner input = new Scanner(System.in);
        List<Freqofrev> b=new ArrayList<>();
        Freqofrev o= new Freqofrev(k);
        b.add(new Freqofrev(k));
    }

    @GetMapping(value = "/freqs")
    public Freqofrev newAlias(@RequestParam(value = "freq") Integer freq) {



        Freqofrev nowy = new Freqofrev(freq);
        nowe.add(nowy);
        return nowy;
    }


    @RequestMapping(value = "/freq" ,method=GET)
    public Freqofrev getAllF() {

        int k=5000;
        if(nowe.isEmpty()){
        Freqofrev nowy = new Freqofrev(k);
        nowe.add(nowy);}
        Freqofrev e = nowe.get(nowe.size() - 1);
        return e;
    }
    @RequestMapping(value = "/freq2" ,method=GET)
    public List<Freqofrev> getAllF23() {
            nowe.clear();
        return nowe;
    }

    @RequestMapping(value = "/freq3" ,method=GET)
    public List<Integer> getAllF3() {
        bla =Arrays.asList(1);
        return bla;
    }








    @Qualifier("engineViaHttp")
    //@Qualifier("engineMock")
    @Autowired
    EngineInterface engineInterface;

    @GetMapping(value = "/s")
    public GenericResponse appStatus() {
        return new GenericResponse("App is running OK");
    }

    @GetMapping(value = "/enginestatus")
    public EngineStatus getEngineStatus() {
        return engineInterface.status();
    }

    @GetMapping(value = "/engine/start")
    public EngineStatus getEngineStart() {
        return engineInterface.start();
    }

    @GetMapping(value = "/engine/stop")
    public EngineStatus getEngineStop() {
        return engineInterface.stop();
    }

    @GetMapping(value = "/engine/reverse")
    public EngineStatus getEngineReverse() {
        return engineInterface.reverse();
    }

    @GetMapping(value = "/engine/temps")
    public List<Double> getEngineTemps() {
        return engineInterface.getTemps();
    }



}



//ctr + shift do testu
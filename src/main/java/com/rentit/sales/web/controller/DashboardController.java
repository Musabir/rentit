package com.rentit.sales.web.controller;

import com.google.gson.Gson;
import com.rentit.exception.*;
import com.rentit.inventory.service.InventoryService;
import com.rentit.inventory.web.dto.PlantInventoryEntryDTO;
import com.rentit.sales.domain.model.PurchaseOrder;
import com.rentit.sales.domain.model.PurchaseOrderStatus;
import com.rentit.sales.domain.repository.PurchaseOrderRepository;
import com.rentit.sales.service.PurchaseOrderAssembler;
import com.rentit.sales.service.SalesService;
import com.rentit.sales.web.dto.CatalogQueryDTO;
import com.rentit.sales.web.dto.CustomPurchaseOrderDto;
import com.rentit.sales.web.dto.PurchaseOrderDTO;
import com.rentit.sales.web.dto.PurchaseOrderViewDTO;
import org.json.JSONArray;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.mail.MessagingException;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static org.springframework.web.bind.annotation.RequestMethod.*;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Controller
@RequestMapping("/dashboard")
public class DashboardController {

    @Autowired
    SalesService salesService;

    @Autowired
    PurchaseOrderRepository purchaseOrderRepository;

    @Autowired
    PurchaseOrderAssembler purchaseOrderAssembler;

    @Autowired
    InventoryService inventoryService;


    @GetMapping("/catalog/form")
    public String getQueryForm(Model model) throws InvoiceNotFoundException, MessagingException, PurchaseOrderNotFoundException, IOException, JSONException {
        List<PlantInventoryEntryDTO> list = new ArrayList<>();
        URL url = new URL("http://final-rentitt.herokuapp.com/api/sales/orders");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.connect();
        String inline = "";
        Scanner sc = new Scanner(url.openStream());
        while (sc.hasNext()) {
            inline += sc.nextLine();
        }
        JSONArray jsonArray = new JSONArray(inline);

        for (int i = 0; i < jsonArray.length(); i++) {
            PlantInventoryEntryDTO data = new Gson().fromJson(jsonArray.get(i).toString(), PlantInventoryEntryDTO.class);
            System.out.println(" data " + data);
            list.add(data);

        }
        model.addAttribute("catalogQuery", new CatalogQueryDTO());
        model.addAttribute("plants", list);
        return "dashboard/catalog/query-form";
    }

    @PostMapping("/catalog/query")
    public String postQueryForm(CatalogQueryDTO catalogQueryDTO, Model model) throws JSONException, InvoiceNotFoundException, MessagingException, PurchaseOrderNotFoundException, IOException {
        salesService.closePurchaseOrder();

        List<PlantInventoryEntryDTO> list = new ArrayList<>();

        String walletBalanceUrl = "http://final-rentitt.herokuapp.com/api/sales/orders/query/";

        RestTemplate restTemplate = new RestTemplate();

        String result = restTemplate.postForObject(walletBalanceUrl, catalogQueryDTO, String.class);
        JSONArray jsonArray = new JSONArray(result);

        for (int i = 0; i < jsonArray.length(); i++) {
            PlantInventoryEntryDTO data = new Gson().fromJson(jsonArray.get(i).toString(), PlantInventoryEntryDTO.class);
            System.out.println(" data " + data);
            list.add(data);

        }
        System.out.println(result);
        model.addAttribute("plants", list);
        model.addAttribute("rentalPeriod", catalogQueryDTO.getRentalPeriod());


        return "dashboard/catalog/query-result";
    }

    @RequestMapping(method = GET, path = "/allorders")
    public String getAllCancelPurchaseOrders(Model model) throws InvoiceNotFoundException, MessagingException, PurchaseOrderNotFoundException, IOException, JSONException {
        List<CustomPurchaseOrderDto> list = new ArrayList<>();
        URL url = new URL("http://final-builditt.herokuapp.com/api/sales/orders/allorders/");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.connect();
        String inline = "";
        Scanner sc = new Scanner(url.openStream());
        while (sc.hasNext()) {
            inline += sc.nextLine();
        }
        JSONArray jsonArray = new JSONArray(inline);

        for (int i = 0; i < jsonArray.length(); i++) {
            CustomPurchaseOrderDto data = new Gson().fromJson(jsonArray.get(i).toString(), CustomPurchaseOrderDto.class);
            System.out.println(" data " + data);
            list.add(data);
        }
        model.addAttribute("orders", list);
        model.addAttribute("pod", new CustomPurchaseOrderDto());
        return "dashboard/orders/allorders";

    }


    @RequestMapping(method = GET, path = "/pendingorders")
    public String getAllPendingPurchaseOrders(Model model) throws InvoiceNotFoundException, MessagingException, PurchaseOrderNotFoundException, IOException, JSONException {
        List<CustomPurchaseOrderDto> list = new ArrayList<>();
        URL url = new URL("http://final-builditt.herokuapp.com/api/sales/orders/pendingdorders/");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.connect();
        String inline = "";
        Scanner sc = new Scanner(url.openStream());
        while (sc.hasNext()) {
            inline += sc.nextLine();
        }
        JSONArray jsonArray = new JSONArray(inline);

        for (int i = 0; i < jsonArray.length(); i++) {
            CustomPurchaseOrderDto data = new Gson().fromJson(jsonArray.get(i).toString(), CustomPurchaseOrderDto.class);
            list.add(data);
            System.out.println("-----------MMMMMMMMMMMM<> " + data.toString());

        }

        model.addAttribute("orders", list);
        model.addAttribute("pod", new CustomPurchaseOrderDto());
        return "dashboard/orders/pending-orders";
    }

    @RequestMapping(method = GET, path = "/cancelrequests")
    public String getAllCancelPendingPurchaseOrders(Model model) throws InvoiceNotFoundException, MessagingException, PurchaseOrderNotFoundException, IOException, JSONException {
        List<CustomPurchaseOrderDto> list = new ArrayList<>();
        URL url = new URL("http://final-builditt.herokuapp.com/api/sales/orders/cancelpending/");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.connect();
        String inline = "";
        Scanner sc = new Scanner(url.openStream());
        while (sc.hasNext()) {
            inline += sc.nextLine();
        }
        JSONArray jsonArray = new JSONArray(inline);

        for (int i = 0; i < jsonArray.length(); i++) {
            CustomPurchaseOrderDto data = new Gson().fromJson(jsonArray.get(0).toString(), CustomPurchaseOrderDto.class);
            list.add(data);
        }

        model.addAttribute("orders", list);
        model.addAttribute("pod", new CustomPurchaseOrderDto());
        return "dashboard/orders/cancelpending";
    }

    @RequestMapping(method = POST, path = "/pendingorderss")
    public String acceptPendingPurchaseOrders(@RequestParam(name = "links[0]") String id, Model model) throws BindException, PurchaseOrderExtensionNotFoundException {
        PurchaseOrder purchaseOrderResult = null;
        String result = null;
        String result1 = "";
        List<CustomPurchaseOrderDto> list = new ArrayList<>();
        System.out.println("-----------<> " + id);

        try {

            String url = "http://final-rentitt.herokuapp.com/api/sales/orders/pendingorders/";
            String findPurchaseOrder = "http://final-builditt.herokuapp.com/api/sales/orders/findpurchaseorderbyid/";
            RestTemplate restTemplate = new RestTemplate();
            purchaseOrderResult = restTemplate.postForObject(findPurchaseOrder, id, PurchaseOrder.class);

            result = restTemplate.postForObject(url, purchaseOrderResult, String.class);
            System.out.println("-----------------   UEEEEEEEEEEE  "+ purchaseOrderResult.getPlant().getName()+ "  "+result);


            if (result.equals("OK")) {
                String acceptUrl = "http://final-builditt.herokuapp.com/api/sales/orders/acceptpendingorders/";

                result1 = restTemplate.postForObject(acceptUrl, id, String.class);

                JSONArray jsonArray = new JSONArray(result1);
                for (int i = 0; i < jsonArray.length(); i++) {
                    CustomPurchaseOrderDto data = new Gson().fromJson(jsonArray.get(i).toString(), CustomPurchaseOrderDto.class);
                    System.out.println();
                    list.add(data);

                }
            }
            else {
                URL urll = new URL("http://final-builditt.herokuapp.com/api/sales/orders/pendingdorders/");
                HttpURLConnection conn = (HttpURLConnection) urll.openConnection();
                conn.setRequestMethod("GET");
                conn.connect();
                String inline = "";
                Scanner sc = new Scanner(urll.openStream());
                while (sc.hasNext()) {
                    inline += sc.nextLine();
                }
                JSONArray jsonArray = new JSONArray(inline);

                for (int i = 0; i < jsonArray.length(); i++) {
                    CustomPurchaseOrderDto data = new Gson().fromJson(jsonArray.get(i).toString(), CustomPurchaseOrderDto.class);
                    list.add(data);

                }
                model.addAttribute("error_message", "Plant is not available");
            }


        } catch (JSONException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            model.addAttribute("orders", list);

            model.addAttribute("pod", new CustomPurchaseOrderDto());
        }

        return "dashboard/orders/pending-orders";
    }

    @RequestMapping(method = DELETE, path = "/pendingorderss")
    public String rejectPendingPurchaseOrders(@RequestParam(name = "links[0]") String id, Model model) throws PlantNotFoundException, BindException, PurchaseOrderExtensionNotFoundException {
        String result1 = "";
        List<CustomPurchaseOrderDto> list = new ArrayList<>();
        try {

            RestTemplate restTemplate = new RestTemplate();

            String acceptUrl = "http://final-builditt.herokuapp.com/api/sales/orders/rejectpendingorders/";
            System.out.println("-----------<> " + id);
            result1 = restTemplate.postForObject(acceptUrl, id, String.class);
            JSONArray jsonArray = new JSONArray(result1);
            for (int i = 0; i < jsonArray.length(); i++) {
                CustomPurchaseOrderDto data = new Gson().fromJson(jsonArray.get(i).toString(), CustomPurchaseOrderDto.class);
                list.add(data);

            }

        } catch (JSONException e1) {
            e1.printStackTrace();
        }

        model.addAttribute("orders", list);
        model.addAttribute("pod", new CustomPurchaseOrderDto());

        return "dashboard/orders/pending-orders";
    }

    @RequestMapping(method = POST, path = "/cancelpendingorders")
    public String acceptCancelPendingPurchaseOrders(@RequestParam(name = "links[0]") String id, Model model) throws BindException, PurchaseOrderExtensionNotFoundException {
        String result1 = "";
        List<CustomPurchaseOrderDto> list = new ArrayList<>();
        System.out.println("-------------><  " + id);
        try {
            //salesService.acceptCancelPurchaseOrder(id);
            String url = "http://final-rentitt.herokuapp.com/api/sales/orders/closeReservation/";
            RestTemplate restTemplate = new RestTemplate();
            //  String result = restTemplate.postForObject( url,id, String.class);
            String acceptUrl = "http://final-builditt.herokuapp.com/api/sales/orders/cancelacceptpendingorders/";

            result1 = restTemplate.postForObject(acceptUrl, id, String.class);
            JSONArray jsonArray = new JSONArray(result1);
            for (int i = 0; i < jsonArray.length(); i++) {
                CustomPurchaseOrderDto data = new Gson().fromJson(jsonArray.get(i).toString(), CustomPurchaseOrderDto.class);
                list.add(data);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        } finally {
            model.addAttribute("orders", list);
            model.addAttribute("pod", new CustomPurchaseOrderDto());
        }

        return "dashboard/orders/cancelpending";
    }

    @RequestMapping(method = DELETE, path = "/cancelpendingorders")
    public String rejectCancelPendingPurchaseOrders(@RequestParam(name = "links[0]") String id, Model model) throws PlantNotFoundException, BindException, PurchaseOrderExtensionNotFoundException {
        String result1 = "";
        List<CustomPurchaseOrderDto> list = new ArrayList<>();
        try {

            RestTemplate restTemplate = new RestTemplate();

            String acceptUrl = "http://final-builditt.herokuapp.com/api/sales/orders/cancelrejectpendingorders/";

            result1 = restTemplate.postForObject(acceptUrl, id, String.class);
            JSONArray jsonArray = new JSONArray(result1);
            for (int i = 0; i < jsonArray.length(); i++) {
                CustomPurchaseOrderDto data = new Gson().fromJson(jsonArray.get(i).toString(), CustomPurchaseOrderDto.class);
                list.add(data);


            }
        } catch (JSONException e1) {
            e1.printStackTrace();
        }

        model.addAttribute("orders", list);
        model.addAttribute("po", new CustomPurchaseOrderDto());
        return "dashboard/orders/cancelpending";
    }


    @ExceptionHandler(PlantNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public void handlePlantNotFoundException(PlantNotFoundException ex) {
    }

    @ExceptionHandler(PurchaseOrderNotFoundException.class)
    public ResponseEntity<String> handlePurchaseOrderNotFoundException(PurchaseOrderNotFoundException ex) {
        return new ResponseEntity<String>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }


}
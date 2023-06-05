package com.dizertatie.reactiveinvestigations.service;

import com.dizertatie.reactiveinvestigations.common.ResourceNotFoundException;
import com.dizertatie.reactiveinvestigations.persistance.model.ParticipantModel;
import com.dizertatie.reactiveinvestigations.persistance.model.ProductModel;
import com.dizertatie.reactiveinvestigations.persistance.model.TargetModel;
import com.dizertatie.reactiveinvestigations.repository.ParticipantsRepo;
import com.dizertatie.reactiveinvestigations.repository.ProductsRepo;
import com.dizertatie.reactiveinvestigations.repository.TargetRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.time.Duration;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

import static com.dizertatie.reactiveinvestigations.common.Utils.*;

@Service
public class ProductService {

    @Autowired
    private ProductsRepo productsRepo;

    @Autowired
    private TargetRepo targetRepo;

    @Autowired
    private ParticipantsRepo participantsRepo;
    @Autowired
    private ParticipantService participantService;

    public static String RO_PREFIX = "07";



    public void addReport(Long productId) {
         PrintStream printStream;
         PrintStream endStream = System.out;
         ProductModel productModel = productsRepo.findById(productId).orElse(null);
        try {
            printStream = new PrintStream(new File("reports\\report"+productId+".txt"));
            System.setOut(printStream);
            if (productModel != null) {
                String content = reportContent(productModel);
                printStream.println(content);
                System.setOut(endStream);
                System.out.println("Report generated!");
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    private String reportContent(ProductModel productModel){
        String title = "Report no." + productModel.getProductId();
        String date = "Date: " + new Date(System.currentTimeMillis());
        String fullName = "Full name: " + productModel.getFirstName() + " " + productModel.getLastName();
        String productType = "Intercept type " + productModel.getProductType();
        String phoneNr = "Telephone: " + productModel.getPhoneNr();
        String duration = "Intercept duration: " + productModel.getCallDuration();
        String email = "E-mail: " + productModel.getEmailAddress();
        String location = "Location: " + "\n" + "Lat/Long: " + productModel.getCoordinates();
        String direction = "Direction of intercept: " + productModel.getDirection();
        String header = "MINISTRY OF JUSTICE " + "\n" + date ;
        String content = title + "\n" + "\n" + "\n" + fullName + "\n" + "\n" + productType + "\n" + phoneNr + "\n" + duration
                + "\n" + email + "\n" + location + "\n" + direction;
        String footer = "Present report represents classified information and property of Romanian Government";
        return header + "\n"+ "\n"+ "\n" + content + "\n"+ "\n" + footer;
    }

    public Flux<ProductModel> getProductsReactive() throws InterruptedException {
        System.out.println("Start retrieving product list reactive:");
        long start = System.currentTimeMillis();
        Flux<ProductModel> productModelFlux = Flux.fromIterable(productsRepo.findAll())
                .delayElements(Duration.ofSeconds(1))
                .doOnNext(productModel -> System.out.println("Processing count: " + productModel.getProductId()));
        long end = System.currentTimeMillis();
        System.out.println("Total execution time: " + (end - start) + " milliseconds.");
        return productModelFlux;
    }

    public List<ProductModel> getProductList() throws InterruptedException {
        System.out.println("Start retrieving product list:");
        long start = System.currentTimeMillis();
        List<ProductModel> productsWithDelay = new ArrayList<>();
        List<ProductModel> products = productsRepo.findAll();
        for (ProductModel prd : products) {
            // Delay of a second to simulate a big load of objects
            Thread.sleep(1000);
            System.out.println("Processing count: " + prd.getProductId());
            productsWithDelay.add(prd);
        }
        long end = System.currentTimeMillis();
        System.out.println("Total execution time: " + (end - start) + " milliseconds.");
        return productsWithDelay;
    }


    public List<ProductModel> getAll() {
        return productsRepo.findAll();
    }

    public ProductModel findProductById(Long id) {
        return productsRepo.findById(id).orElse(null);
    }

    public List<ProductModel> findProductByType(String type) {
        return productsRepo.findAll().stream().filter(productModel -> PRODUCT_TYPES_LIST.contains(type))
                .filter(productModel -> productModel.getProductType().equalsIgnoreCase(type))
                .collect(Collectors.toList());
    }

    public List<ProductModel> findProductByFirstName(String firstName) {
        return productsRepo.findAll().stream().filter(productModel -> productModel.getFirstName().equalsIgnoreCase(firstName)).collect(Collectors.toList());
    }

    public List<ProductModel> findProductByLastName(String lastName) {
        return productsRepo.findAll().stream().filter(productModel -> productModel.getLastName().equalsIgnoreCase(lastName)).collect(Collectors.toList());
    }

    public List<ProductModel> findProductByPhoneNr(String phoneNr) {
        return productsRepo.findAll().stream().filter(productModel -> productModel.getPhoneNr().equalsIgnoreCase(phoneNr)).collect(Collectors.toList());
    }

    public List<ProductModel> findProductByDuration(String startCall, String endCall) {

        LocalTime start = LocalTime.parse(startCall);
        LocalTime end = LocalTime.parse(endCall);
        List<ProductModel> allProducts = productsRepo.findAll();
        List<ProductModel> productsToReturn = new ArrayList<>();

        for (ProductModel entry : allProducts) {
            LocalTime entryTime = LocalTime.parse(entry.getCallDuration());
            if (entryTime.isAfter(start) && !entryTime.isAfter(end)) {
                productsToReturn.add(entry);
            }
        }
        return productsToReturn;
    }

    public List<ProductModel> findProductOfTarget(Long id) {
        TargetModel targetModel = targetRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Object not found with ID: " + id));
        List<ProductModel> allProducts = productsRepo.findAll();
        return allProducts.stream().filter(productModel -> productModel.getTargetModel().getTargetId().equals(targetModel.getTargetId())).collect(Collectors.toList());
    }

    public List<ProductModel> findProductOfParticipant(Long id) {
        ParticipantModel participantModel = participantsRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Object not found with ID: " + id));
        List<ProductModel> allProducts = productsRepo.findAll();
        return allProducts.stream().filter(productModel -> productModel.getParticipantModel().getParticipantId() == participantModel.getParticipantId()).collect(Collectors.toList());
    }

    public void addProduct() {
        ProductModel productModel = new ProductModel();
        productModel.setProductType(generateRandomProductType());
        productModel.setFirstName(generateRomanianFirstName());
        productModel.setLastName(generateRomanianLastName());
        productModel.setPhoneNr(generatePhoneNumberRO());
        if (!productModel.getProductType().equals(CALL)) {
            productModel.setCallDuration(NO_DURATION);
        } else {
            productModel.setCallDuration(generateRandomCallDuration());
        }
        productModel.setEmailAddress(generateRandomEmailAddress(productModel.getFirstName(), productModel.getLastName()));
        productModel.setCoordinates(generateRomaniaCoordinates());
        productModel.setDirection(generateRandomDirection());
        productModel.setTargetModel(new TargetModel());
        productsRepo.save(productModel);

    }

    public void addProduct(ProductModel productModel) {
        productsRepo.save(productModel);
    }

    public void addProduct(TargetModel targetModel) {
        ProductModel productModel = new ProductModel();
        productModel.setProductType(generateRandomProductType());
        productModel.setFirstName(generateRomanianFirstName());
        productModel.setLastName(generateRomanianLastName());
        productModel.setPhoneNr(generatePhoneNumberRO());
        if (!productModel.getProductType().equals(CALL)) {
            productModel.setCallDuration(NO_DURATION);
        } else {
            productModel.setCallDuration(generateRandomCallDuration());
        }
        productModel.setEmailAddress(generateRandomEmailAddress(productModel.getFirstName(), productModel.getLastName()));
        productModel.setCoordinates(generateRomaniaCoordinates());
        productModel.setDirection(generateRandomDirection());
        //productModel.setParticipant();
        productModel.setTargetModel(targetModel);
        productsRepo.save(productModel);

    }


    public String generateRandomProductType() {
        Random random = new Random();
        return PRODUCT_TYPES[random.nextInt(PRODUCT_TYPES.length)];
    }

    public String generateRandomDirection() {
        Random random = new Random();
        return PRODUCT_DIRECTION[random.nextInt(PRODUCT_DIRECTION.length)];
    }

    public String generateRandomEmailAddress(String firstName, String lastName) {
        Random random = new Random();
        String domain = DOMAINS[random.nextInt(DOMAINS.length)];
        int number = random.nextInt(10000);
        return firstName + "." + lastName + number + "@" + domain;
    }

    public String generateRandomCallDuration() {
        Random random = new Random();
        int minute = random.nextInt(10);
        int seconds = random.nextInt(59);
        return "0" + minute + ":" + seconds;
    }


    public String generateRomanianFirstName() {
        Random random = new Random();
        return FIRST_NAMES[random.nextInt(FIRST_NAMES.length)];
    }

    public String generateRomanianLastName() {
        Random random = new Random();
        return LAST_NAMES[random.nextInt(LAST_NAMES.length)];
    }

    public static String generateRomaniaCoordinates() {
        Random random = new Random();
        double latitude = random.nextDouble() * (48.2658 - 43.6186) + 43.6186; // random double between 43.6186 and 48.2658 (Romania's northernmost point)
        double longitude = random.nextDouble() * (29.6912 - 20.2611) + 20.2611; // random double between 20.2611 and 29.6912 (Romania's westernmost point)
        double[] coordinates = new double[]{latitude, longitude};
        String myCoordinates = Arrays.toString(coordinates);
        return myCoordinates.substring(1, myCoordinates.length() - 1);
    }


    public String generatePhoneNumberRO() {
        Random random = new Random();
        StringBuilder phoneNumber = new StringBuilder();
        phoneNumber.append(RO_PREFIX); // romania mobile nr prefix
        for (int i = 0; i < 8; i++) {
            phoneNumber.append(random.nextInt(10)); // random digits to complete phone nr.
        }
        return phoneNumber.toString();
    }


    public void removeProduct(Long productId) {
        productsRepo.deleteById(productId);
    }

    public void updateProduct(ProductModel productModel) {
        Optional<ProductModel> optionalProductModel = productsRepo.findById(productModel.getProductId());
        if (optionalProductModel.isPresent()) {
            ProductModel productFound = optionalProductModel.get();

            productFound.setProductType(productModel.getProductType());
            productFound.setCallDuration(productModel.getCallDuration());
            // rest of fields

            TargetModel targetModel = targetRepo.findById(productModel.getTargetModel().getTargetId()).orElse(null);
            productFound.setTargetModel(targetModel);
            ParticipantModel participantModel = participantsRepo.findById(productModel.getParticipantModel().getParticipantId()).orElse(null);
            productFound.setParticipantModel(participantModel);

            productsRepo.save(productFound);
        }
    }


    public void generateRandomTraffic() throws InterruptedException {
        ProductModel productModel = new ProductModel();
        productModel.setProductType(generateRandomProductType());
        productModel.setFirstName(generateRomanianFirstName());
        productModel.setLastName(generateRomanianLastName());
        productModel.setPhoneNr(generatePhoneNumberRO());
        if (!productModel.getProductType().equals(CALL)) {
            productModel.setCallDuration(NO_DURATION);
        } else {
            productModel.setCallDuration(generateRandomCallDuration());
        }
        productModel.setEmailAddress(generateRandomEmailAddress(productModel.getFirstName(), productModel.getLastName()));
        productModel.setCoordinates(generateRomaniaCoordinates());
        productModel.setDirection(generateRandomDirection());
        productModel.setParticipantModel(participantService.generateParticipant());
        productModel.setTargetModel(new TargetModel());
        Thread.sleep(1000);
        productsRepo.save(productModel);
        System.out.println("Processing count: " + productModel.getProductId());

    }

    public void generateTraffic4SpecificTarget(Long id) {
        Optional<TargetModel> targetModelOptional = targetRepo.findById(id);
        if (targetModelOptional.isEmpty()) {
            addProduct();
        } else {
            TargetModel targetModel = targetModelOptional.get();
            for (int i = 0; i < PRODUCTS_TO_GENERATE; i++) {
                addProduct(targetModel);
            }
        }
    }

    public void initializeDatabaseForFirstDemo() throws InterruptedException {
        for (int i = 0; i <= 10; i++) {
            generateRandomTraffic();
        }
    }
}

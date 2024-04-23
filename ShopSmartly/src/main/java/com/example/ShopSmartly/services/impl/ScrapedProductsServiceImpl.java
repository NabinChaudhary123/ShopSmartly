package com.example.ShopSmartly.services.impl;

import com.example.ShopSmartly.services.ScrapedProductService;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

@Service
public class ScrapedProductsServiceImpl implements ScrapedProductService {

    private static final List<String> USER_AGENTS = new ArrayList<>();

    static {
        USER_AGENTS.add("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/96.0.4664.110 Safari/537.36");
        USER_AGENTS.add("Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:97.0) Gecko/20100101 Firefox/97.0");
        // Add more User-Agent strings as needed
    }
    private static final List<String> PROXIES = new ArrayList<>();

    static {
        PROXIES.add("http://proxy1.example.com:8080");
        PROXIES.add("http://proxy2.example.com:8080");
        PROXIES.add("http://proxy3.example.com:8080");
        PROXIES.add("http://proxy4.example.com:8080");
        PROXIES.add("http://proxy5.example.com:8080");
        // Add more proxy URLs as needed
    }


// free people
//    @Override
//    public ResponseEntity<?> scrapeWebForProducts(String query) throws IOException {
//        StringBuilder result =new StringBuilder();
//        try{
//            Document doc = Jsoup.connect("https://www.freepeople.com/search?q="+query)
//                    .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/96.0.4664.110 Safari/537.36")
//                    .get();
//            //Extract item elements
//            Elements items = doc.select(".c-pwa-tile-grid-tile");
//
//            List<Map<String, String>> productList = new ArrayList<>();
//
//            //Iterate over items and extract data
//            for(Element item:items){
//                String title = item.select(".o-pwa-product-tile__heading").text();
//                String price = item.select(".c-pwa-product-price__current").text();
//                String imageURL = item.select(".o-pwa-product-tile__media img").attr("src");
//                String productLink = item.select(".c-pwa-link").attr("href");
//
//
//                // Create a map to store title and price
//                Map<String, String> product = new HashMap<>();
//                product.put("title",title);
//                product.put("price", price);
//                product.put("link",productLink);
//                product.put("imageURL",imageURL);
//                productList.add(product);
//            }
//            return ResponseEntity.ok().body(productList);
//        }
//        catch (IOException e){
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: "+e.getMessage());
//        }
//    }

    //salt surf less products
//    @Override
//    public ResponseEntity<?> scrapeWebForProducts(String query) throws IOException {
//        StringBuilder result =new StringBuilder();
//        try{
//            Document doc = Jsoup.connect("https://www.saltsurf.com/search?type=product&q="+query).get();
//            //Extract item elements
//            Elements items = doc.select(".product-block");
//
//            List<Map<String, String>> productList = new ArrayList<>();
//
//            //Iterate over items and extract data
//            for(Element item:items){
//                String title = item.select(".title").text();
////                String availability = item.select(".bold").text();
//                String price = item.select(".theme-money").first().text();
////                String originalPrice = item.select(".was-price.theme-money").first().text();
//                String imageURL = item.select(".rimage__image").attr("src");
//                String productLink = "https://www.saltsurf.com/"+item.select("a.image-inner").attr("href");
//
//
//
//                // Create a map to store title and price
//                Map<String, String> product = new HashMap<>();
//                product.put("title",title);
//                product.put("price", price);
//                product.put("link",productLink);
//                product.put("imageURL",imageURL);
//                productList.add(product);
//            }
//            return ResponseEntity.ok().body(productList);
//        }
//        catch (IOException e){
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: "+e.getMessage());
//        }
//    }


    // Snapdeal

    @Override
    public List<Map<String, String>> fetchSnapDeal(String query) throws IOException {

        Random random = new Random();
        String userAgent = USER_AGENTS.get(random.nextInt(USER_AGENTS.size()));

            Document doc = Jsoup.connect("https://snapdeal.com/search?keyword=" + query)
                    .userAgent(userAgent)
                    .get();
            //Extract item elements
            Elements items = doc.select(".product-tuple-listing");

            String proxyUrl = PROXIES.get(random.nextInt(PROXIES.size()));
            System.setProperty("http.proxyHost", proxyUrl.split(":")[0]);
            System.setProperty("http.proxyPort", proxyUrl.split(":")[2]);

            List<Map<String, String>> productList = new ArrayList<>();

            int limit =4;
            int count =0;
            //Iterate over items and extract data
            for(Element item:items){
                if(count>=limit){
                    break;
                }
                String title = item.select(".product-title").text();
                String price = item.select(".product-price").text();

                // Extract the product link
//                Element productLinkElement = item.select(".product-title a").first();
                String productLink =item.select("a").attr("href");
                Element reviewCountElement = doc.selectFirst(".product-rating-count");

                // Extract the text content of the review count element
                String reviewCountText = reviewCountElement.text();

//                String imageURL = item.select("img").attr("src");
                String imageURL;
                if(item.select(".product-image").hasClass("wooble")){
                    imageURL = item.select(".product-tuple-image img").attr("src");
                }
                else{
                    imageURL = item.select("img").attr("src");
                }

                // Create a map to store title and price
                Map<String, String> product = new HashMap<>();
                product.put("title",title);
                product.put("price", price);
                product.put("link",productLink);
                product.put("imageURL",imageURL);
                product.put("review",reviewCountText);
                productList.add(product);
                count++;
            }
            return productList;
    }


//                 ETSY.com

//    @Override
//    public List<Map<String, String>> scrapeEtsy(String query) throws IOException {
//
//        try {
//            Thread.sleep(1000); // Sleep for 1 seconds
//        } catch (InterruptedException e) {
//            Thread.currentThread().interrupt();
//        }
//        Random random = new Random();
//        String userAgent = USER_AGENTS.get(random.nextInt(USER_AGENTS.size()));
//        String proxyUrl = PROXIES.get(random.nextInt(PROXIES.size()));
//
//        System.setProperty("http.proxyHost", proxyUrl.split(":")[0]);
//        System.setProperty("http.proxyPort", proxyUrl.split(":")[2]);
//
//            Connection connection = Jsoup.connect("https://www.etsy.com/search?q="+query)
//                    .userAgent(userAgent);
//
//            Document doc = connection.get();
//            //Extract item elements
//            Elements items = doc.select(".v2-listing-card");
//
//            List<Map<String, String>> productList = new ArrayList<>();
//
//            int limit =10;
//            int count =0;
//            //Iterate over items and extract data
//            for(Element item:items){
//                if(count>=limit){
//                    break;
//                }
//                String title =item.select("h3").text();
//                String price = item.select(".currency-value").text();
//                String productLink = item.select("a").attr("href");
//                String imageURL = item.select("img").attr("src");
//
//                // Create a map to store title and price
//                Map<String, String> product = new HashMap<>();
//                product.put("title",title);
//                product.put("price", price);
//                product.put("link",productLink);
//                product.put("imageURL",imageURL);
//                productList.add(product);
//                count++;
//            }
//            return productList;
//    }

    //H&M
    @Override
    public List<Map<String, String>> scrapeHM(String query) throws IOException {

        try {
            Thread.sleep(1000); // Sleep for 1 seconds
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        Random random = new Random();
        String userAgent = USER_AGENTS.get(random.nextInt(USER_AGENTS.size()));
        String proxyUrl = PROXIES.get(random.nextInt(PROXIES.size()));

        System.setProperty("http.proxyHost", proxyUrl.split(":")[0]);
        System.setProperty("http.proxyPort", proxyUrl.split(":")[2]);

        Connection connection = Jsoup.connect("https://www2.hm.com/en_us/search-results.html?q="+query)
                .userAgent(userAgent);

        Document doc = connection.get();
        //Extract item elements
        Elements items = doc.select(".product-item");

        List<Map<String, String>> productList = new ArrayList<>();

        int limit =4;
        int count =0;
        //Iterate over items and extract data
        for(Element item:items){
            if(count>=limit){
                break;
            }
            String title = item.select(".item-heading").text();
            String price = item.select(".item-price").text();
            String productLink ="https://www2.hm.com/"+ item.select("a").attr("href");
            String imageURL = item.select("img").attr("src");
            String rating = scrapeRating(productLink);

            // Create a map to store title and price
            Map<String, String> product = new HashMap<>();
            product.put("title",title);
            product.put("price", price);
            product.put("link",productLink);
            product.put("imageURL",imageURL);
            product.put("rating",rating);
            productList.add(product);
            count++;
        }
        return productList;
    }

    // ratings
    private String scrapeRating(String productLink) throws IOException{
        Connection connection = Jsoup.connect(productLink);
        Document doc = connection.get();
        Element ratingElement = doc.select(".d1cd7b").first();
//        String rating = "N/A";
        if(ratingElement != null){
            String ariaLabel = ratingElement.attr("aria-label");
            return ariaLabel.split(" ")[0];
        }
        else{
            return "N/A";
        }
    }

    //fashionjunkee
    @Override
    public List<Map<String, String>> scrapeFashionJunkee(String query) throws IOException {

        try {
            Thread.sleep(1000); // Sleep for 1 seconds
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        Random random = new Random();
        String userAgent = USER_AGENTS.get(random.nextInt(USER_AGENTS.size()));
        String proxyUrl = PROXIES.get(random.nextInt(PROXIES.size()));

        System.setProperty("http.proxyHost", proxyUrl.split(":")[0]);
        System.setProperty("http.proxyPort", proxyUrl.split(":")[2]);

        Connection connection = Jsoup.connect("https://www.fashionjunkee.com/search.asp?sortby=4&keyword="+query)
                .userAgent(userAgent);

        Document doc = connection.get();
        //Extract item elements
        Elements items = doc.select(".product-container");

        List<Map<String, String>> productList = new ArrayList<>();

        int limit =20;
        int count =0;
        //Iterate over items and extract data
        for(Element item:items){
            if(count>=limit){
                break;
            }
            String title = item.select(".name a").text();
//            String price = item.select(".price span").text();
            String price = item.select(".price").text();
            price = price.split(" ")[3];
            String productLink = "https://www.fashionjunkee.com/" + item.select(".name a").attr("href");
            String imageURL = item.select(".img img").attr("src");

            // Create a map to store title and price
            Map<String, String> product = new HashMap<>();
            product.put("title",title);
            product.put("price", price);
            product.put("link",productLink);
            product.put("imageURL",imageURL);
            productList.add(product);
            count++;
        }
        return productList;
    }

    //abercrombie
    @Override
    public List<Map<String, String>> scrapeAbercrombie(String query) throws IOException {

        try {
            Thread.sleep(1000); // Sleep for 1 seconds
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        Random random = new Random();
        String userAgent = USER_AGENTS.get(random.nextInt(USER_AGENTS.size()));
        String proxyUrl = PROXIES.get(random.nextInt(PROXIES.size()));

        System.setProperty("http.proxyHost", proxyUrl.split(":")[0]);
        System.setProperty("http.proxyPort", proxyUrl.split(":")[2]);

        Connection connection = Jsoup.connect("https://www.abercrombie.com/shop/wd/search?searchTerm="+query)
                .userAgent(userAgent);

        Document doc = connection.get();
        //Extract item elements
        Elements items = doc.select("div.catalog-productCard-module__template");

        List<Map<String, String>> productList = new ArrayList<>();

        int limit =20;
        int count =0;
        //Iterate over items and extract data
        for(Element item:items){
            if(count>=limit){
                break;
            }
            Element titleElement = item.selectFirst("h2[data-aui=product-card-name]");
            String title = titleElement.text();
            String productLink = "https://www.abercrombie.com" + item.select("a.catalog-productCard-module__product-content-link").attr("href");
            String price = item.select("span.product-price-text-wrapper span.product-price-text").text();
            String imageURL = item.select("img[data-aui=product-card-image]").attr("src");


            // Create a map to store title and price
            Map<String, String> product = new HashMap<>();
            product.put("title",title);
            product.put("price", price);
            product.put("link",productLink);
            product.put("imageURL",imageURL);
            productList.add(product);
            count++;
        }
        return productList;
    }


//    @Override
//    public List<Map<String, String>> scrapealoYoga(String query) throws IOException {
//
//        try {
//            Thread.sleep(1000); // Sleep for 1 seconds
//        } catch (InterruptedException e) {
//            Thread.currentThread().interrupt();
//        }
//        Random random = new Random();
//        String userAgent = USER_AGENTS.get(random.nextInt(USER_AGENTS.size()));
//        String proxyUrl = PROXIES.get(random.nextInt(PROXIES.size()));
//
//        System.setProperty("http.proxyHost", proxyUrl.split(":")[0]);
//        System.setProperty("http.proxyPort", proxyUrl.split(":")[2]);
//
//        Connection connection = Jsoup.connect("https://www.aloyoga.com/search?q="+query)
//                .userAgent(userAgent);
//
//        Document doc = connection.get();
//        //Extract item elements
//        Elements items = doc.select(".PlpTile");
//
//        List<Map<String, String>> productList = new ArrayList<>();
//
//        int limit =20;
//        int count =0;
//        //Iterate over items and extract data
//        for(Element item:items){
//            if(count>=limit){
//                break;
//            }
//            Element titleElement = item.selectFirst("h3.styles_product-details__name__4lhUG a");
//            String title = titleElement.text();
//            String productLink = "https://www.everlane.com" + titleElement.attr("href");
//            String price = item.selectFirst("p.styles_product-details__price__02KDd").text();
//            String imageURL = item.select("img.styles_responsive-image__5f_zr").attr("src");
//
//            // Create a map to store title and price
//            Map<String, String> product = new HashMap<>();
//            product.put("title",title);
//            product.put("price", price);
//            product.put("link",productLink);
//            product.put("imageURL",imageURL);
//            productList.add(product);
//            count++;
//        }
//        return productList;
//    }
}

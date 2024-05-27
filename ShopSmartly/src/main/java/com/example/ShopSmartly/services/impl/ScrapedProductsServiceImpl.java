package com.example.ShopSmartly.services.impl;

import com.example.ShopSmartly.services.ScrapedProductService;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class ScrapedProductsServiceImpl implements ScrapedProductService {

    private static final List<String> USER_AGENTS = new ArrayList<>();
    private final CloseableHttpClient httpClient;

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


    private final ExecutorService executorService = Executors.newFixedThreadPool(10);

    public ScrapedProductsServiceImpl() {
        this.httpClient = HttpClients.createDefault();
    }

    @Override
    public CompletableFuture<List<Map<String, String>>> fetchFreePeopleAsync(String query){
        return CompletableFuture.supplyAsync(()->{
            try{
                return fetchFreePeople(query);
            }
            catch (IOException e){
                throw new RuntimeException(e);
            }
        }, executorService);
    }

    @Override
    public CompletableFuture<List<Map<String,String>>> fetchSaltSurfAsync(String query){
        return CompletableFuture.supplyAsync(()->{
            try{
                return fetchSaltSurf(query);
            }
            catch (IOException e){
                throw new RuntimeException(e);
            }
        }, executorService);
    }

    @Override
    public CompletableFuture<List<Map<String, String>>> fetchSnapDealAsync(String query){
        return CompletableFuture.supplyAsync(()->{
            try{
                return fetchSnapDeal(query);
            }
            catch (IOException e){
                throw new RuntimeException(e);
            }
        }, executorService);
    }

    @Override
    public CompletableFuture<List<Map<String, String>>> fetchHMAsync(String query){
        return CompletableFuture.supplyAsync(()->{
            try{
                return scrapeHM(query);
            }
            catch (IOException e){
                throw new RuntimeException(e);
            }
        }, executorService);
    }

    @Override
    public CompletableFuture<List<Map<String, String>>> fetchFashionJunkeeAsync(String query){
        return CompletableFuture.supplyAsync(()->{
            try{
                return scrapeFashionJunkee(query);
            }
            catch (IOException e){
                throw new RuntimeException(e);
            }
        }, executorService);
    }

    @Override
    public CompletableFuture<List<Map<String, String>>> fetchAberCrombieAsync(String query){
        return CompletableFuture.supplyAsync(()->{
            try{
                return scrapeAbercrombie(query);
            }
            catch (IOException e){
                throw new RuntimeException(e);
            }
        }, executorService);
    }
    @Override
    public CompletableFuture<List<Map<String, String>>> fetchMacysAsync(String query){
        return CompletableFuture.supplyAsync(()->{
            try{
                return scrapeMacys(query);
            }
            catch (IOException e){
                throw new RuntimeException(e);
            }
        }, executorService);
    }


// free people - Url failure
    public List<Map<String, String>> fetchFreePeople(String query) throws IOException {

        Random random = new Random();
        String userAgent = USER_AGENTS.get(random.nextInt(USER_AGENTS.size()));
        HttpGet httpGet = new HttpGet("https://www.freepeople.com/search?q="+query);
        httpGet.setHeader("User-Agent",userAgent);
//            Document doc = Jsoup.connect("https://www.freepeople.com/search?q="+query)
//                    .userAgent(userAgent)
//                    .get();

        CloseableHttpResponse response = httpClient.execute(httpGet);
        try{
            if(response.getStatusLine().getStatusCode() ==200){
                Document doc = Jsoup.parse(EntityUtils.toString(response.getEntity()));
                //Extract item elements
                Elements items = doc.select(".c-pwa-tile-grid-tile");
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
                    String title = item.select(".o-pwa-product-tile__heading").text();
                    String price = item.select(".c-pwa-product-price__current").text();
                    String imageURL = item.select(".o-pwa-product-tile__media img").attr("src");
                    String productLink = item.select(".c-pwa-link").attr("href");


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
            else{
                System.err.println("Failed to fetch data from salt surf "+response.getStatusLine());
                return new ArrayList<>();
            }
        }
        finally {
            httpGet.releaseConnection();
        }
    }

    //salt surf less products
    public List<Map<String, String>> fetchSaltSurf(String query) throws IOException {

            Random random = new Random();
            String userAgent = USER_AGENTS.get(random.nextInt(USER_AGENTS.size()));
            Document doc = Jsoup.connect("https://www.saltsurf.com/search?type=product&q="+query)
                    .userAgent(userAgent).get();
            //Extract item elements
            Elements items = doc.select(".product-block");

        String proxyUrl = PROXIES.get(random.nextInt(PROXIES.size()));
        System.setProperty("http.proxyHost", proxyUrl.split(":")[0]);
        System.setProperty("http.proxyPort", proxyUrl.split(":")[2]);

            List<Map<String, String>> productList = new ArrayList<>();

            //Iterate over items and extract data
            for(Element item:items){
                String title = item.select(".title").text();
                String price = item.select(".theme-money").first().text();
                String imageURL = item.select(".rimage__image").attr("src");
                String productLink = "https://www.saltsurf.com/"+item.select("a.image-inner").attr("href");

                // Create a map to store title and price
                Map<String, String> product = new HashMap<>();
                product.put("title",title);
                product.put("price", price);
                product.put("link",productLink);
                product.put("imageURL",imageURL);
                productList.add(product);
            }
            return productList;
    }


    // Snapdeal -> no ratings
    public List<Map<String, String>> fetchSnapDeal(String query) throws IOException {

        Random random = new Random();
        String userAgent = USER_AGENTS.get(random.nextInt(USER_AGENTS.size()));
        String proxyUrl = PROXIES.get(random.nextInt(PROXIES.size()));
        System.setProperty("http.proxyHost", proxyUrl.split(":")[0]);
        System.setProperty("http.proxyPort", proxyUrl.split(":")[2]);

            Document doc = Jsoup.connect("https://snapdeal.com/search?keyword=" + query)
                    .userAgent(userAgent)
                    .get();
            //Extract item elements
            Elements items = doc.select(".product-tuple-listing");

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
                String productLink =item.select("a").attr("href");
                Element reviewCountElement = item.selectFirst(".product-rating-count");

                // Extract the text content of the review count element
                String reviewCountText = (reviewCountElement != null)? reviewCountElement.text() : "No reviews";
                // Extract ratings number
//                String ratings = item.select(".filled-stars").attr("style");
//                String ratingsNumber = "No ratings";
//                if (ratings != null && ratings.contains(":") && ratings.contains("%")) {
//                    try {
//                        ratingsNumber = ratings.substring(ratings.indexOf(':') + 1, ratings.indexOf('%')).trim();
//                    } catch (IndexOutOfBoundsException e) {
//                        ratingsNumber = "No ratings";
//                    }
//                }
                // Parse ratings from style attribute
//                String ratingsNumber = ratings.substring(ratings.indexOf(':') + 1, ratings.indexOf('%'));

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
//                product.put("ratings",ratings);
                productList.add(product);
                count++;
            }
            return productList;
    }


    //H&M
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

    //fashionjunkee has some problems with pants/ has no ratings and reviews
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
            String price = item.select(".price").text();
//            price = price.split(" ")[3];
            String[] priceParts = price.split(" ");
            if (priceParts.length >= 4) {
                price = priceParts[3];
            } else {
                price = "Price not available"; // Default value if price format is unexpected
            }
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

    //abercrombie has no ratings and reviews
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

        int limit =12;
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

    // macys work better
    public List<Map<String, String>> scrapeMacys(String query) throws IOException {

        try {
            Thread.sleep(1000); // Sleep for 1 second
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        Random random = new Random();
        String userAgent = USER_AGENTS.get(random.nextInt(USER_AGENTS.size()));
        String proxyUrl = PROXIES.get(random.nextInt(PROXIES.size()));

        System.setProperty("http.proxyHost", proxyUrl.split(":")[0]);
        System.setProperty("http.proxyPort", proxyUrl.split(":")[2]);

        // Construct the URL for Macy's search
        String searchUrl = "https://www.macys.com/shop/featured/" + query;

        Connection connection = Jsoup.connect(searchUrl).userAgent(userAgent);

        Document doc = connection.get();

        // Extract item elements
        Elements items = doc.select("div.productThumbnail");

        List<Map<String, String>> productList = new ArrayList<>();

        int limit = 9;
        int count = 0;

        // Iterate over items and extract data
        for (Element item : items) {
            if (count >= limit) {
                break;
            }

            Element titleElement = item.selectFirst("div.productDescription > a.productDescLink > div.productBrand");
            String brand = titleElement != null ? titleElement.text(): "Unknown Brand";
            Element titleDescElement = item.selectFirst("div.productDescription > a.productDescLink");
            assert titleDescElement != null;
            String title = brand + " " + titleDescElement.text();
            String productLink = "https://www.macys.com" + titleDescElement.attr("href");
            String price = Objects.requireNonNull(item.selectFirst("div.priceInfo > div.prices > div > span.regular")).text();
            String imageURL = item.select("div.productThumbnailImage > a > div > picture > img").attr("src");

            String ratings = Objects.requireNonNull(item.selectFirst("div.stars")).attr("aria-label");
            // Extract reviews count
            String reviews = Objects.requireNonNull(item.selectFirst("span.aggregateCount")).text();

            // Create a map to store title, price, link, and image URL
            Map<String, String> product = new HashMap<>();
            product.put("title", title);
            product.put("price", price);
            product.put("link", productLink);
            product.put("imageURL", imageURL);
            product.put("ratings", ratings);
            product.put("reviews", reviews);
            productList.add(product);

            count++;
        }

        return productList;
    }
}

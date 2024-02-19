import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;

public class ShopeeCrawler {
    public static void main(String[] args) {
        String keyword = "iphone"; // 搜尋關鍵字
//        String url = "https://shopee.tw/search?keyword=" + keyword;
        String url = "https://shopee.tw/";


        try {
            Document document = Jsoup.connect(url).get();
            //Elements products = document.select(".col-xs-2-4.shopee-search-item-result__item");
            Elements products = document.select(".product-item");
            for (Element product : products) {
                String name = product.select(".shopee-item-card__text-name").text();
                String price = product.select(".shopee-item-card__current-price").text();
                System.out.println(name + " - " + price);
            }
        }catch (Exception e){
            e.printStackTrace();
        }


        try {
            // 發送HTTP GET請求並取得網頁內容
            Document doc = Jsoup.connect(url).get();

            // 解析網頁內容並獲取商品元素
            Elements products = doc.select(".shopee-search-item-result__item");

            // 逐個處理商品元素
            for (Element product : products) {
                // 獲取商品名稱
                String productName = product.select(".shopee-item-card__text-name").text();
                // 獲取商品價格
                String productPrice = product.select(".shopee-item-card__current-price").text();

                // 輸出商品資訊
                System.out.println("商品名稱: " + productName);
                System.out.println("商品價格: " + productPrice);
                System.out.println("--------------------------");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

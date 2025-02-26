package TQS.Lab02;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProductFinderServiceIT {

    TqsBasicHttpClient httpClient = new TqsBasicHttpClient();
    ProductFinderService service = new ProductFinderService(httpClient);

    @Test
    void findProductDetail() {
        try {
            Product p = service.findProductDetail(1).get();
            assertEquals("Fjallraven - Foldsack No. 1 Backpack, Fits 15 Laptops", p.getTitle());
            assertEquals(109.95, p.getPrice());
            assertEquals("https://fakestoreapi.com/img/81fPKd-2AYL._AC_SL1500_.jpg", p.getImage());
            assertEquals("Your perfect pack for everyday use and walks in the forest. Stash your laptop (up to 15 inches) in the padded sleeve, your everyday", p.getDescription());

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }}
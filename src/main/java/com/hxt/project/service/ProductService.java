package com.hxt.project.service;

import com.hxt.project.bean.Product;
import com.hxt.project.bean.WarehouseLog;

import java.util.List;

/**
 * ClassName: ProductService
 * Package: com.hxt.project.service
 * Description:
 *
 * @Author hxt
 * @Create 2024/9/25 18:28
 * @Version 1.0
 */
public interface ProductService {

    Long getTokenId(String token);
    String getTokenUsername(String token);

    List<Product> getAllProducts();

    List<Product> getAllByProductName(String name);

    boolean saveProduct(Product product);

    boolean updateProduct(Product product);

    boolean deleteProduct(Long id);

    boolean outBoundProduct(String name,Integer inventoryRemove,String operator);

    boolean inBoundProduct(String name, Integer inventorySum,String operator);

    void saveAllProducts(List<Product> products);

    byte[] generateBarcode(Long id, String code);
    byte[] generateQRCode(String text, int width, int height);

    Product getProductByCode(String code);

    int updateProductImgUrl(Long id,String imgUrl);
    int saveWarehouseLog(WarehouseLog log);

    List<Product> getTopFiveOutBound();
}

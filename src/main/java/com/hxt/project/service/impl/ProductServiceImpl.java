package com.hxt.project.service.impl;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.hxt.project.bean.Product;
import com.hxt.project.bean.WarehouseLog;
import com.hxt.project.common.ErrorCode;
import com.hxt.project.common.OperationType;
import com.hxt.project.exception.BusinessException;
import com.hxt.project.mapper.ProductMapper;
import com.hxt.project.mapper.WarehouseLogMapper;
import com.hxt.project.service.ProductService;
import com.hxt.project.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;


/**
 * ClassName: ProductServiceImpl
 * Package: com.hxt.project.service.impl
 * Description:
 *
 * @Author hxt
 * @Create 2024/9/25 18:29
 * @Version 1.0
 */
@Service
@Transactional
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    WarehouseLogMapper warehouseLogMapper;

    public Long getTokenId(String token){
        DecodedJWT verify = JwtUtil.verify(token);
        if (token != null) {
            String userId = verify.getClaim("id").asString();
            long id = Long.parseLong(userId);
            return id;
        }
        return null;
    }

    public String getTokenUsername(String token){
        DecodedJWT verify = JwtUtil.verify(token);
        if (token != null) {
            String username = verify.getClaim("username").asString();
            return username;
        }
        return null;
    }

    @Override
    public List<Product> getAllProducts() {
        List<Product> productList = productMapper.selectAll();

        return productList;
    }

    @Override
    public List<Product> getAllByProductName(String name) {
        List<Product> productList = productMapper.selectByProductName(name);
        return productList;
    }

    @Override
    public boolean saveProduct(Product product) {
        if(product.getInventory() < product.getAlarmValue()){
            product.setAlarm((byte) 1);
        }
        product.setAlarm((byte) 0);
        return productMapper.insert(product)>0;
    }

    @Override
    public boolean updateProduct(Product product) {
        if(product.getInventory() < product.getAlarmValue()){
            product.setAlarm((byte) 1);
        }
        product.setAlarm((byte) 0);
        return productMapper.updateByPrimaryKey(product)>0;
    }

    @Override
    public boolean deleteProduct(Long id) {
        return productMapper.deleteByPrimaryKey(id)>0;
    }

    @Override
    public boolean outBoundProduct(String name,Integer inventoryRemove,String operator) {
        if(inventoryRemove ==null || name == null || inventoryRemove <= 0){
            throw  new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        int result = productMapper.outBound(name, inventoryRemove);
        if(result <= 0) {
            throw  new BusinessException(ErrorCode.SYSTEM_ERROR);
        }
        Product product = productMapper.getByName(name);
        if(product == null){
            throw  new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        if(product.getInventory() < inventoryRemove){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"库存不足");
        }
        if(product.getInventory() < product.getAlarmValue()){
            product.setAlarm((byte) 1);
        }
        WarehouseLog warehouseLog = new WarehouseLog();
        warehouseLog.setProductId(product.getId());
        warehouseLog.setProductName(product.getName());
        warehouseLog.setOperationType(String.valueOf(OperationType.OUT));
        warehouseLog.setQuantity(inventoryRemove);
        warehouseLog.setOperator(operator);
        warehouseLog.setOperationTime(new Date());
        saveWarehouseLog(warehouseLog);
        return result>0;
    }

    @Override
    public boolean inBoundProduct(String name, Integer inventorySum,String operator) {
        if(inventorySum ==null || name == null || inventorySum <= 0){
            throw  new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        int result = productMapper.inBound(name, inventorySum);
        if(result <= 0) {
            throw  new BusinessException(ErrorCode.SYSTEM_ERROR);
        }
        Product product = productMapper.getByName(name);
        if(product == null){
            throw  new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        if(product.getInventory() < product.getAlarmValue()){
            product.setAlarm((byte) 1);
        }
        WarehouseLog warehouseLog = new WarehouseLog();
        warehouseLog.setProductId(product.getId());
        warehouseLog.setProductName(product.getName());
        warehouseLog.setOperationType(String.valueOf(OperationType.IN));
        warehouseLog.setQuantity(inventorySum);
        warehouseLog.setOperator(operator);
        warehouseLog.setOperationTime(new Date());
        saveWarehouseLog(warehouseLog);
        return result>0;
    }

    @Override
    public void saveAllProducts(List<Product> products) {
        if(products == null || products.size()<=0){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        productMapper.insertProductsBatch(products);
    }



    /**
     * 生成条形码
     * @param id
     * @param code
     * @return byte[]
     */
    public byte[] generateBarcode(Long id, String code){
        String barcodeText = id + "_" + code;

        // 设置条形码宽度和高度
        int width = 300;
        int height = 100;

        // 生成条形码
        BitMatrix bitMatrix = null;
        try {
            bitMatrix = new MultiFormatWriter().encode(barcodeText, BarcodeFormat.CODE_128, width, height);
        } catch (WriterException e) {
            e.printStackTrace();
        }

        // 将条形码转为图片流
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            MatrixToImageWriter.writeToStream(bitMatrix, "png", outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return outputStream.toByteArray();
    }


    /**
     * 生成二维码
     * @param text
     * @param width
     * @param height
     * @return
     * @throws WriterException
     * @throws IOException
     */
    public byte[] generateQRCode(String text, int width, int height){
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = null;
        try {
            bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height);
        } catch (WriterException e) {
            e.printStackTrace();
        }
        ByteArrayOutputStream pngOutputStream = new ByteArrayOutputStream();
        try {
            MatrixToImageWriter.writeToStream(bitMatrix, "PNG", pngOutputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return pngOutputStream.toByteArray();
    }

    @Override
    public Product getProductByCode(String code) {
        return productMapper.findByCode(code);
    }

    @Override
    public int updateProductImgUrl(Long  id,
                                   String imgUrl) {
        return productMapper.updateProductImgUrl(id,
                imgUrl);
    }

    @Override
    public int saveWarehouseLog(WarehouseLog log) {
        return warehouseLogMapper.insert(log);
    }

    @Override
    public List<Product> getTopFiveOutBound() {
        return productMapper.getTopFiveOutBound();
    }
}

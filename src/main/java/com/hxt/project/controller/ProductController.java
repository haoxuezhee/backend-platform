package com.hxt.project.controller;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.read.listener.PageReadListener;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hxt.project.bean.Goods;
import com.hxt.project.bean.Product;
import com.hxt.project.bean.request.BarcodeRequest;
import com.hxt.project.bean.request.ProductInboundRequest;
import com.hxt.project.bean.request.ProductOutboundRequest;
import com.hxt.project.bean.request.UploadImgRequest;
import com.hxt.project.common.ErrorCode;
import com.hxt.project.common.Resp;
import com.hxt.project.common.RespCode;
import com.hxt.project.exception.BusinessException;
import com.hxt.project.service.GoodsService;
import com.hxt.project.service.ProductService;
import com.hxt.project.utils.WebUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.Date;
import java.util.List;

/**
 * ClassName: GoodsController
 * Package: com.hxt.project.controller
 * Description:
 *
 * @Author hxt
 * @Create 2024/9/15 16:32
 * @Version 1.0
 */

@RestController
@RequestMapping("/product")
@Slf4j
public class ProductController {


    @Autowired
    private ProductService productService;

    @GetMapping("/list")
    public Resp getAllGoods(String name){
        if (name == null || name.equals("")) {
            List<Product> ProductList = productService.getAllProducts();
            return Resp.builder().data(ProductList).msg("获取商品信息中")
                    .code(ProductList!=null? RespCode.QUERY_SUCCESS_CODE:RespCode.QUERY_FAIL_CODE).build();
        }

        List<Product> ProductList = productService.getAllByProductName(name);
        log.info("goodList:{}",ProductList);
        return Resp.builder().data(ProductList).msg("获取商品信息中")
                .code(ProductList!=null?RespCode.QUERY_SUCCESS_CODE:RespCode.QUERY_FAIL_CODE).build();
    }

    @PostMapping("/save")
    public Resp saveProduct(@RequestBody Product product, HttpServletRequest request){
        if(product == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        String token = request.getHeader("token");
        String username = productService.getTokenUsername(token);
        product.setCreateUser(username);
        product.setCreateTime(new Date());
        boolean ret = productService.saveProduct(product);
        return Resp.builder()
                .data(ret)
                .msg(ret?"保存成功":"保存失败")
                .code(ret?RespCode.SAVE_SUCCESS_CODE:RespCode.SAVE_FAIL_CODE)
                .build();
    }


    @PostMapping("/update")
    public Resp updateProduct(@RequestBody Product product,HttpServletRequest request){
        if(product == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        String token = request.getHeader("token");
        String username = productService.getTokenUsername(token);
        product.setUpdateTime(new Date());
        product.setUpdateUser(username);
        boolean ret = productService.updateProduct(product);
        return Resp.builder().
                code(ret?RespCode.UPDATE_SUCCESS_CODE:RespCode.UPDATE_FAIL_CODE)
                .data(ret)
                .msg(ret?"修改成功":"修改失败")
                .build();
    }

    @PostMapping("/delete/{id}")
    public Resp deleteProduct(@PathVariable Long id){
        if(id == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        boolean ret = productService.deleteProduct(id);
        return Resp.builder().
                code(ret?RespCode.DELETE_SUCCESS_CODE:RespCode.DELETE_FAIL_CODE)
                .data(ret)
                .msg(ret?"删除成功":"删除失败")
                .build();
    }

    @PostMapping("/out")
    public Resp outBoundProduct(@RequestBody ProductOutboundRequest productOutboundRequest,HttpServletRequest request){
        String name = productOutboundRequest.getName();
        Integer inventoryRemove = productOutboundRequest.getInventoryRemove();
        if(inventoryRemove == null || name == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        String token = request.getHeader("token");
        String operator = productService.getTokenUsername(token);
        boolean ret =  productService.outBoundProduct(name,inventoryRemove,operator);
        return Resp.builder().
                code(ret?RespCode.OUTBOUND_SUCCESS_CODE:RespCode.OUTBOUND_FAIL_CODE)
                .data(ret)
                .msg(ret?"出库成功":"出库失败")
                .build();
    }

    @GetMapping("/topFiveOutBound")
    public Resp getTopFiveOutBound(){
        List<Product> topFiveOutBound = productService.getTopFiveOutBound();
        return Resp.builder()
                .data(topFiveOutBound)
                .msg("获取成功")
                .code(RespCode.QUERY_SUCCESS_CODE)
                .build();
    }

    @PostMapping("/in")
    public Resp inBoundProduct(@RequestBody ProductInboundRequest productInboundRequest,HttpServletRequest request){
        String name = productInboundRequest.getName();
        Integer inventorySum = productInboundRequest.getInventorySum();
        if(inventorySum == null || name == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        String token = request.getHeader("token");
        String operator = productService.getTokenUsername(token);
        boolean ret =  productService.inBoundProduct(name,inventorySum,operator);
        return Resp.builder().
                code(ret?RespCode.INBOUND_SUCCESS_CODE:RespCode.INBOUND_FAIL_CODE)
                .data(ret)
                .msg(ret?"入库成功":"入库失败")
                .build();
    }

    @GetMapping("/downloadExcel")
    public void downloadExcel(HttpServletResponse response) throws IOException {
        // 设置响应头
        response.setContentType("application/vnd.ms-excel");
        response.setHeader("Content-Disposition", "attachment; filename=product_inventory.xlsx");

        // 获取数据库中的产品数据
        List<Product> products = productService.getAllProducts();

        // 使用阿里巴巴 EasyExcel 写入数据到 Excel 文件
        EasyExcel.write(response.getOutputStream(), Product.class)
                .sheet("库存列表")
                .doWrite(products);
    }

    @PostMapping("/uploadExcel")
    public Resp uploadExcel(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return Resp.builder()
                    .code(RespCode.UPLOAD_FAIL_CODE)
                    .msg("文件为空")
                    .build();
        }
        try {
            // 使用阿里巴巴 EasyExcel 解析 Excel 文件
            List<Product> products = EasyExcel.read(file.getInputStream())
                    .head(Product.class)
                    .sheet()
                    .doReadSync();

            // 保存解析后的产品列表
            productService.saveAllProducts(products);

            return Resp.builder()
                    .code(RespCode.UPLOAD_SUCCESS_CODE)
                    .msg("上传成功")
                    .build();
        } catch (Exception e) {
            return Resp.builder()
                    .code(RespCode.UPLOAD_FAIL_CODE)
                    .msg("文件处理失败: " + e.getMessage())
                    .build();
        }
    }

    @PostMapping(value = "/generateBarcode", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<byte[]> generateBarcode(@RequestBody BarcodeRequest barcodeRequest) {
        try {
            Long id = barcodeRequest.getId();
            String code = barcodeRequest.getCode();
            byte[] barcodeImage = productService.generateBarcode(id, code);

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=barcode.png")
                    .body(barcodeImage);

        } catch (Exception e) {
            log.error("生成条形码失败", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping(value = "/generateQRCode", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<byte[]> generateQRCode(@RequestBody BarcodeRequest barcodeRequest) {
        try {
            Long id = barcodeRequest.getId();  // 获取传入的id
            String code = barcodeRequest.getCode();  // 获取传入的code
            String text = id + "-" + code;  // 组合生成二维码的文本
            byte[] qrCodeImage = productService.generateQRCode(text, 200, 200);  // 生成二维码图片

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=qrcode.png")
                    .body(qrCodeImage);  // 返回二维码图片
        } catch (Exception e) {
            log.error("生成二维码失败", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/getByCode")
    public Resp getProductByCode(@RequestParam String code){
        if(code == null || code.trim().isEmpty()){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Product product = productService.getProductByCode(code.trim());
        if(product != null){
            return Resp.builder().data(product).msg("获取商品成功")
                    .code(RespCode.QUERY_SUCCESS_CODE).build();
        } else {
            return Resp.builder().msg("未找到对应的商品").code(RespCode.QUERY_FAIL_CODE).build();
        }
    }

    private static final String IMAGE_UPLOAD_DIR = "D:/XunLeiDownLoad/planetProject/project/src/main/resources/static/images/";

    @PostMapping("/uploadImage")
    @CrossOrigin(origins = "http://localhost:9999")
    public Resp uploadImage(@RequestParam("file") MultipartFile file, @RequestParam Long id) {
        if (file.isEmpty()) {
            return Resp.builder()
                    .code(RespCode.UPLOAD_FAIL_CODE)
                    .msg("文件为空")
                    .build();
        }
        try {
            // 获取文件名并生成唯一文件名
            String originalFilename = file.getOriginalFilename();
            String newFileName = System.currentTimeMillis() + "_" + originalFilename;

            // 保存文件到指定路径
            File dest = new File(IMAGE_UPLOAD_DIR + newFileName);
            file.transferTo(dest);

            // 返回保存的图片 URL（这里假设是本地服务器）
            String fileUrl = "/images/" + newFileName; // 确保路径与静态资源路径匹配

            String finalUrl = "http://localhost:8083"+fileUrl;
            productService.updateProductImgUrl(id,finalUrl);  // 调用 service 保存图片 URL

            return Resp.builder()
                    .code(RespCode.UPLOAD_SUCCESS_CODE)
                    .msg("上传成功")
                    .data(fileUrl) // 返回图片 URL
                    .build();
        } catch (IOException e) {
            log.error("图片上传失败", e);
            return Resp.builder()
                    .code(RespCode.UPLOAD_FAIL_CODE)
                    .msg("文件上传失败: " + e.getMessage())
                    .build();
        }
    }





}


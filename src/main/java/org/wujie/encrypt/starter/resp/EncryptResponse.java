package org.wujie.encrypt.starter.resp;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;
import org.wujie.encrypt.starter.annotation.Encrypt;
import org.wujie.encrypt.starter.base.RespBean;
import org.wujie.encrypt.starter.config.EncryptProperties;
import org.wujie.encrypt.starter.util.AESUtils;

/**
 * @author niehaisheng
 * @date 2022/6/6 14:30
 */
@ControllerAdvice
@EnableConfigurationProperties(EncryptProperties.class)
public class EncryptResponse implements ResponseBodyAdvice<RespBean> {

    private ObjectMapper om = new ObjectMapper();

    @Autowired
    private EncryptProperties encryptProperties;

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return returnType.hasMethodAnnotation(Encrypt.class);
    }

    @Override
    public RespBean beforeBodyWrite(RespBean body, MethodParameter returnType, MediaType selectedContentType,
            Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request,
            ServerHttpResponse response) {
        byte[] keyBytes = encryptProperties.getKey().getBytes();
        try {
            if (body.getMsg()!=null) {
                body.setMsg(AESUtils.encrypt(body.getMsg().getBytes(), keyBytes));
            }
            if (body.getObject() != null) {
                body.setObject(AESUtils.encrypt(om.writeValueAsBytes(body.getObject()), keyBytes));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return body;
    }

}
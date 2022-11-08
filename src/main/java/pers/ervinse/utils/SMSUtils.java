package pers.ervinse.utils;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 短信发送工具类
 */
@Slf4j
@Component
public class SMSUtils {

    private static String regionId;

    private static String accessKeyId;

    private static String secret;

    private static String templateCode;

    private static String signName;

    @Value("${SMS.regionId}")
    public void setRegionId(String regionId) {
        SMSUtils.regionId = regionId;
    }

    @Value("${SMS.accessKeyId}")
    public void setAccessKeyId(String accessKeyId) {
        SMSUtils.accessKeyId = accessKeyId;
    }
    @Value("${SMS.secret}")
    public void setSecret(String secret) {
        SMSUtils.secret = secret;
    }

    @Value("${SMS.templateCode}")
    public void setTemplateCode(String templateCode){
        SMSUtils.templateCode = templateCode;
    }

    @Value("${SMS.signName}")
    public void setSignName(String signName){
        SMSUtils.signName = signName;
    }


    /**
     * 发送短信
     * @param phoneNumbers 手机号
     * @param param        参数
     */
    public static void sendMessage(String phoneNumbers, String param) throws ClientException {
        log.info("SMSUtils - sendMessage - signName = {},templateCode = {},phoneNumbers = {},param = {}", signName, templateCode, phoneNumbers, param);
        DefaultProfile profile = DefaultProfile.getProfile(regionId, accessKeyId, secret);
        IAcsClient client = new DefaultAcsClient(profile);

        SendSmsRequest request = new SendSmsRequest();
        request.setSysRegionId(regionId);
        request.setPhoneNumbers(phoneNumbers);
        request.setSignName(signName);
        request.setTemplateCode(templateCode);
        request.setTemplateParam("{\"code\":\"" + param + "\"}");

        SendSmsResponse response = client.getAcsResponse(request);
        log.info("SMSUtils - sendMessage : sendSmsResponse = {}",new Gson().toJson(response));
        if (StringUtils.equals(response.getCode(),"isp.RAM_PERMISSION_DENY")){
            throw new RuntimeException("短信服务请求被拒绝");
        }else {
            log.info("SMSUtils - sendMessage : 短信发送成功");
        }
    }

}

package com.taotao.portal.service.impl;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.HttpClientUtil;
import com.taotao.common.utils.JsonUtils;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemDesc;
import com.taotao.pojo.TbItemParamItem;
import com.taotao.portal.pojo.Item;
import com.taotao.portal.pojo.ItemInfo;
import com.taotao.portal.service.ItemService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 商品信息管理service
 */
@Service
public class ItemServiceImpl implements ItemService {
    @Value("${REST_BASE_URL}")
    private String REST_BASE_URL;
    @Value("${ITEM_INFO_URL}")
    private String ITEM_INFO_URL;
    @Value("${ITEM_DESC_URL}")
    private String ITEM_DESC_URL;
    @Value("${ITEM_PARAM_URL}")
    private String ITEM_PARAM_URL;
    @Override
    public ItemInfo getItemById(long itemId) {
        //调用rest的服务查询商品信息
        String json = HttpClientUtil.doGet(REST_BASE_URL + ITEM_INFO_URL + itemId);
        if(!StringUtils.isBlank(json)){
            TaotaoResult result = TaotaoResult.formatToPojo(json, ItemInfo.class);
            if(result.getStatus()==200){
                ItemInfo data = (ItemInfo) result.getData();
                return data;
            }
        }
        return null;
    }

    @Override
    public String getIdemDescById(long itemId) {
        //调用rest服务根据商品id查询商品描述信息
        String json = HttpClientUtil.doGet(REST_BASE_URL + ITEM_DESC_URL + itemId);
        TaotaoResult taotaoResult = TaotaoResult.formatToPojo(json, TbItemDesc.class);
        if(taotaoResult.getStatus()==200){
            TbItemDesc data = (TbItemDesc) taotaoResult.getData();
            return data.getItemDesc();
        }
        return null;
    }

    @Override
    public String getItemParamById(long itemId) {
        //调用rest服务通过商品id查询商品规格
        String json = HttpClientUtil.doGet(REST_BASE_URL + ITEM_PARAM_URL + itemId);
        System.out.println("json"+json);
        //把json转换成pojo
        TaotaoResult result = TaotaoResult.formatToPojo(json, TbItemParamItem.class);
        if(result.getStatus()==200){
            TbItemParamItem data = (TbItemParamItem) result.getData();
            System.out.println("data"+data);
            String paramData = data.getParamData();
            //把json数据转换成java对象
            List<Map> paramList = JsonUtils.jsonToList(paramData, Map.class);
            //将参数信息转换成html
            StringBuffer sb = new StringBuffer();
            //sb.append("<div>");
            sb.append("<table cellpadding=\"0\" cellspacing=\"1\" width=\"100%\" border=\"0\" class=\"Ptable\">\n");
            sb.append("    <tbody>\n");
            for (Map map : paramList) {
                sb.append("        <tr>\n");
                sb.append("            <th class=\"tdTitle\" colspan=\"2\">"+map.get("group")+"</th>\n");
                sb.append("        </tr>\n");
                List<Map> params = (List<Map>) map.get("params");
                for (Map map2 : params) {
                    sb.append("        <tr>\n");
                    sb.append("            <td class=\"tdTitle\">"+map2.get("k")+"</td>\n");
                    sb.append("            <td>"+map2.get("v")+"</td>\n");
                    sb.append("        </tr>\n");
                }
            }
            sb.append("    </tbody>\n");
            sb.append("</table>");
            //sb.append("</div>");
            return sb.toString();
        }
        return null;
    }
}

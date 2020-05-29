package com.taotao.portal.service.impl;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.CookieUtils;
import com.taotao.common.utils.HttpClientUtil;
import com.taotao.common.utils.JsonUtils;
import com.taotao.pojo.TbItem;
import com.taotao.portal.pojo.CartItem;
import com.taotao.portal.service.CartService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * 购物车Service
 */
@Service
public class CartServiceImpl implements CartService {
    @Value("${REST_BASE_URL}")
    private String REST_BASE_URL;
    @Value("${ITEM_INFO_URL}")
    private String ITEM_INFO_URL;
    /**
     * 向购物车添加商品
     * @param itemId
     * @param num
     * @return
     */
    @Override
    public TaotaoResult addCartItem(long itemId, int num, HttpServletRequest request, HttpServletResponse response) {
        CartItem cartItem = null;
        //先取购物车商品列表
        List<CartItem> cartItemList = getCartItemList(request);
        //判断列表中是否存在该商品
        for (CartItem item : cartItemList) {
            if(item.getId()==itemId){
                //有
                cartItem = item;
                item.setNum(num+item.getNum());
                break;
            }
        }
        if(cartItem==null){
            //根据itemId查询商品信息(调用rest的服务)
            String json = HttpClientUtil.doGet(REST_BASE_URL + ITEM_INFO_URL + itemId);
            //把json转换成pojo
            TaotaoResult result = TaotaoResult.formatToPojo(json, TbItem.class);
            if(result.getStatus()==200){
                TbItem item = (TbItem) result.getData();
                cartItem = new CartItem();
                cartItem.setId(item.getId());
                cartItem.setImage(item.getImage()==null? "":item.getImage().split(",")[0]);
                cartItem.setTitle(item.getTitle());
                cartItem.setPrice(item.getPrice());
                cartItem.setNum(num);
            }
            //把信息写入商品列表
            cartItemList.add(cartItem);
        }
        //把列表写回cookie
        CookieUtils.setCookie(request,response,"TT_CART",JsonUtils.objectToJson(cartItemList),true);
        return TaotaoResult.ok();
    }

    /**
     * 删除购物车商品
     * @param itemId
     * @return
     */
    @Override
    public TaotaoResult deleteCartItem(long itemId,HttpServletRequest request, HttpServletResponse response) {
        //从cookei中获取商品信息
        List<CartItem> list = getCartItemList(request);
        for (CartItem cartItem : list) {
            if(cartItem.getId()==itemId){
                list.remove(cartItem);
                break;
            }
        }
        CookieUtils.setCookie(request,response,"TT_CART",JsonUtils.objectToJson(list),true);
        return TaotaoResult.ok();
    }

    /**
     * 从cookie中取商品列表
     * @return
     */
    @Override
    public List<CartItem> getCartItemList(HttpServletRequest request){
        //从cookie中取商品列表
        String tt_cart = CookieUtils.getCookieValue(request, "TT_CART", true);
        if(StringUtils.isBlank(tt_cart)){
            return new ArrayList<>();
        }
        List<CartItem> cartItems = null;
        try {
            //把json转换成商品列表
            cartItems = JsonUtils.jsonToList(tt_cart, CartItem.class);
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
        return cartItems;
    }
}

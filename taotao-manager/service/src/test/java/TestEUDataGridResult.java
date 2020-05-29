import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.common.pojo.EUTreeNode;
import com.taotao.mapper.TbItemCatMapper;
import com.taotao.mapper.TbItemMapper;
import com.taotao.common.pojo.EUDataGridResult;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemCat;
import com.taotao.pojo.TbItemCatExample;
import com.taotao.pojo.TbItemExample;
import com.taotao.service.ContentCategoryService;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.ArrayList;
import java.util.List;

public class TestEUDataGridResult {
    @Test
    public void testFenYe() {
        ApplicationContext context = new ClassPathXmlApplicationContext("classpath:spring/applicationContext-service.xml");
        TbItemMapper bean = context.getBean(TbItemMapper.class);
        TbItemExample example = new TbItemExample();
        PageHelper.startPage(1,30);
        List<TbItem> list = bean.selectByExample(example);
        for (TbItem tbItem : list) {
            System.out.println(tbItem);
        }
        PageInfo<TbItem> pageInfo = new PageInfo<>(list);
        long total = pageInfo.getTotal();
        EUDataGridResult result = new EUDataGridResult();
        result.setRows(list);
        result.setTotal(total);
    }
}

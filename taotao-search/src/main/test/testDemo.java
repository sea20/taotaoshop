import com.taotao.search.pojo.Item;
import com.taotao.search.pojo.SearchResult;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class testDemo {
    @Test
    public void testString(){
        System.out.println("哦哦");
    }
    SolrServer solrServer;
    @Test
    public void searchItem() throws Exception {
        //ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring/applicationContext-solr.xml");
        //SolrServer bean = applicationContext.getBean(SolrServer.class);
        SolrServer solrServer = new HttpSolrServer("http://localhost:8080/solr");
        SolrQuery solrQuery = new SolrQuery();
        solrQuery.setQuery("手机");
        solrQuery.set("df","item_keywords");
        QueryResponse query = solrServer.query(solrQuery);
        SolrDocumentList results = query.getResults();
        System.out.println(results);
    }
}

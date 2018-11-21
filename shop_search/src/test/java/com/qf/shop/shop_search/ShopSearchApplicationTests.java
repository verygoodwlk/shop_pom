package com.qf.shop.shop_search;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ShopSearchApplicationTests {

    @Autowired
    private SolrClient solrClient;

    /**
     * 添加索引库 - 后台工程每添加一个商品都应该同步到索引库中
     */
    @Test
    public void addIndex() throws IOException, SolrServerException {
        //相当于数据库中的一条记录
        for(int i = 2; i <= 20; i++) {
            SolrInputDocument document = new SolrInputDocument();
            document.addField("id", i);
            document.addField("gtitle", "美的冰箱" + i);
            document.addField("ginfo", "详细的电器的描述" + i);
            document.addField("gprice", 199.00 + i);
            document.addField("gcount", 100 + i);
            document.addField("gimage", "group1/M00/00/00/wKjioFvya8CAK75FAAQGJqISeUs664.jpg");

            solrClient.add(document);
        }
        solrClient.commit();


//        SolrInputDocument document = new SolrInputDocument();
//        document.addField("id", 2);
//        document.addField("gtitle", "性价比很高的手机");
//        document.addField("ginfo", "详细的华为手机描述");
//        document.addField("gprice", 199.00);
//        document.addField("gcount", 100);
//        document.addField("gimage", "http://www.baidu.com");
//
//        solrClient.add(document);
//        solrClient.commit();
    }

    /**
     * 修改索引库
     */
    @Test
    public void updateIndex() throws IOException, SolrServerException {
        SolrInputDocument document = new SolrInputDocument();
        document.addField("id", 1);
        document.addField("gtitle", "华为手机");
        document.addField("ginfo", "详细的电器的描述，华为手机");
        document.addField("gprice", 1999.00);
        document.addField("gcount", 1000);
        document.addField("gimage", "http://www.baidu.com");

        solrClient.add(document);
        solrClient.commit();
    }


    /**
     * 删除索引
     */
    @Test
    public void deleteIndex() throws IOException, SolrServerException {
//        solrClient.deleteById("3");
//        solrClient.commit();

//        solrClient.deleteByQuery("gtitle:美的");
//        solrClient.commit();
    }


    /**
     * 查询
     */
    @Test
    public void query() throws IOException, SolrServerException {
        SolrQuery solrQuery = new SolrQuery();
        solrQuery.setQuery("gtitle:华为 || ginfo:华为");

        QueryResponse result = solrClient.query(solrQuery);
        SolrDocumentList results = result.getResults();
        for(SolrDocument document : results){
            String id = (String) document.get("id");
            String title = (String) document.get("gtitle");
            String info = (String) document.get("ginfo");
            float price = (float) document.get("gprice");
            int count = (int) document.get("gcount");
            String gimage = (String) document.get("gimage");

            System.out.println(id + " " + title + " " + info + " " + price + " " + count + " " + gimage);
        }
    }


}

package com.qf.shop_service_provider.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.qf.entity.Goods;
import com.qf.entity.PageSolr;
import com.qf.service.ISearchService;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Author ken
 * @Time 2018/11/20 15:56
 * @Version 1.0
 */
@Service
public class SearchServiceImpl implements ISearchService {

    @Autowired
    private SolrClient solrClient;

    /**
     * 添加索引
     * @param goods
     * @return
     */
    @Override
    public int addIndex(Goods goods) {
        try {
            SolrInputDocument solrDocument = new SolrInputDocument();
            solrDocument.addField("id", goods.getId());
            solrDocument.addField("gtitle", goods.getTitle());
            solrDocument.addField("ginfo", goods.getGinfo());
            solrDocument.addField("gprice", goods.getPrice());
            solrDocument.addField("gcount", goods.getGcount());
            solrDocument.addField("gimage", goods.getGimage());


            solrClient.add(solrDocument);
            solrClient.commit();

            return 1;
        } catch (SolrServerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public List<Goods> queryIndex(String keyword)  {

        String queryStr = null;
        if(keyword == null || keyword.trim().equals("")){
            queryStr = "*:*";
        } else {
            queryStr = String.format("gtitle:%s || ginfo:%s", keyword, keyword);
        }

        SolrQuery solrQuery = new SolrQuery();
        solrQuery.setQuery(queryStr);

        //开启高亮
        solrQuery.setHighlight(true);

        //设置高亮的前后缀
        solrQuery.setHighlightSimplePre("<font color='red'>");
        solrQuery.setHighlightSimplePost("</font>");

        //设置哪些字段需要高亮显示
        solrQuery.addHighlightField("gtitle");

        //设置高亮的折叠
        solrQuery.setHighlightSnippets(2);//折叠的次数
        solrQuery.setHighlightFragsize(5);//关键字前后内容的小


        //limit ?,?
//        solrQuery.setStart();//设置查询的开始位置
//        solrQuery.setRows();//设置每页显示多少条


        List<Goods> goodsList = new ArrayList<>();
        QueryResponse response = null;
        try {
            response = solrClient.query(solrQuery);
            SolrDocumentList results = response.getResults();

            //额外的获得高亮结果
            Map<String, Map<String, List<String>>> highlighting = response.getHighlighting();

            //遍历所有的普通结果（内容无高亮）
            for(SolrDocument document : results){
                Integer id = Integer.parseInt(document.get("id") + "");
                String title = (String) document.get("gtitle");
                float price = (float) document.get("gprice");
                Integer gcount = (Integer) document.get("gcount");
                String gimage = (String) document.get("gimage");

                //判断当前商品是否有高亮
                if(highlighting.containsKey(id + "")){
                    //说明当前id的商品存在高亮内容

                    Map<String, List<String>> stringListMap = highlighting.get(id + "");
                    List<String> gtitle = stringListMap.get("gtitle");
                    if(gtitle != null){
                        title = "";
                        for (String str : gtitle)
                            title += str + "...";
                    }
                }


                Goods goods = new Goods(id, title, null, gcount, null, null, (double)price, gimage);
                goodsList.add(goods);
            }

        } catch (SolrServerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return goodsList;
    }

    /**
     * 根据关键字分页搜索结果
     * @param keyword
     * @param pageSolr
     * @return
     */
    @Override
    public PageSolr<Goods> queryIndexPage(String keyword, PageSolr<Goods> pageSolr) {
        String queryStr = null;
        if(keyword == null || keyword.trim().equals("")){
            queryStr = "*:*";
        } else {
            queryStr = String.format("gtitle:%s || ginfo:%s", keyword, keyword);
        }

        SolrQuery solrQuery = new SolrQuery();
        solrQuery.setQuery(queryStr);

        //开启高亮
        solrQuery.setHighlight(true);

        //设置高亮的前后缀
        solrQuery.setHighlightSimplePre("<font color='red'>");
        solrQuery.setHighlightSimplePost("</font>");

        //设置哪些字段需要高亮显示
        solrQuery.addHighlightField("gtitle");

        //设置高亮的折叠
//        solrQuery.setHighlightSnippets(2);//折叠的次数
//        solrQuery.setHighlightFragsize(5);//关键字前后内容的小


        //limit ?,?
        solrQuery.setStart((pageSolr.getPage() - 1) * pageSolr.getPageSize());//设置查询的开始位置
        solrQuery.setRows(pageSolr.getPageSize());//设置每页显示多少条


        List<Goods> goodsList = new ArrayList<>();
        QueryResponse response = null;
        try {
            response = solrClient.query(solrQuery);
            SolrDocumentList results = response.getResults();

            //获得搜索的总条数
            pageSolr.setPageCount((int) results.getNumFound());
            pageSolr.setPageSum(pageSolr.getPageCount() % pageSolr.getPageSize() == 0 ?
                    pageSolr.getPageCount() / pageSolr.getPageSize() :
                    pageSolr.getPageCount() / pageSolr.getPageSize() + 1);

            //额外的获得高亮结果
            Map<String, Map<String, List<String>>> highlighting = response.getHighlighting();

            //遍历所有的普通结果（内容无高亮）
            for(SolrDocument document : results){
                Integer id = Integer.parseInt(document.get("id") + "");
                String title = (String) document.get("gtitle");
                float price = (float) document.get("gprice");
                Integer gcount = (Integer) document.get("gcount");
                String gimage = (String) document.get("gimage");

                //判断当前商品是否有高亮
                if(highlighting.containsKey(id + "")){
                    //说明当前id的商品存在高亮内容

                    Map<String, List<String>> stringListMap = highlighting.get(id + "");
                    List<String> gtitle = stringListMap.get("gtitle");
                    if(gtitle != null){
                        title = gtitle.get(0);
                    }
                }


                Goods goods = new Goods(id, title, null, gcount, null, null, (double)price, gimage);
                goodsList.add(goods);
            }

            pageSolr.setDatas(goodsList);

        } catch (SolrServerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        return pageSolr;
    }
}

package com.qf.shop_service_provider.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.qf.entity.Goods;
import com.qf.service.ISearchService;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.List;

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
    public List<Goods> queryIndex(String keyword) {
        return null;
    }
}

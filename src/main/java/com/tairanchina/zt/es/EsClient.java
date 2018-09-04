package com.tairanchina.zt.es;

import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.cluster.node.DiscoveryNode;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

public class EsClient {
    private static Logger logger = LoggerFactory.getLogger(EsClient.class);
    private static String inetAddressSource = "localhost";
    private static String clusterName = "my-application";
    private static TransportClient sourceClient = null;
    private static TransportClient targetClient = null;

    private static String inetAddressTarget = "localhost";

    public static TransportClient init(String hostsString, String cluster) throws UnknownHostException {

        Settings settings = Settings.builder()
                .put("cluster.name", cluster)
                .build();
        TransportClient client = new PreBuiltTransportClient(settings)
                .addTransportAddress(new TransportAddress(InetAddress.getByName(hostsString), 9300));
        List<DiscoveryNode> discoveryNodes = client.connectedNodes();
        if (discoveryNodes.size() > 0) {
            discoveryNodes.forEach(
                    discoveryNode -> logger.info(discoveryNode.getHostAddress())
            );
        } else {
            logger.error("connect failed");
        }
        return client;

    }

    public static void main(String[] args) throws Exception {
        sourceClient = init(inetAddressSource, clusterName);
        targetClient = init(inetAddressTarget, clusterName);

        int batchSize = 0;
        List<IndexRequest> list = new ArrayList<>();

        SearchResponse searchResponse = sourceClient.prepareSearch("bank")
                .setTypes("_doc")
                .setSize(10000)
                //这个游标维持多长时间
                .setScroll(TimeValue.timeValueMinutes(8))
                .execute()
                .actionGet();

        while (true) {
            for (SearchHit searchHit : searchResponse.getHits()) {
                IndexRequest indexRequest = new IndexRequest("bank2", "_doc", searchHit.getId());
                indexRequest.source(searchHit.getSourceAsMap());
                list.add(indexRequest);
                batchSize++;
                if (batchSize == 1000) {
                    bulkInsert(list);
                    list.clear();
                    batchSize = 0;
                }
            }

            if (list.size() > 0) {
                bulkInsert(list);
                list.clear();
                batchSize = 0;
            }


            searchResponse = sourceClient.prepareSearchScroll(searchResponse.getScrollId())
                    .setScroll(TimeValue.timeValueMinutes(8))
                    .execute()
                    .actionGet();

            if (searchResponse.getHits().getHits().length == 0) {
                break;
            }
        }

        bulkInsert(list);

    }

    public static void bulkInsert(List<IndexRequest> requests) {

        if (requests.size() == 0)
            return;
        logger.info("bulk insert size :{}", requests.size());

        BulkRequestBuilder bulkRequest = targetClient.prepareBulk();
        for (IndexRequest request : requests) {
            bulkRequest.add(request);
        }
        BulkResponse bulkResponse = bulkRequest.execute().actionGet();
        if (bulkResponse.hasFailures()) {
            logger.error("bulk insert failed");
        }
    }
}

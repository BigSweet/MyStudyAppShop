package com.demo.swt.mystudyappshop;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

/**
 * 介绍：这里写介绍
 * 作者：sweet
 * 邮箱：sunwentao@imcoming.cn
 * 时间: 2017/2/20
 */

public class Test {
    private int index = 1;
    private String url = "http://www.qiushibaike.com/8hr/page/" + index + "/";

    public static void main(String[] args) {
        Test utils = new Test();
        utils.testAes();
    }

    public void testAes() {
        new Thread() {
            @Override
            public void run() {
                super.run();

                try {
                    Document document = Jsoup.connect(url).get();
                    Elements datas = document.select("div.article.block.untagged.mb15");
                  /*  Elements element = document.select("a.contentHerf");
                    Elements srcele = document.select("div.author.clearfix");
                    Elements image = document.select(".thumb img[src$=jpg]");
                    Element image1 = document.getElementById("content_left");*/
                    for (int i = 0; i < datas.size(); i++) {
                        Element element = datas.get(i);

                        Elements infoData = element.select("div.author.clearfix");
                        Elements picdata = element.select("div.thumb");
                        Elements conentData =element.select("div.content");
                        System.out.println("头像   " + infoData.get(0).getElementsByIndexEquals(0).attr("src"));
                        System.out.println("picdata"+conentData.text());
                        if (picdata.size() > 0) {
                                System.out.println("内容----------------图片  " + picdata.get(0).getElementsByIndexEquals(0).attr("src"));
                        } else {
                            System.out.println("内容下面没有图片");
                        }
                        /*System.out.println("conent start :" + i);
                        System.out.println(element.get(i).text());
                        System.out.println("conent end :" + i);*/
//                        System.out.println("image :" + elements.get(i).children().size());
                       /* if (image!=null){
                            System.out.println("image" +image);
                        }else {
                            System.out.println("image没有图片");
                        }*/
                    }
              /*      for (int i = 0; i < srcele.size(); i++) {
                        Element element1= srcele.get(i);
                        Elements elements = element1.children();
                        for (int j = 0; j < elements.size(); j++) {
                            Element elementchild = elements.get(j);
//                            System.out.println("elementchild="+j+"/"+ elementchild);
                            if(j == 0){
                                System.out.println("img=" + elementchild.getElementsByIndexEquals(0).attr("src"));
                            }else if(j==1){
                                System.out.println("name=" + elementchild.text());
                            }else if(j == 2){
                                System.out.println("age=" + elementchild.text());
                            }
                        }

//                        System.out.println("child="+ element1.children());
//                        System.out.println("size="+ elements.size());
//                        System.out.println(srcele.get(i).toString());

                    }*/

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

}

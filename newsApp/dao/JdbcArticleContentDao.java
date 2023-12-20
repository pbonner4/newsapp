package newsApp.dao;

import io.cucumber.java.an.E;
import newsApp.model.Article;
import newsApp.model.ArticleContent;
import newsApp.services.ArticleService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.jdbc.core.JdbcTemplate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import newsApp.dao.JdbcArticleDao;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Set;

import org.json.JSONObject;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcArticleContentDao implements ArticleContentDao {

    private final JdbcTemplate jdbcTemplate;

    private JdbcArticleDao articleDao;

    public JdbcArticleContentDao(JdbcTemplate jdbcTemplate, JdbcArticleDao articleDao) {
        this.jdbcTemplate = jdbcTemplate;
        this.articleDao = articleDao;
    }

    @Override
    public void insertBusinessContent() {
        List<Article> articles = articleDao.getBusinessArticles();


        for (Article article : articles) {
            try {
                if (article.getName().equals("Investing.com")) {
                    String url = article.getUrl();
                    Document doc = Jsoup.connect(url).get();
                    Elements paragraphs = doc.select("p");
                    StringBuilder content = new StringBuilder();
                    for (Element paragraph : paragraphs) {
                        if (!paragraph.text().contains("Please try another search") && !paragraph.text().contains("Position added successfully to:") && !paragraph.text().contains("Add a Comment") && !paragraph.text().contains("We encourage you to use comments to engage with other users, share your perspective and ask questions of authors and each other. However, in order to maintain the high level of discourse we’ve all come to value and expect, please keep the following criteria in mind:") && !paragraph.text().contains("the conversation, don’t trash it.") && !paragraph.text().contains("Only post material that’s relevant to the topic being discussed.") && !paragraph.text().contains("Even negative opinions can be framed positively and diplomatically.") && !paragraph.text().contains("Avoid profanity, slander or personal attacks") && !paragraph.text().contains("directed at an author or another user. Racism, sexism and other forms of discrimination will not be tolerated.") && !paragraph.text().contains("Perpetrators of spam or abuse will be deleted from the site and prohibited from future registration at Investing.com’s discretion.") && !paragraph.text().contains("Are you sure you want to block %USER_NAME%?") && !paragraph.text().contains("By doing so, you and %USER_NAME% will not be able to see\n" +
                                "any of each other's Investing.com's posts.") && !paragraph.text().contains("%USER_NAME%") && !paragraph.text().contains("Since you’ve just unblocked this person, you must wait 48 hours before renewing the block.") && !paragraph.text().contains("I feel that this comment is:") && !paragraph.text().contains("Trade With A Regulated Broker")) {

                            content.append("<p>" + paragraph.text() + "</p>" + System.lineSeparator() + System.lineSeparator());
                        }
                    }

                    String contentString = content.toString();

                    String sql = "INSERT INTO article_content (article_id, title, text, url, url_to_image, category, category_specified) VALUES (?,?,?,?,?,?,?) ON CONFLICT (article_id) DO NOTHING;";
                    jdbcTemplate.update(sql, article.getId(), article.getTitle(), contentString, url, article.getUrlToImage(), article.getCategory(), article.getCategorySpecified());
                } else if (article.getName().equals("The Wall Street Journal")) {
                    String url = article.getUrl();
                    Document doc = Jsoup.connect(url).get();
                    Elements paragraphs = doc.select("p");
                    StringBuilder content = new StringBuilder();
                    content.append("You need to Subscribe to the WSJ in order to view this content. If you have a subscription feel free to click the link above to read the article, and make sure to come right back and drop a comment down below!" + System.lineSeparator() + System.lineSeparator());
                    String contentString = content.toString();
                    String sql = "INSERT INTO article_content (article_id, title, text, url, url_to_image, category, category_specified) VALUES (?,?,?,?,?,?,?) ON CONFLICT (article_id) DO NOTHING;";
                    jdbcTemplate.update(sql, article.getId(), article.getTitle(), contentString, url, article.getUrlToImage(), article.getCategory(), article.getCategorySpecified());
                } else if (article.getName().equals("MarketWatch")) {
                    String url = article.getUrl();
                    Document doc = Jsoup.connect(url).get();
                    Elements paragraphs = doc.select("p");
                    StringBuilder content = new StringBuilder();
                    for (Element paragraph : paragraphs) {
                        if (!paragraph.text().contains("Tim Rostan, our managing editor") && !paragraph.text().contains("An expanded version of this story appears on WSJ.com.") && !paragraph.text().contains("Visit a quote page and your recently viewed tickers will be displayed here.")) {
                            content.append("<p>" + paragraph.text() + "</p>" + System.lineSeparator() + System.lineSeparator());
                        }
                    }
                    String contentString = content.toString();

                    String sql = "INSERT INTO article_content (article_id, title, text, url, url_to_image, category, category_specified) VALUES (?,?,?,?,?,?,?) ON CONFLICT (article_id) DO NOTHING;";
                    jdbcTemplate.update(sql, article.getId(), article.getTitle(), contentString, url, article.getUrlToImage(), article.getCategory(), article.getCategorySpecified());
                } else if (article.getName().equals("The Economist")) {
                    String url = article.getUrl();
                    Document doc = Jsoup.connect(url).get();
                    Elements paragraphs = doc.select("p");
                    StringBuilder content = new StringBuilder();
                    content.append("You need to Subscribe to The Economist in order to view the full content. If you have a subscription feel free to click the link above to read the article, and make sure to come right back and drop a comment down below!" + System.lineSeparator() + System.lineSeparator());
                    for (Element paragraph : paragraphs) {
                        if (!paragraph.text().contains("IMAGES")) {
                            content.append("<p>" + paragraph.text() + "</p>" + System.lineSeparator() + System.lineSeparator());
                        }
                    }
                    String contentString = content.toString();

                    String sql = "INSERT INTO article_content (article_id, title, text, url, url_to_image, category, category_specified) VALUES (?,?,?,?,?,?,?) ON CONFLICT (article_id) DO NOTHING;";
                    jdbcTemplate.update(sql, article.getId(), article.getTitle(), contentString, url, article.getUrlToImage(), article.getCategory(), article.getCategorySpecified());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void insertNbaContent() {
        List<Article> articles = articleDao.getNbaArticles();


        for (Article article : articles) {
            try {
                if (article.getName().equals("CBS Sports") && !article.getTitle().contains("How to Watch") && !article.getTitle().contains("How to watch")) {
                    String url = article.getUrl();
                    Document doc = Jsoup.connect(url).get();
                    Elements paragraphs = doc.select(".Article-bodyContent p");
                    Elements h2Elements = doc.select("h2");
                    Elements ulAfterPElements = doc.select("ul");
                    StringBuilder content = new StringBuilder();
                    for (Element paragraph : paragraphs) {
                        if (!paragraph.text().contains("Play Now") && !paragraph.text().contains("Football Pick'em") && !paragraph.text().contains("Play Now") && !paragraph.text().contains("College Football Pick'em") && !paragraph.text().contains("Your Daily NFL Fix") && !paragraph.text().contains("Daily Soccer Podcast") && !paragraph.text().contains("© 2004-2023 CBS Interactive. All Rights Reserved.") && !paragraph.text().contains("CBS Sports is a registered trademark of CBS Broadcasting Inc. Commissioner.com is a registered trademark of CBS Interactive Inc.")) {
                            content.append("<p>" + paragraph.text() + "</p>" + System.lineSeparator() + System.lineSeparator());
                            for (Element ulAfterPElement : ulAfterPElements){
                                Element previousElement = ulAfterPElement.previousElementSibling();
                                if(previousElement == paragraph){
                                    content.append(ulAfterPElement.html());
                                }
                            }
                        }
                        for (Element h3 : h2Elements) {
                            if (h3.previousElementSibling() == paragraph) {
                                content.append("<h3>" + h3.text()  + "</h3>" + System.lineSeparator() + System.lineSeparator());
                            }
                        }
                        String contentString = content.toString();

                        String sql = "INSERT INTO article_content (article_id, title, text, url, url_to_image, category, category_specified) VALUES (?,?,?,?,?,?,?) ON CONFLICT (article_id) DO NOTHING;";
                        jdbcTemplate.update(sql, article.getId(), article.getTitle(), contentString, url, article.getUrlToImage(), article.getCategory(), article.getCategorySpecified());
                    }
                }else if (article.getName().equals("NBCSports.com")) {
                        String articleUrl = article.getUrl();
                        Document doc = Jsoup.connect(articleUrl).get();
                        StringBuilder content = new StringBuilder();
                        Elements pElements = doc.select(".entry-content p");
                        Elements backupPElements = doc.select(".field p");
                        Set<String> tweetUrls = new HashSet<>();
                        for (Element pElement: pElements) {

                            if(!pElement.text().contains("https://www.facebook.com/"))
                            content.append("<p>" + pElement.text() + "</p>" + System.lineSeparator() + System.lineSeparator());



                            Elements aTags = doc.select("a[href*=twitter.com]");
                            for (Element aTag : aTags) {
                                String tweetUrl = aTag.attr("href").split("\\?")[0];
                                String tweetRegex = "https://twitter.com/(?!(?:i\\/|.*timelines\\/))\\w+/status/\\d+";
                                if (tweetUrl.matches(tweetRegex) && !tweetUrls.contains(tweetUrl)) {
                                    // Construct the API URL with the tweet URL as a query parameter
                                    String apiUrl = "https://publish.twitter.com/oembed?url=" + URLEncoder.encode(tweetUrl, "UTF-8");
                                    URL url = new URL(apiUrl);
                                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                                    conn.setRequestMethod("GET");
                                    BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                                    String inputLine;
                                    StringBuilder response = new StringBuilder();
                                    while ((inputLine = in.readLine()) != null) {
                                        response.append(inputLine);
                                    }
                                    in.close();
                                    JSONObject jsonResponse = new JSONObject(response.toString());
                                    String html = jsonResponse.getString("html");
                                    String blockquoteHtml = html.replaceAll("(?s)<blockquote[^>]*>((?:(?!</?script)[\\s\\S])*?)</blockquote>", "<blockquote class=\"twitter-tweet\">$1</blockquote>");
                                    String removingScriptTags = blockquoteHtml.replaceAll("<script async src=\"https://platform.twitter.com/widgets.js\" charset=\"utf-8\"></script>", "");
                                    if (blockquoteHtml != null) {
                                        content.append(removingScriptTags);
                                        tweetUrls.add(tweetUrl); // Add the tweet URL to the set
                                    }
                                }
                            }
                        }
                    for (Element pElement: backupPElements) {

                        content.append("<p>" + pElement.text() + "</p>" + System.lineSeparator() + System.lineSeparator());

                        Elements aTags = doc.select("a[href*=twitter.com]");
                        for (Element aTag : aTags) {
                            String tweetUrl = aTag.attr("href").split("\\?")[0];
                            String tweetRegex = "https://twitter.com/(?!(?:i\\/|.*timelines\\/))\\w+/status/\\d+";
                            if (tweetUrl.matches(tweetRegex) && !tweetUrls.contains(tweetUrl)) {
                                // Construct the API URL with the tweet URL as a query parameter
                                String apiUrl = "https://publish.twitter.com/oembed?url=" + URLEncoder.encode(tweetUrl, "UTF-8");
                                URL url = new URL(apiUrl);
                                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                                conn.setRequestMethod("GET");
                                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                                String inputLine;
                                StringBuilder response = new StringBuilder();
                                while ((inputLine = in.readLine()) != null) {
                                    response.append(inputLine);
                                }
                                in.close();
                                JSONObject jsonResponse = new JSONObject(response.toString());
                                String html = jsonResponse.getString("html");
                                String blockquoteHtml = html.replaceAll("(?s)<blockquote[^>]*>((?:(?!</?script)[\\s\\S])*?)</blockquote>", "<blockquote class=\"twitter-tweet\">$1</blockquote>");
                                String removingScriptTags = blockquoteHtml.replaceAll("<script async src=\"https://platform.twitter.com/widgets.js\" charset=\"utf-8\"></script>", "");
                                if (blockquoteHtml != null) {
                                    content.append(removingScriptTags);
                                    tweetUrls.add(tweetUrl); // Add the tweet URL to the set
                                }
                            }
                        }
                    }
                        String contentString = content.toString();


                        String sql = "INSERT INTO article_content (article_id, title, text, url, url_to_image, category, category_specified) VALUES (?,?,?,?,?,?,?) ON CONFLICT (article_id) DO NOTHING;";
                        jdbcTemplate.update(sql, article.getId(), article.getTitle(), contentString, articleUrl, article.getUrlToImage(), article.getCategory(), article.getCategorySpecified());
                    } else if (article.getName().equals("NBCSports.com")) {
                        String articleUrl = article.getUrl();
                        Document doc = Jsoup.connect(articleUrl).get();
                        Elements pElements = doc.select("p");
                        StringBuilder content = new StringBuilder();
                        for (int i = 0; i < pElements.size(); i++) {
                            Element paragraph = pElements.get(i);
                            content.append("<p>" + paragraph.text() + "</p>" + System.lineSeparator() + System.lineSeparator());


                            Elements aTags = paragraph.select("a[href*=twitter.com]");
                            for (Element aTag : aTags) {
                                String tweetUrl = aTag.attr("href").split("\\?")[0];
                                String tweetRegex = "https://twitter.com/(?!(?:i\\/|.*timelines\\/))\\w+/status/\\d+";
                                if (tweetUrl.matches(tweetRegex)) {
                                    // Construct the API URL with the tweet URL as a query parameter
                                    String apiUrl = "https://publish.twitter.com/oembed?url=" + URLEncoder.encode(tweetUrl, "UTF-8");


                                    URL url = new URL(apiUrl);
                                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                                    conn.setRequestMethod("GET");


                                    BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                                    String inputLine;
                                    StringBuilder response = new StringBuilder();
                                    while ((inputLine = in.readLine()) != null) {
                                        response.append(inputLine);
                                    }
                                    in.close();


                                    JSONObject jsonResponse = new JSONObject(response.toString());
                                    String html = jsonResponse.getString("html");

                                    String blockquoteHtml = html.replaceAll("(?s)<blockquote[^>]*>((?:(?!</?script)[\\s\\S])*?)</blockquote>", "<blockquote class=\"twitter-tweet\">$1</blockquote>");


                                    String removingScriptTags = blockquoteHtml.replaceAll("<script async src=\"https://platform.twitter.com/widgets.js\" charset=\"utf-8\"></script>", "");


                                    if (blockquoteHtml != null) {
                                        content.append(removingScriptTags);
                                    }

                                }
                            }
                        }
                    String contentString = content.toString();



                        String sql = "INSERT INTO article_content (article_id, title, text, url, url_to_image, category, category_specified) VALUES (?,?,?,?,?,?,?) ON CONFLICT (article_id) DO NOTHING;";
                        jdbcTemplate.update(sql, article.getId(), article.getTitle(), contentString, articleUrl, article.getUrlToImage(), article.getCategory(), article.getCategorySpecified());
                    }
                } catch(IOException e){
                    e.printStackTrace();
                }
            }
        }

    @Override
    public void insertNflContent() {
        List<Article> articles = articleDao.getNflArticles();


        for (Article article : articles) {
            try {
                if (article.getName().equals("CBS Sports")) {
                    String articleUrl = article.getUrl();
                    Document doc = Jsoup.connect(articleUrl).get();
                    Elements paragraphs = doc.select(".Article-bodyContent p");
                    Elements h3Elements = doc.select("h3");
                    Elements h2Elements = doc.select("h2");
                    Elements ulAfterPElements = doc.select("ul");
                    Elements youtubeVids = doc.select("div.MediaShortcodeYoutube-Video");
                    StringBuilder content = new StringBuilder();
                    for (Element paragraph : paragraphs) {
                        if (!paragraph.text().contains("Play Now") && !paragraph.text().contains("Football Pick'em") && !paragraph.text().contains("Play Now") && !paragraph.text().contains("College Football Pick'em") && !paragraph.text().contains("Your Daily NFL Fix") && !paragraph.text().contains("Daily Soccer Podcast") && !paragraph.text().contains("© 2004-2023 CBS Interactive. All Rights Reserved.") && !paragraph.text().contains("CBS Sports is a registered trademark of CBS Broadcasting Inc. Commissioner.com is a registered trademark of CBS Interactive Inc.")) {
                            content.append("<p>" + paragraph.text() + "</p>" + System.lineSeparator() + System.lineSeparator());
                            for (Element ulAfterPElement : ulAfterPElements){
                                Element previousElement = ulAfterPElement.previousElementSibling();
                                if(previousElement == paragraph){
                                    content.append(ulAfterPElement.html());
                                }
                            }
                        }
                        for (Element h3 : h2Elements) {
                            if (h3.previousElementSibling() == paragraph) {
                                content.append("<strong>" + h3.text() + ":" + "</strong>" + System.lineSeparator() + System.lineSeparator());
                            }
                        }
                        for (Element youtubeVid : youtubeVids) {

                            content.append(youtubeVid.html());
                        }
//                        Elements aTags = paragraph.select("a[href*=twitter.com]");
//                        for (Element aTag : aTags) {
//                            String tweetUrl = aTag.attr("href").split("\\?")[0];
//                            String tweetRegex = "https://twitter.com/[a-zA-Z0-9_]+/status/\\d+";
//                                if (tweetUrl.matches(tweetRegex)) {
//                                String apiUrl = "https://publish.twitter.com/oembed?url=" + URLEncoder.encode(tweetUrl, "UTF-8");
//
//
//                                URL url = new URL(apiUrl);
//                                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//                                conn.setRequestMethod("GET");
//
//
//                                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
//                                String inputLine;
//                                StringBuilder response = new StringBuilder();
//                                while ((inputLine = in.readLine()) != null) {
//                                    response.append(inputLine);
//                                }
//                                in.close();
//
//
//                                JSONObject jsonResponse = new JSONObject(response.toString());
//                                String html = jsonResponse.getString("html");
//
//                                String blockquoteHtml = html.replaceAll("(?s)<blockquote[^>]*>((?:(?!</?script)[\\s\\S])*?)</blockquote>", "<blockquote class=\"twitter-tweet\">$1</blockquote>");
//
//
//                                String removingScriptTags = blockquoteHtml.replaceAll("<script async src=\"https://platform.twitter.com/widgets.js\" charset=\"utf-8\"></script>", "");
//
//
//                                if (blockquoteHtml != null) {
//                                    content.append(removingScriptTags);
//                                }
//
//                            }
//                        }
                    }
                    String contentString = content.toString();

                    String sql = "INSERT INTO article_content (article_id, title, text, url, url_to_image, category, category_specified) VALUES (?,?,?,?,?,?,?) ON CONFLICT (article_id) DO NOTHING;";
                    jdbcTemplate.update(sql, article.getId(), article.getTitle(), contentString, articleUrl, article.getUrlToImage(), article.getCategory(), article.getCategorySpecified());
                } else if (article.getName().equals("NFL News")) {
                    String url = article.getUrl();
                    Document doc = Jsoup.connect(url).get();
                    Elements pElements = doc.select(".nfl-c-article__container p");
                    StringBuilder content = new StringBuilder();
                    for (Element paragraph : pElements) {
                        if (!paragraph.text().contains("Follow Ian Rapoport on Twitter.")) {
                            content.append(paragraph.text() + System.lineSeparator() + System.lineSeparator());
                        }
                    }
                    String contentString = content.toString();
                    String sql = "INSERT INTO article_content (article_id, title, text, url, url_to_image, category, category_specified) VALUES (?,?,?,?,?,?,?) ON CONFLICT (article_id) DO NOTHING;";
                    jdbcTemplate.update(sql, article.getId(), article.getTitle(), contentString, url, article.getUrlToImage(), article.getCategory(), article.getCategorySpecified());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void insertNhlContent() {
        List<Article> articles = articleDao.getNhlArticles();


        for (Article article : articles) {
            try {
                if (article.getName().equals("CBS Sports")) {
                    String articleUrl = article.getUrl();
                    Document doc = Jsoup.connect(articleUrl).get();
                    Set<String> tweetUrls = new HashSet<>();
                    Elements paragraphs = doc.select("p");
                    Elements h3Elements = doc.select("h3");
                    StringBuilder content = new StringBuilder();
                    for (Element paragraph : paragraphs) {
                        if (!paragraph.text().contains("Nfl Draft Podcast") && !paragraph.text().contains("Getty") && !paragraph.text().contains("Play Now") && !paragraph.text().contains("Football Pick'em") && !paragraph.text().contains("Play Now") && !paragraph.text().contains("College Football Pick'em") && !paragraph.text().contains("Your Daily NFL Fix") && !paragraph.text().contains("Daily Soccer Podcast") && !paragraph.text().contains("© 2004-2023 CBS Interactive. All Rights Reserved.") && !paragraph.text().contains("CBS Sports is a registered trademark of CBS Broadcasting Inc. Commissioner.com is a registered trademark of CBS Interactive Inc.")) {
                            content.append("<p>" + paragraph.text() + "</p>" + System.lineSeparator() + System.lineSeparator());
                        }
                        for (Element h3 : h3Elements) {
                            if (h3.previousElementSibling() == paragraph) {
                                content.append("<strong>" + h3.text() + ":" + "</strong>" + System.lineSeparator() + System.lineSeparator());
                            }
                        }
                        Elements aTags = doc.select("a[href*=twitter.com]");
                        for (Element aTag : aTags) {
                            String tweetUrl = aTag.attr("href").split("\\?")[0];
                            String tweetRegex = "https://twitter.com/(?!(?:i\\/|.*timelines\\/))\\w+/status/\\d+";
                            if (tweetUrl.matches(tweetRegex) && !tweetUrls.contains(tweetUrl)) {
                                // Construct the API URL with the tweet URL as a query parameter
                                String apiUrl = "https://publish.twitter.com/oembed?url=" + URLEncoder.encode(tweetUrl, "UTF-8");
                                URL url = new URL(apiUrl);
                                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                                conn.setRequestMethod("GET");
                                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                                String inputLine;
                                StringBuilder response = new StringBuilder();
                                while ((inputLine = in.readLine()) != null) {
                                    response.append(inputLine);
                                }
                                in.close();
                                JSONObject jsonResponse = new JSONObject(response.toString());
                                String html = jsonResponse.getString("html");
                                String blockquoteHtml = html.replaceAll("(?s)<blockquote[^>]*>((?:(?!</?script)[\\s\\S])*?)</blockquote>", "<blockquote class=\"twitter-tweet\">$1</blockquote>");
                                String removingScriptTags = blockquoteHtml.replaceAll("<script async src=\"https://platform.twitter.com/widgets.js\" charset=\"utf-8\"></script>", "");
                                if (blockquoteHtml != null) {
                                    content.append(removingScriptTags);
                                    tweetUrls.add(tweetUrl); // Add the tweet URL to the set
                                }
                            }
                        }
                    }
                    String contentString = content.toString();

                    String sql = "INSERT INTO article_content (article_id, title, text, url, url_to_image, category, category_specified) VALUES (?,?,?,?,?,?,?) ON CONFLICT (article_id) DO NOTHING;";
                    jdbcTemplate.update(sql, article.getId(), article.getTitle(), contentString, articleUrl, article.getUrlToImage(), article.getCategory(), article.getCategorySpecified());
                } else if (article.getName().equals("NHL News")) {
                    String url = article.getUrl();
                    Document doc = Jsoup.connect(url).get();
                    Elements pElements = doc.select(".article-item__body p");
                    StringBuilder content = new StringBuilder();
                    for (Element paragraph : pElements) {
                        content.append(paragraph.text() + System.lineSeparator() + System.lineSeparator());
                    }
                    String contentString = content.toString();
                    String sql = "INSERT INTO article_content (article_id, title, text, url, url_to_image, category, category_specified) VALUES (?,?,?,?,?,?,?) ON CONFLICT (article_id) DO NOTHING;";
                    jdbcTemplate.update(sql, article.getId(), article.getTitle(), contentString, url, article.getUrlToImage(), article.getCategory(), article.getCategorySpecified());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void insertMmaContent() {
        List<Article> articles = articleDao.getMmaArticles();


        for (Article article : articles) {
            try {
                if (article.getName().equals("MMA Fighting")) {
                    String articleUrl = article.getUrl();
                    Set<String> tweetUrls = new HashSet<>();
                    Document doc = Jsoup.connect(articleUrl).get();
                    Elements pElements = doc.select(".c-entry-content p");
                    StringBuilder content = new StringBuilder();
                    for (Element paragraph : pElements) {
                        content.append("<p>"+paragraph.text()+"</p>" + System.lineSeparator() + System.lineSeparator());

                        Elements youtubeVids = doc.select("#player div");
                        for (Element youtubeVid : youtubeVids){
                            content.append(youtubeVid.html());
                        }

                        Elements aTags = doc.select("a[href*=twitter.com]");
                        for (Element aTag : aTags) {
                            String tweetUrl = aTag.attr("href").split("\\?")[0];
                            String tweetRegex = "https://twitter.com/(?!(?:i\\/|.*timelines\\/))\\w+/status/\\d+";
                            if (tweetUrl.matches(tweetRegex) && !tweetUrls.contains(tweetUrl)) {
                                // Construct the API URL with the tweet URL as a query parameter
                                String apiUrl = "https://publish.twitter.com/oembed?url=" + URLEncoder.encode(tweetUrl, "UTF-8");
                                URL url = new URL(apiUrl);
                                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                                conn.setRequestMethod("GET");
                                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                                String inputLine;
                                StringBuilder response = new StringBuilder();
                                while ((inputLine = in.readLine()) != null) {
                                    response.append(inputLine);
                                }
                                in.close();
                                JSONObject jsonResponse = new JSONObject(response.toString());
                                String html = jsonResponse.getString("html");
                                String blockquoteHtml = html.replaceAll("(?s)<blockquote[^>]*>((?:(?!</?script)[\\s\\S])*?)</blockquote>", "<blockquote class=\"twitter-tweet\">$1</blockquote>");
                                String removingScriptTags = blockquoteHtml.replaceAll("<script async src=\"https://platform.twitter.com/widgets.js\" charset=\"utf-8\"></script>", "");
                                if (blockquoteHtml != null) {
                                    content.append(removingScriptTags);
                                    tweetUrls.add(tweetUrl); // Add the tweet URL to the set
                                }
                            }
                        }
                    }
                    String contentString = content.toString();

                    String sql = "INSERT INTO article_content (article_id, title, text, url, url_to_image, category, category_specified) VALUES (?,?,?,?,?,?,?) ON CONFLICT (article_id) DO NOTHING;";
                    jdbcTemplate.update(sql, article.getId(), article.getTitle(), contentString, articleUrl, article.getUrlToImage(), article.getCategory(), article.getCategorySpecified());
                } else if (article.getName().equals("ESPN")) {
                    String articleUrl = article.getUrl();
                    Set<String> tweetUrls = new HashSet<>();
                    Document doc = Jsoup.connect(articleUrl).get();
                    Elements pElements = doc.select(".article-body p");
                    StringBuilder content = new StringBuilder();
                    for (Element paragraph : pElements) {
                        content.append("<p>" + paragraph.text() + "</p>" + System.lineSeparator() + System.lineSeparator());

                        Elements aTags = doc.select("a[href*=twitter.com]");
                        for (Element aTag : aTags) {
                            String tweetUrl = aTag.attr("href").split("\\?")[0];
                            String tweetRegex = "https://twitter.com/(?!(?:i\\/|.*timelines\\/))\\w+/status/\\d+";
                            if (tweetUrl.matches(tweetRegex) && !tweetUrls.contains(tweetUrl)) {
                                // Construct the API URL with the tweet URL as a query parameter
                                String apiUrl = "https://publish.twitter.com/oembed?url=" + URLEncoder.encode(tweetUrl, "UTF-8");
                                URL url = new URL(apiUrl);
                                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                                conn.setRequestMethod("GET");
                                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                                String inputLine;
                                StringBuilder response = new StringBuilder();
                                while ((inputLine = in.readLine()) != null) {
                                    response.append(inputLine);
                                }
                                in.close();
                                JSONObject jsonResponse = new JSONObject(response.toString());
                                String html = jsonResponse.getString("html");
                                String blockquoteHtml = html.replaceAll("(?s)<blockquote[^>]*>((?:(?!</?script)[\\s\\S])*?)</blockquote>", "<blockquote class=\"twitter-tweet\">$1</blockquote>");
                                String removingScriptTags = blockquoteHtml.replaceAll("<script async src=\"https://platform.twitter.com/widgets.js\" charset=\"utf-8\"></script>", "");
                                if (blockquoteHtml != null) {
                                    content.append(removingScriptTags);
                                    tweetUrls.add(tweetUrl); // Add the tweet URL to the set
                                }
                            }
                        }
                    }

                    String contentString = content.toString();
                    String sql = "INSERT INTO article_content (article_id, title, text, url, url_to_image, category, category_specified) VALUES (?,?,?,?,?,?,?) ON CONFLICT (article_id) DO NOTHING;";
                    jdbcTemplate.update(sql, article.getId(), article.getTitle(), contentString, articleUrl, article.getUrlToImage(), article.getCategory(), article.getCategorySpecified());
                }
//                } else if (article.getName().equals("ESPN")) {
//                    String url = article.getUrl();
//                    Document doc = Jsoup.connect(url).get();
//                    Elements pElements = doc.select(".article-body p");
//                    StringBuilder content = new StringBuilder();
//                    for (Element paragraph : pElements) {
//                        content.append(paragraph.text() + System.lineSeparator() + System.lineSeparator());
//                    }
//                    String contentString = content.toString();
//                    String sql = "INSERT INTO article_content (article_id, title, text, url, url_to_image, category, category_specified) VALUES (?,?,?,?,?,?,?) ON CONFLICT (article_id) DO NOTHING;";
//                    jdbcTemplate.update(sql, article.getId(), article.getTitle(), contentString, url, article.getUrlToImage(), article.getCategory(), article.getCategorySpecified());
//                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void insertMovieContent() {
        List<Article> articles = articleDao.getMovieArticles();


        for (Article article : articles) {
            try {
                if (article.getName().equals("Variety")) {
                    String articleUrl = article.getUrl();
                    Document doc = Jsoup.connect(articleUrl).get();
                    Elements pElements = doc.select(".vy-cx-page-content p");
                    Elements backupPElements = doc.select(".a-content p");
                    Elements imageElements = doc.select(".post-content-image div");
                    Elements h4elements = doc.select("h4");
                    StringBuilder content = new StringBuilder();
                    for (Element paragraph : pElements) {
                        if(paragraph.html().contains("<strong>")){
                            content.append("<p>" + "<strong>" + paragraph.text() + "</strong>" + "</p>" + System.lineSeparator() + System.lineSeparator());
                        }
                        if(!paragraph.html().contains("<strong")) {
                            content.append("<p>" + paragraph.text() + "</p>" + System.lineSeparator() + System.lineSeparator());
                        }
                        for(Element h4 : h4elements){
                            if(h4.previousElementSibling() == paragraph){
                                content.append("h4" + h4.text() + "</h4>");
                            }
                        }
                        for(Element img : imageElements){
                            if(img.previousElementSibling() == paragraph){
                                content.append(img.html());
                            }
                        }
                        Elements aTags = doc.select("a[href*=twitter.com]");
                        for (Element aTag : aTags) {
                            String tweetUrl = aTag.attr("href").split("\\?")[0];
                            String tweetRegex = "https://twitter.com/(?!(?:i\\/|.*timelines\\/))\\w+/status/\\d+";
                            if (tweetUrl.matches(tweetRegex)) {
                                // Construct the API URL with the tweet URL as a query parameter
                                String apiUrl = "https://publish.twitter.com/oembed?url=" + URLEncoder.encode(tweetUrl, "UTF-8");


                                URL url = new URL(apiUrl);
                                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                                conn.setRequestMethod("GET");


                                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                                String inputLine;
                                StringBuilder response = new StringBuilder();
                                while ((inputLine = in.readLine()) != null) {
                                    response.append(inputLine);
                                }
                                in.close();


                                JSONObject jsonResponse = new JSONObject(response.toString());
                                String html = jsonResponse.getString("html");

                                String blockquoteHtml = html.replaceAll("(?s)<blockquote[^>]*>((?:(?!</?script)[\\s\\S])*?)</blockquote>", "<blockquote class=\"twitter-tweet\">$1</blockquote>");


                                String removingScriptTags = blockquoteHtml.replaceAll("<script async src=\"https://platform.twitter.com/widgets.js\" charset=\"utf-8\"></script>", "");


                                if (blockquoteHtml != null) {
                                    content.append(removingScriptTags);
                                }

                            }
                        }
                    }
                    for (Element paragraph : backupPElements) {
                        if(paragraph.html().contains("<strong>")){
                            content.append("<p>" + "<strong>" + paragraph.text() + "</strong>" + "</p>" + System.lineSeparator() + System.lineSeparator());
                        }
                        if(!paragraph.html().contains("<strong")) {
                            content.append("<p>" + paragraph.text() + "</p>" + System.lineSeparator() + System.lineSeparator());
                        }
                        for(Element h4 : h4elements){
                            if(h4.previousElementSibling() == paragraph){
                                content.append("h4" + h4.text() + "</h4>");
                            }
                        }
                        for(Element img : imageElements){
                            if(img.previousElementSibling() == paragraph){
                                content.append(img.html());
                            }
                        }
                        Elements aTags = doc.select("a[href*=twitter.com]");
                        for (Element aTag : aTags) {
                            String tweetUrl = aTag.attr("href").split("\\?")[0];
                            String tweetRegex = "https://twitter.com/(?!(?:i\\/|.*timelines\\/))\\w+/status/\\d+";
                            if (tweetUrl.matches(tweetRegex)) {
                                // Construct the API URL with the tweet URL as a query parameter
                                String apiUrl = "https://publish.twitter.com/oembed?url=" + URLEncoder.encode(tweetUrl, "UTF-8");


                                URL url = new URL(apiUrl);
                                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                                conn.setRequestMethod("GET");


                                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                                String inputLine;
                                StringBuilder response = new StringBuilder();
                                while ((inputLine = in.readLine()) != null) {
                                    response.append(inputLine);
                                }
                                in.close();


                                JSONObject jsonResponse = new JSONObject(response.toString());
                                String html = jsonResponse.getString("html");

                                String blockquoteHtml = html.replaceAll("(?s)<blockquote[^>]*>((?:(?!</?script)[\\s\\S])*?)</blockquote>", "<blockquote class=\"twitter-tweet\">$1</blockquote>");


                                String removingScriptTags = blockquoteHtml.replaceAll("<script async src=\"https://platform.twitter.com/widgets.js\" charset=\"utf-8\"></script>", "");


                                if (blockquoteHtml != null) {
                                    content.append(removingScriptTags);
                                }

                            }
                        }
                    }
                    String contentString = content.toString();
                    String sql = "INSERT INTO article_content (article_id, title, text, url, url_to_image, category, category_specified) VALUES (?,?,?,?,?,?,?) ON CONFLICT (article_id) DO NOTHING;";
                    jdbcTemplate.update(sql, article.getId(), article.getTitle(), contentString, articleUrl, article.getUrlToImage(), article.getCategory(), article.getCategorySpecified());
                } else if (article.getName().equals("Hollywood Reporter")) {
                    String url = article.getUrl();
                    Document doc = Jsoup.connect(url).get();
                    Elements pElements = doc.select(".a-content p");
                    StringBuilder content = new StringBuilder();
                    for (Element paragraph : pElements) {
                        if (!paragraph.text().contains("Click here to subscribe.")) {
                            content.append("<p>" + paragraph.text() + "</p>" + System.lineSeparator() + System.lineSeparator());
                        }
                    }
                    String contentString = content.toString();
                    String sql = "INSERT INTO article_content (article_id, title, text, url, url_to_image, category, category_specified) VALUES (?,?,?,?,?,?,?) ON CONFLICT (article_id) DO NOTHING;";
                    jdbcTemplate.update(sql, article.getId(), article.getTitle(), contentString, url, article.getUrlToImage(), article.getCategory(), article.getCategorySpecified());
                } else if (article.getName().equals("Eonline.com")) {
                    String url = article.getUrl();
                    Document doc = Jsoup.connect(url).get();
                    Elements pElements = doc.select(".article-detail__text-only p");
                    StringBuilder content = new StringBuilder();
                    for (Element paragraph : pElements) {
                        content.append("<p>" + paragraph.text() + "</p>" + System.lineSeparator() + System.lineSeparator());
                    }
                    String contentString = content.toString();
                    String sql = "INSERT INTO article_content (article_id, title, text, url, url_to_image, category, category_specified) VALUES (?,?,?,?,?,?,?) ON CONFLICT (article_id) DO NOTHING;";
                    jdbcTemplate.update(sql, article.getId(), article.getTitle(), contentString, url, article.getUrlToImage(), article.getCategory(), article.getCategorySpecified());
                } else if (article.getName().equals("Entertainment Weekly")) {
                    String url = article.getUrl();
                    Document doc = Jsoup.connect(url).get();
                    Elements pElements = doc.select(".articleContainer__content p");
                    Elements backupPElements = doc.select(".longformContent p");
                    Elements backupH3Elements = doc.select("h3");
                    Elements h3Elements = doc.select(".articleContainer__content h3");
                    Elements images = doc.select(".lazy-image div");
                    StringBuilder content = new StringBuilder();
                    for (Element paragraph : pElements) {
                        content.append(paragraph.html() + System.lineSeparator() + System.lineSeparator());
                        for (Element image : images) {
                            if (image.previousElementSibling() == paragraph) {
                                content.append(image.html() + System.lineSeparator() + System.lineSeparator());
                            }
                        }
                        for (Element h3 : h3Elements) {
                            if (h3.previousElementSibling() == paragraph) {
                                content.append("<h3>" + h3.text() + "</h3>" + System.lineSeparator() + System.lineSeparator());
                            }
                        }
                    }
                    for (Element paragraph : backupPElements) {
                        content.append(paragraph.html() + System.lineSeparator() + System.lineSeparator());
                        for (Element image : images) {
                                content.append(image.html() + System.lineSeparator() + System.lineSeparator());
                        }
                        for (Element h3 : backupH3Elements) {
                                content.append("<h3>" + h3.text() + "</h3>" + System.lineSeparator() + System.lineSeparator());
                        }
                    }
                    String contentString = content.toString();
                    String sql = "INSERT INTO article_content (article_id, title, text, url, url_to_image, category, category_specified) VALUES (?,?,?,?,?,?,?) ON CONFLICT (article_id) DO NOTHING;";
                    jdbcTemplate.update(sql, article.getId(), article.getTitle(), contentString, url, article.getUrlToImage(), article.getCategory(), article.getCategorySpecified());
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void insertTvContent() {
        List<Article> articles = articleDao.getTvArticles();


        for (Article article : articles) {
            try {
                    if (article.getName().equals("Variety")) {
                        String url = article.getUrl();
                        Document doc = Jsoup.connect(url).get();
                        Elements pElements = doc.select(".vy-cx-page-content p");
                        Elements backupPElements = doc.select(".a-content p");
                        Elements imageElements = doc.select(".post-content-image div");
                        Elements h4elements = doc.select("h4");
                        StringBuilder content = new StringBuilder();
                        for (Element paragraph : pElements) {
                            if(paragraph.html().contains("<strong>")){
                                content.append("<p>" + "<strong>" + paragraph.text() + "</strong>" + "</p>" + System.lineSeparator() + System.lineSeparator());
                            }
                            if(!paragraph.html().contains("<strong")) {
                                content.append("<p>" + paragraph.text() + "</p>" + System.lineSeparator() + System.lineSeparator());
                            }
                            for(Element h4 : h4elements){
                                if(h4.previousElementSibling() == paragraph){
                                    content.append("h4" + h4.text() + "</h4>");
                                }
                            }
                            for(Element img : imageElements){
                                if(img.previousElementSibling() == paragraph){
                                    content.append(img.html());
                                }
                            }
                        }
                        for (Element paragraph : backupPElements) {
                            if(paragraph.html().contains("<strong>")){
                                content.append("<p>" + "<strong>" + paragraph.text() + "</strong>" + "</p>" + System.lineSeparator() + System.lineSeparator());
                            }
                            if(!paragraph.html().contains("<strong")) {
                                content.append("<p>" + paragraph.text() + "</p>" + System.lineSeparator() + System.lineSeparator());
                            }
                            for(Element h4 : h4elements){
                                if(h4.previousElementSibling() == paragraph){
                                    content.append("h4" + h4.text() + "</h4>");
                                }
                            }
                            for(Element img : imageElements){
                                if(img.previousElementSibling() == paragraph){
                                    content.append(img.html());
                                }
                            }
                        }
                        String contentString = content.toString();
                        String sql = "INSERT INTO article_content (article_id, title, text, url, url_to_image, category, category_specified) VALUES (?,?,?,?,?,?,?) ON CONFLICT (article_id) DO NOTHING;";
                        jdbcTemplate.update(sql, article.getId(), article.getTitle(), contentString, url, article.getUrlToImage(), article.getCategory(), article.getCategorySpecified());
                    } else if (article.getName().equals("Hollywood Reporter")) {
                        String url = article.getUrl();
                        Document doc = Jsoup.connect(url).get();
                        Elements pElements = doc.select(".a-content p");
                        StringBuilder content = new StringBuilder();
                        for (Element paragraph : pElements) {
                            if (!paragraph.text().contains("Click here to subscribe.")) {
                                content.append("<p>" + paragraph.text() + "</p>" + System.lineSeparator() + System.lineSeparator());
                            }
                        }
                        String contentString = content.toString();
                        String sql = "INSERT INTO article_content (article_id, title, text, url, url_to_image, category, category_specified) VALUES (?,?,?,?,?,?,?) ON CONFLICT (article_id) DO NOTHING;";
                        jdbcTemplate.update(sql, article.getId(), article.getTitle(), contentString, url, article.getUrlToImage(), article.getCategory(), article.getCategorySpecified());
                    } else if (article.getName().equals("Eonline.com")) {
                        String url = article.getUrl();
                        Document doc = Jsoup.connect(url).get();
                        Elements pElements = doc.select(".article-detail__text-only p");
                        StringBuilder content = new StringBuilder();
                        for (Element paragraph : pElements) {
                            content.append("<p>" + paragraph.text() + "</p>" + System.lineSeparator() + System.lineSeparator());
                        }
                        String contentString = content.toString();
                        String sql = "INSERT INTO article_content (article_id, title, text, url, url_to_image, category, category_specified) VALUES (?,?,?,?,?,?,?) ON CONFLICT (article_id) DO NOTHING;";
                        jdbcTemplate.update(sql, article.getId(), article.getTitle(), contentString, url, article.getUrlToImage(), article.getCategory(), article.getCategorySpecified());
                    } else if (article.getName().equals("Entertainment Weekly")) {
                        String url = article.getUrl();
                        Document doc = Jsoup.connect(url).get();
                        Elements pElements = doc.select(".articleContainer__content p");
                        Elements backupPElements = doc.select(".longformContent p");
                        Elements backupH3Elements = doc.select("h3");
                        Elements h3Elements = doc.select(".articleContainer__content h3");
                        Elements images = doc.select(".lazy-image div");
                        StringBuilder content = new StringBuilder();
                        for (Element paragraph : pElements) {
                            content.append(paragraph.html() + System.lineSeparator() + System.lineSeparator());
                            for (Element image : images) {
                                if (image.previousElementSibling() == paragraph) {
                                    content.append(image.html() + System.lineSeparator() + System.lineSeparator());
                                }
                            }
                            for (Element h3 : h3Elements) {
                                if (h3.previousElementSibling() == paragraph) {
                                    content.append("<h3>" + h3.text() + "</h3>" + System.lineSeparator() + System.lineSeparator());
                                }
                            }
                        }
                        for (Element paragraph : backupPElements) {
                            content.append(paragraph.html() + System.lineSeparator() + System.lineSeparator());
                            for (Element image : images) {
                                content.append(image.html() + System.lineSeparator() + System.lineSeparator());
                            }
                            for (Element h3 : backupH3Elements) {
                                content.append("<h3>" + h3.text() + "</h3>" + System.lineSeparator() + System.lineSeparator());
                            }
                        }
                        String contentString = content.toString();
                        String sql = "INSERT INTO article_content (article_id, title, text, url, url_to_image, category, category_specified) VALUES (?,?,?,?,?,?,?) ON CONFLICT (article_id) DO NOTHING;";
                        jdbcTemplate.update(sql, article.getId(), article.getTitle(), contentString, url, article.getUrlToImage(), article.getCategory(), article.getCategorySpecified());
                    }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void insertMusicContent() {
        List<Article> articles = articleDao.getMusicArticles();


        for (Article article : articles) {
            try {
                if (article.getName().equals("Variety")) {
                    String url = article.getUrl();
                    Document doc = Jsoup.connect(url).get();
                    Elements pElements = doc.select(".vy-cx-page-content p");
                    Elements backupPElements = doc.select(".a-content p");
                    Elements imageElements = doc.select(".post-content-image div");
                    Elements h4elements = doc.select("h4");
                    StringBuilder content = new StringBuilder();
                    for (Element paragraph : pElements) {
                        if (!paragraph.text().contains("Listen to “")){
                            if (paragraph.html().contains("<strong>")) {
                                content.append("<p>" + "<strong>" + paragraph.text() + "</strong>" + "</p>" + System.lineSeparator() + System.lineSeparator());
                            }
                        if (!paragraph.html().contains("<strong")) {
                            content.append("<p>" + paragraph.text() + "</p>" + System.lineSeparator() + System.lineSeparator());
                        }
                        for (Element h4 : h4elements) {
                            if (h4.previousElementSibling() == paragraph) {
                                content.append("h4" + h4.text() + "</h4>");
                            }
                        }
                        for (Element img : imageElements) {
                            if (img.previousElementSibling() == paragraph) {
                                content.append(img.html());
                            }
                        }
                    }
                    }
                    for (Element paragraph : backupPElements) {
                        if (!paragraph.text().contains("Listen to “")) {
                            if (paragraph.html().contains("<strong>")) {
                                content.append("<p>" + "<strong>" + paragraph.text() + "</strong>" + "</p>" + System.lineSeparator() + System.lineSeparator());
                            }
                            if (!paragraph.html().contains("<strong")) {
                                content.append("<p>" + paragraph.text() + "</p>" + System.lineSeparator() + System.lineSeparator());
                            }
                            for (Element h4 : h4elements) {
                                if (h4.previousElementSibling() == paragraph) {
                                    content.append("h4" + h4.text() + "</h4>");
                                }
                            }
                            for (Element img : imageElements) {
                                if (img.previousElementSibling() == paragraph) {
                                    content.append(img.html());
                                }
                            }
                        }
                    }
                    String contentString = content.toString();
                    String sql = "INSERT INTO article_content (article_id, title, text, url, url_to_image, category, category_specified) VALUES (?,?,?,?,?,?,?) ON CONFLICT (article_id) DO NOTHING;";
                    jdbcTemplate.update(sql, article.getId(), article.getTitle(), contentString, url, article.getUrlToImage(), article.getCategory(), article.getCategorySpecified());
                } else if (article.getName().equals("Hollywood Reporter")) {
                    String url = article.getUrl();
                    Document doc = Jsoup.connect(url).get();
                    Elements pElements = doc.select(".a-content p");
                    StringBuilder content = new StringBuilder();
                    for (Element paragraph : pElements) {
                        if (!paragraph.text().contains("Click here to subscribe.")) {
                            content.append("<p>" + paragraph.text() + "</p>" + System.lineSeparator() + System.lineSeparator());
                        }
                    }
                    String contentString = content.toString();
                    String sql = "INSERT INTO article_content (article_id, title, text, url, url_to_image, category, category_specified) VALUES (?,?,?,?,?,?,?) ON CONFLICT (article_id) DO NOTHING;";
                    jdbcTemplate.update(sql, article.getId(), article.getTitle(), contentString, url, article.getUrlToImage(), article.getCategory(), article.getCategorySpecified());
                } else if (article.getName().equals("Hip-Hop Wired")) {
                    String articleUrl = article.getUrl();
                    Document doc = Jsoup.connect(articleUrl).get();
                    Elements pElements = doc.select(".post-content p");
                    Elements h3Elements = doc.select("h3");
                    StringBuilder content = new StringBuilder();
                    for (Element paragraph : pElements) {
                        if (!paragraph.text().contains("RELATED TAGS")) {
                            if(paragraph.html().contains("<em>")){
                                content.append("<p>" + "<em>" + paragraph.text() + "</p>" + "</em>" + System.lineSeparator() + System.lineSeparator());
                            } else {
                                content.append("<p>" + paragraph.text() + "</p>" + System.lineSeparator() + System.lineSeparator());
                            }
                        }
                        for (Element h3 : h3Elements) {
                            if (h3.previousElementSibling() == paragraph) {
                                content.append("<h3>" + h3.text() + "</h3>" + System.lineSeparator() + System.lineSeparator());
                            }
                        }
                        Elements aTags = doc.select("a[href*=twitter.com]");
                        for (Element aTag : aTags) {
                            String tweetUrl = aTag.attr("href").split("\\?")[0];
                            String tweetRegex = "https://twitter.com/(?!(?:i\\/|.*timelines\\/))\\w+/status/\\d+";
                            if (tweetUrl.matches(tweetRegex)) {
                                // Construct the API URL with the tweet URL as a query parameter
                                String apiUrl = "https://publish.twitter.com/oembed?url=" + URLEncoder.encode(tweetUrl, "UTF-8");


                                URL url = new URL(apiUrl);
                                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                                conn.setRequestMethod("GET");


                                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                                String inputLine;
                                StringBuilder response = new StringBuilder();
                                while ((inputLine = in.readLine()) != null) {
                                    response.append(inputLine);
                                }
                                in.close();


                                JSONObject jsonResponse = new JSONObject(response.toString());
                                String html = jsonResponse.getString("html");

                                String blockquoteHtml = html.replaceAll("(?s)<blockquote[^>]*>((?:(?!</?script)[\\s\\S])*?)</blockquote>", "<blockquote class=\"twitter-tweet\">$1</blockquote>");


                                String removingScriptTags = blockquoteHtml.replaceAll("<script async src=\"https://platform.twitter.com/widgets.js\" charset=\"utf-8\"></script>", "");


                                if (blockquoteHtml != null) {
                                    content.append(removingScriptTags);
                                }

                            }
                        }
                    }
                    String contentString = content.toString();
                    String sql = "INSERT INTO article_content (article_id, title, text, url, url_to_image, category, category_specified) VALUES (?,?,?,?,?,?,?) ON CONFLICT (article_id) DO NOTHING;";
                    jdbcTemplate.update(sql, article.getId(), article.getTitle(), contentString, articleUrl, article.getUrlToImage(), article.getCategory(), article.getCategorySpecified());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void insertCenterContent() {
        List<Article> articles = articleDao.getCenterArticles();


        for (Article article : articles) {
            try {
                if (article.getName().equals("Zerohedge.com")) {
                    String articleUrl = article.getUrl();
                    Set<String> tweetUrls = new HashSet<>();
                    Document doc = Jsoup.connect(articleUrl).get();
                    Elements pElements = doc.select("p");
                    Elements imgElements = doc.select("img");
                    StringBuilder content = new StringBuilder();
                    for (Element paragraph : pElements) {
                        content.append("<p>" + paragraph.text() + "</p>" + System.lineSeparator() + System.lineSeparator());
                        for (Element img : imgElements) {
                            Element parentElement = img.parent();
                            if (parentElement.tagName().equals("figure")) {
                                content.append(img.html() + System.lineSeparator() + System.lineSeparator());
                            }
                        }
                        Elements aTags = doc.select("a[href*=twitter.com]");
                        for (Element aTag : aTags) {
                            String tweetUrl = aTag.attr("href").split("\\?")[0];
                            String tweetRegex = "https://twitter.com/(?!(?:i\\/|.*timelines\\/))\\w+/status/\\d+";
                            if (tweetUrl.matches(tweetRegex) && !tweetUrls.contains(tweetUrl)) {
                                // Construct the API URL with the tweet URL as a query parameter
                                String apiUrl = "https://publish.twitter.com/oembed?url=" + URLEncoder.encode(tweetUrl, "UTF-8");
                                URL url = new URL(apiUrl);
                                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                                conn.setRequestMethod("GET");
                                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                                String inputLine;
                                StringBuilder response = new StringBuilder();
                                while ((inputLine = in.readLine()) != null) {
                                    response.append(inputLine);
                                }
                                in.close();
                                JSONObject jsonResponse = new JSONObject(response.toString());
                                String html = jsonResponse.getString("html");
                                String blockquoteHtml = html.replaceAll("(?s)<blockquote[^>]*>((?:(?!</?script)[\\s\\S])*?)</blockquote>", "<blockquote class=\"twitter-tweet\">$1</blockquote>");
                                String removingScriptTags = blockquoteHtml.replaceAll("<script async src=\"https://platform.twitter.com/widgets.js\" charset=\"utf-8\"></script>", "");
                                if (blockquoteHtml != null) {
                                    content.append(removingScriptTags);
                                    tweetUrls.add(tweetUrl); // Add the tweet URL to the set
                                }
                            }
                        }
                    }
                    String contentString = content.toString();
                    String sql = "INSERT INTO article_content (article_id, title, text, url, url_to_image, category, category_specified) VALUES (?,?,?,?,?,?,?) ON CONFLICT (article_id) DO NOTHING;";
                    jdbcTemplate.update(sql, article.getId(), article.getTitle(), contentString, articleUrl, article.getUrlToImage(), article.getCategory(), article.getCategorySpecified());
                } else if (article.getName().equals("The Wall Street Journal")) {
                    String url = article.getUrl();
                    Document doc = Jsoup.connect(url).get();
                    Elements paragraphs = doc.select("p");
                    StringBuilder content = new StringBuilder();
                    content.append("You need to Subscribe to the WSJ in order to view this content. If you have a subscription feel free to click the link above to read the article, and make sure to come right back and drop a comment down below!" + System.lineSeparator() + System.lineSeparator());
                    String contentString = content.toString();
                    String sql = "INSERT INTO article_content (article_id, title, text, url, url_to_image, category, category_specified) VALUES (?,?,?,?,?,?,?) ON CONFLICT (article_id) DO NOTHING;";
                    jdbcTemplate.update(sql, article.getId(), article.getTitle(), contentString, url, article.getUrlToImage(), article.getCategory(), article.getCategorySpecified());
                } else if (article.getName().equals("Axios")) {
                    String url = article.getUrl();
                    Document doc = Jsoup.connect(url).get();
                    Elements pElements = doc.select(".DraftjsBlocks_draftjs__jDHy3 p");
                    Elements ulAfterPElements = doc.select("ul");
                    StringBuilder content = new StringBuilder();
                    for (Element paragraph : pElements) {
                        content.append("<p>" + paragraph.text() + "</p>" + System.lineSeparator() + System.lineSeparator());
                        for (Element ulAfterPElement : ulAfterPElements){
                            Element previousElement = ulAfterPElement.previousElementSibling();
                            if(previousElement == paragraph){
                                content.append(ulAfterPElement.html());
                            }
                        }
                    }
                    String contentString = content.toString();
                    String sql = "INSERT INTO article_content (article_id, title, text, url, url_to_image, category, category_specified) VALUES (?,?,?,?,?,?,?) ON CONFLICT (article_id) DO NOTHING;";
                    jdbcTemplate.update(sql, article.getId(), article.getTitle(), contentString, url, article.getUrlToImage(), article.getCategory(), article.getCategorySpecified());
                } else if (article.getName().equals("The Hill")) {
                    String url = article.getUrl();
                    Document doc = Jsoup.connect(url).get();
                    Elements pElements = doc.select(".article__text p");
                    Elements backUpElements = doc.select("ca-article__content p");
                    Elements h2ForbackUpElements = doc.select("ca-article__content h2");
                    Elements lineSeperators = doc.select("ca-article__content hr");
                    Elements ulForLineSeperators = doc.select("ul");
                    Elements relatedStories = doc.select("aside");
                    StringBuilder content = new StringBuilder();
                    for (Element paragraph : pElements) {
                        content.append("<p>" + paragraph.text() + "</p>" + System.lineSeparator() + System.lineSeparator());
                    }

                    for (Element h2 : h2ForbackUpElements) {
                        for(Element aside : relatedStories)
                        if (h2.previousElementSibling() == aside){
                            content.append("<h2>" + h2.text() + "</h2>" + System.lineSeparator() + System.lineSeparator());
                        for (Element lineSeperator : lineSeperators) {
                            if (lineSeperator.previousElementSibling() == h2) {
                                content.append(lineSeperator.html());
                                for (Element ul : ulForLineSeperators) {
                                    if (ul.previousElementSibling() == lineSeperator) {
                                        content.append(ul);
                                    }
                                }
                            }
                        }
                        for (Element paragraph : backUpElements) {
                            content.append("<p>" + paragraph.text() + "</p>" + System.lineSeparator() + System.lineSeparator());
                        }
                    }
                    }

                    String contentString = content.toString();
                    String sql = "INSERT INTO article_content (article_id, title, text, url, url_to_image, category, category_specified) VALUES (?,?,?,?,?,?,?) ON CONFLICT (article_id) DO NOTHING;";
                    jdbcTemplate.update(sql, article.getId(), article.getTitle(), contentString, url, article.getUrlToImage(), article.getCategory(), article.getCategorySpecified());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void insertLeftContent() {
        List<Article> articles = articleDao.getLeftWingArticles();


        for (Article article : articles) {
            try {
                if (article.getName().equals("Politico")) {
                    String url = article.getUrl();
                    Document doc = Jsoup.connect(url).get();
                    Elements pElements = doc.select(".story-text p");
                    Elements photosForStory = doc.select(".story-photo figure");
                    StringBuilder content = new StringBuilder();
                    for (Element paragraph : pElements) {
                        if(!paragraph.text().contains("Link Copied") && !paragraph.text().contains("/AP Photo")){
                            content.append("<p>" + paragraph.text() + "</p>" + System.lineSeparator() + System.lineSeparator());
                        }
                        for(Element photo:photosForStory){

                            content.append(photo.html());

                        }
                    }
                    String contentString = content.toString();
                    String sql = "INSERT INTO article_content (article_id, title, text, url, url_to_image, category, category_specified) VALUES (?,?,?,?,?,?,?) ON CONFLICT (article_id) DO NOTHING;";
                    jdbcTemplate.update(sql, article.getId(), article.getTitle(), contentString, url, article.getUrlToImage(), article.getCategory(), article.getCategorySpecified());
                } else if (article.getName().equals("Slate Magazine")) {
                    String articleUrl = article.getUrl();
                    Document doc = Jsoup.connect(articleUrl).get();
                    Elements pElements = doc.select(".article__content p");
                    Elements h3Elements = doc.select("h3");
                    Set<String> tweetUrls = new HashSet<>();
                    StringBuilder content = new StringBuilder();
                    for (Element paragraph : pElements) {
                        content.append("<p>" + paragraph.text() + "</p>" + System.lineSeparator() + System.lineSeparator());
                        for (Element h3 : h3Elements) {
                            if (h3.previousElementSibling() == paragraph) {
                                content.append("<h3>" + h3.text() + "</h3>" + System.lineSeparator() + System.lineSeparator());
                            }
                        }
                        Elements aTags = doc.select("a[href*=twitter.com]");
                        for (Element aTag : aTags) {
                            String tweetUrl = aTag.attr("href").split("\\?")[0];
                            String tweetRegex = "https://twitter.com/(?!(?:i\\/|.*timelines\\/))\\w+/status/\\d+";
                            if (tweetUrl.matches(tweetRegex) && !tweetUrls.contains(tweetUrl)) {
                                // Construct the API URL with the tweet URL as a query parameter
                                String apiUrl = "https://publish.twitter.com/oembed?url=" + URLEncoder.encode(tweetUrl, "UTF-8");
                                URL url = new URL(apiUrl);
                                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                                conn.setRequestMethod("GET");
                                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                                String inputLine;
                                StringBuilder response = new StringBuilder();
                                while ((inputLine = in.readLine()) != null) {
                                    response.append(inputLine);
                                }
                                in.close();
                                JSONObject jsonResponse = new JSONObject(response.toString());
                                String html = jsonResponse.getString("html");
                                String blockquoteHtml = html.replaceAll("(?s)<blockquote[^>]*>((?:(?!</?script)[\\s\\S])*?)</blockquote>", "<blockquote class=\"twitter-tweet\">$1</blockquote>");
                                String removingScriptTags = blockquoteHtml.replaceAll("<script async src=\"https://platform.twitter.com/widgets.js\" charset=\"utf-8\"></script>", "");
                                if (blockquoteHtml != null) {
                                    content.append(removingScriptTags);
                                    tweetUrls.add(tweetUrl); // Add the tweet URL to the set
                                }
                            }
                        }
                    }
                    String contentString = content.toString();
                    String sql = "INSERT INTO article_content (article_id, title, text, url, url_to_image, category, category_specified) VALUES (?,?,?,?,?,?,?) ON CONFLICT (article_id) DO NOTHING;";
                    jdbcTemplate.update(sql, article.getId(), article.getTitle(), contentString, articleUrl, article.getUrlToImage(), article.getCategory(), article.getCategorySpecified());
                } else if (article.getName().equals("Associated Press")) {
                    String url = article.getUrl();
                    Document doc = Jsoup.connect(url).get();
                    Elements pElements = doc.select(".Article p");
                    StringBuilder content = new StringBuilder();
                    for (Element paragraph : pElements) {
                        content.append("<p>" + paragraph.text() + "</p>" + System.lineSeparator() + System.lineSeparator());
                    }
                    String contentString = content.toString();
                    String sql = "INSERT INTO article_content (article_id, title, text, url, url_to_image, category, category_specified) VALUES (?,?,?,?,?,?,?) ON CONFLICT (article_id) DO NOTHING;";
                    jdbcTemplate.update(sql, article.getId(), article.getTitle(), contentString, url, article.getUrlToImage(), article.getCategory(), article.getCategorySpecified());
                } else if (article.getName().equals("Time")) {
                    String url = article.getUrl();
                    Document doc = Jsoup.connect(url).get();
                    Elements pElements = doc.select("#article-body p");
                    StringBuilder content = new StringBuilder();
                    for (Element paragraph : pElements) {

                        if(!paragraph.text().contains("@time") && !paragraph.text().contains("Follow this link to learn more") && !paragraph.text().contains("Read More")) {
                            content.append("<p>" + paragraph.text() + "</p>" + System.lineSeparator() + System.lineSeparator());
                        }
                    }
                    String contentString = content.toString();
                    String sql = "INSERT INTO article_content (article_id, title, text, url, url_to_image, category, category_specified) VALUES (?,?,?,?,?,?,?) ON CONFLICT (article_id) DO NOTHING;";
                    jdbcTemplate.update(sql, article.getId(), article.getTitle(), contentString, url, article.getUrlToImage(), article.getCategory(), article.getCategorySpecified());
                } else if (article.getName().equals("The New Yorker")) {
                    String url = article.getUrl();
                    Document doc = Jsoup.connect(url).get();
                    Elements pElements = doc.select("#article-body p");
                    StringBuilder content = new StringBuilder();
                    for (Element paragraph : pElements) {
                        content.append("<p>" + paragraph.text() + "</p>" + System.lineSeparator() + System.lineSeparator());
                    }
                    String contentString = content.toString();
                    String sql = "INSERT INTO article_content (article_id, title, text, url, url_to_image, category, category_specified) VALUES (?,?,?,?,?,?,?) ON CONFLICT (article_id) DO NOTHING;";
                    jdbcTemplate.update(sql, article.getId(), article.getTitle(), contentString, url, article.getUrlToImage(), article.getCategory(), article.getCategorySpecified());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    public void insertRightContent() {
        List<Article> articles = articleDao.getRightWingArticles();


        for (Article article : articles) {
            try {
                if (article.getName().equals("Thegatewaypundit.com")) {
                    String articleUrl = article.getUrl();
                    Document doc = Jsoup.connect(articleUrl).get();
                    Elements pElements = doc.select(".entry-content p");
                    Elements imgElements = doc.select("img");
                    Set<String> tweetUrls = new HashSet<>();
                    StringBuilder content = new StringBuilder();
                    for (Element paragraph : pElements) {
                        content.append("<p>" + paragraph.text() + "</p>" + System.lineSeparator() + System.lineSeparator());
                        for (Element img : imgElements) {
                            Element parentElement = img.parent();
                            if (parentElement.tagName().equals("p")) {
                                content.append(img.html() + System.lineSeparator() + System.lineSeparator());
                            }
                        }
                        Elements aTags = doc.select("a[href*=twitter.com]");
                        for (Element aTag : aTags) {
                            String tweetUrl = aTag.attr("href").split("\\?")[0];
                            String tweetRegex = "https://twitter.com/(?!(?:i\\/|.*timelines\\/))\\w+/status/\\d+";
                            if (tweetUrl.matches(tweetRegex) && !tweetUrls.contains(tweetUrl)) {
                                // Construct the API URL with the tweet URL as a query parameter
                                String apiUrl = "https://publish.twitter.com/oembed?url=" + URLEncoder.encode(tweetUrl, "UTF-8");
                                URL url = new URL(apiUrl);
                                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                                conn.setRequestMethod("GET");
                                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                                String inputLine;
                                StringBuilder response = new StringBuilder();
                                while ((inputLine = in.readLine()) != null) {
                                    response.append(inputLine);
                                }
                                in.close();
                                JSONObject jsonResponse = new JSONObject(response.toString());
                                String html = jsonResponse.getString("html");
                                String blockquoteHtml = html.replaceAll("(?s)<blockquote[^>]*>((?:(?!</?script)[\\s\\S])*?)</blockquote>", "<blockquote class=\"twitter-tweet\">$1</blockquote>");
                                String removingScriptTags = blockquoteHtml.replaceAll("<script async src=\"https://platform.twitter.com/widgets.js\" charset=\"utf-8\"></script>", "");
                                if (blockquoteHtml != null) {
                                    content.append(removingScriptTags);
                                    tweetUrls.add(tweetUrl); // Add the tweet URL to the set
                                }
                            }
                        }
                    }
                    String contentString = content.toString();
                String sql = "INSERT INTO article_content (article_id, title, text, url, url_to_image, category, category_specified) VALUES (?,?,?,?,?,?,?) ON CONFLICT (article_id) DO NOTHING;";
                    jdbcTemplate.update(sql, article.getId(), article.getTitle(), contentString, articleUrl, article.getUrlToImage(), article.getCategory(), article.getCategorySpecified());
                } else if (article.getName().equals("Zerohedge.com")) {
                    String url = article.getUrl();
                    Document doc = Jsoup.connect(url).get();
                    Elements pElements = doc.select(".NodeContent_body__6iJOI p");
                    StringBuilder content = new StringBuilder();
                    for (Element paragraph : pElements) {
                        if(!paragraph.text().contains("Assistance and Requests:") && !paragraph.text().contains("@zerohedge.com") && !paragraph.text().contains("Cookie Policy")){
                            content.append("<p>" + paragraph.text() + "</p>" + System.lineSeparator() + System.lineSeparator());
                        }
                    }
                    String contentString = content.toString();
                    String sql = "INSERT INTO article_content (article_id, title, text, url, url_to_image, category, category_specified) VALUES (?,?,?,?,?,?,?) ON CONFLICT (article_id) DO NOTHING;";
                    jdbcTemplate.update(sql, article.getId(), article.getTitle(), contentString, url, article.getUrlToImage(), article.getCategory(), article.getCategorySpecified());
                } else if (article.getName().equals("The Federalist")) {
                    String articleUrl = article.getUrl();
                    Document doc = Jsoup.connect(articleUrl).get();
                    Set<String> tweetUrls = new HashSet<>();
                    Elements pElements = doc.select(".article-content p");
                    Elements ptagBackup = doc.select("p");
                    Elements imgElements = doc.select("img");
                    Elements youtubeVids = doc.select("iframe");
                    StringBuilder content = new StringBuilder();
                    for (Element paragraph : ptagBackup) {
                        if(!paragraph.text().contains("All Rights Reserved") && !paragraph.text().contains("Join Now") && !paragraph.text().contains("Free Trial") && !paragraph.text().contains("[READ:") && !paragraph.text().contains("FREE TRIAL"))
                        content.append("<p>" + paragraph.text() + "</p>" + System.lineSeparator() + System.lineSeparator());
                        for (Element img : imgElements) {
                            Element parentElement = img.parent();
                            if (parentElement.tagName().equals("figure")) {
                                content.append(img.html() + System.lineSeparator() + System.lineSeparator());
                            }
                        }
                        for (Element youtube : youtubeVids){
                            content.append(youtube.html());
                        }
                        Elements aTags = doc.select("a[href*=twitter.com]");
                        for (Element aTag : aTags) {
                            String tweetUrl = aTag.attr("href").split("\\?")[0];
                            String tweetRegex = "https://twitter.com/(?!(?:i\\/|.*timelines\\/))\\w+/status/\\d+";
                            if (tweetUrl.matches(tweetRegex) && !tweetUrls.contains(tweetUrl)) {
                                // Construct the API URL with the tweet URL as a query parameter
                                String apiUrl = "https://publish.twitter.com/oembed?url=" + URLEncoder.encode(tweetUrl, "UTF-8");
                                URL url = new URL(apiUrl);
                                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                                conn.setRequestMethod("GET");
                                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                                String inputLine;
                                StringBuilder response = new StringBuilder();
                                while ((inputLine = in.readLine()) != null) {
                                    response.append(inputLine);
                                }
                                in.close();
                                JSONObject jsonResponse = new JSONObject(response.toString());
                                String html = jsonResponse.getString("html");
                                String blockquoteHtml = html.replaceAll("(?s)<blockquote[^>]*>((?:(?!</?script)[\\s\\S])*?)</blockquote>", "<blockquote class=\"twitter-tweet\">$1</blockquote>");
                                String removingScriptTags = blockquoteHtml.replaceAll("<script async src=\"https://platform.twitter.com/widgets.js\" charset=\"utf-8\"></script>", "");
                                if (blockquoteHtml != null) {
                                    content.append(removingScriptTags);
                                    tweetUrls.add(tweetUrl); // Add the tweet URL to the set
                                }
                            }
                        }
                    }
                    for (Element paragraph : pElements) {
                        if(!paragraph.text().contains("All Rights Reserved") && !paragraph.text().contains("Join Now") && !paragraph.text().contains("Free Trial") && !paragraph.text().contains("[READ:") && !paragraph.text().contains("FREE TRIAL"))
                        content.append("<p>" + paragraph.text() + "</p>" + System.lineSeparator() + System.lineSeparator());
                        for (Element img : imgElements) {
                            Element parentElement = img.parent();
                            if (parentElement.tagName().equals("figure")) {
                                content.append(img.html() + System.lineSeparator() + System.lineSeparator());
                            }
                        }
                        for (Element youtube : youtubeVids){
                            content.append(youtube.html());
                        }
                        Elements aTags = doc.select("a[href*=twitter.com]");
                        for (Element aTag : aTags) {
                            String tweetUrl = aTag.attr("href").split("\\?")[0];
                            String tweetRegex = "https://twitter.com/(?!(?:i\\/|.*timelines\\/))\\w+/status/\\d+";
                            if (tweetUrl.matches(tweetRegex) && !tweetUrls.contains(tweetUrl)) {
                                // Construct the API URL with the tweet URL as a query parameter
                                String apiUrl = "https://publish.twitter.com/oembed?url=" + URLEncoder.encode(tweetUrl, "UTF-8");
                                URL url = new URL(apiUrl);
                                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                                conn.setRequestMethod("GET");
                                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                                String inputLine;
                                StringBuilder response = new StringBuilder();
                                while ((inputLine = in.readLine()) != null) {
                                    response.append(inputLine);
                                }
                                in.close();
                                JSONObject jsonResponse = new JSONObject(response.toString());
                                String html = jsonResponse.getString("html");
                                String blockquoteHtml = html.replaceAll("(?s)<blockquote[^>]*>((?:(?!</?script)[\\s\\S])*?)</blockquote>", "<blockquote class=\"twitter-tweet\">$1</blockquote>");
                                String removingScriptTags = blockquoteHtml.replaceAll("<script async src=\"https://platform.twitter.com/widgets.js\" charset=\"utf-8\"></script>", "");
                                if (blockquoteHtml != null) {
                                    content.append(removingScriptTags);
                                    tweetUrls.add(tweetUrl); // Add the tweet URL to the set
                                }
                            }
                        }
                    }
                    String contentString = content.toString();
                    String sql = "INSERT INTO article_content (article_id, title, text, url, url_to_image, category, category_specified) VALUES (?,?,?,?,?,?,?) ON CONFLICT (article_id) DO NOTHING;";
                    jdbcTemplate.update(sql, article.getId(), article.getTitle(), contentString, articleUrl, article.getUrlToImage(), article.getCategory(), article.getCategorySpecified());
                } else if (article.getName().equals("New York Post")) {
                    String url = article.getUrl();
                    Document doc = Jsoup.connect(url).get();
                    Elements pElements = doc.select(".single__content p");
                    Elements imgElements = doc.select("img");
                    StringBuilder content = new StringBuilder();
                    for (Element paragraph : pElements) {
                        content.append("<p>" + paragraph.text() + "</p>" + System.lineSeparator() + System.lineSeparator());
                        for (Element img : imgElements) {
                            Element previouselement = img.previousElementSibling();
                            if (previouselement != null && previouselement.tagName() != null && previouselement.tagName().equals("button")) {
                                content.append(img.html() + System.lineSeparator() + System.lineSeparator());
                            }
                        }

                    }
                    String contentString = content.toString();
                    String sql = "INSERT INTO article_content (article_id, title, text, url, url_to_image, category, category_specified) VALUES (?,?,?,?,?,?,?) ON CONFLICT (article_id) DO NOTHING;";
                    jdbcTemplate.update(sql, article.getId(), article.getTitle(), contentString, url, article.getUrlToImage(), article.getCategory(), article.getCategorySpecified());
                } else if (article.getName().equals("The American Conservative")) {
                    String url = article.getUrl();
                    Document doc = Jsoup.connect(url).get();
                    Elements pElements = doc.select(".c-blog-post__content p");
                    StringBuilder content = new StringBuilder();
                    for (Element paragraph : pElements) {
                        content.append("<p>" + paragraph.text() + "</p>" + System.lineSeparator() + System.lineSeparator());
                    }
                    String contentString = content.toString();
                    String sql = "INSERT INTO article_content (article_id, title, text, url, url_to_image, category, category_specified) VALUES (?,?,?,?,?,?,?) ON CONFLICT (article_id) DO NOTHING;";
                    jdbcTemplate.update(sql, article.getId(), article.getTitle(), contentString, url, article.getUrlToImage(), article.getCategory(), article.getCategorySpecified());
                } else if (article.getName().equals("Reason")) {
                    String url = article.getUrl();
                    Document doc = Jsoup.connect(url).get();
                    Elements pElements = doc.select(".entry-content p");
                    Elements h2Elements = doc.select("h2");
                    StringBuilder content = new StringBuilder();
                    for (Element paragraph : pElements) {
                        content.append("<p>" + paragraph.text() + "</p>" + System.lineSeparator() + System.lineSeparator());
                        for (Element h2 : h2Elements) {
                            if (h2.previousElementSibling() == paragraph) {
                                content.append("<h2>" + h2.text() + "</h2>" + System.lineSeparator() + System.lineSeparator());
                            }
                        }
                    }
                    String contentString = content.toString();
                    String sql = "INSERT INTO article_content (article_id, title, text, url, url_to_image, category, category_specified) VALUES (?,?,?,?,?,?,?) ON CONFLICT (article_id) DO NOTHING;";
                    jdbcTemplate.update(sql, article.getId(), article.getTitle(), contentString, url, article.getUrlToImage(), article.getCategory(), article.getCategorySpecified());
                } else if (article.getName().equals("TheBlaze")) {
                    String articleUrl = article.getUrl();
                    Document doc = Jsoup.connect(articleUrl).get();
                    Elements pElements = doc.select(".post-body p");
                    StringBuilder content = new StringBuilder();
                    for (Element paragraph : pElements) {
                        if(!paragraph.text().contains("Sign up")) {
                            content.append("<p>" + paragraph.text() + "</p>" + System.lineSeparator() + System.lineSeparator());
                            Elements aTags = paragraph.select("a[href*=twitter.com]");
                            for (Element aTag : aTags) {
                                String tweetUrl = aTag.attr("href").split("\\?")[0];
                                String tweetRegex = "https://twitter.com/[a-zA-Z0-9_]+/status/\\d+";
                                if (tweetUrl.matches(tweetRegex)) {
                                    // Construct the API URL with the tweet URL as a query parameter
                                    String apiUrl = "https://publish.twitter.com/oembed?url=" + URLEncoder.encode(tweetUrl, "UTF-8");

                                    URL url = new URL(apiUrl);
                                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                                    conn.setRequestMethod("GET");

                                    BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                                    String inputLine;
                                    StringBuilder response = new StringBuilder();
                                    while ((inputLine = in.readLine()) != null) {
                                        response.append(inputLine);
                                    }
                                    in.close();

                                    JSONObject jsonResponse = new JSONObject(response.toString());
                                    String html = jsonResponse.getString("html");

                                    String blockquoteHtml = html.replaceAll("(?s)<blockquote[^>]*>((?:(?!</?script)[\\s\\S])*?)</blockquote>", "<blockquote class=\"twitter-tweet\">$1</blockquote>");


                                    String removingScriptTags = blockquoteHtml.replaceAll("<script async src=\"https://platform.twitter.com/widgets.js\" charset=\"utf-8\"></script>", "");


                                    if (blockquoteHtml != null) {
                                        content.append(removingScriptTags);
                                    }

                                }
                            }
                        }
                    }
                    String contentString = content.toString();
                    String sql = "INSERT INTO article_content (article_id, title, text, url, url_to_image, category, category_specified) VALUES (?,?,?,?,?,?,?) ON CONFLICT (article_id) DO NOTHING;";
                    jdbcTemplate.update(sql, article.getId(), article.getTitle(), contentString, articleUrl, article.getUrlToImage(), article.getCategory(), article.getCategorySpecified());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
